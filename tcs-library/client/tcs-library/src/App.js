import { useEffect, useState } from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';

import './App.css';
import Landing from './components/Landing';
import NotFound from './components/NotFound';

function App() {

  const [user, setUser] = useState(null);

  return (
    <BrowserRouter>
      
      <Routes>
        <Route path="/" element={<Landing />} />
        <Route path="*" element= {<NotFound />} />

      </Routes>
    </BrowserRouter>
  );
}

export default App;
