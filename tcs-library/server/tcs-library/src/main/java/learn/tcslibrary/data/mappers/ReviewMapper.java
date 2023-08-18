package learn.tcslibrary.data.mappers;

import learn.tcslibrary.models.Review;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewMapper implements RowMapper<Review> {
    @Override
    public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
        Review review=new Review();
        review.setReviewText(rs.getString("review"));
        review.setAppUserId(rs.getInt("app_user_id"));
        review.setItemId(rs.getInt("item_id"));
        return review;
    }
}
