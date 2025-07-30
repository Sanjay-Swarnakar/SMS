<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="auth.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Exam Dashboard</title>
  <style>
    body {
      margin: 0;
      font-family: Arial, Helvetica, sans-serif;
      background: url('images/wallpaper2you_115591.jpg') no-repeat center center fixed;
      background-size: cover;
      color: white;
    }

    header {
      background-color: #993366;
      text-align: center;
      padding: 20px;
    }

    header h1 {
      font-size: 36px;
      margin: 0;
    }

    nav {
      display: flex;
      justify-content: center;
      gap: 15px;
      background-color: rgba(0, 0, 0, 0.7);
      padding: 10px;
      position: sticky;
      top: 0;
      z-index: 999;
    }

    nav a img {
      height: 30px;
    }

    main {
      padding: 30px 20px;
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
      gap: 20px;
      max-width: 1000px;
      margin: auto;
    }

    .card {
      background-color: rgba(0, 0, 0, 0.6);
      padding: 20px;
      border-radius: 10px;
      text-align: center;
    }

    .card a {
      text-decoration: none;
      color: white;
      font-size: 24px;
      font-family: 'Times New Roman', Times, serif;
    }

    .dashboard-title {
      text-align: center;
      margin-top: 20px;
      font-size: 32px;
      font-weight: bold;
    }

    @media (max-width: 600px) {
      header h1 {
        font-size: 24px;
      }
      .card a {
        font-size: 20px;
      }
    }
  </style>
</head>
<body>
  <header>
    <h1>Student Management System</h1>
  </header>

  <nav>
    <a href="login.jsp"><img src="images/nav924349530i.gif" alt="Home"></a>
    <a href="registration.jsp"><img src="images/nav924349531i.gif" alt="Register"></a>
    <a href="fees.jsp"><img src="images/nav924349532i.gif" alt="Fees"></a>
    <a href="exam.jsp"><img src="images/nav924349533i.gif" alt="Exam"></a>
    <a href="login.jsp"><img src="images/nav924349534i.gif" alt="Logout"></a>
  </nav>

  <div class="dashboard-title">Exam Dashboard</div>

  <main>
    <div class="card">
      <a href="addExam.jsp">Add Exam</a>
    </div>
    <div class="card">
      <a href="Schedule exam.jsp">Schedule Exam</a>
    </div>
    <div class="card">
      <a href="enterMarks.jsp">Enter Marks</a>
    </div>
    <div class="card">
      <a href="viewResult.jsp">View Result</a>
    </div>
  </main>
</body>
</html>