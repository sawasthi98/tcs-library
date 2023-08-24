import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";

const SearchResults = () => {

  const params = useParams();
  const [results, setResults] = useState([]);
  const [hasFinishedLoading, setHasFinishedLoading] = useState(false);

  useEffect (() => {
    const fetchResults = async () => {
    
    try {
      const response = await fetch(

        `http://localhost:8080/tcslibrary/search/${params.searchText}`,

        {
          method: "GET",
          headers: {
            Accept: "application/json",
          },
        }
      );
      
      if (!response.ok) {
        throw new Error("Request failed");
      }

      const data = await response.json();
        setResults(data);

    } catch (error) {
      console.error("Request error:", error);
      // Handle error here
    }
  };

  fetchResults();
  }, [params.searchText])

  return (
    
    <>
    {/* Book Search Result of Title
    "Search Results for {whatever user typed in}" */}
    <h1 className="searchResultHeader">Search Results for "{params.searchText}"</h1>
    <div id='searchResult'>
    {/* { 
      <div className="div-containing-spinner">
        <div className="loading-div" id="spinner">Loading...</div>
      </div>} */}
      {results.map((result) => (
        <div className="book" key={result.id}>
          <Link to={`/readingitem/${result.identifier}/filename/${result.fileName}`}>
            <img src={result.imgLink} alt='Cover art for selected book' />
          </Link>
          <h2>{result.title}</h2>
          <p>{result.description}</p>
          {/* how to change this to "read more"? if we want that */}
        </div>
      ))}
    </div>
    </>
  )
}

export default SearchResults;
