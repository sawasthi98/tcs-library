package learn.tcslibrary.models;

public class ItemShelf {
    private int itemShelfId;
    private int itemId;
    private int pageNumber;
    private int appUserId;
    private String identifier;
    private String filename;

    public ItemShelf() {
    }

    public ItemShelf(int itemShelfId, int itemId, int pageNumber, int appUserId, String identifier, String filename) {
        this.itemShelfId = itemShelfId;
        this.itemId = itemId;
        this.pageNumber = pageNumber;
        this.appUserId = appUserId;
        this.identifier = identifier;
        this.filename = filename;
    }

    public int getItemShelfId() {
        return itemShelfId;
    }

    public void setItemShelfId(int itemShelfId) {
        this.itemShelfId = itemShelfId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
