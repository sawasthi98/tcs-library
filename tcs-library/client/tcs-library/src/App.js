import { useEffect, useState } from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { Document, Page } from 'react-pdf';
import logo from './logo.png'; 

import 'bootstrap/dist/css/bootstrap.min.css';

import './App.css';
import Landing from './components/Landing';
import NotFound from './components/NotFound';
import jwtDecode from 'jwt-decode';
import AuthContext from './contexts/AuthContext';
import Nav from './components/Nav';
import Login from './components/Login';
import ReadingItem from './components/ReadingItem';
import SearchResults from './components/SearchResults';
import ReviewForm from './components/ReviewForm';
import MyProfile from './components/MyProfile';


function App() {

  const [user, setUser] = useState(null);
  
  

  const login = (token) => {
    
    // Decode the token
    const { sub: username, authorities: authoritiesString } = jwtDecode(token);
    
  
    // Split the authorities string into an array of roles
    const roles = authoritiesString.split(',');
  
    // Create the "user" object
    const user = {
      username,
      roles,
      token,
      hasRole(role) {
        return this.roles.includes(role);
      }
    };
    localStorage.setItem("auth-token", token)
    setUser(user);
    return user;
  };

  const logout = () => {
    setUser(null)
    localStorage.removeItem("auth-token")
  }

  const auth = {login, logout, user}  


  useEffect(() => {
    console.log("useEffect App");
    const token = localStorage.getItem("auth-token");
    // console.log("Token from useEffect:", token);
  
    if (token) {
      login(token);
      // loadAnswers(token); Load effect for what we need to load to the front
    }
    
  }, []);  



  return (
    <AuthContext.Provider value={auth}>
    <BrowserRouter>
    <div className="homePage">
      <div className="logo"><img src={logo} alt="byte sized books logo"/></div>
    {/* replace h1 with logo that can be used as home button also */}
    <Nav />
      
      <Routes>
        {/* Always */}
        <Route path="/" element={<Landing />} />
        <Route path="*" element= {<NotFound />} />

        {/* Logged IN */}
        {/* Add the My profile and search bar functionality here
            also add the log out here */}

        <Route path="/readingitem/:identifier/filename/:filename" element={ user ? <ReadingItem /> : <Login />} />
        <Route path="/search/:searchText" element={ user ? <SearchResults /> : <Login />} />
        <Route path="/review-form/:identifier/filename/:filename" element={ user ? <ReviewForm /> : <Login />} />

        <Route path="/my-bookshelf" element={user ? <MyProfile /> : <Login />} />

        {/* Logged OUT */}
        
        <Route path="/login" element={ user ? <Navigate to="/" /> : <Login />} />
        {/* route protect to redirect to login paged when logged out */}

        


      </Routes>
      </div>
    </BrowserRouter>

    </AuthContext.Provider>
    
  );
}

export default App;
