package learn.tcslibrary.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ItemsController {

    public static void main(String[] args) throws IOException {
        String document = "Adventures of Huckleberry Finn.pdf";
        String urlString = "https://archive.org/download/adventures-of-huckleberry-finn/" + document;
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        InputStream is = conn.getInputStream();

        System.out.println(url);
        System.out.println(is);
    }


}
