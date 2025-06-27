<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--<%@page contentType="text/html" pageEncoding="UTF-8"%>--%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <link rel="icon" type="image/x-icon" href="/uploads/base/favicon.png">
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Register</title>
        <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
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
            <section class="img-account">
<!--                <img src="https://wphix.com/template/dukamarket/dukamarket/assets/img/banner/page-banner-4.jpg" alt="">
                <div class="box-img-account">
                    <h1>MY ACCOUNT</h1>
                    <ul>
                        <li><a style="color: #666;" href="">Home</a></li>
                        &#47;
                        <li><a href="">My account</a></li>
                    </ul>
                </div>-->
            </section>
            <section class="all-box-create-log">
                <!-- section box information -->
                <section class="form-inf">
                    <!-- Pills navs -->
                    <ul class="nav nav-pills nav-justified mb-3">
                        <li class="nav-item-auth" role="presentation">
                            <a href="/auth/login" class="nav-link-auth" id="tab-login">Đăng nhập</a>
                        </li> 
                        <li class="nav-item-auth" role="presentation">
                            <a class="nav-link-auth active text-uppercase" id="tab-login">Đăng ký</a>
                        </li> 
                    </ul>
                    <!-- Pills content -->
                    <div class="container-subnav" id="register-active-show">
                        <form action="/auth/register" class="form__login" method="post" id='form-register'>
                            <div class="form-group">
                                <label for="fullname">Tên đăng nhập</label>
                                <input id='fullname' name='username' name="signUpName" type="text" placeholder="" class='form-control'>
                                <span class="message_error"></span>
                            </div>
                            <div class="form-group">
                                <label for="email">Email</label>
                                <input id='email' name='email' type="text" placeholder="" class='form-control'>
                                <span class="message_error"></span>
                            </div>
                            <div class="form-group">
                                <label for="phone">Số điện thoại</label>
                                <input id='phone' name='phone' type="text" placeholder="" class='form-control'>
                                <span class="message_error"></span>
                            </div>
                            <div class="form-group">
                                <label for="password">Mật khẩu</label>
                                <input id='password' name='password' type="password"  placeholder="" class='form-control'>
                                <span class="message_error"></span>
                            </div>
                            <button id="register-btn" class="form-submit btn btn-primary btn-submit-form" name="register" type="submit">Đăng kí</button>

                        </form>
                        
                    </div>
                                                                                                <!-- Google Sign-In Button -->
                            <div class="mt-3 text-center" id="google-button"></div>
                </section>
            </section>
        </main>
        <%@include file="components/footer.jsp" %>
        <script
            src="https://code.jquery.com/jquery-3.7.0.js"
            integrity="sha256-JlqSTELeR4TLqP0OG9dxM7yDPqX1ox/HfgiSLBj8+kM="
            crossorigin="anonymous"
        ></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="/user/assets/js/app.js"></script>
        <script src="/user/assets/js/toast.js"></script>
        <script src="/user/assets/js/validator.js"></script>
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
        <script>
            <c:if test="${messageFailRegister != null}">
            showError("${messageFailRegister}");
            </c:if>
                
            
// validation form
            const messageUsername = "Username must be form 6 to 100 character",
                    messagePassword = "Password must greater 8 character",
                    messageFullname = "Full name can not empty",
                    messageEmail = "Email is not valid. Ex: domain.@gmail.com",
                    messagePhone = "Phone is not valid. Ex: 0865341745";

            const usernameSignUp = document.getElementById('fullname'),
                    passwordSignUp = document.getElementById('password'),
                    passwordASignUp = document.getElementById('password_confimation'),
                    emailSignUp = document.getElementById('email'),
                    phoneSignUp = document.getElementById('phone');
            const inputsToValidate = [
                {element: usernameSignUp, message: messageUsername, regex: /^[a-zA-Z0-9.,!#$%&'*+/=?^_]{6,100}$/},
                {element: passwordSignUp, message: messagePassword, regex: /^[a-zA-Z0-9.,!#$%&'*+/=?^_]{8,}$/},
                {element: emailSignUp, message: messageEmail, regex: /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|.(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/},
                {element: phoneSignUp, message: messagePhone, regex: /^[0][0-9]{9}$/}
            ];
            validation(inputsToValidate, document.querySelector("#register-btn"));
        </script>
    </body>
</html>