package learn.tcslibrary.models;

public class Comment {

    private String commentText;

    private int appUserId;

    private int reviewId;

    private int commentId;

    public Comment() {
    }

    public Comment(String commentText, int appUserId, int reviewId, int commentId) {
        this.commentText = commentText;
        this.appUserId = appUserId;
        this.reviewId = reviewId;
        this.commentId = commentId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }
}
