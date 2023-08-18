import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";

const SearchResults = () => {

  const params = useParams();
  const [results, setResults] = useState([]);

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
    <div id='searchResult'>
      {results.map((result) => (
        <div className="book" key={result.id}>
          <Link to={`/readingitem/${result.internetArchiveIdentifier}/filename/${result.fileName}`}>
            <img src={result.imgLink} alt='Cover art for selected book' />
          </Link>
          <h2>{result.title}</h2>
          <p>{result.description}</p>
          {/* Capital links, take you to reading item page */}
        </div>
      ))}
    </div>
    </>
  )
}

export default SearchResults;
