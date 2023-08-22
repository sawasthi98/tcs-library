package learn.tcslibrary.domain;

import learn.tcslibrary.data.ReviewRepository;
import learn.tcslibrary.models.Review;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    ReviewRepository repository;

    public ReviewService(ReviewRepository repository) {
        this.repository = repository;
    }

    public Result add(Review review){
        Result result=new Result();
        if(review==null||review.getReviewText().isBlank()){
            result.addErrorMessage("Review cannot be null or blank", ResultType.NOT_FOUND);
            return result;
        }
        for(Review review1: repository.findAll()){
            if(review1.equalsReview(review)){
                result.addErrorMessage("This user already left a review for this product", ResultType.INVALID);
                return result;
            }
            if(review1.getReviewId()==review.getReviewId()){
                result.addErrorMessage("Review already in system", ResultType.INVALID);
                return result;
            }
        }//end of sqrt(16) ln(e) (e^x)^(1/x) 2({e: f(ex)=x=f(xe) ∀x∈Z}) p
        if(repository.addReview(review)==null){
            result.addErrorMessage("Unable to add a review.",ResultType.INVALID);
            return result;
        }
        return result;
    }

    public Result update(Review review){
        Result result=new Result();
        if(review==null||review.getReviewText()==null||review.getReviewText().isBlank()){
            result.addErrorMessage("Review text cannot be null or blank.", ResultType.NOT_FOUND);
            return result;
        }
        if(!repository.findByReviewId(review.getReviewId()).equalsReview(review)){
            result.addErrorMessage("This is not the same review",ResultType.INVALID);
            return result;
        }
        if(!repository.updateReview(review)){
            result.addErrorMessage("The review did not update", ResultType.INVALID);
            return result;
        }
        return result;
    }

    public Result delete(Review review){
        Result result=new Result();
        if(review==null||review.getReviewText().isBlank()){
            result.addErrorMessage("Review text cannot be null or blank.", ResultType.NOT_FOUND);
            return result;
        }
//        if(!repository.findByReviewId(review.getReviewId()).equalsReview(review)){
//            result.addErrorMessage("This is not the same review",ResultType.INVALID);
//            return result;
//        }
        if(repository.hasComments(review)){
            result.addErrorMessage("Cannot delete review with existing comments.",ResultType.INVALID);
            return result;
        }
        if(!repository.deleteReview(review)){
            result.addErrorMessage("Failed to delete review.",ResultType.INVALID);
            return result;
        }
        return result;
    }

    public List<Review> findReviewsByItemId(int itemId){
        return repository.findByItemId(itemId);
    }
    public List<Review>findReviewsByAppUserId(int appUserId){
        return repository.findByAppUserId(appUserId);
    }


}
