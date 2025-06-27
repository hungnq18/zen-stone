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
                    <c:set var="voucher" value="${currentVoucher}"></c:set>
                        <div class="panel-body">
                            <div class=" form">
                                <form class="cmxform form-horizontal" method="post" action="/admin/voucher">
                                    <div class="form-group ">
                                        <label for="cname" class="control-label col-lg-3">Name (required)</label>
                                        <div class="col-lg-6">
                                            <input id="name" value="${voucher.name}" class=" form-control" name="name" required>
                                        <span class="message_error"></span>
                                        <input value="${voucher.id}" type="hidden" name="id">
                                    </div>
                                </div>
                                <div class="form-group ">
                                    <label for="cname" class="control-label col-lg-3">Code(From 3-15 character) </label>
                                    <div class="col-lg-6">
                                        <input id="code" value="${voucher.code}" class=" form-control" name="code" required>
                                        <span class="message_error"></span>
                                    </div>
                                </div>
                                <div class="form-group ">
                                    <label for="cname" class="control-label col-lg-3">Type of voucher</label>
                                    <div class="col-lg-6">
                                        <select name="type" class="form-control" readonly>
                                            <option value="1"
                                                    <c:if test="${voucher.type == 1}">selected</c:if>>By price of bill</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group ">
                                        <label for="cname" class="control-label col-lg-3">Value of voucher</label>
                                        <div class="col-lg-6">
                                            <input id="value" value="${voucher.value}" class=" form-control" name="value" required>
                                        <span class="message_error"></span>
                                    </div>
                                </div>
                                <div class="form-group ">
                                    <label for="cname" class="control-label col-lg-3">Value limit of voucher</label>
                                    <div class="col-lg-6">
                                        <input id="limit" value="${voucher.limit}" min="${voucher.value}" class=" form-control" name="limit" required>
                                        <span class="message_error"></span>
                                    </div>
                                </div>
                                <div class="form-group ">
                                    <label for="cname" class="control-label col-lg-3">Start</label>
                                    <div class="col-lg-6">
                                        <input id="start" type="date" value="${voucher.start}" class=" form-control" name="start" required>
                                        <span class="message_error"></span>
                                    </div>
                                </div>
                                <div class="form-group ">
                                    <label for="cname" class="control-label col-lg-3">End</label>
                                    <div class="col-lg-6">
                                        <input id="end" type="date" value="${voucher.end}" class=" form-control" name="end" required>
                                        <span class="message_error"></span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-3">Status</label>
                                    <div class="col-lg-6">
                                        <select name="status" class="form-control">
                                            <option value="1"
                                                    <c:if test="${voucher.status == 1}">selected</c:if>
                                                        >Active
                                                    </option>
                                                    <option value="0"
                                                    <c:if test="${voucher.status == 0}">selected</c:if>>Hidden</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-lg-offset-3 col-lg-6">
                                            <button id="btn-update-voucher" class="btn btn-primary" name="btn-update-voucher" type="submit">Save</button>
                                        </div>
                                    </div>
                                    <a class="btn btn-info btn-back-to-page" href="/admin/voucher"><< Back to voucher</a>
                                </form>
                            </div>
                        </div>
                    </section>
                </div>
            </div>
        </section>
        <script src="/admin/assets/js/validation.js"></script>
        <script>
            // validation form
            const name = document.getElementById('name'),
                    code = document.getElementById('code'),
                    value = document.getElementById('value'),
                    limit = document.getElementById('limit'),
                    start = document.getElementById('start'),
                    end = document.getElementById('end');
            const messageName = "Name product can not empty",
                    messageCode = "Code must be from 3 to 15 character",
                    messageValue = "Value must be a number",
                    messageLimit = "Limit must be a number and less than value",
                    messageStart = "Date start can not empty",
                    messageEnd = "Date end can not empty";


            // array to save all input to check, message show error of each and a string regex to check 
            const inputsToValidate = [
                {element: name, message: messageName, regex: /^.{1,}$/},
                {element: code, message: messageCode, regex: /^[a-zA-Z0-9]{3,15}$/},
                {element: value, message: messageValue, regex: /^[0-9]+(?:\.[0-9]+)?$/},
                {element: limit, message: messageLimit, regex: /^[0-9]+(?:\.[0-9]+)?$/},
                {element: start, message: messageStart, regex: false},
                {element: end, message: messageEnd, regex: false}
            ];
            validation(inputsToValidate, document.querySelector("#btn-update-voucher"));
        </script>
    <%@include file="../components/footer.jsp" %>
