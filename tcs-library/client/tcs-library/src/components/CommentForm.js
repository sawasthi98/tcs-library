import { useContext, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom"
import AuthContext from "../contexts/AuthContext";



const CommentForm = (props) => {
  const params = useParams();
  const navigate = useNavigate();
  const auth = useContext(AuthContext);

  const [errors, setErrors] = useState([]);

  
  const [comment, setComment] = useState("");

  const resetState = () => {
    setComment("");
  }

  const handleSubmit = (evt) => {

    evt.preventDefault()
    const newComment = {
      // Make the new comment here
    }
  
      fetch("http://localhost:8080/api/tcslibrary", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Accept: "application/json",
          Authorization: "Bearer " + auth.user.token
        },
        body: JSON.stringify(newComment)
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
        <label htmlFor="comment-input">Leave a comment: </label>
        <textarea id="comment-input"
        value={comment}
        onChange={(evt) => 
          setComment(evt.target.value)} />
      </fieldset>

      <button type="submit">Submit Comment</button>
      <Link to="/questions">Cancel</Link>
    </form>
  )}

export default CommentForm;
