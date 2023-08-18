import React, { useEffect, useState } from "react";
import { Document, Page } from "react-pdf";
import { useParams } from "react-router-dom";
import { pdfjs } from "react-pdf";


pdfjs.GlobalWorkerOptions.workerSrc = `//cdnjs.cloudflare.com/ajax/libs/pdf.js/${pdfjs.version}/pdf.worker.js`;


const ReadingItem = () => {
  const [pdfUrl, setPdfUrl] = useState("");
  const [numPages, setNumPages] = useState(null);
	const [pageNumber, setPageNumber] = useState(1);
  const [inputPage, setInputPage] = useState("");
  
  const params = useParams();

	const onDocumentLoadSuccess = ({ numPages }) => {
		setNumPages(numPages);
	};

  const goToPrevPage = () =>
		setPageNumber(pageNumber - 1 <= 1 ? 1 : pageNumber - 1);

	const goToNextPage = () =>
		setPageNumber(
			pageNumber + 1 >= numPages ? numPages : pageNumber + 1,
		);

    const goToInputPage = () => {
    const newPage = parseInt(inputPage);
    if (!isNaN(newPage) && newPage >= 1 && newPage <= numPages) {
      setPageNumber(newPage);
    }
  };


  // use that to findByTitle on backend
  // This will request back end with doc and id
  // find or create ITem in the back
  // and item shelf for user
  // request IA with doc and id
  // Then send back the PDF
  
  // Want to grab last read page of this shelf item


  useEffect(() => {
    const fetchPdf = async () => {
      try {
        const response = await fetch(

          `http://localhost:8080/tcslibrary/reading-item/${params.identifier}/filename/${params.filename}`,

          {
            method: "GET",
            headers: {
              Accept: "application/pdf",
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
        // Handle error here
      }
    };

    fetchPdf();
  }, [params.identifier, params.filename]);
  

  return (
    <div>

      {pdfUrl && (
        <Document
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
        <nav>
          <button onClick={goToPrevPage}>Prev</button>
          {/* request to the backend with the current page, send user ID and current page and the item ID also in the text input helper function */}
          <button onClick={goToNextPage}>Next</button>
          <p>
            Page {pageNumber} of {numPages}
          </p>
          <input
            type="text"
            value={inputPage}
            onChange={(e) => setInputPage(e.target.value)}
          />
          <button onClick={goToInputPage}>Go</button>
        </nav>
    </div>
  );
};

export default ReadingItem;
