import { useContext, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom"
import AuthContext from "../contexts/AuthContext";



const ReviewForm = (props) => {
  const params = useParams();
  const navigate = useNavigate();
  const auth = useContext(AuthContext);

  const [errors, setErrors] = useState([]);

  
  const [review, setReview] = useState("");

  const resetState = () => {
    setReview("");
  }

  const handleSubmit = (evt) => {

    evt.preventDefault()
    const newReview = {
      // Make the review object here
    }
  
      fetch("http://localhost:8080/api/tcslibrary/reviews/add-review/${identifier", {
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
          // Navigate to where?
          navigate("/")
          resetState()
          props.loadQuestions()

        } else {
          response.json()
          .then(errors => {
            if (Array.isArray(errors)) {
              setErrors(errors)
            } else {
              setErrors([errors])
            }
          })
        }
      })
    }

  return (
    
    <form onSubmit={handleSubmit}>
      <ul>
        {errors.map(error => <li key={error}>{error}</li>)}
      </ul>

      <fieldset class="form-input">
        <label htmlFor="review-input">Leave a Review: </label>
        <textarea id="review-input"
        value={review}
        onChange={(evt) => 
          setReview(evt.target.value)} />
          {/* keep track of identifier */}
      </fieldset>

      <button type="submit">Submit Review</button>
      <Link to={`/reading-item/${params.identifier}/filename/${params.filename}`}>
        <button>Cancel</button>
      </Link>
    </form>
  )}

export default ReviewForm;
