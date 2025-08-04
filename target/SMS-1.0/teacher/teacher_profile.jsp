<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="sms.model.Teacher" %>
<%@ include file="/auth.jsp" %>
<%	Teacher teacher = (Teacher) request.getAttribute("teacher");
	if (teacher == null) {
		response.sendRedirect("/SMS/login.jsp");
		return;
	}
	String contextPath = request.getContextPath();
	String profilePic = teacher.getProfilePicture() != null && !teacher.getProfilePicture().isEmpty()
			? contextPath + "/profile_pics/" + teacher.getProfilePicture()
			: contextPath + "/images/default-profile.png";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title>Teacher Profile</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="stylesheet" href="<%= request.getContextPath()%>/css/global.css">
		<link rel="stylesheet" href="<%= request.getContextPath()%>/css/teacher.css">
		<style>
			:root {
				--bg: #f4f6f8;
				--text: #333;
				--card: #fff;
				--primary: #27ae60;
				--header-bg: #27ae60;
				--header-text: white;
			}
			body.dark-mode {
				--bg: #1e1e1e;
				--text: #ddd;
				--card: #2c2c2c;
				--primary: #58d68d;
				--header-bg: #121212;
				--header-text: #ddd;
			}
/*			body {
				margin: 0;
				background-color: var(--bg);
				color: var(--text);
				font-family: 'Segoe UI', sans-serif;
			}
			header {
				background: var(--header-bg);
				color: var(--header-text);
				padding: 10px 20px;
				display: flex;
				justify-content: space-between;
				align-items: center;
			}*/
			.container {
				max-width: 600px;
				margin: 30px auto;
				background: var(--card);
				padding: 20px;
				border-radius: 8px;
				box-shadow: 0 2px 6px rgba(0,0,0,0.1);
			}
			h2 {
				text-align: center;
			}
			input[type="text"], input[type="email"], input[type="password"], input[type="file"] {
				width: 100%;
				padding: 10px;
				margin: 8px 0 16px;
				border: 1px solid #ccc;
				border-radius: 4px;
			}
			button {
				background-color: var(--primary);
				color: white;
				padding: 10px 20px;
				border: none;
				border-radius: 4px;
				cursor: pointer;
				width: 100%;
			}
			button:hover {
				opacity: 0.9;
			}
			.profile-pic {
				width: 100px;
				height: 100px;
				object-fit: cover;
				border-radius: 50%;
				display: block;
				margin: 0 auto 20px;
			}			
		</style>
	</head>
	<body>
		<%@ include file="teacher_sidebar.jsp" %>
			<div class="container">
				<h2>Update Profile</h2>
				<% if (teacher.getProfilePicture() != null) {%>
				<!--<img src="profile_pics/<%= teacher.getProfilePicture()%>" alt="Profile Picture" class="profile-pic">-->
				<img src="<%= profilePic%>" alt="Profile Picture" class="profile-pic"><br><br>
				<% }%>
				<form action="UpdateTeacherProfile" method="post" enctype="multipart/form-data">
					<input type="text" name="name" value="<%= teacher.getName()%>" required placeholder="Full Name">
					<input type="email" name="email" value="<%= teacher.getEmail()%>" required placeholder="Email">
					<input type="password" name="password" value="<%= teacher.getPassword()%>" required placeholder="Password">
					<label for="profilePicture">Profile Picture:</label>
					<input type="file" name="profilePicture" id="profilePicture">
					<button type="submit">Update Profile</button>
				</form>
			</div>
		<jsp:include page="footer.jsp" />
	</body>
</html>
