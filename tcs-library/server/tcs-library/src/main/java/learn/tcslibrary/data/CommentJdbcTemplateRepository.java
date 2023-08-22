package learn.tcslibrary.data;

import learn.tcslibrary.data.mappers.CommentMapper;
import learn.tcslibrary.models.Comment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentJdbcTemplateRepository implements CommentRepository {
    private JdbcTemplate jdbcTemplate;

    public CommentJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Comment> findAll(int reviewId) {
        final String sql= "select comment_text,app_user_id,review_id from comments where review_id=?;";
        List<Comment> comments=jdbcTemplate.query(sql, new CommentMapper(),reviewId);
        return (comments==null||comments.size()==0 ? null : comments);
    }

    @Override
    public Comment findByCommentId(int commentId) {
        final String sql="select comment_text,app_user_id,review_id from comments where comment_id=?;";
        List<Comment> comments=jdbcTemplate.query(sql, new CommentMapper(),commentId);
        return (comments==null||comments.size()==0 ? null : comments.get(0));
    }

    @Override
    public List<Comment> findByAppUserId(int appUserId) {
        final String sql= "select comment_text,app_user_id,review_id from comments where app_user_id=?;";
        List<Comment> comments=jdbcTemplate.query(sql, new CommentMapper(),appUserId);
        return (comments==null||comments.size()==0 ? null : comments);
    }

    @Override
    public Comment addComment(Comment comment) {
        final String sql = "insert into comments (comment_text,app_user_id,review_id) VALUES (?, ?, ?);";
        int rowsAffected = jdbcTemplate.update(sql, comment.getCommentText(), comment.getAppUserId(),comment.getReviewId());
        return (rowsAffected >0 ? comment : null);
    }

    @Override
    public Comment updateComment(Comment comment) {
        final String sql= "update comments set comment_text=? where comment_id=?;";
        int rowsAffected = jdbcTemplate.update(sql, comment.getCommentText(), comment.getCommentId());
        return (rowsAffected >0 ? comment : null);
    }

    @Override
    public boolean deleteComment(int commentId) {
        final String sql="delete from comments where comment_id=?;";
        int rowsAffected = jdbcTemplate.update(sql, commentId);
        return rowsAffected>0;
    }
}
