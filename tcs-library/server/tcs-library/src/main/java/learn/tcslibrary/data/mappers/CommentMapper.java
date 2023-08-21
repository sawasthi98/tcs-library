package learn.tcslibrary.data.mappers;

import learn.tcslibrary.models.Comment;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentMapper implements RowMapper<Comment> {
    @Override
    public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
        Comment comment=new Comment();
        comment.setCommentText(rs.getString("comment_text"));
        comment.setAppUserId(rs.getInt("app_user_id"));
        comment.setReviewId(rs.getInt("review_id"));
        return comment;
    }
}
