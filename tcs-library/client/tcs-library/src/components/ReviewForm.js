import { useContext, useState } from "react";
import { Link, json, useNavigate, useParams } from "react-router-dom"
import AuthContext from "../contexts/AuthContext";



const ReviewForm = () => {
  const params = useParams();
  const navigate = useNavigate();
  const auth = useContext(AuthContext);

  const [errors, setErrors] = useState([]);
  
  const [reviewText, setReviewText] = useState("");

  const resetState = () => {
    setReviewText("");
  }

  const handleSubmit = (evt) => {

    evt.preventDefault()
    const newReview = {
      reviewText    
    }
  
      fetch(`http://localhost:8080/tcslibrary/reviews/add-review/${params.identifier}`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Accept: "application/json",
          Authorization: "Bearer " + auth.user.token
        },
        body: JSON.stringify(newReview)
      })
      .then(response => {
        if (response.ok) {
            navigate("/");
            resetState();
        } else {
            response.json().then(data => {
                if (Array.isArray(data)) {
                    setErrors(data);
                } else {
                    setErrors([data]);
                }
            });
        }
      })
    
    };

  return (
    
    <form className="addReviewForm" onSubmit={handleSubmit}>
      <ul>
        {errors.map(error => <li className="error" key={error}>{typeof error==="string" ? error : error.message}</li>)}
      </ul>

      <div className="reviewLabelContainer">
        <label id="reviewLabel" htmlFor="review-input">
          Leave a Review
        </label>
      </div>
      <div className="form-input">
        <fieldset className="form-input">
        <textarea
          id="review-input"
          value={reviewText}
          onChange={(evt) => setReviewText(evt.target.value)}
        />
      </fieldset>
      </div>
      

      <div className="buttonContainer">
        <Link to="/">
          <button className="p-2" type="submit">Submit Review</button>
          </Link>
        <Link to={`/readingitem/${params.identifier}/filename/${params.filename}`}>
          <button className="p-2">Cancel</button>
          </Link>
      </div>
      
    </form>
  )}

export default ReviewForm;
