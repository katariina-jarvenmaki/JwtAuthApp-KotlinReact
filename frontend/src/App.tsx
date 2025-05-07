import React, { useEffect, useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import axios from "axios";

import Home from './pages/Home';
import Dashboard from './pages/Dashboard'

function App() {
  const [username, setUsername] = useState<string | null>(null);

  useEffect(() => {
    const storedUsername = localStorage.getItem('username');
    if (storedUsername && storedUsername !== "undefined") {
      setUsername(storedUsername);
    }
  
    // Check token expiration
    const interceptor = axios.interceptors.response.use(
      response => response,
      error => {
        if (error.response && error.response.status === 401) {
          // Token expired or invalid
          localStorage.removeItem("jwt");
          localStorage.removeItem("username");
          window.location.href = "/"; 
        }
        return Promise.reject(error);
      }
    );
  
    return () => {
      axios.interceptors.response.eject(interceptor); 
    };
  }, []);  

  return (
    <Router>
      <nav style={{ padding: '1rem', background: '#eee', display: 'flex', justifyContent: 'space-between' }}>
        <div>
          <Link to="/" style={{ marginRight: '1rem' }}>Home</Link>
          <Link to="/dashboard">Dashboard</Link>
        </div>
        <div id="hello-user">
          {username && username !== "undefined" && <span>Hello, {username}!</span>}
        </div>
      </nav>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/dashboard" element={<Dashboard />} />
      </Routes>
    </Router>
  );
}

export default App;
