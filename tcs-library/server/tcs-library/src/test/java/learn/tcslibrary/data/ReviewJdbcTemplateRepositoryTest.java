package learn.tcslibrary.data;

import learn.tcslibrary.models.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;

import learn.tcslibrary.data.ReviewJdbcTemplateRepository;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewJdbcTemplateRepositoryTest {

    @Autowired
    private ReviewJdbcTemplateRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    static boolean hasSetup = false;

    @BeforeEach
    void setup() {
        if (!hasSetup) {
            hasSetup = true;
            jdbcTemplate.update("call set_known_good_state();");
        }
    }

    @Test
    void findAll() {
        List<Review> reviews = repository.findAll();
        assertNotNull(reviews);
        assertFalse(reviews.isEmpty());
        assertEquals(3, reviews.size());
    }

    @Test
    void findByItemId() {
        List<Review> reviews = repository.findByItemId(1);
        assertNotNull(reviews);
        assertFalse(reviews.isEmpty());
        for (Review review : reviews) {
            assertEquals(1, review.getItemId());
        }
        List<Review> reviews1 = repository.findByItemId(2);
        assertNotNull(reviews1);
        assertFalse(reviews1.isEmpty());
        for (Review review : reviews1) {
            assertEquals(2, review.getItemId());
        }
    }

    @Test
    void findByAppUserId() {
        List<Review> reviews = repository.findByAppUserId(2);
        assertNotNull(reviews);
        assertFalse(reviews.isEmpty());
        for (Review review : reviews) {
            assertEquals(2, review.getAppUserId());
        }
    }

    @Test
    void addReview() {
        Review newReview = new Review();
        newReview.setReviewText("This is a test review!");
        newReview.setAppUserId(2);
        newReview.setItemId(1);

        Review addedReview = repository.addReview(newReview);
        assertNotNull(addedReview);
        assertEquals(newReview.getReviewText(), addedReview.getReviewText());
        assertEquals(newReview.getAppUserId(), addedReview.getAppUserId());
        assertEquals(newReview.getItemId(), addedReview.getItemId());

        repository.deleteReview(addedReview);

    }

    @Test
    void updateReview() {
        Review reviewToUpdate = new Review(2,"this is a test review",1,2);
        repository.addReview(reviewToUpdate);
        assertNotNull(reviewToUpdate);
        Review updatedReview = new Review(2,"this is an updated review",1,2);
        reviewToUpdate = repository.updateReview(updatedReview);
        assertNotNull(updatedReview);
        assertEquals(reviewToUpdate.getItemId(), updatedReview.getItemId());
        assertEquals(reviewToUpdate.getAppUserId(),updatedReview.getAppUserId());
        assertEquals(reviewToUpdate.getReviewText(), updatedReview.getReviewText());

        repository.deleteReview(reviewToUpdate);
    }

    @Test
    void deleteReview() {
        Review reviewToDelete = new Review(1,"this is a test review", 1, 2);
        repository.addReview(reviewToDelete);
        assertNotNull(reviewToDelete);

        if (reviewToDelete != null) {
            boolean isDeleted = repository.deleteReview(reviewToDelete);
            assertTrue(isDeleted);
        }
    }
}