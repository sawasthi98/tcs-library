package learn.tcslibrary.models;

import java.util.List;

public class Item {

    private String title;
    private String author;
    private String publishedDate;
    private String publisher;
    private List<String> topic;
    private int pageAmount;
    private String language;
    private String internetArchiveIdentifier;
    private int itemId;
    // add item description

    public Item() {
    }

    public Item(String title, String author, String publishedDate, String publisher, List<String> topic, int pageAmount, String language, String internetArchiveIdentifier, int itemId) {
        this.title = title;
        this.author = author;
        this.publishedDate = publishedDate;
        this.publisher = publisher;
        this.topic = topic;
        this.pageAmount = pageAmount;
        this.language = language;
        this.internetArchiveIdentifier = internetArchiveIdentifier;
        this.itemId = itemId;
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

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPageAmount() {
        return pageAmount;
    }

    public void setPageAmount(int pageAmount) {
        this.pageAmount = pageAmount;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
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

    public List<String> getTopic() {
        return topic;
    }

    public void setTopic(List<String> topic) {
        this.topic = topic;
    }
}
