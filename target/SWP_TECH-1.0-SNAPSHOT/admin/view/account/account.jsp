<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../components/header.jsp" %>
<section id="main-content">
    <section class="wrapper">
        <div class="wrapper-box-add">
            <div class="row">
                <div class="col-lg-12">
                    <div id="toast"></div>
                    <section class="panel">
                        <div class="panel-body">
                            <div class="position-center">
                                <div class="text-center">
                                    <a href="#myModal" data-toggle="modal" class="btn btn-success">
                                        Add new
                                    </a>
                                </div>
                                <div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="myModal" class="modal fade">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">×</button>
                                                <h4 class="modal-title">Add account</h4>
                                            </div>
                                            <div class="modal-body">
                                                <form role="form" method="post" action="/admin/account">
                                                    <div class="form-group">
                                                        <label>Full name</label>
                                                        <input id="fullname" required type="text" name="fullname" class="form-control">
                                                        <span class="message_error"></span>
                                                    </div>
                                                    <div class="form-group">
                                                        <label>Email</label>
                                                        <input id="email" required type="text" name="email" class="form-control">
                                                        <span class="message_error"></span>
                                                    </div>
                                                    <div class="form-group">
                                                        <label>Phone</label>
                                                        <input id="phone" required type="text" name="phone" class="form-control">
                                                        <span class="message_error"></span>
                                                    </div>
                                                    <div class="form-group">
                                                        <label>Username</label>
                                                        <input id="username" required type="text" name="username" class="form-control">
                                                        <span class="message_error"></span>
                                                    </div>
                                                    <div class="form-group">
                                                        <label>Password</label>
                                                        <input id="password" required type="password" name="password" class="form-control">
                                                        <span class="message_error"></span>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="control-label">Role</label>
                                                        <select name="role" class="form-control">
                                                            <option value="2">High admin</option>
                                                            <option value="1">Admin</option>
                                                            <option value="0">Normal user</option>
                                                        </select>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="control-label">Status</label>
                                                        <select name="status" class="form-control">
                                                            <option value="1">Active</option>
                                                            <option value="0">Hidden</option>
                                                        </select>
                                                    </div>
                                                    <button id="add-account" name="btn-add-account" type="submit" class="btn btn-group-justified btn-primary">Add</button>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>
                </div>
            </div>
        </div>
        <div class="table-agile-info">
            <div class="panel panel-default">
                <div class="panel-heading">
                    Account
                </div>
                <div class="row w3-res-tb">
                    <div class="col-sm-5 m-b-xs">
                        <select onchange="filterStatus('account', this.value)" class="input-sm form-control w-sm inline v-middle">
                            <option value="-1">All</option>
                            <option value="1">Active</option>
                            <option value="0">Hidden</option>
                        </select>   
                    </div>
                </div>
                <div class="table-responsive" style="padding: 5px;">
                    <script>
                        $(document).ready(function () {
                        $("#category").DataTable();
                        });
                    </script>
                    <form method="post" action="/admin/account">
                        <div class="wrapper-box-btn">
                            <button name="btn-delete-accounts" onclick="return confirm('Are you sure to delete?')" class="btn btn-sm btn-danger">
                                Delete all checked
                            </button>
                        </div>
                        <table id="category" class="table table-striped b-t b-light">
                            <thead>
                                <tr>
                                    <th style="width:20px;">
                                        <label class="i-checks all-check m-b-none">
                                            <input type="checkbox"><i></i>
                                        </label>
                                    </th>
                                    <th>Full name</th>
                                    <th>Email</th>
                                    <th>Phone</th>
                                    <th>Username</th>
                                    <th>Date at</th>
                                    <th>Role</th>
                                    <th>Status</th>
                                    <th style="width:30px;">Action</th>
                                </tr>
                            </thead>
                            <tbody class="render-data">
                                <c:forEach items="${accounts}" var="account">
                                    <c:if test="${usernameAdmin != null && usernameAdmin != account.username}">
                                        <tr>
                                            <td><input type="checkbox" name="delete-account-item" value="${account.id}"></td>
                                            <td>${account.fullname}</td>
                                            <td>${account.email}</td>
                                            <td>${account.phone}</td>
                                            <td>${account.username}</td>
                                            <td><span class="text-ellipsis">${account.date}</span></td>
                                            <td>
                                                <c:if test="${account.role == 2}">
                                                    <span class="label label-success">High Admin</span>
                                                </c:if>
                                                <c:if test="${account.role == 1}">
                                                    <span class="label label-info">Admin</span>
                                                </c:if>
                                                <c:if test="${account.role == 0}">
                                                    <span class="label label-warning">User</span>
                                                </c:if>
                                            </td>
                                            <td>
                                                <c:if test="${account.status == 1}">
                                                    <span class="label label-success">Active</span>
                                                </c:if>
                                                <c:if test="${account.status == 0}">
                                                    <span class="label label-default">Hidden</span>
                                                </c:if>
                                            </td>
                                            <td class="box-action">
                                                <a href="/admin/account/update/${account.id}" class="active btn-action green">
                                                    <i class="bx bx-edit"></i>
                                                </a>
                                                <a onclick="return confirm('Are you sure to delete?')" 
                                                   href="/admin/account/delete/${account.id}" 
                                                   class="active btn-action orange">
                                                    <i class='bx bx-trash'></i>
                                                </a>
                                            </td>
                                        </tr>
                                    </c:if>
                                </c:forEach>
                            </tbody>
                        </table>
                    </form>
                </div>
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
                                                {element: password, message: messagePassword, regex: /^[a-zA-Z0-9.,!#$%&'*+/=?^_]{8,}$/}
                                                ];
                                                validation(inputsToValidate, document.querySelector("#add-account"));
    </script>
    <%@include file="../components/footer.jsp" %>