
package learn.tcslibrary.domain;

import learn.tcslibrary.data.ReviewRepository;
import learn.tcslibrary.models.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewServiceTest {
    @Autowired
    ReviewService service;

    @MockBean
    ReviewRepository repository;

    @Test
    void shouldFindAllByItemId() {
        when(repository.findByItemId(1)).thenReturn(List.of(
                new Review(1,"This book is amazing, 10/10",1,1),
                new Review(2,"Absolute trash, I don't know why anyone would read this book willingly",1,1)
        ));

        List<Review> result = service.findReviewsByItemId(1);

        assertEquals(2,result.size());
    }

    @Test
    void shouldFindByAppUserId() {
        when(repository.findByAppUserId(1)).thenReturn(List.of(
                new Review(1,"This book is amazing, 10/10",9,1),
                new Review(2,"Absolute trash, I don't know why anyone would read this book willingly",2,1)
        ));

        List<Review> result = service.findReviewsByAppUserId(1);

        assertEquals(2,result.size());
    }

    @Test
    void shouldAdd() {
        Review review = new Review();

        review.setReviewId(1);
        review.setReviewText("I love it");
        review.setItemId(2);
        review.setAppUserId(1);

        when(repository.addReview(review)).thenReturn(review);

        Result result = service.add(review);

        assertTrue(result.isSuccess());
        assertNotNull(result.getErrorMessages());
    }
    @Test
    void shouldNotAddDuplicate() {
            Review review1 = new Review();

            review1.setReviewId(1);
            review1.setReviewText("I love it");
            review1.setItemId(2);
            review1.setAppUserId(1);

            when(repository.addReview(review1)).thenReturn(review1);

            Result result = service.add(review1);

            assertTrue(result.isSuccess());
        Review review = new Review(1,"Testing duplicate review",2,1);

        when(repository.findByReviewId(review.getReviewId())).thenReturn(review);

        Result result2 = service.add(review);

        assertFalse(result2.isSuccess());
        //assertTrue(result2.getErrorMessages().contains("This user already left a review for this product"));
    }

    @Test
    void shouldNotAddNullOrBlank() {
        Review review = null;
        Review blankReview = new Review();
        blankReview.setReviewText(" ");

        Result result = service.add(review);
        Result blankResult = service.add(blankReview);

        assertFalse(result.isSuccess());
        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        assertEquals(1,blankResult.getErrorMessages().size());
        assertTrue(result.getErrorMessages().contains("Review cannot be null or blank"));
        assertTrue(blankResult.getErrorMessages().contains("Review cannot be null or blank"));
    }

    @Test
    void shouldUpdateReview() {
        Review existingReview = new Review(1, "Existing review", 1, 1);
        Review updatedReview = new Review(1, "Updated review", 1, 1);

        when(repository.findByReviewId(1)).thenReturn(existingReview);
        when(repository.updateReview(updatedReview)).thenReturn(true);

        Result result = service.update(updatedReview);

        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotUpdateToNullOrBlank() {
        Review review = new Review(1, "Review to update", 1, 1);
        review.setReviewText(null);

        Result result = service.update(review);

        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().contains("Review text cannot be null or blank."));
    }


    @Test
    void shouldDelete() {
        Review review = new Review(1, "Existing review", 1, 1);

        when(repository.deleteReview(review)).thenReturn(true);

        Result result = service.delete(review);

        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotDeleteReviewWithExistingComments() {
        // refer to field agent on how it was able to keep from deleting agents that had an alias
        Review review = new Review(1, "Existing review", 1, 1);

        // can change the name of the method?
        when(repository.hasComments(review)).thenReturn(true);

        Result result = service.delete(review);

        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().contains("Cannot delete review with existing comments."));

    }
}

