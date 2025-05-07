import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

// Dashboard-view
const Dashboard = () => {

  const navigate = useNavigate();

  useEffect(() => {

    // Get Jwt token from localStorage
    const token = localStorage.getItem("jwt");
    if (!token) {
      navigate("/");
      return;
    }
  }, [navigate]);

  // Dashboard-view
  return (
    <div className="container" style={{ padding: '1rem' }}>

      <button 
        onClick={() => {
          localStorage.removeItem("jwt");
          localStorage.removeItem("username");
          /* Return to home */
          window.location.reload();
          navigate("/");
        }} 
        className="logout">
        Logout
      </button>
  
      <div className="section">

        <h2>Dashboard</h2>

      </div>

    </div>  
  );

};

export default Dashboard;
