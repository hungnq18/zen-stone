<%-- 
    Document   : updateCategory
    Created on : Oct 1, 2023, 12:23:25 PM
    Author     : Le Tan Kim
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../components/header.jsp" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<section id="main-content">
    <section class="wrapper">
        <div class="row">
            <div class="col-lg-12">
                <section class="panel">
                    <header class="panel-heading">
                        Update account
                    </header>
                    <c:set var="account" value="${account}"></c:set>
                        <div class="panel-body">
                            <div class=" form">
                                <form class="cmxform form-horizontal" method="post" action="/admin/account">
                                    <div class="form-group">
                                        <div class="control-label col-lg-3">
                                            <label>Full name</label>
                                        </div>
                                        <div class="col-lg-6">
                                            <input value="${account.id}" type="hidden" name="id">
                                        <input value="${account.fullname}" id="fullname" required type="text" name="fullname" class="form-control">
                                        <span class="message_error"></span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="control-label col-lg-3">
                                        <label>Email</label>
                                    </div>
                                    <div class="col-lg-6">
                                        <input value="${account.email}" id="email" required type="text" name="email" class="form-control">
                                        <span class="message_error"></span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="control-label col-lg-3">
                                        <label>Phone</label>
                                    </div>
                                    <div class="col-lg-6">
                                        <input value="${account.phone}" id="phone" required type="text" name="phone" class="form-control">
                                        <span class="message_error"></span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="control-label col-lg-3">
                                        <label>Username</label>
                                    </div>
                                    <div class="col-lg-6">
                                        <input value="${account.username}" id="username" readonly="true" type="text" name="username" class="form-control">
                                        <span class="message_error"></span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="control-label col-lg-3">
                                        <label>Password</label>
                                    </div>
                                    <div class="col-lg-6">
                                        <input value="${account.password}" type="hidden" name="oldPassword">
                                        <input id="password" type="password" name="password" class="form-control">
                                        <span class="message_error"></span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-3">Role</label>
                                    <div class="col-lg-6">
                                        <select name="role" class="form-control">
                                            <option value="1"
                                                    <c:if test="${account.role == 2}">selected</c:if>
                                                        >High admin
                                                    </option>
                                                    <c:if test="${account.role == 1}">selected</c:if>
                                                        >Admin
                                                    </option>
                                                    <option value="0"
                                                    <c:if test="${account.role == 0}">selected</c:if>>User</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-lg-3">Status</label>
                                        <div class="col-lg-6">
                                            <select name="status" class="form-control">
                                                <option value="1"
                                                <c:if test="${account.status == 1}">selected</c:if>
                                                    >Active
                                                </option>
                                                <option value="0"
                                                <c:if test="${account.status == 0}">selected</c:if>>Hidden</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-lg-offset-3 col-lg-6">
                                            <button id="update-account" name="btn-update-account" type="submit" class="btn btn-group-justified btn-primary">Save</button>
                                        </div>
                                    </div>
                                </form>
                                <a class="btn btn-info btn-back-to-page" href="/admin/account"><< Back to account</a>
                            </div>
                        </div>
                    </section>
                </div>
            </div>
        </section>
        <script src="/admin/assets/js/validation.js"></script>
        <script>
            // validation form
            const fullname = document.getElementById('fullname'),
                    email = document.getElementById('email'),
                    phone = document.getElementById('phone'),
                    username = document.getElementById('username'),
                    password = document.getElementById('password');
            const messageUsername = "Username must be form 6 to 100 character",
                    messagePassword = "Password must greater 8 character",
                    messageFullname = "Full name can not empty",
                    messageEmail = "Email is not valid. Ex: domain.@gmail.com",
                    messagePhone = "Phone is not valid. Ex: 0865341745";

            // array to save all input to check, message show error of each and a string regex to check 
            const inputsToValidate = [
                {element: fullname, message: messageFullname, regex: /^.{1,}$/},
                {element: email, message: messageEmail, regex: /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|.(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/},
                {element: phone, message: messagePhone, regex: /^[0][0-9]{9}$/},
                {element: username, message: messageUsername, regex: /^[a-zA-Z0-9.,!#$%&'*+/=?^_]{6,100}$/},
                {element: password, message: messagePassword, regex: /^(?:[a-zA-Z0-9.,!#$%&'*+=?^_]{8,}|)$/}
            ];
            validation(inputsToValidate, document.querySelector("#update-account"));
        </script>                                          
    <%@include file="../components/footer.jsp" %>
