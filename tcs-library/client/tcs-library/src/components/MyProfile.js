import { useContext, useState } from "react";
import AuthContext from "../contexts/AuthContext";
import { useParams, useSearchParams } from "react-router-dom";




const MyProfile = () => { 
    
    const params = useParams();
    const auth = useContext(AuthContext);
    const [books, setBooks] = useState("");

    useEffect( () => {
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
            }
    
          } catch (error) {
            console.error("Request error:", error);
          }


    }
    })
    

    // have user and bookshelf 
    // fetching from backend - useEffect() 
    // click on book redirects to readingItem
    // links rendered for all like search fetch

    // linked with appUserId to fetch proper ItemShelf

    // protect routes












    return (<h1>teehee</h1>);
}


export default MyProfile;
