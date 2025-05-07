import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

const LoginForm = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const login = async () => {
    try {
      const res = await fetch('/api/auth/login', {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password })
      });

      if (!res.ok) throw new Error("Login failed");

      const data = await res.json();
      localStorage.setItem("jwt", data.token);
      localStorage.setItem("username", username);
      /* Go to Dashboard */
      navigate("/dashboard", { replace: true });
      window.location.reload();

    } catch (err) {
      setError("Login failed. Check credentials.");
    }
  };

  return (
    <div className="section" id="login-form" style={{ padding: '1rem' }}>
      <h2>Login</h2>
      <input
        type="text"
        placeholder="Username"
        value={username}
        style={{ display: 'block', marginBottom: '1rem', width: '400px' }}
        onChange={e => setUsername(e.target.value)}
      />
      <input
        type="password"
        placeholder="Password"
        value={password}
        style={{ display: 'block', marginBottom: '1rem', width: '400px' }}
        onChange={e => setPassword(e.target.value)}
      />
      <button onClick={login}>Login</button>
      {error && <p style={{ color: "red" }}>{error}</p>}
    </div>
  );
};

export default LoginForm;
