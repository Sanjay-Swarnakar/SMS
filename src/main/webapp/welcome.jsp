<%-- 
    Document   : welcome
    Created on : May 6, 2025, 10:27:18 PM
    Author     : Sanjay Swarnakar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>SMS - Unknown User</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <style>
        body {
            margin: 0;
            background-image: url('images/wallpaper2you_115591.jpg');
            background-size: cover;
            background-attachment: fixed;
            background-repeat: no-repeat;
        }
        
        .wrapper {
            display: flex;
            justify-content: center;   /* Horizontal center */
            align-items: center;       /* Vertical center */
            height: 100vh;             /* Full height of the viewport */
        }

        .center-box {
            background-color: lightcoral;
            opacity: 100%;
            border-radius: 6px;
            padding: 20px;
        }
        
        h1 {
            text-align: center;
        }
        </style>
    </head>
    <body>
        <div class="wrapper">
            <div class="center-box">
                <h1>Login Successful!! <br>
                    But User has not assigned any role. <br>
                    Please contact admin.
                </h1>
            </div>
        </div>
    </body>
</html>
