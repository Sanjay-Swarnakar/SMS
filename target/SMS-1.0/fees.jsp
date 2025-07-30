<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Student Management System</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        body {
            background: url("images/wallpaper2you_115591.jpg") no-repeat fixed center center;
            background-size: cover;
            font-family: Arial, Helvetica, sans-serif;
            color: white;
            margin: 0;
            padding: 0;
        }

        header {
            background-color: #993366;
            padding: 20px;
            text-align: center;
        }

        header h1 {
            margin: 0;
            font-size: 36px;
        }

        nav {
            display: flex;
            justify-content: center;
            gap: 15px;
            background-color: rgba(0, 0, 0, 0.5);
            padding: 10px;
            flex-wrap: wrap;
        }

        nav a img {
            height: 40px;
        }

        .container {
            max-width: 500px;
            margin: 100px auto;
            background-color: rgba(0, 0, 0, 0.6);
            padding: 30px;
            border-radius: 8px;
        }

        label {
            display: block;
            margin-bottom: 10px;
            font-size: 18px;
        }

        input[type="text"], input[type="submit"], input[type="reset"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: none;
            border-radius: 4px;
            font-size: 16px;
        }

        input[type="submit"], input[type="reset"] {
            background-color: #993366;
            color: white;
            cursor: pointer;
        }

        input[type="submit"]:hover, input[type="reset"]:hover {
            background-color: #b14a7d;
        }

        @media (max-width: 600px) {
            header h1 {
                font-size: 24px;
            }

            nav a img {
                height: 30px;
            }
        }
    </style>
</head>
<body>
    <header>
        <h1>Student Management System</h1>
    </header>

    <nav>
        <a href="login.jsp"><img src="images/nav975619210i.gif" alt="Login"></a>
        <a href="registration.jsp"><img src="images/nav975619211i.gif" alt="Register"></a>
        <a href="fees.jsp"><img src="images/nav975619212i.gif" alt="Fees"></a>
        <a href="exam.jsp"><img src="images/nav975619213i.gif" alt="Exam"></a>
        <a href="login.jsp"><img src="images/nav975619214i.gif" alt="Logout"></a>
    </nav>

    <div class="container">
        <form method="POST">
            <label for="studentId">Student's ID:</label>
            <input type="text" id="studentId" name="Id" required>

            <input type="submit" name="Submit" value="Submit">
            <input type="reset" name="Reset" value="Reset">
        </form>
    </div>
</body>
</html>
