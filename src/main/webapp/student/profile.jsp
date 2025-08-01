<%@ page import="model.Student" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/auth.jsp" %>
<%
    Student student = (Student) session.getAttribute("loggedStudent");
    if (student == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>My Profile</title>
    <link rel="stylesheet" href="css/student.css">
    <style>
        .profile-container {
            max-width: 800px;
            margin: auto;
            background: #fff;
            padding: 20px;
            border-radius: 10px;
        }
        .profile-picture {
            width: 120px;
            height: 120px;
            border-radius: 50%;
            object-fit: cover;
            border: 2px solid #ddd;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            font-weight: bold;
        }
        input[type=text], input[type=password], input[type=email], input[type=file] {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
        }
        button {
            padding: 10px 20px;
            background: #007bff;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
        }
    </style>
</head>
<body>
<div class="profile-container">
    <h2>My Profile</h2>

    <div style="text-align: center;">
        <img class="profile-picture" src="uploads/<%= student.getProfilePicture() != null ? student.getProfilePicture() : "default.jpg" %>" alt="Profile Picture">
        <form action="UpdateProfilePicture" method="post" enctype="multipart/form-data">
            <input type="file" name="profilePicture" accept="image/*" required />
            <button type="submit">Upload New Picture</button>
        </form>
    </div>

    <form action="UpdateProfile" method="post">
        <div class="form-group">
            <label>Full Name</label>
            <input type="text" name="name" value="<%= student.getName() %>" required />
        </div>

        <div class="form-group">
            <label>Email Address</label>
            <input type="email" name="email" value="<%= student.getEmail() %>" required />
        </div>

        <div class="form-group">
            <label>Username</label>
            <input type="text" name="username" value="<%= student.getUsername() %>" readonly />
        </div>

        <div class="form-group">
            <label>Change Password</label>
            <input type="password" name="password" placeholder="Leave blank to keep current" />
        </div>

        <button type="submit">Update Profile</button>
    </form>
</div>
</body>
</html>
