package learn.tcslibrary.data;

import learn.tcslibrary.data.mappers.CommentMapper;
import learn.tcslibrary.data.mappers.ReviewMapper;
import learn.tcslibrary.models.Comment;
import learn.tcslibrary.models.Review;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class ReviewJdbcTemplateRepository implements ReviewRepository{
    private JdbcTemplate jdbcTemplate;

    public ReviewJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // do we need a findAll() if we never want to see reviews unless they are connected to the book?
    // i think we should also include a findByReviewId method
    @Override
    public List<Review> findAll() {
        final String sql= "select review_id, review, app_user_id, item_id from review;";
        return jdbcTemplate.query(sql,new ReviewMapper());
    }

    @Override
    public List<Review> findByItemId(int itemId) {
        final String sql= "select review_id, review, app_user_id, item_id from review where item_id = ?;";
        return jdbcTemplate.query(sql,new ReviewMapper(),itemId);
    }

    @Override
    public List<Review> findByAppUserId(int appUserId) {
        final String sql= "select review_id, review, app_user_id,item_id from review where app_user_id = ?;";
        return jdbcTemplate.query(sql,new ReviewMapper(),appUserId);
    }

    @Override
    public Review addReview(Review review) {
        final String sql = "insert into review (review, app_user_id, item_id) values (?,?,?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, review.getReviewText());
            ps.setInt(2, review.getAppUserId());
            ps.setInt(3,review.getItemId());
            return ps;
        }, keyHolder);
        return (rowsAffected <=0 ? null : review);
    }

    @Override
    public boolean updateReview(Review review) {
        final String sql = "update review set review = ? where app_user_id = ? and item_id = ?;";
        int rowsChanged = jdbcTemplate.update(sql, review.getReviewText(), review.getAppUserId(), review.getItemId());//only updates review text
        return rowsChanged>0;
    }

    @Override
    public boolean deleteReview(Review review) {
        final String sql = "delete from review where app_user_id= ? and item_id= ?;";
        int rowsChanged = jdbcTemplate.update(sql, review.getAppUserId(), review.getItemId());
        return rowsChanged>0;
    }

    @Override
    public Review findByReviewId(int reviewId){
        final String sql="select review, app_user_id,item_id from review where review_id = ?;";
        List<Review>reviews= jdbcTemplate.query(sql,new ReviewMapper(),reviewId);
        return (reviews !=null&&reviews.size()>0 ? reviews.get(0):null);
    }

    @Override
    public boolean hasComments(Review review){
        final String sql="select comment_text,app_user_id,review_id from comments where review_id=?;";
        List<Comment>comments=jdbcTemplate.query(sql,new CommentMapper(),review.getReviewId());
        return comments.size()>0;
    }

}

