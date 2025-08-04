<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/auth.jsp" %>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8" />
		<title>Messages</title>
		<link rel="stylesheet" href="<%= request.getContextPath()%>/css/global.css">
		<link rel="stylesheet" href="<%= request.getContextPath()%>/css/teacher.css">
		<style>
			.container {
				max-width: 900px;
				margin: auto;
				padding: 20px;
			}
			.contacts-list {
				width: 30%;
				float: left;
				border-right: 1px solid #ccc;
				padding-right: 10px;
			}
			.contacts-list h3 {
				margin-top: 0;
			}
			.contact-item {
				padding: 8px;
				border-bottom: 1px solid #eee;
				cursor: pointer;
			}
			.contact-item:hover {
				background-color: #f0f0f0;
			}
			.messages-section {
				width: 65%;
				float: right;
			}
			.message {
				margin: 10px 0;
				padding: 8px 12px;
				border-radius: 8px;
				max-width: 70%;
			}
			.sent {
				background-color: #27ae60;
				color: white;
				margin-left: auto;
				text-align: right;
			}
			.received {
				background-color: #ddd;
				color: black;
				margin-right: auto;
			}
			.message-time {
				font-size: 0.8em;
				color: #666;
				margin-top: 2px;
			}
			.message-input {
				margin-top: 20px;
			}
			textarea {
				width: 100%;
				height: 80px;
			}
			button {
				padding: 10px 15px;
				background-color: #27ae60;
				color: white;
				border: none;
				cursor: pointer;
			}
			button:hover {
				background-color: #219150;
			}
			.clear {
				clear: both;
			}
		</style>
	</head>
	<body>
		<%@ include file="teacher_sidebar.jsp" %>
		<div class="container">
			<h2>Messages</h2>

			<div class="contacts-list">
				<h3>Contacts</h3>
				<c:if test="${empty contacts}">
					<p>No contacts available.</p>
				</c:if>
				<c:forEach var="contact" items="${contacts}">
					<div class="contact-item">
						<a href="Messages?receiverType=${contact.receiverType} & receiverId=${contact.receiverId}">
							${contact.messageText}
						</a>
					</div>
				</c:forEach>
			</div>

			<div class="messages-section">
				<c:choose>
					<c:when test="${not empty messages}">
						<div>
							<c:forEach var="msg" items="${messages}">
								<div class="message ${msg.senderId == sessionScope.id ? 'sent' : 'received'}">
									<div>${msg.messageText}</div>
									<div class="message-time">${msg.sentAt}</div>
								</div>
							</c:forEach>
						</div>

						<div class="message-input">
							<form method="post" action="Messages">
								<input type="hidden" name="receiverType" value="${receiverType}" />
								<input type="hidden" name="receiverId" value="${receiverId}" />
								<textarea name="messageText" required placeholder="Type your message..."></textarea>
								<button type="submit">Send</button>
							</form>
						</div>
					</c:when>
					<c:otherwise>
						<p>Select a contact to start messaging.</p>
					</c:otherwise>
				</c:choose>
			</div>

			<div class="clear"></div>
		</div>
		<%@ include file="footer.jsp" %>
	</body>
</html>
