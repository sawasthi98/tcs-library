package learn.tcslibrary.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import learn.tcslibrary.domain.AppUserService;
import learn.tcslibrary.domain.ItemService;
import learn.tcslibrary.domain.ItemShelfService;
import learn.tcslibrary.domain.Result;
import learn.tcslibrary.models.AppUser;
import learn.tcslibrary.models.Item;
import learn.tcslibrary.models.ItemShelf;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/tcslibrary")
@CrossOrigin
public class ItemsController {

    private ItemService itemService;
    private ItemShelfService itemShelfService;
    private UserDetailsService userDetailsService;
    private AppUserService appUserService;

    public ItemsController(ItemService itemService, ItemShelfService itemShelfService, UserDetailsService userDetailsService, AppUserService appUserService) {
        this.itemService = itemService;
        this.itemShelfService = itemShelfService;
        this.userDetailsService = userDetailsService;
        this.appUserService = appUserService;
    }

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
    }

    @GetMapping("/search/{title}")
    public ResponseEntity<Object> searchInternetArchive(@PathVariable String title) throws IOException {
        title = title.replaceAll(" ", "%20");

        String fetchUrl = "https://archive.org/advancedsearch.php?q=" + title + "%20pdf&output=json";
        // returns all listings

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
                List<String> files = new ArrayList<>();
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
                // Create metadata object and add it to the list
                metadataList.add(new Item(titleOfSearch,identifier,description,subject,filename,imgLink));

                idx++;
                if (idx > 8) { // grabbed the first 9 listings
                    break;
                }
        }

        // Return the metadata list
        return new ResponseEntity<>(metadataList, HttpStatus.OK);
    } catch (Exception ex) {
        System.out.println(ex);
    }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/reading-item/{identifier}/filename/{filename}/page")
    public ResponseEntity<?> loadPageNumber(@PathVariable String identifier, @PathVariable String filename) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println("Authentication: " + authentication);
        String username1 = (String) authentication.getPrincipal();
//        System.out.println("Username: " + username1);

        //String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser user = appUserService.loadUserByUsername(username1);
        Item item = itemService.findByIdentifier(identifier);

        ItemShelf shelfItem = itemShelfService.findByAppUserIdAndItemId(user, item.getItemId());

        return new ResponseEntity<>(shelfItem, HttpStatus.OK);
    }

    @PutMapping("/reading-item/{identifier}")
    public ResponseEntity<?> updatePageNumber(@PathVariable String identifier, @RequestBody HashMap<String, Integer> pageNumber) {

        Item item = itemService.findByIdentifier(identifier);
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //find user ID by user name
        AppUser user = appUserService.loadUserByUsername(username);

        Integer number = pageNumber.get("pageNumber");

        Result updatedPageNumber = itemShelfService.updatePageNumber(user.getAppUserId(),item.getItemId(),number);

        if (updatedPageNumber.isSuccess()) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/my-bookshelf")
    public ResponseEntity<?> getPersonalBookshelf() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //find user ID by user name
        AppUser user = appUserService.loadUserByUsername(username);

        // service find by app user id
        List<ItemShelf> bookshelf = itemShelfService.findByAppUserId(user.getAppUserId());

        if (bookshelf != null) {
            return new ResponseEntity<>(bookshelf, HttpStatus.OK);
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
