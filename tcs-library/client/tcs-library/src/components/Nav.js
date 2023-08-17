import { useContext, useState } from "react";
import AuthContext from "../contexts/AuthContext";
import { Link } from "react-router-dom";



const Nav = () => {
  const auth = useContext(AuthContext);
  const user = auth.user;

  // function SearchBar() {
  //   const [searchText, setSearchText] = useState('');
  
  //   const handleSearchChange = (event) => {
  //     setSearchText(event.target.value);
  //   };
  // }


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
        <Link to="/readingitem">BOOK TEST</Link>
        {" "}
        <button onClick={auth.logout}>Logout</button>

        {/* Search bar */}
    <div>
      <input
        className="searchBar"
        type="text"
        placeholder="Search..."
        // value={}
        // onChange={}
        />
      <button>Search</button>
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