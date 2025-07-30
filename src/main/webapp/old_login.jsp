<%-- 
    Document   : login
    Created on : May 6, 2025, 4:53:23 PM
    Author     : Sanjay Swarnakar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>old Login Page</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
    <div align=center>
        <h1>User Login</h1>
        <form action="Login" method="post">
            <table>
                <tr><td>Enter Name: </td> <td> <input type="text" name="name"> </td> </tr>
                <tr><td>Enter Password: </td> <td> <input type="password" name="password"> </td> </tr>
                <tr><td><input type="submit" value="login"> </td> <td> <input type="reset"> </td> </tr>
            </table>
        </form>
    </div>
    </body>
</html>
