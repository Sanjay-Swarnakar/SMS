<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Student"%>
<%
    Student student = (Student) request.getAttribute("student");
    String contextPath = request.getContextPath();
    String profilePic = student.getProfilePicture() != null && !student.getProfilePicture().isEmpty()
                        ? contextPath + "/profile_pics/" + student.getProfilePicture()
                        : contextPath + "/images/default-profile.png";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Profile</title>
    <link rel="stylesheet" href="<%=contextPath%>/css/style.css">
    <style>
        body.dark-mode {
            background-color: #121212;
            color: #f1f1f1;
        }
        .dark-mode input,
        .dark-mode textarea,
        .dark-mode select {
            background-color: #2c2c2c;
            color: white;
        }
        .profile-container {
            max-width: 600px;
            margin: 50px auto;
            padding: 20px;
            border-radius: 8px;
            background-color: white;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .dark-mode .profile-container {
            background-color: #1e1e1e;
        }
        .profile-pic {
            width: 120px;
            height: 120px;
            border-radius: 50%;
            object-fit: cover;
        }
    </style>
</head>
<body>
    <button onclick="toggleDarkMode()" style="float:right; margin:10px;">🌙 Toggle Dark Mode</button>

    <div class="profile-container">
        <h2>My Profile</h2>
        <img src="<%= profilePic %>" alt="Profile Picture" class="profile-pic"><br><br>

        <form action="UpdateProfile" method="post" enctype="multipart/form-data">
            <label>Name:</label><br>
            <input type="text" name="name" value="<%= student.getName() %>" required><br><br>

            <label>Email:</label><br>
            <input type="email" name="email" value="<%= student.getEmail() %>" required><br><br>

            <label>Password:</label><br>
            <input type="password" name="password" value="<%= student.getPassword() %>" required><br><br>

            <label>Update Profile Picture:</label><br>
            <input type="file" name="profilePicture" accept="image/*"><br><br>

            <button type="submit">Update Profile</button>
        </form>
    </div>

    <script>
        function toggleDarkMode() {
            document.body.classList.toggle('dark-mode');
        }
    </script>
</body>
</html>
