import { useContext, useState } from "react";
import AuthContext from "../contexts/AuthContext";
import { Link, useNavigate } from "react-router-dom";
import SearchResults from "./SearchResults";



const Nav = () => {
  const auth = useContext(AuthContext);
  const user = auth.user;
  const navigate = useNavigate();

  const [searchText, setSearchText] = useState('');

  
    const handleSearch = (event) => {

      navigate(`/search/${searchText}`)

      
      
      // map each one into links

    };
  


  return (
    <nav>
      {/* ALWAYS */}
      <Link to="/">Home</Link>
      {" "}


      {/* LOGGED IN */}
      {user &&(
        <>
        <Link to="/">My Profile</Link>        
        {" "}
        <button onClick={auth.logout}>Logout</button>

        {/* Search bar */}
    <div>
      <input
        className="searchBar"
        type="text"
        placeholder="Search for a book"
        value={searchText}
        onChange={(evt) => setSearchText(evt.target.value)}
        />
      <button type="submit" onClick={handleSearch}>Search</button>
    </div>

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