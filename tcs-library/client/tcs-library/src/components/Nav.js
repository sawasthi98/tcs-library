import { useContext } from "react";
import AuthContext from "../contexts/AuthContext";
import { Link } from "react-router-dom";



const Nav = () => {
  const auth = useContext(AuthContext);
  const user = auth.user;


  return (
    <nav>
      {/* ALWAYS */}
      <Link to="/">Home</Link>
      {" "}
      <Link to="/">A different Link</Link>
      {" "}


      {/* LOGGED IN */}
      {user &&(
        <>
        <Link to="/">A Link</Link>
        {" "}
        <button onClick={auth.logout}>Logout</button>

        </>
      )}

      {/* LOGGED OUT */}

      {!user && (
        <Link to="/login">Log In</Link>
      )} 

    </nav>
  )
}

export default Nav;