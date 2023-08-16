import { useEffect, useState } from 'react';
import './App.css';
import { Document, Page } from 'react-pdf'; // Import directly from 'react-pdf'

function App() {

  const document = 'Adventures of Huckleberry Finn.pdf';

  useEffect(() => {
    getData()
  }, [])

  async function getData () {
    await fetch(`https://archive.org/download/adventures-of-huckleberry-finn/${document}`, {
      method: "GET",
      headers: {
        "Content-Type": "application/pdf",
        Accept: "application/pdf",
      }
        })
        // fetch local 8080 with controller endpoint 
        // makes requests to archive.org
        // java tool like fetch
  .then(response => {
    console.log(response);
  })
  .catch(error => {
    console.log("Error fetching data: ", error);
  });
  }
  

  return (
    <div className="App">
      <header className="App-header">
        <div>
            <Document file={document}>
              <Page pageNumber={1} />  
            </Document>
          
        </div>
      </header>
    </div>
  );
}

export default App;
