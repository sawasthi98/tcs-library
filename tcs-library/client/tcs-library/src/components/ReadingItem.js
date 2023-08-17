import { useEffect, useState } from "react";

  const ReadingItem = () => {
    const [pdfUrl, setPdfUrl] = useState('');
  
    useEffect(() => {
      const fetchPdf = async () => {
        try {
          const response = await fetch("http://localhost:8080/tcslibrary/huckfinn", {
            method: "GET",
            headers: {
              "Accept": "application/pdf",
            },
          });
  
          if (!response.ok) {
            throw new Error('Request failed');
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
    }, []);
  
    return (
      <div>
        {pdfUrl && (
          <iframe
            src={pdfUrl}
            width="100%"
            height="600px"
            title="PDF Viewer"
          />
        )}
      </div>
    );
  };
  
  export default ReadingItem;