package learn.tcslibrary.models;

import java.util.List;

public class Item {

    private int itemId;
    private String title;
    private String identifier;

    private String description;
    private String subject;
    private String fileName;
    private String imgLink;
    public Item() {
    }
//
//    public Item(int itemId, String identifier, String fileName) {
//        this.itemId = itemId;
//        this.identifier = identifier;
//        this.fileName = fileName;
//    }
//
//    public Item(String identifier, String fileName) {
//        this.identifier = identifier;
//        this.fileName = fileName;
//    }
//
//    public int getItemId() {
//        return itemId;
//    }
//
//    public void setItemId(int itemId) {
//        this.itemId = itemId;
//    }
//
//    public String getIdentifier() {
//        return identifier;
//    }
//
//    public void setIdentifier(String identifier) {
//        this.identifier = identifier;
//    }
//
//    public String getFileName() {
//        return fileName;
//    }
//
//    public void setFileName(String fileName) {
//        this.fileName = fileName;
//    }

        public Item(String title, String identifier, String description, String subject, String filename, String imgLink) {
        this.title = title;
        this.identifier = identifier;
        this.description = description;
        this.subject = subject;
        this.fileName = filename;
        this.imgLink = imgLink;
    }

    public Item(String title, String identifier, int itemId, String description, String subject, String fileName, String imgLink) {
        this.title = title;
        this.identifier = identifier;
        this.itemId = itemId;
        this.description = description;
        this.subject = subject;
        this.fileName = fileName;
        this.imgLink = imgLink;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }
}
