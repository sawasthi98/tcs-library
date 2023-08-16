package learn.tcslibrary.models;

public class Review {

    private String reviewText;
    private int itemId;
    private int appUserId;

    public Review() {
    }

    public Review(String reviewText, int itemId, int appUserId) {
        this.reviewText = reviewText;
        this.itemId = itemId;
        this.appUserId = appUserId;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }
}
