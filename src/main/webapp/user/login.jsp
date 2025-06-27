<%--<%@page contentType="text/html" pageEncoding="UTF-8"%>--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <link rel="icon" type="image/x-icon" href="/uploads/base/favicon.png">
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Login</title>

        <!-- External CSS -->
        <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="/user/assets/css/style.css" />
        <link rel="stylesheet" href="/user/assets/css/base.css" />
        <link rel="stylesheet" href="/user/assets/css/footer.css">
        <link rel="stylesheet" href="/user/assets/css/account.css">
        <link rel="stylesheet" href="/user/assets/css/form.css">
        <link rel="stylesheet" href="/user/assets/css/toast.css">
        <link rel="stylesheet" href="/user/assets/css/responsive.css" />

        <!-- Google Sign-In API -->
        <script src="https://accounts.google.com/gsi/client" async defer></script>
    </head>
    <body>
        <%@include file="components/header.jsp" %>
        <div id="toast"></div>

        <main>
            <section class="img-account"></section>

            <section class="all-box-create-log">
                <section class="form-inf">
                    <!-- Tabs -->
                    <ul class="nav nav-pills nav-justified mb-3">
                        <li class="nav-item-auth" role="presentation">
                            <a class="nav-link-auth active text-uppercase" id="tab-login">Login</a>
                        </li> 
                        <li class="nav-item-auth" role="presentation">
                            <a href="/auth/register" class="nav-link-auth text-uppercase">Register</a>
                        </li> 
                    </ul>

                    <!-- Login Form -->
                    <div class="tab-content" id="login-active-show">
                        <div class="tab-pane fade show active" id="pills-login" role="tabpanel" aria-labelledby="tab-login">
                            <form id="form-login" action="/auth/login" method="post">
                                <div class="form-outline form-group mb-4">
                                    <label for="loginName">Username</label>
                                    <input type="text" id="loginName" name="loginName" class="form-control" />
                                    <span class="message_error"></span>
                                </div>
                                <div class="form-outline form-group mb-4">
                                    <label for="loginPassword">Password</label>
                                    <input type="password" id="loginPassword" name="loginPassword" class="form-control" />
                                    <span class="message_error"></span>
                                </div>
                                <div class="row mb-4 box-config-account">
                                    <div class="col-6 d-flex justify-content-center">
                                        <div class="form-check mb-3 mb-md-0">
                                            <input name="remember" class="form-check-input" type="checkbox" id="loginCheck" checked />
                                            <label class="form-check-label" for="loginCheck"> Remember me </label>
                                        </div>
                                    </div>
                                    <div class="col-6 d-flex justify-content-center">
                                        <a href="/forget-password">Forgot password?</a>
                                    </div>
                                </div>
                                <button id="login-btn" type="submit" name="submitLogin" class="btn btn-primary btn-submit-form">Sign in</button>
                            </form>

                            <!-- Google Sign-In Button -->
                            <div class="mt-3 text-center" id="google-button"></div>
                        </div>
                    </div>
                </section>
            </section>
        </main>

        <%@include file="components/footer.jsp" %>

        <!-- Scripts -->
        <script src="https://code.jquery.com/jquery-3.7.0.js"
                integrity="sha256-JlqSTELeR4TLqP0OG9dxM7yDPqX1ox/HfgiSLBj8+kM="
        crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="/user/assets/js/app.js"></script>
        <script src="/user/assets/js/validator.js"></script>
        <script src="/user/assets/js/toast.js"></script>

        <!-- JSTL Toast Messages -->
        <c:if test="${messageSuccessRegister != null}">
            <script>showSuccess("${messageSuccessRegister}");</script>
        </c:if>
        <c:if test="${messageUserAuth != null}">
            <script>showError("${messageUserAuth}");</script>
        </c:if>

        <!-- Google Login JS -->
        <script>
            function handleCredentialResponse(response) {
                const id_token = response.credential;
                fetch('/auth/login', {
                    method: 'POST',
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                    body: 'id_token=' + encodeURIComponent(id_token)
                }).then(res => {
                    if (res.redirected) {
                        window.location.href = res.url;
                    } else {
                        window.location.href = '/';
                    }
                });
            }

            window.onload = function () {
                google.accounts.id.initialize({
                    client_id: "671924345403-ueq7k824a4nnspeeqlkpvi23p4l84523.apps.googleusercontent.com",
                    callback: handleCredentialResponse
                });
                google.accounts.id.renderButton(
                        document.getElementById("google-button"),
                        {theme: "outline", size: "large"}
                );
            };
        </script>

        <!-- Form Validation -->
        <script>
            const messageUsername = "Username must be from 6 to 100 characters",
                    messagePassword = "Password must be at least 8 characters";

            const username = document.getElementById('loginName'),
                    password = document.getElementById('loginPassword');

            const inputsToValidateLogin = [
                {element: username, message: messageUsername, regex: /^[a-zA-Z0-9.,!@#$%&'*+/=?^_]{6,100}$/},
                {element: password, message: messagePassword, regex: /^[a-zA-Z0-9.,!@#$%&'*+/=?^_]{8,}$/}
            ];

            validation(inputsToValidateLogin, document.querySelector("#login-btn"));
        </script>
    </body>
</html>
