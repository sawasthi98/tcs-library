package learn.tcslibrary.models;

public class Review {

    private int reviewId;
    private String reviewText;
    private int itemId;
    private int appUserId;

    public Review() {
    }

    public Review(int reviewId, String reviewText, int itemId, int appUserId) {
        this.reviewId = reviewId;
        this.reviewText = reviewText;
        this.itemId = itemId;
        this.appUserId = appUserId;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
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

    public boolean equalsReview(Review review) {
        return this.appUserId == review.getAppUserId() && this.itemId == review.getItemId();
    }
}
