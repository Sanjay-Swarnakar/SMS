<%
    if (session.getAttribute("role") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>