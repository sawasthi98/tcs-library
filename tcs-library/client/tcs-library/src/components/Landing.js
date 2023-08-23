import { useEffect, useState, useContext } from "react";
import { Link, useNavigate } from "react-router-dom";
import AuthContext from "../contexts/AuthContext";
import Nav from "./Nav";

function Landing(){
  const auth = useContext(AuthContext);
  const user = auth.user;
  const navigate = useNavigate();

  const [searchText, setSearchText] = useState('');
  
    const handleSearch = (event) => {
      navigate(`/search/${searchText}`)   
    };


    return(
      <div>
        <div className="landing">
            <h1>Byte Sized Books!</h1>
            <p>Enhancing countless reading experiences since 2023</p>
            <p>Click on any of the books below or search to get your reading on!</p>
        </div>

        {user && 
        <div className="searchContainer" >
        {/* <form onClick={handleSearch}> */}
          {/* make it as form 
        on submit on the form, not button */} 
          <input
            className="searchBar"
            type="text"
            placeholder="Search for a book"
            value={searchText}
            onChange={(evt) => setSearchText(evt.target.value)}
            />
          <button className="homeSearch" type="submit" onClick={handleSearch}>Search</button>
        {/* </form> */}
      </div>}

        <div id="landing-book-collection">
          <div>
            <Link to="readingitem/pride-and-prejudice_201907/filename/pride-and-prejudice.pdf">
              <img src='https://www.dramaticpublishing.com/media/catalog/product/cache/1/image/300x436/9df78eab33525d08d6e5fb8d27136e95/p/r/pride_and_prejudice_cover_p36000_web.jpg' alt='P&P Book Cover' className="landing-book" id='prideAndPrejudice' />
          </Link>
          </div>
          <div>
            <Link to="/readingitem/thegiftofthemagilevel1/filename/The%20Gift%20of%20the%20Magi%20(level%201).pdf" >
              <img src='https://pictures.abebooks.com/isbn/9780671647063-us.jpg' alt='Gift of the Magi Book Cover' className="landing-book" id="giftOfTheMagi"/>
            </Link>
          </div>
          <div>
            <Link to="readingitem/082-the-blackwing-puzzle-franklin-w.-dixon/filename/001-The%20Tower%20Treasure%20-%20Franklin%20W.%20Dixon.pdf">
              <img src='https://bookoutlet.com/_next/image?url=https%3A%2F%2Fimages.bookoutlet.com%2Fcovers%2Flarge%2Fisbn978044%2F9780448089010-l.jpg&w=640&q=75' alt='Hardy Boys Book Cover' className="landing-book" id='hardyBoys' />
            </Link>
          </div>
          <div>
            <Link to="readingitem/heidi0000unse_u3i9/filename/heidi0000unse_u3i9.pdf">
              <img src='https://m.media-amazon.com/images/I/51Po+Z03NyL._SY344_BO1,204,203,200_.jpg' alt='Heidi Girl of the Alps book cover' className="landing-book" id='heidi' />
            </Link>
          </div>
          <div>
            <Link to="readingitem/bram-stoker_dracula/filename/bram-stoker_dracula.pdf">
              <img src='https://ih1.redbubble.net/image.1139423065.9197/flat,750x,075,f-pad,750x1000,f8f8f8.jpg' alt='Dracula Book Cover' className="landing-book" id="dracula" />
            </Link>
          </div>
        </div>

        <footer className="footer">
          Copyright: TCS Enterprises®
        </footer>
      </div>


    );
}

export default Landing;