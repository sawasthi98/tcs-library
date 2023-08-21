package learn.tcslibrary.models;

import java.util.List;

public class Item {

    private String title;
    private String internetArchiveIdentifier;
    private int itemId;
    private String description;
    private String subject;
    private String fileName;
    private String imgLink;
    public Item() {
    }

    public Item(String title, String internetArchiveIdentifier, String description, String subject, String filename, String imgLink) {
        this.title = title;
        this.internetArchiveIdentifier = internetArchiveIdentifier;
        this.description = description;
        this.subject = subject;
        this.fileName = filename;
        this.imgLink = imgLink;
    }

    public Item(String title, String internetArchiveIdentifier, int itemId, String description, String subject, String fileName, String imgLink) {
        this.title = title;
        this.internetArchiveIdentifier = internetArchiveIdentifier;
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

    public String getInternetArchiveIdentifier() {
        return internetArchiveIdentifier;
    }

    public void setInternetArchiveIdentifier(String internetArchiveIdentifier) {
        this.internetArchiveIdentifier = internetArchiveIdentifier;
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
