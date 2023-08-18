import React, { useEffect, useState } from "react";
import { Document, Page } from "react-pdf";
import { useParams } from "react-router-dom";
import { pdfjs } from "react-pdf";


pdfjs.GlobalWorkerOptions.workerSrc = `//cdnjs.cloudflare.com/ajax/libs/pdf.js/${pdfjs.version}/pdf.worker.js`;


const ReadingItem = () => {
  const [pdfUrl, setPdfUrl] = useState("");
  const [numPages, setNumPages] = useState(null);
	const [pageNumber, setPageNumber] = useState(1);
  
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


  // use that to findByTitle on backend
  // This will request back end with doc and id
  // find or create ITem in the back
  // and item shelf for user
  // request IA with doc and id
  // Then send back the PDF


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
        console.log("pdfUrl:", pdfBlobUrl);
      } catch (error) {
        console.error("Request error:", error);
        // Handle error here
      }
    };

    fetchPdf();
  }, [params.identifier, params.filename]);

  console.log("numPages:", numPages);

  return (
    <div>
      <nav>
        <button onClick={goToPrevPage}>Prev</button>
        <button onClick={goToNextPage}>Next</button>
        <p>
          Page {pageNumber} of {numPages}
        </p>
      </nav>

      {pdfUrl && (
        <Document
          file={pdfUrl}
          onLoadSuccess={onDocumentLoadSuccess}
          loading={<div>Loading...</div>}
        >
          <Page pageNumber={pageNumber} />
        </Document>
      )}
    </div>
  );
};

export default ReadingItem;
