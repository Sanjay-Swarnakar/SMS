<%@ page import="model.Course" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="auth.jsp" %>
<% List<Course> courses = (List<Course>) request.getAttribute("courseList"); %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title>Manage Courses</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="stylesheet" href="css/manage_users.css">
	</head>
	<body>
		<header>
			<h2>Manage Courses</h2>
			<button class="btn" onclick="toggleDarkMode()">🌓</button>
		</header>

		<div class="container">
			<button class="btn" onclick="openCourseModal()">➕ Add Course</button>

			<h2>Manage Courses</h2>
			<table>
				<tr>
					<th>ID</th>
					<th>Course Name</th>
					<th>Description</th>
					<th>Credits</th>
					<th class="actions">Actions</th>
				</tr>
				<%
					if (courses != null && !courses.isEmpty()) {
						for (Course course : courses) {
				%>
				<tr>
					<td><%= course.getCourseId()%></td>
					<td><%= course.getCourseName()%></td>
					<td><%= course.getDescription()%></td>
					<td><%= course.getCredits()%></td>
					<td class="actions">
						<button class="btn" onclick="openEditCourseModal(
								'<%= course.getCourseId()%>',
								'<%= course.getCourseName()%>',
								'<%= course.getDescription()%>',
								'<%= course.getCredits()%>')">✏️ Edit</button>
						<form action="DeleteCourse" method="post" style="display:inline;" onsubmit="return confirm('Are you sure you want to delete this course?');">
							<input type="hidden" name="id" value="<%= course.getCourseId()%>">
							<button type="submit" class="btn">🗑️ Delete</button>
						</form>
					</td>
				</tr>
				<%
					}
				} else {
				%>
				<tr><td colspan="5">No courses found.</td></tr>
				<%
					}
				%>
			</table>
		</div>

		<!-- Modal for Add/Edit Course -->
		<div class="modal" id="courseModal">
			<div class="modal-content">
				<h3 id="modalTitle">Add Course</h3>
				<form id="courseForm">
					<input type="text" name="courseName" placeholder="Course Name" required>
					<textarea name="description" placeholder="Description" required></textarea>
					<input type="number" name="credits" placeholder="Credits" required>

					<div class="modal-footer">
						<button type="button" class="btn" onclick="closeModal()">Cancel</button>
						<button type="submit" class="btn">Save</button>
					</div>
				</form>
			</div>
		</div>

		<script>
			const modal = document.getElementById('courseModal');
			const form = document.getElementById('courseForm');
			const title = document.getElementById('modalTitle');

			function openCourseModal() {
				title.textContent = 'Add Course';
				form.reset();
				form.action = 'AddCourse';
				modal.style.display = 'flex';

				const idField = document.getElementById('courseIdField');
				if (idField)
					idField.remove();
			}

			function openEditCourseModal(id, name, description, credits) {
				title.textContent = 'Edit Course';
				form.courseName.value = name;
				form.description.value = description;
				form.credits.value = credits;
				form.action = 'EditCourse';
				modal.style.display = 'flex';

				let idField = document.getElementById('courseIdField');
				if (!idField) {
					idField = document.createElement('input');
					idField.type = 'hidden';
					idField.name = 'id';
					idField.id = 'courseIdField';
					form.appendChild(idField);
				}
				idField.value = id;
			}

			form.addEventListener('submit', function (e) {
				e.preventDefault();
				fetch(form.action, {
					method: 'POST',
					enctype: 'application/x-www-form-urlencoded',
					body: new FormData(form)
				}).then(res => res.text()).then(() => {
					location.reload();
				}).catch(() => alert('Error saving course.'));
			});

			function closeModal() {
				modal.style.display = 'none';
			}

			function toggleDarkMode() {
				document.body.classList.toggle('dark-mode');
				localStorage.setItem('theme', document.body.classList.contains('dark-mode') ? 'dark' : 'light');
			}

			window.onclick = function (e) {
				if (e.target === modal) {
					closeModal();
				}
			};

			document.addEventListener('DOMContentLoaded', function () {
				const savedTheme = localStorage.getItem('theme');
				if (savedTheme === 'dark') {
					document.body.classList.add('dark-mode');
				}
			});
		</script>
	</body>
</html>
