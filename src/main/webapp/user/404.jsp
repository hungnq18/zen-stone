<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <!--<base href="http://localhost:8080/" />-->
        <link rel="icon" type="image/x-icon" href="/uploads/base/favicon.png">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>404</title>
        <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css"  rel="stylesheet"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="/user/assets/css/style.css" />
        <link rel="stylesheet" href="/user/assets/css/base.css" />
        <link rel="stylesheet" href="/user/assets/css/footer.css">
        <link rel="stylesheet" href="/user/assets/css/404.css">
        <link rel="stylesheet" href="/user/assets/css/responsive.css" />
    </head>
    <body>
        <%@include file="components/header.jsp" %>
        <main>
            <div class="container">
                <div class="wrapper-status">
                    <div class="img-status">
                        <img src="https://wphix.com/template/dukamarket/dukamarket/assets/img/banner/404.png" alt="">
                    </div>
                    <div class="box-content-status">
                        <h1>Page Not Found</h1>
                        <p class="message-status">
                            Sorry, the page you've requested is not available. Please try searching for something else or return to Homepage.
                        </p>
                        <a href="/" class="back-to-page">
                            Return to home page
                        </a>
                    </div>
                </div>
            </div>
        </main>
        <%@include file="components/footer.jsp" %>
    </body>
</html>