<%-- 
    Document   : forgetPassword
    Created on : Oct 30, 2023, 2:12:43 PM
    Author     : Le Tan Kim
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <link rel="icon" type="image/x-icon" href="/uploads/base/favicon.png">
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Forget password</title>
        <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="/user/assets/css/style.css" />
        <link rel="stylesheet" href="/user/assets/css/base.css" />
        <link rel="stylesheet" href="/user/assets/css/footer.css">
        <link rel="stylesheet" href="/user/assets/css/account.css">
        <link rel="stylesheet" href="/user/assets/css/form.css">
        <link rel="stylesheet" href="/user/assets/css/toast.css">
        <link rel="stylesheet" href="/user/assets/css/responsive.css" />
    </head>
    <body>
        <%@include file="components/header.jsp" %>
        <div id="toast"></div>
        <main>
            <section class="img-account">
                <img src="https://wphix.com/template/dukamarket/dukamarket/assets/img/banner/page-banner-4.jpg" alt="">
                <div class="box-img-account">
                    <h1>Reset password</h1>
                    <ul>
                        <li><a style="color: #666;" href="">Home</a></li>
                        &#47;
                        <li><a href="">Forget password</a></li>
                    </ul>
                </div>
            </section>
            <section class="all-box-create-log">
                <!-- section box information -->
                <section class="form-inf">
                    <!-- Pills navs -->
                    <ul class="nav nav-pills nav-justified mb-3">
                        <li class="nav-item-auth forget-password">
                            <a class="nav-link-auth text-uppercase"  id="tab-login"
                               >Reset password</a>
                        </li>
                    </ul>
                    <!-- Pills navs -->
                    <!-- Pills content -->
                    <div class="tab-content" id="login-active-show">
                        <div class="tab-pane fade show active" id="pills-login" role="tabpanel" aria-labelledby="tab-login">
                            <form method="post" action="/forget-password" id="form-reset">
                                <!-- Email input -->
                                <div class="form-outline form-group mb-4">
                                    <label for="email">Email: </label>
                                    <input type="email" id="email" name="email" class="form-control" />
                                    <span class="message_error"></span>
                                </div>
                                <!-- Submit button -->
                                <button name="btn-forget-password" id="reset-password" type="submit" class="btn btn-primary btn-submit-form">Reset password</button>
                                <!-- Register buttons -->
                            </form>
                        </div>
                    </div>
                </section>
            </section>
        </main>
        <%@include file="components/footer.jsp" %>
        <script src="https://code.jquery.com/jquery-3.7.0.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="/user/assets/js/app.js"></script>
        <script src="/user/assets/js/validator.js"></script>
        <script src="/user/assets/js/toast.js"></script>
        <c:if test="${forgetPasswordMessage != null}">
            <script>showError("${forgetPasswordMessage}");</script>
        </c:if>
        <c:if test="${resetPasswordSuccess != null}">
            <script>showSuccess("${resetPasswordSuccess}");</script>
        </c:if>
        <script>
            const username = document.getElementById('username'),
                    email = document.getElementById('email');
            const messageUsername = "Username must be form 6 to 100 character",
                    messageEmail = "Email is not valid. Ex: domain.@gmail.com";
            const inputsToValidateForget = [
                {element: username, message: messageUsername, regex: /^[a-zA-Z0-9.,!#$%&'*+/=?^_]{6,100}$/},
                {element: email, message: messageEmail, regex: /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|.(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/}
            ];
            validation(inputsToValidateForget, document.querySelector("#reset-password"));
        </script>
    </body>
</html>