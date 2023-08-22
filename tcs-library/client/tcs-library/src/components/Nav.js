import { useContext, useState } from "react";
import AuthContext from "../contexts/AuthContext";
import { Link, useNavigate } from "react-router-dom";
import SearchResults from "./SearchResults";


//import Stack from 'react-bootstrap/Stack';
import Button from 'react-bootstrap/Button';


  
    



const Nav = () => {
  const auth = useContext(AuthContext);
  const user = auth.user;
  const navigate = useNavigate();

  const [searchText, setSearchText] = useState('');

  
    const handleSearch = (event) => {
      navigate(`/search/${searchText}`)   
    };
  


  return (
    <>
      <nav className="navContainer">
        {/* ALWAYS */}
        <div className="navLink">
          <Link to="/" className="p-2" >Home</Link>
        </div>
        


        {/* LOGGED IN */}
        {user &&(
          <>        
          <div className="navLink">
            <Link to="/" className="p-2" style={{ marginLeft: '1650%' }}>My Profile</Link>
          </div>

          <div className="navLink">
            
            <Link to="/" className="p-2" onClick={auth.logout} style={{ marginLeft: '2210%' }}>Logout</Link>
            </div>
          {/* Search bar 
        <div className="searchContainer" >
           <form onClick={handleSearch}> */}
            {/* make it as form 
          on submit on the form, not button  
            <input
              className="searchBar"
              type="text"
              placeholder="Search for a book"
              value={searchText}
              onChange={(evt) => setSearchText(evt.target.value)}
              />
            <button className="homeSearch" type="submit" onClick={handleSearch}>Search</button>
          {/* </form> 
        </div>*/}

          </>
        )}
        

        {/* LOGGED OUT */}

        {!user && (
          <div className="navLink">
            <Link to="/login" className="">Log In</Link>
          </div>
        )} 
        
      </nav>
      
    </>
  )
}


export default Nav;