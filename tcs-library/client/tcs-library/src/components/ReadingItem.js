import React, { useContext, useEffect, useState } from "react";
import { Document, Page } from "react-pdf";
import { Link, useParams } from "react-router-dom";
import { pdfjs } from "react-pdf";
import AuthContext from "../contexts/AuthContext";
import { identifier } from "@babel/types";

pdfjs.GlobalWorkerOptions.workerSrc = `//cdnjs.cloudflare.com/ajax/libs/pdf.js/${pdfjs.version}/pdf.worker.js`;


const ReadingItem = () => {
  const [pdfUrl, setPdfUrl] = useState("");
  const [numPages, setNumPages] = useState(null);
	const [pageNumber, setPageNumber] = useState(1);
  const [inputPage, setInputPage] = useState("");
  const [reviews, setReviews] = useState("");
  const auth = useContext(AuthContext);
  
  const params = useParams();

	const onDocumentLoadSuccess = ({ numPages }) => {
		setNumPages(numPages); // display
	};

  const goToPrevPage = () => {
    const newPage = pageNumber - 1 <= 1 ? 1 : pageNumber - 1
		setPageNumber(newPage);
    pageUpdate(newPage);
  }


	const goToNextPage = () => {
    const newPage = pageNumber + 1 >= numPages ? numPages : pageNumber + 1

		setPageNumber(newPage);
    pageUpdate(newPage);

  }

    const goToInputPage = () => {
    const newPage = parseInt(inputPage);
    if (!isNaN(newPage) && newPage >= 1 && newPage <= numPages) {
      setPageNumber(newPage);
    }
    pageUpdate(newPage);
  };
  

  useEffect( () => {
    const loadWithPageNumber = async () => {
      try {
        const response = await fetch(

          `http://localhost:8080/tcslibrary/reading-item/${params.identifier}/filename/${params.filename}/page`,

          {
            method: "GET",
            headers: {
              Accept: "application/pdf",
              Authorization: "Bearer " + auth.user.token
            },            
          }
        );


        if (!response.ok) {
          throw new Error("Request failed");
        } else {
          const json = await response.json();
          setPageNumber(json.pageNumber)
          console.log(pageNumber);
        }

      } catch (error) {
        console.error("Request error:", error);
      }
    }
    loadWithPageNumber();

  }, [params.identifier,params.filename, auth])

  // useEffect( () => {
  //   const fetchReviews = async () => {
  //     try {
  //       const response = await fetch(

  //         `http://localhost:8080/tcslibrary/reviews/${params.identifier}`,

  // useEffect( () => {
  //   const fetchReviews = async () => {
  //     try {
  //       const response = await fetch(

  //         `http://localhost:8080/tcslibrary/reviews/${params.identifier}`,


  //         {
  //           method: "GET",
  //           headers: {
  //             Accept: "application/json",
  //             Authorization: "Bearer " + auth.user.token
  //           },            
  //         }
  //       );

  //       if (!response.ok) {
  //         throw new Error("Request failed");
  //       }

  //       const data = await response.json();
  //       setReviews(data);

  //     } catch (error) {
  //       console.error("Request error:", error);
  //     }
  //   }
  //   console.log(reviews);

  //   fetchReviews();

  // }, [params.identifier, auth])

  // how to call 
  
    const pageUpdate = async (arg) => {
      if (arg === 1) {
        return
      }
      try {
        console.log(arg);
        const response = await fetch(

          `http://localhost:8080/tcslibrary/reading-item/${params.identifier}`,

          {
            method: "PUT",
            headers: {
              "Content-Type": "application/json",
              Accept: "application/json",
              Authorization: "Bearer " + auth.user.token
            },
            body: 
              JSON.stringify(
                {
              pageNumber: arg
                }
              )
          }
        );

        if (!response.ok) {
          throw new Error("Request failed");
        }

      } catch (error) {
        console.error("Request error:", error);
      }
    }

    //  what page was this item shelf on? 

  useEffect(() => {
    console.log(`ID: `,params.identifier);
    console.log(`filename: `,params.filename);
    const fetchPdf = async () => {
      try {
        const response = await fetch(

          `http://localhost:8080/tcslibrary/reading-item/${params.identifier}/filename/${params.filename}`,

          {
            method: "GET",
            headers: {
              Accept: "application/pdf",
              Authorization: "Bearer " + auth.user.token
            },
          }
        );

        if (!response.ok) {
          throw new Error("Request failed");
        }

        const pdfBlob = await response.blob();
        const pdfBlobUrl = URL.createObjectURL(pdfBlob);
        setPdfUrl(pdfBlobUrl);
      } catch (error) {
        console.error("Request error:", error);
      }
    };

    fetchPdf();

  }, [params.identifier, params.filename, auth]);
  

  return (
    <div className="readingItem">

    <div className="pdfStyle">
      {pdfUrl && (
        <Document id="readBook"
        file={pdfUrl}
        onLoadSuccess={onDocumentLoadSuccess}
        loading={<div>Loading...</div>}
        >
          {/* Once page num is grabbed set pageNum state to it
          PDF will display on page 1 and trigger redraw bc state change
          put it in state and it will be reflected on 92 and 100 */}
          <Page pageNumber={pageNumber} renderAnnotationLayer={false} renderTextLayer={false}/>
        </Document>
      )}
      </div>
        <nav id="pagination">
          <button id="prev-btn" onClick={goToPrevPage} style={{ height: '50px', width: '60px', fontSize: '22px' }}>Prev</button>
          {/* request to the backend with the current page, send user ID and current page and the item ID also in the text input helper function */}
          <button id="next-btn" onClick={goToNextPage} style={{ height: '50px', width: '60px', fontSize: '22px' }}>Next</button>
          <p style={{ fontSize: '26px' }}>
            Page {pageNumber} of {numPages}
          </p>
          <input
            type="text"
            value={inputPage}
            onChange={(e) => setInputPage(e.target.value)}
          />

          <button onClick={goToInputPage}>Go</button>
{/* 
          <div>
            <Link to={`/review-form/${params.identifier}/filename/${params.filename}`}>
              <button>Add a Review</button>
            </Link>
        </div> */}

        </nav>

        {/* map the reviews */}
        {/* <div className="reviews">
          {reviews.map((review) => {
            <div className="singleReview" key={review.id}>
              <h3>{review.review}</h3>
              <span>{auth.user.username}</span>
            </div>
          })}
        </div> */}
    </div>
  );
};

export default ReadingItem;
