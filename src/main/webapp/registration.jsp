
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Management System - Registration</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="css/registration.css">
</head>
<body>
    <header>Student Management System</header>

    <nav>
        <a href="registration.jsp">Register</a>
        <a href="fees.jsp">Fees</a>
        <a href="exam.jsp">Exam</a>
        <a href="logout.jsp">Logout</a>
    </nav>

    <!-- Main registration form container -->
    <div class="container">
        <h2>New User Registration Form</h2>
        <form action="Register" method="POST">
            <label for="fname">First Name</label>
            <input type="text" id="fname" name="fname" required>
            <label for="mname">Middle Name</label>
            <input type="text" id="mname" name="mname">
            <label for="lname">Last Name</label>
            <input type="text" id="lname" name="lname" required>
            <label for="dob">Date of Birth</label>
            <input type="date" id="dob" name="dob" required>
            <label for="email">E-Mail</label>
            <input type="email" id="email" name="email" required>
            <label for="addr">Address</label>
            <input type="text" id="addr" name="addr">
            <label for="phone">Phone No</label>
            <input type="text" id="phone" name="phone">
            <label for="crn">CRN</label>
            <input type="text" id="crn" name="crn">
            <label for="uname">User Name</label>
            <input type="text" id="uname" name="uname" required>
            <label for="pwd">Password</label>
            <input type="password" id="pwd" name="pwd" required>
            <label for="pwd1">Confirm Password</label>
            <input type="password" id="pwd1" name="pwd1" required>
            <div class="button-group">
                <input type="submit" value="Submit">
                <input type="reset" value="Reset">
            </div>
        </form>
    </div>
    
    <%
        String errorMessage = (String) request.getAttribute("errorMessage");
        String message = (String) request.getAttribute("message");
        if (errorMessage != null || message != null) {
    %>
    <script>
        window.onload = function () {
            const alertBox = document.getElementById("customAlert");
            const alertMsg = document.getElementById("alertMessage");
            alertMsg.textContent = "<%= (errorMessage != null) ? errorMessage : message %>";
            alertBox.style.display = "flex";
        };

        function closeAlert() {
            document.getElementById("customAlert").style.display = "none";
        }
    </script>
    <%
        }
    %>

    <div id="customAlert" class="custom-alert">
        <div class="alert-box">
            <span id="alertMessage"></span>
            <button onclick="closeAlert()">OK</button>
        </div>
    </div>
    
</body>
</html>
