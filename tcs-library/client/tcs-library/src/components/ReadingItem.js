import React, { useEffect, useState } from "react";
import pdfjsLib from "pdfjs-dist";
import { useParams } from "react-router-dom";

const ReadingItem = () => {
  const [pdfUrl, setPdfUrl] = useState("");
  const [totalPages, setTotalPages] = useState(0);
  const [currentPage, setCurrentPage] = useState(1);
  const params = useParams();

  // use that to findByTitle on backend

  useEffect(() => {
    const fetchPdf = async () => {
      try {
        const response = await fetch(
          `http://localhost:8080/tcslibrary/reading-item/${params.itemId}`,
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

        // Get total pages using PDF.js
        const pdfDoc = await pdfjsLib.getDocument(pdfBlobUrl).promise;
        setTotalPages(pdfDoc.numPages);
      } catch (error) {
        console.error("Request error:", error);
        // Handle error here
      }
    };

    fetchPdf();
  }, []);

  const handlePageChange = (newPage) => {
    if (newPage >= 1 && newPage <= totalPages) {
      setCurrentPage(newPage);
    }
  };

  return (
    <div>
      <div>
        <h2>PDF Viewer</h2>
        <p>
          Page {currentPage} of {totalPages}
        </p>
        <button onClick={() => handlePageChange(currentPage - 1)}>
          Previous
        </button>
        <button onClick={() => handlePageChange(currentPage + 1)}>Next</button>
      </div>
      <div>
        {pdfUrl && (
          <iframe
            src={pdfUrl + `#page=${currentPage}`}
            width="99%"
            height="1000px"
            title="PDF Viewer"
          />
        )}
      </div>
    </div>
  );
};

export default ReadingItem;
