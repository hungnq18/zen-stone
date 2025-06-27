<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <link rel="icon" type="image/x-icon" href="/uploads/base/favicon.png">
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Order status</title>
        <link
            href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css"
            rel="stylesheet"
            />
        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
            rel="stylesheet"
            />
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
                        <c:if test="${success}">
                            <img src="/uploads/base/send-contact-success.png" alt="Send sucess">
                        </c:if>
                        <c:if test="${fail}">
                            <img src="/uploads/base/send-contact-error.jpg" alt="Send fail">
                        </c:if>
                    </div>
                    <div class="box-content-status">
                        <p class="message-status">
                            <c:if test="${success}">
                                Thank you for contact with us. We will response for you as soon as possible.
                            </c:if>
                            <c:if test="${fail}">
                                I'm sorry the system is busy right now, please try again later.
                            </c:if>

                        </p>
                        <a href="/" class="back-to-page">
                            Go to home
                        </a>
                    </div>
                </div>
            </div>
        </main>
        <%@include file="components/footer.jsp" %>
        <script src="https://code.jquery.com/jquery-3.7.0.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="/user/assets/js/app.js"></script>
    </body>
</html>
