package learn.tcslibrary.data;

import learn.tcslibrary.models.Review;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository {
    public List<Review> findAll();
    public List<Review> findByItemId(int itemId);
    public List<Review>findByAppUserId(int appUserId);
    public Review addReview(Review review);
    public boolean updateReview(Review review);
    public boolean deleteReview(Review review);
    public Review findByReviewId(int reviewId);
    public boolean hasComments (Review review);

}
