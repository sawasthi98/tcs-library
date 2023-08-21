package learn.tcslibrary.data;

import learn.tcslibrary.models.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentJdbcTemplateRepositoryTest {

    @Autowired
    private CommentJdbcTemplateRepository repository;

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
    void shouldFindAllComments() {
        List<Comment> comments = repository.findAll(1);
        assertNotNull(comments);
        assertEquals(comments.size(),2);
    }

    @Test
    void shouldReturnNullForNonexistentComments() {
        List<Comment> comments = repository.findAll(1000);
        assertNull(comments);
    }

    @Test
    void shouldFindAllCommentsByAppUserId() {
        List<Comment> comments = repository.findByAppUserId(1);
        assertNotNull(comments);
        assertEquals(comments,1);
    }

//    @Test
//    void shouldFindCommentByCommentId() {
//        Comment comment = repository.findByCommentId(1);
//        assertNotNull(comment);
//        assertEquals(1, comment.getCommentId());
//        assertEquals("This is a great book!", comment.getCommentText());
//        assertEquals(1, comment.g());
//        assertEquals(1, comment.getAppUserId());
//    }

    @Test
    void shouldAddComment() {
        Comment commentToAdd = new Comment("Added comment",2,3,1);

        Comment added = repository.addComment(commentToAdd);

        assertNotNull(added);
        assertEquals(added,commentToAdd);
    }

    @Test
    void shouldUpdateComment() {
        Comment comment = new Comment();
        comment.setCommentId(1);
        comment.setReviewId(1);
        comment.setCommentText("Updated comment");

        Comment updatedComment = repository.findByCommentId(1);

        assertNotNull(updatedComment);
        assertEquals("Updated comment", updatedComment.getCommentText());
    }

    @Test
    void shouldDeleteComment() {
        Comment commentToDelete = new Comment("Deleting this comment",2,1,1);
        boolean result = repository.deleteComment(1);
        assertTrue(result);

        Comment deletedComment = repository.findByCommentId(1);
        assertNull(deletedComment);
    }

}