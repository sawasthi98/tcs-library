package learn.tcslibrary.domain;

import learn.tcslibrary.data.CommentRepository;
import learn.tcslibrary.models.Comment;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class CommentService {
    private CommentRepository repository;

    public CommentService(CommentRepository repository) {
        this.repository = repository;
    }

    public List<Comment> findAll(int reviewId){
        Result result=validate(reviewId);
        if(result.isSuccess()){
            return repository.findAll(reviewId);
        }
        return null;
    }

    public List<Comment> findByAppUserId(int appUserId){
        Result result=validate(appUserId);
        return (result.isSuccess() ? repository.findByAppUserId(appUserId) : null);
    }

    public Result validate(int id){
        Result result=new Result();
        if(id<1) {
            result.addErrorMessage("Unusual id detected", ResultType.INVALID);
            return result;
        }
        return result;
    }

    public Result addComment(Comment comment){
        Result result=new Result();
        if(comment==null||comment.getCommentText()==null||comment.getCommentText().isBlank()){
            result.addErrorMessage("Comment cannot be null or blank",ResultType.INVALID);
            return result;
        }
        for(Comment comment1:findAll(comment.getReviewId())){
            if(comment1.getCommentText().equalsIgnoreCase(comment.getCommentText()) &&
            comment1.getAppUserId()==comment.getAppUserId()){
                result.addErrorMessage("Duplicate comment spotted",ResultType.INVALID);
                return result;
            }
        }//look, I'm not gonna top that square root logarithm nonsense, I quit
        if(repository.addComment(comment)==null){
            result.addErrorMessage("Unable to add comment",ResultType.INVALID);
            return result;
        }
        return result;
    }

    public Result updateComment(Comment comment){
        Result result=new Result();
        if(comment==null||comment.getCommentText()==null||comment.getCommentText().isBlank()){
            result.addErrorMessage("Null comment detected.",ResultType.INVALID);
            return result;
        }
        if(repository.updateComment(comment)==null){
            result.addErrorMessage("Unable to update comment",ResultType.INVALID);
            return result;
        }
        return result;
    }

    public Result deleteComment(Comment comment){
        Result result =new Result();
        if(comment==null){
            result.addErrorMessage("Null comment spotted",ResultType.INVALID);
            return result;
        }
        if(!repository.deleteComment(comment.getCommentId())){
            result.addErrorMessage("Unable to delete comment",ResultType.INVALID);
            return result;
        }
        return result;
    }

}
