document.addEventListener("DOMContentLoaded", function () {
  // Initialize Google OAuth client
  function initGoogleAuth() {
    google.accounts.id.initialize({
      client_id:
        "879823097282-t425pr4ohefudhbj2hv6jga78qit4mg5.apps.googleusercontent.com",
      callback: handleGoogleSignIn,
      auto_select: false,
      cancel_on_tap_outside: true,
      context: "signin"
    });

    // Display the Google Sign In button
    google.accounts.id.renderButton(document.getElementById("googleLogin"), {
      theme: "outline",
      size: "large",
      width: 250,
      text: "continue_with",
      shape: "rectangular"
    });
  }

  // Handle Google Sign In
  function handleGoogleSignIn(response) {
    if (!response.credential) {
      console.error("No credential received");
      return;
    }

    fetch("/api/auth/google", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        token: response.credential
      })
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Network response was not ok");
        }
        return response.json();
      })
      .then((data) => {
        if (data.token) {
          localStorage.setItem("token", data.token);
          window.location.href = "/dashboard";
        } else {
          throw new Error("No token received");
        }
      })
      .catch((error) => {
        console.error("Error:", error);
        alert("Failed to sign in with Google: " + error.message);
      });
  }

  // Handle regular form submission
  document.getElementById("loginForm").addEventListener("submit", function (e) {
    e.preventDefault();

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    fetch("/api/auth/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        email: email,
        password: password
      })
    })
      .then((response) => response.json())
      .then((data) => {
        if (data.success) {
          // Store the JWT token
          localStorage.setItem("token", data.token);
          // Redirect to dashboard
          window.location.href = "/dashboard";
        } else {
          alert("Invalid credentials");
        }
      })
      .catch((error) => {
        console.error("Error:", error);
        alert("Failed to sign in");
      });
  });

  // Load Google Sign In API
  const script = document.createElement("script");
  script.src = "https://accounts.google.com/gsi/client";
  script.async = true;
  script.defer = true;
  script.onload = initGoogleAuth;
  document.head.appendChild(script);

  // Add this function to check authentication
  function checkAuth() {
    const token = localStorage.getItem("token");
    if (!token) {
      window.location.href = "/login.html";
      return;
    }

    // Verify token is valid
    fetch("/api/auth/verify", {
      headers: {
        Authorization: `Bearer ${token}`
      }
    }).catch(() => {
      localStorage.removeItem("token");
      window.location.href = "/login.html";
    });
  }

  // Call checkAuth when loading dashboard
  if (window.location.pathname === "/dashboard") {
    checkAuth();
  }
});
