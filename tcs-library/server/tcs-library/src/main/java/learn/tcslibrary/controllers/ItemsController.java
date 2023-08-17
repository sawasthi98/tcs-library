package learn.tcslibrary.controllers;

//import learn.tcslibrary.domain.ItemService;
import learn.tcslibrary.models.Item;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

@RestController
@RequestMapping("/tcslibrary/huckfinn")
@CrossOrigin
public class ItemsController {

    @GetMapping
    public ResponseEntity<Object> findByTitle() throws IOException {
        String document = "jane-austen_pride-and-prejudice_advanced.pdf";
        // Replace spaces with %20
        document = document.replaceAll(" ", "%20");

        String fetchUrl = "https://archive.org/download/jane-austen_pride-and-prejudice_202302/" + document;

//        try {
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
//            headers.setContentDisposition(ContentDisposition.parse("inline; filename=\""));
            inputStream.close();
            return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);

            // return bytearray in response entity
            // get to postman before react
            // accept app/pdf
//        } catch (Exception ex) {
//            System.out.println(ex);
//        }

//        return null;
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
