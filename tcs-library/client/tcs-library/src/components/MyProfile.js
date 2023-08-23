import { useContext, useEffect, useState } from "react";
import AuthContext from "../contexts/AuthContext";
import { Link, useParams, useSearchParams } from "react-router-dom";
import { getByDisplayValue } from "@testing-library/react";


const MyProfile = () => { 
    
    const params = useParams();
    const auth = useContext(AuthContext);
    const [books, setBooks] = useState([]);

    useEffect( () => {

        if (!auth){
            return;
        }

        const getBookshelf = async () => {
        try {
            const response = await fetch(
    
              `http://localhost:8080/tcslibrary/my-bookshelf`,
    
              {
                method: "GET",
                headers: {
                  Accept: "application/json",
                  Authorization: "Bearer " + auth.user.token
                },            
              }
            );
    
            if (!response.ok) {
              throw new Error("Request failed");
            //   if it's not ok, display bookshelf is empty (for new users)
            } else {
              const json = await response.json();
                setBooks(json);
                // console.log(json);
            }
    
          } catch (error) {
            console.error("Request error:", error);
          }

    }

        getBookshelf();
    },[auth])
    
    // protect routes

    return (
        <>
            <h1 className="bookshelfTitle">{auth?.user?.username}'s Bookshelf</h1>
            <div id="myBookshelf">
                {books.map((book) => (
                    <div className="bookshelfBook" >
                        <p>{book.itemId}</p>
                        <Link to={`/readingitem/${book.identifier}/filename/${book.filename}`}>
                            <img src={`https://archive.org/services/img/${book.identifier}`} alt='Cover art for selected book' />
                        </Link>
                        <p>{book.pageNumber}</p>
                    </div>
                ))}

            </div>

        </>
        
    );
}


export default MyProfile;
