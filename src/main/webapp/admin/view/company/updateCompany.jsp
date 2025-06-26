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
                        Update category
                    </header>
                    <c:set var="company" value="${currentCompany}"></c:set>
                        <div class="panel-body">
                            <div class=" form">
                                <form class="cmxform form-horizontal" method="post" action="/admin/company">
                                    <div class="form-group ">
                                        <label for="name" class="control-label col-lg-3">Name company</label>
                                        <div class="col-lg-6">
                                            <input value="${company.name}" class=" form-control" id="name" name="name" required>
                                        <input value="${company.id}" type="hidden" name="id">
                                        <span class="message_error"></span>
                                    </div>
                                </div>
                                <div class="form-group ">
                                    <label for="address" class="control-label col-lg-3">Address</label>
                                    <div class="col-lg-6">
                                        <input value="${company.address}" required="" type="text" id="address" name="address" class="form-control">
                                        <span class="message_error"></span>
                                    </div>
                                </div>
                                <div class="form-group ">
                                    <label for="phone" class="control-label col-lg-3">Phone</label>
                                    <div class="col-lg-6">
                                        <input value="${company.getPhone()}" required="" type="text" id="phone" name="phone" class="form-control">
                                        <span class="message_error"></span>
                                    </div>
                                </div>
                                <div class="form-group ">
                                    <label for="email" class="control-label col-lg-3">Email</label>
                                    <div class="col-lg-6">
                                        <input value="${company.email}" required="" type="text" id="email" name="email" class="form-control">
                                        <span class="message_error"></span>
                                    </div>
                                </div>
                                <div class="form-group ">
                                    <label for="introduction" class="control-label col-lg-3">Introduction</label>
                                    <div class="col-lg-6">
                                        <textarea required="" name="introduction" id= "ck-editor1" class="form-control introduction">
                                            ${company.introduce}
                                        </textarea>
                                        <span class="message_error"></span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-3">Status</label>
                                    <div class="col-lg-6">
                                        <select name="status" class="form-control">
                                            <option value="1"
                                                    <c:if test="${company.status == 1}">selected</c:if>
                                                        >Active
                                                    </option>
                                                    <option value="0"
                                                    <c:if test="${company.status == 0}">selected</c:if>>Hidden</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-lg-offset-3 col-lg-6">
                                            <button class="btn btn-primary" id="btn-update-new-company" name="btn-update-company" type="submit">Save</button>
                                        </div>
                                    </div>
                                </form>
                                <a class="btn btn-info btn-back-to-page" href="/admin/company"><< Back to company</a>
                            </div>
                        </div>
                    </section>
                </div>
            </div>
        </section>
        <script src="./admin/assets/js/validation.js"></script>
        <script src="./admin/ckeditor/ckeditor.js"></script> 
        <script>
            CKEDITOR.replace('ck-editor1');
            // validation form
            const name = document.getElementById('name'),
                    email = document.getElementById('email'),
                    phone = document.getElementById('phone'),
                    address = document.getElementById('address');
            const  messageAddress = "Address can not empty",
                    messagename = "Name company can not empty",
                    messageEmail = "Email is not valid. Ex: domain.@gmail.com",
                    messagePhone = "Phone is not valid. Ex: 0865341745";

            // array to save all input to check, message show error of each and a string regex to check 
            const inputsToValidate = [
                {element: name, message: messagename, regex: /^.{1,}$/},
                {element: email, message: messageEmail, regex: /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|.(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/},
                {element: phone, message: messagePhone, regex: /^[0][0-9]{9}$/},
                {element: address, message: messageAddress, regex: /^.{1,}$/}
            ];
            validation(inputsToValidate, document.querySelector("#btn-update-new-company"));
        </script>
    <%@include file="../components/footer.jsp" %>
