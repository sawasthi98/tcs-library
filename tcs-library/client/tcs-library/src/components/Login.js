import React, { useContext, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import AuthContext from "../contexts/AuthContext";

const Login = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [errors, setErrors] = useState([]);

  const navigate = useNavigate();
  const auth = useContext(AuthContext);

  const handleLogin = async (event) => {
    event.preventDefault();

    try {
      const response = await fetch("http://localhost:8080/authenticate", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          username,
          password,
        }),
      });

      const responseData = await response.json();

      if (response.status === 200) {
        auth.login(responseData.jwt_token);
        navigate("/");
      } else {
        setErrors(["Login failed."]);
      }
    } catch (error) {
      console.error("Login error:", error);
      setErrors(["Unknown error. Please try again later."]);
    }
  };



  const handleSignUp = async (event) => {
    event.preventDefault();

    try {
      const response = await fetch("http://localhost:8080/create_account", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Accept": "application/json"
        },
        body: JSON.stringify({
          username,
          password,
        }),
      });
// This will work but we need to change the below code to reflect the call accurately
      const responseData = await response.json();

      if (response.status === 200) {
        auth.login(responseData.jwt_token);
        navigate("/");
      } else {
        setErrors(["Sign-up failed. Please try again later."]);
      }
    } catch (error) {
      console.error("Sign-up error:", error);
      setErrors(["Unknown error. Please try again later."]);
    }
  };

  return (
    <div className="login-container">
      <h2>Login</h2>
      {errors.map((error, i) => (
        <div className="error" key={i}>{error} Password must be at least 8 characters and contain a digit</div>
      ))}
      <div className="login-input">
        <div className="input-container">
          <label htmlFor="username">Username: </label>
          <input
            type="text"
            onChange={(event) => setUsername(event.target.value)}
            value={username}
            id="username"
            className="input-field"
          />
        </div>
        <div className="input-container">
          <label htmlFor="password">Password: </label>
          <input
            type="password"
            onChange={(event) => setPassword(event.target.value)}
            value={password}
            id="password"
            className="input-field"
          />
        </div>
        <div className="buttonContainer">
          <button className="p-2" onClick={handleLogin}>Sign in</button>
          <button className="p-2"onClick={handleSignUp}>Sign up</button>
          <Link to="/"><button className="p-2">Cancel</button></Link>
        </div>
      </div>
    </div>
  );
};

export default Login;
