package learn.tcslibrary.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import learn.tcslibrary.data.ItemJdbcTemplateRepository;
import learn.tcslibrary.data.ItemRepository;
import learn.tcslibrary.data.ItemShelfRepository;
import learn.tcslibrary.domain.ItemService;
import learn.tcslibrary.domain.ItemShelfService;
import learn.tcslibrary.models.AppUser;
import learn.tcslibrary.models.Item;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/tcslibrary")
@CrossOrigin
public class ItemsController {

//    private ItemRepository itemRepository;
//    private ItemShelfRepository itemShelfRepository;

    private ItemService itemService;
    private ItemShelfService itemShelfService;
    private UserDetailsService userDetailsService;

    public ItemsController(ItemService itemService, ItemShelfService itemShelfService, UserDetailsService userDetailsService) {
        this.itemService = itemService;
        this.itemShelfService = itemShelfService;
        this.userDetailsService = userDetailsService;
    }
//    get recent page and here is token as login when clicked on
    // followup request for page number
//

    @GetMapping("/reading-item/{iaIdentifier}/filename/{filename}")
    public ResponseEntity<Object> getBookPdf(@PathVariable String iaIdentifier, @PathVariable String filename) throws IOException {

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser user = (AppUser) userDetailsService.loadUserByUsername(username);

        itemService.findOrCreate(iaIdentifier,filename);
        itemShelfService.findOrAddToShelf(iaIdentifier,user.getAppUserId());

        String document = filename;
        // Replace spaces with %20
        document = document.replaceAll(" ", "%20");

        String fetchUrl = "https://archive.org/download/" + iaIdentifier + "/" + document;
//        run through service method findOrCreate
//        if exists in database, fetch using identifier and document name
//        if does not exist, grab identifier based on which one was clicked as well as the file name ending in .pdf

        URL url = new URL(fetchUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setConnectTimeout(500000);
        conn.setReadTimeout(500000);
        conn.setRequestMethod("GET");

        InputStream inputStream = conn.getInputStream();
        byte[] pdfContent = readInputStream(inputStream); // Read PDF content from input stream

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        inputStream.close();

        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);

        // return bytearray in response entity
        // get to postman before react
        // accept app/pdf

        // Use a token to see who is making request
        // IA ID should be able to find the item in our item DB "find by IA ID"
        // Item shelf repo method find or create by IA ID and User ID

        // Pull user ID from token, pass the IA ID and User ID into service method that will find or create
        // item shelf, then we have page number

        // Might belong in its own method two sep methods, two sep fetches on page load
        // one to get PDF one to get last read page
    }

    @GetMapping("/search/{title}")
    public ResponseEntity<Object> searchInternetArchive(@PathVariable String title) throws IOException {
        title = title.replaceAll(" ", "%20");

        String fetchUrl = "https://archive.org/advancedsearch.php?q=" + title + "%20pdf&output=json";
        // returns all listings - need to limit to 6/9 however many

        try {
        URL url = new URL(fetchUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setConnectTimeout(500000);
        conn.setReadTimeout(500000);
        conn.setRequestMethod("GET");

        // Read response using BufferedReader
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        StringBuilder responseContent = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            responseContent.append(line);
        }
        reader.close();

        // Parse JSON response
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(responseContent.toString());


        // Process the metadata, for example:
        JsonNode items = jsonResponse.get("response").get("docs");
        List<Object> metadataList = new ArrayList<>();
        int idx = 0;
            for (JsonNode item : items) {
                String identifier = item.get("identifier").asText();
                String titleOfSearch = item.get("title").asText();
                String description = item.get("description").asText();
                String subject = item.get("subject").asText();
                String imgLink = "https://archive.org/services/img/" + identifier;

                String pdfFetch = "https://archive.org/details/" + identifier + "&output=json";

                URL urlPdf = new URL(pdfFetch);
                HttpURLConnection connPdf = (HttpURLConnection) urlPdf.openConnection();
                connPdf.setDoOutput(true);
                connPdf.setConnectTimeout(500000);
                connPdf.setReadTimeout(500000);
                connPdf.setRequestMethod("GET");

                // Read response using BufferedReader
                BufferedReader reader2 = new BufferedReader(new InputStreamReader(connPdf.getInputStream()));
                String line2;
                StringBuilder responseContent2 = new StringBuilder();
                while ((line2 = reader2.readLine()) != null) {
                    responseContent2.append(line2);
                }
                reader2.close();

                // Parse JSON response
                ObjectMapper objectMapper2 = new ObjectMapper();
                JsonNode jsonResponse2 = objectMapper2.readTree(responseContent2.toString());

                // loop through the items to find the ones that contain .pdf
                JsonNode listOfItems = jsonResponse2.get("files");
//                List<String> files = new ArrayList<>();
                String filename = null;
                Iterator<String> fieldNames = listOfItems.fieldNames();
                while (fieldNames.hasNext()) {
                    String fieldName = fieldNames.next();
                    if (fieldName.contains(".pdf")) {
                        if (fieldName.startsWith("/")) {
                            filename = fieldName.substring(1); // Remove the leading '/'
                        } else {
                            filename = fieldName;
                        }
                        break;
                    }
                }


                // Create your metadata object and add it to the list
                metadataList.add(new Item(titleOfSearch,identifier,description,subject,filename,imgLink));
                idx++;
                if (idx > 8) { // grabbed the first 9 listings
                    break;
                }

                // items saved in database
                // send to service findOrCreate

        }

            // send to front end
            // links go to read items

        // Return the metadata list
        return new ResponseEntity<>(metadataList, HttpStatus.OK);
    } catch (Exception ex) {
        System.out.println(ex);
    }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PutMapping("/reading-item/{identifier}")
    public ResponseEntity<?> updatePageNumber(@PathVariable String identifier, @RequestBody int pageNumber) {

        Item item = itemService.findByInternetArchiveId(identifier);
        AppUser user = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        boolean updatedPageNumber = itemShelfService.updatePageNumber(user.getAppUserId(),item.getItemId(),pageNumber);

        if (updatedPageNumber) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    private static byte[] readInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        return outputStream.toByteArray();
    }

    private static void sendPdfContent(byte[] pdfContent, String postUrl) throws IOException {
        URL url = new URL(postUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/pdf");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        conn.setRequestMethod("POST");

        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.write(pdfContent);
        wr.flush();
        wr.close();
    }
}
