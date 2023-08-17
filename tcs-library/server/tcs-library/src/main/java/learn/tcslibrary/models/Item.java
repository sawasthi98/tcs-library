package learn.tcslibrary.models;

import java.util.List;

public class Item {

    private String title;
    private String author;
    private List<String> topic; // maybe if they want to search for a collection of specific genres?
//    private int pageAmount;
    private String internetArchiveIdentifier;
    private int itemId;
    private String description;
    private String subject;
    public Item() {
    }

    public Item(String title, String internetArchiveIdentifier, String description, String subject) {
        this.title = title;
        this.internetArchiveIdentifier = internetArchiveIdentifier;
        this.description = description;
        this.subject = subject;
    }

    public Item(String title, String author, List<String> topic, String internetArchiveIdentifier, int itemId, String description, String subject) {
        this.title = title;
        this.author = author;
        this.topic = topic;
        this.internetArchiveIdentifier = internetArchiveIdentifier;
        this.itemId = itemId;
        this.description = description;
        this.subject = subject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


//    public int getPageAmount() {
//        return pageAmount;
//    }
//
//    public void setPageAmount(int pageAmount) {
//        this.pageAmount = pageAmount;
//    }

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

    public List<String> getTopic() {
        return topic;
    }

    public void setTopic(List<String> topic) {
        this.topic = topic;
    }
}
