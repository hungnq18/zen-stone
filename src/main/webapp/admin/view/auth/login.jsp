<%-- 
    Document   : login
    Created on : Oct 8, 2023, 9:35:06 PM
    Author     : Le Tan Kim
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <base href="http://localhost:8080/" />
    <title>Login | Admin</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <!-- bootstrap-css -->
    <link rel="stylesheet" href="../admin/assets/css/bootstrap.min.css" >
    <!-- //bootstrap-css -->
    <!-- Custom CSS -->
    <link href="../admin/assets/css/style.css" rel='stylesheet' type='text/css' />
    <link href="../admin/assets/css/style-responsive.css" rel="stylesheet"/>
    <!-- font CSS -->
    <link href='//fonts.googleapis.com/css?family=Roboto:400,100,100italic,300,300italic,400italic,500,500italic,700,700italic,900,900italic' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="../admin/assets/css/myCustom.css">
</head>
<body>
    <div class="log-w3" >
        <div class="w3layouts-main">
            <h2>Sign In Now</h2>
            <form action="/admin/login" method="post">
                <input type="text" class="ggg" name="username" placeholder="USERNAME" required>
                <input type="password" class="ggg" name="password" placeholder="Password" required>
                <h6><a href="/admin/forget-password">Forgot Password?</a></h6>
                <div class="clearfix"></div>
                <input type="submit" value="Sign In" name="login">
                <span class="message-error">${requestScope.message}</span>
            </form>
        </div>
    </div>
    <script src="../admin/assets/js/bootstrap.js"></script>
    <script src="../admin/assets/js/scripts.js"></script>
</body>
</html>