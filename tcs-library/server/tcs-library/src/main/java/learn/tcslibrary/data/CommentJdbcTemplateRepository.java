package learn.tcslibrary.data;

import learn.tcslibrary.models.Comment;

import java.util.List;

public class CommentJdbcTemplateRepository implements CommentRepository {
    @Override
    public List<Comment> findAll(int reviewId) {
        return null;
    }

    @Override
    public Comment findByCommentId(int commentId) {
        return null;
    }

    @Override
    public Comment addComment(Comment comment) {
        return null;
    }

    @Override
    public Comment updateComment(Comment comment) {
        return null;
    }

    @Override
    public boolean deleteComment(int commentId) {
        return false;
    }
}
