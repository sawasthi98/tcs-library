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
        <div className="navLink" id="homeLink">
          <Link to="/" className="p-2" >Home</Link>
        </div>
        

        <div className="profileAndLogin">
          {/* LOGGED IN */}
          {user &&(
            <>        
            <div className="navLink">
              <Link to="/my-bookshelf" className="p-2 profileLink" >My Bookshelf</Link>
            </div>

            <div className="navLink">
              
              <Link to="/" className="p-2 logoutLink" onClick={auth.logout} >Logout</Link>
              </div>
            </>
          )}

          {/* LOGGED OUT */}

          {!user && (
            <div className="navLink">
              <Link to="/login" className="p-2">Log In</Link>
            </div>
          )} 
        </div>
        
      </nav>
      
    </>
  )
}


export default Nav;