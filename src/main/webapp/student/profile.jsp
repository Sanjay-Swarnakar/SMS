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
		<title>My Profile</title>
		<link rel="stylesheet" href="<%=contextPath%>/css/student.css">
		<link rel="stylesheet" href="<%=contextPath%>/css/profile.css">
		<style>
/*			body.dark-mode {
				background-color: #121212;
				color: #f1f1f1;
			}
			.dark-mode input,
			.dark-mode textarea,
			.dark-mode select {
				background-color: #2c2c2c;
				color: white;
			}
			.dark-mode .container {
				background-color: #1e1e1e;
			}*/
			.profile-pic {
				width: 120px;
				height: 120px;
				border-radius: 50%;
				object-fit: cover;
				margin-bottom: 15px;
			}
/*			.toggle-btn {
				position: fixed;
				top: 15px;
				right: 15px;
				background: none;
				border: none;
				font-size: 20px;
				cursor: pointer;
			}*/
		</style>
	</head>
	<body>
		<jsp:include page="student_nav.jsp" />

		<div class="container">
			<h2>My Profile</h2>
			<img src="<%= profilePic %>" alt="Profile Picture" class="profile-pic">

			<form action="UpdateProfile" method="post" enctype="multipart/form-data">
				<div class="form-group">
					<label>Name:</label>
					<input type="text" name="name" value="<%= student.getName() %>" required>
				</div>

				<div class="form-group">
					<label>Email:</label>
					<input type="email" name="email" value="<%= student.getEmail() %>" required>
				</div>

				<div class="form-group">
					<label>Password:</label>
					<input type="password" name="password" value="<%= student.getPassword() %>" required>
				</div>

				<div class="form-group">
					<label>Update Profile Picture:</label>
					<input type="file" name="profilePicture" accept="image/*">
				</div>

				<div class="form-group">
					<button type="submit">Update Profile</button>
				</div>
			</form>
		</div>

		<jsp:include page="footer.jsp" />
	</body>
</html>
