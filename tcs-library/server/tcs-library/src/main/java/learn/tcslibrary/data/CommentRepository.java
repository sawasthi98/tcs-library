package learn.tcslibrary.data;

import learn.tcslibrary.models.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository {

    public List<Comment> findAll(int reviewId); // this will findByReviewId
    public Comment findByCommentId(int commentId);
    public List<Comment> findByAppUserId(int appUserId);
    public Comment addComment(Comment comment);
    public Comment updateComment(Comment comment);
    public boolean deleteComment(int commentId);

}
