<%@ page contentType="text/html; charset=UTF-8" language="java" %> 
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>Login - Student Management System</title>
		<link rel="stylesheet" href="css/login.css">
		<script>
			function validateForm() {
				const username = document.forms["loginForm"]["username"].value.trim();
				const password = document.forms["loginForm"]["password"].value.trim();

				if (username === "" || password === "") {
					alert("Username and Password cannot be empty.");
					return false;
				}
				return true;
			}
		</script>
	</head>
	<body>
		<div class="login-container">
			<h2>Student Management System</h2>

			<%
				String error = (String) request.getAttribute("errorMessage");
				if (error != null) {
			%>
			<div class="error"><%= error%></div>
			<%
				}
			%>

			<form name="loginForm" action="Login1" method="post" onsubmit="return validateForm();">
				<label for="username">Username:</label>
				<input name="username" type="text" id="username">
				<label for="password">Password:</label>
				<input name="password" type="password" id="password">
				<input type="submit" value="Login">
			</form>

			<div class="links">
				<a href="registration.jsp">Apply New Account</a>
				<a href="">Forgot Password?</a>
			</div>
		</div>

	</body>
</html>