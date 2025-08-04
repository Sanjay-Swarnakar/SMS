<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Edit User</title>
		<meta charset="UTF-8">
		<style>
			body {
				font-family: Arial;
				padding: 20px;
			}
			label, select, input {
				display: block;
				margin: 10px 0;
			}
		</style>
		<script>
			function fetchUserDetails() {
				var username = document.getElementById("userSelect").value;
				if (!username)
					return;

				fetch('GetUserDetails?username=' + encodeURIComponent(username))
						.then(response => response.json())
						.then(data => {
							document.getElementById("fname").value = data.fname || "";
							document.getElementById("lname").value = data.lname || "";
							document.getElementById("email").value = data.email || "";
							// add more as needed
						});
			}
		</script>
	</head>
	<body>
	
		<h1>Edit User</h1>

		<form action="UpdateUser" method="post">
			<label for="userSelect">Select User</label>
			<select id="userSelect" name="username" onchange="fetchUserDetails()" required>
				<option value="">--Select--</option>
				<%-- Filled by servlet using request attribute --%>
				<%
					java.util.List<String> usernames = (java.util.List<String>) request.getAttribute("usernames");
					if (usernames != null) {
						for (String uname : usernames) {
				%>
                <option value="<%= uname%>"><%= uname%></option>
				<%
						}
					}
				%>
			</select>

			<label for="fname">First Name</label>
			<input type="text" id="fname" name="fname">

			<label for="lname">Last Name</label>
			<input type="text" id="lname" name="lname">

			<label for="email">Email</label>
			<input type="email" id="email" name="email">

			<input type="submit" value="Update">
		</form>

	</body>
</html>
