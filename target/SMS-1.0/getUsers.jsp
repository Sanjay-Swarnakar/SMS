<%-- 
    Document   : getUsers
    Created on : May 12, 2025, 7:57:09 PM
    Author     : Sanjay Swarnakar
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Select User</title>
    <style>
        /* Main container for the form */
        .container {
            max-width: 800px;
            margin: auto;
            padding: 20px;
            background: rgba(0, 0, 0, 0.6);
            border-radius: 10px;
            margin-top: 40px;
        }

        /* Main banner heading */
        h1 {
            text-align: center;
            background-color: #993366;
            padding: 10px;
            border-radius: 10px;
            font-size: 32px;
        }

        /* Sub-heading */
        h2 {
            text-align: center;
            font-size: 24px;
            margin-top: 10px;
            margin-bottom: 10px;
        }

        /* Style the form layout */
        form {
            display: flex;
            flex-direction: column;
        }

        /* Label styling */
        label {
            margin-top: 15px;
            font-size: 18px;
        }

        /* Input styling for various types */
        input[type="text"],
        input[type="email"],
        input[type="password"],
        input[type="date"] {
            padding: 10px;
            border-radius: 5px;
            border: none;
            font-size: 16px;
            /*width: 98%;*/
            margin-right: 2px;
        }

        /* Submit and reset button container */
        .button-group {
            display: flex;
            justify-content: space-between;
            margin-top: 20px;
        }

        /* Style for buttons */
        input[type="submit"],
        input[type="reset"] {
            padding: 10px 20px;
            font-size: 16px;
            background-color: #993366;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        /* Button hover effect */
        input[type="submit"]:hover,
        input[type="reset"]:hover {
            background-color: #aa4477;
        }
        /* Responsive tweaks for smaller screens */
        @media (max-width: 600px) {
            .button-group {
                flex-direction: column;
                gap: 10px;
            }
        }
    </style>
</head>
<body>

<h2>Select a User</h2>

<form action="GetUsers" method="post">
    <select name="selectedUser">
        <%
            List<String> users = (List<String>) request.getAttribute("userList");
            if (users != null) {
                for (String user : users) {
        %>
                    <option value="<%= user %>"><%= user %></option>
        <%
                }
            } else {
        %>
                <option>No users found</option>
        <%
            }
        %>
    </select>
    <br><br>
    <input type="submit" value="Submit">
</form>
    
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

</body>
</html>
