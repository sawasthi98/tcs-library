import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

function Landing(){


    return(
      <>
        <div className="Landing">
            <h1>ReadsGood</h1>
            <p>Enhancing countless reading experiences since 2023</p>
            <img src="" alt="books" className="Landing-pic" />
        </div>
        <div id="landing-book-collection">
          <div>
            <Link to="/readingitem">
            <img src='https://www.dramaticpublishing.com/media/catalog/product/cache/1/image/300x436/9df78eab33525d08d6e5fb8d27136e95/p/r/pride_and_prejudice_cover_p36000_web.jpg' alt='P&P Book Cover' className="landing-book" />
          </Link>
          </div>
          <div>
            <img src='https://hips.hearstapps.com/vader-prod.s3.amazonaws.com/1665689329-41fqfwTqojL._SL500_.jpg?crop=1xw:1xh;center,top&resize=980:*' alt='P&P Book Cover' className="landing-book" id="sherlock"/>
          </div>
          <div>
            <img src='https://cdn10.bigcommerce.com/s-g9n04qy/products/487508/images/494633/51Dv42myl0L._SL1300___30804.1626879687.500.500.jpg?c=2' alt='P&P Book Cover' className="landing-book" />
          </div>
          <div>
            <img src='https://i.ebayimg.com/images/g/c9kAAOSw0kpfdFjj/s-l500.jpg' alt='P&P Book Cover' className="landing-book" />
          </div>
          <div>
            <img src='https://ih1.redbubble.net/image.1139423065.9197/flat,750x,075,f-pad,750x1000,f8f8f8.jpg' alt='P&P Book Cover' className="landing-book" id="dracula" />
          </div>
        </div>

            
          </>


    );
}

export default Landing;