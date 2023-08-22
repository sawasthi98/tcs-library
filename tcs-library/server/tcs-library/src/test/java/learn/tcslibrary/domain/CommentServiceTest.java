package learn.tcslibrary.domain;

import learn.tcslibrary.data.CommentRepository;
import learn.tcslibrary.models.Comment;
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
class CommentServiceTest {

    @Autowired
    CommentService service;

    @MockBean
    CommentRepository repository;

    @Test
    void shouldFindAllCommentsByReviewId() {
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment("Great review!", 1, 1, 1));
        comments.add(new Comment("Interesting perspective.", 1, 2, 2));

        when(repository.findAll(1)).thenReturn(comments);

        List<Comment> result = service.findAll(1);

        assertEquals(2, result.size());
    }

    @Test
    void shouldFindAllCommentsByAppUserId() {
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment("Nice book!", 1, 1, 1));
        comments.add(new Comment("I didn't like it.", 2, 1, 2));

        when(repository.findByAppUserId(1)).thenReturn(comments);

        List<Comment> result = service.findByAppUserId(1);

        assertEquals(2, result.size());
    }

    @Test
    void shouldNotFindNonexistentComments() {
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment("Nice book!", 1, 1, 1));
        comments.add(new Comment("I didn't like it.", 2, 1, 2));

        when(repository.findByAppUserId(1)).thenReturn(comments);

        List<Comment> result = service.findByAppUserId(1);

        assertEquals(2, result.size());
    }

    @Test
    void shouldAddNewComment() {
        Comment comment = new Comment("Great book!", 1, 1, 1);

        when(repository.addComment(comment)).thenReturn(comment);

        Result result = service.addComment(comment);

        assertTrue(result.isSuccess());
       // assertNotNull(result.getPayload());
    }

    @Test
    void shouldNotAddDuplicateComment() {
        Comment comment = new Comment("Great book!", 1, 1, 1);

        when(repository.addComment(comment)).thenReturn(null);

        Result result = service.addComment(comment);

        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotAddNullOrBlankComment() {
        Comment comment = null;
        Comment blankComment = new Comment();
        blankComment.setCommentText(" ");

        Result result = service.addComment(comment);
        Result blankResult = service.addComment(blankComment);

        assertFalse(result.isSuccess());
        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        assertEquals(1, blankResult.getErrorMessages().size());
        assertTrue(result.getErrorMessages().contains("Comment cannot be null or blank"));
        assertTrue(blankResult.getErrorMessages().contains("Comment cannot be null or blank"));
    }

    @Test
    void shouldUpdateComment() {
        // where do we need to make a conditional that it can only be edited by the person who wrote the comment?
        Comment comment = new Comment("Great book!", 1, 1, 1);

        when(repository.updateComment(comment)).thenReturn(comment);

        Result result = service.updateComment(comment);

        assertTrue(result.isSuccess());
    }

//    @Test
//    void shouldNotUpdateCommentNotWrittenByAppUserId() {
//
//    }

    @Test
    void shouldNotUpdateToNullOrBlank() {
        Comment comment = new Comment("Great book!", 1, 1, 1);
        comment.setCommentText(" ");

        Result result = service.updateComment(comment);

        assertFalse(result.isSuccess());
    }

    @Test
    void shouldDeleteComment() {
        Comment comment = new Comment("Great book!", 1, 1, 1);

        when(repository.deleteComment(1)).thenReturn(true);

        Result result = service.deleteComment(comment);

        assertTrue(result.isSuccess());
    }

//    @Test
//    void shouldNotDeleteCommentNotWrittenByAppUserId() {
//        Comment comment = new Comment( "Nice read!", 2, 1,1);
//
//        Result result = service.deleteComment(comment);
//
//        assertFalse(result.isSuccess());
//    }


}