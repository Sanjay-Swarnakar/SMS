<%@page import="model.User"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="auth.jsp" %>
<%	List<User> users = (List<User>) request.getAttribute("userList");
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title>Manage Users</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="stylesheet" href="css/manage_users.css">
	</head>
	<body>
		<header>
			<h2>Manage Users</h2>
			<button class="btn" onclick="toggleDarkMode()">🌓</button>
		</header>

		<div class="container">
			<button class="btn" onclick="openModal()">➕ Add User</button>

			<h2>Manage Users</h2>
			<table>
				<tr>
					<th>ID</th>
					<th>Username</th>
					<th>Full Name</th>
					<th>Email</th>
					<th>Phone</th>
					<th>Address</th>
					<th>Role</th>
					<th class="actions">Actions</th>
				</tr>
				<%
					if (users != null && !users.isEmpty()) {
						for (User user : users) {
				%>
				<!-- inside your table row loop -->
				<tr>
					<td><%= user.getId()%></td>
					<td><%= user.getUsername()%></td>
					<td><%= user.getFullname()%></td>
					<td><%= user.getEmail()%></td>
					<td><%= user.getPhone()%></td>
					<td><%= user.getAddress()%></td>
					<td><%= user.getRole()%></td>
					<td class="actions">
						<button class="btn"
								onclick="openEditModal('<%= user.getId()%>', '<%= user.getUsername()%>', '<%= user.getFullname()%>', '<%= user.getRole()%>', '<%= user.getEmail()%>')">
							✏️ Edit
						</button>
						<form action="DeleteUserServlet" method="post" style="display:inline;" onsubmit="return confirm('Are you sure?');">
							<input type="hidden" name="id" value="<%= user.getId()%>">
							<button type="submit" class="btn">🗑️ Delete</button>
						</form>
					</td>
				</tr>
				<%
					}
				} else {
				%>
				<tr><td colspan="8">No users found.</td></tr>
				<%
					}
				%>
			</table>
		</div>

		<!-- Modal for Add/Edit User -->
		<div class="modal" id="userModal">
			<div class="modal-content">
				<h3 id="modalTitle">Add User</h3>
				<form id="userForm">
					<input type="text" name="username" placeholder="Username" required>
					<input type="text" name="fullname" placeholder="Full Name" required>
					<select name="role" required>
						<option value="">Select Role</option>
						<option value="Student">Student</option>
						<option value="Teacher">Teacher</option>
						<option value="Admin">Admin</option>
					</select>
					<input type="email" name="email" placeholder="Email" required>

					<div class="modal-footer">
						<button type="button" class="btn" onclick="closeModal()">Cancel</button>
						<button type="submit" class="btn">Save</button>
					</div>
				</form>
			</div>
		</div>

		<script>
			const modal = document.getElementById('userModal');
			const form = document.getElementById('userForm');
			const title = document.getElementById('modalTitle');

			function openModal() {
				title.textContent = 'Add User';
				form.reset();
				modal.style.display = 'flex';
			}

			function openEditModal() {
				title.textContent = 'Edit User';
				// Pre-fill form here (if needed with JS)
				modal.style.display = 'flex';
			}

			function closeModal() {
				modal.style.display = 'none';
			}

			function confirmDelete() {
				if (confirm('Are you sure you want to delete this user?')) {
					// Trigger delete backend logic
					alert('User deleted (not really)');
				}
			}

			window.onclick = function (e) {
				if (e.target === modal) {
					closeModal();
				}
			};

			function toggleDarkMode() {
				document.body.classList.toggle('dark-mode');
				localStorage.setItem('theme', document.body.classList.contains('dark-mode') ? 'dark' : 'light');
			}

			document.addEventListener('DOMContentLoaded', function () {
				const savedTheme = localStorage.getItem('theme');
				if (savedTheme === 'dark') {
					document.body.classList.add('dark-mode');
				}
			});
		</script>
	</body>
</html>
