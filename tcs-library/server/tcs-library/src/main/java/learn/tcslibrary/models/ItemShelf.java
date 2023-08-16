package learn.tcslibrary.models;

public class ItemShelf {
    private int itemId;
    private int pageNumber;

    public ItemShelf() {
    }

    public ItemShelf(int itemId, int pageNumber) {
        this.itemId = itemId;
        this.pageNumber = pageNumber;
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
}
