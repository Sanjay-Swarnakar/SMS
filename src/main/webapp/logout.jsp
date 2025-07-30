<%-- 
    Document   : logout
    Created on : May 9, 2025, 12:06:04 AM
    Author     : Sanjay Swarnakar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    session.invalidate();
    response.sendRedirect("login.jsp");
%>