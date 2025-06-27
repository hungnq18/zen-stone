<%-- 
    Document   : category
    Created on : Oct 1, 2023, 12:02:09 AM
    Author     : Le Tan Kim
--%>
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
                                    <div class="modal-dialog" style="width: 80%">>
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">×</button>
                                                <h4 class="modal-title">Add introduction company</h4>
                                            </div>
                                            <div class="modal-body">
                                                <form role="form" method="post" action="/admin/company">
                                                    <div class="form-group">
                                                        <label>Company name</label>
                                                        <input required="" type="text" name="name" id="name" class="form-control">
                                                        <span class="message_error"></span>
                                                    </div>
                                                    <div class="form-group">
                                                        <label>Address</label>
                                                        <input required="" type="text" id="address" name="address" class="form-control">
                                                        <span class="message_error"></span>
                                                    </div>
                                                    <div class="form-group">
                                                        <label>Phone</label>
                                                        <input required="" type="text" id="phone" name="phone" class="form-control">
                                                        <span class="message_error"></span>
                                                    </div>
                                                    <div class="form-group">
                                                        <label>Email</label>
                                                        <input required="" type="text" id="email" name="email" class="form-control">
                                                        <span class="message_error"></span>
                                                    </div>
                                                    <div class="form-group">
                                                        <label>Introduction</label>
                                                        <textarea required="" name="introduction" id= "ck-editor1" class="form-control introduction"></textarea>
                                                        <span class="message_error"></span>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="control-label">Status</label>
                                                        <select name="status" class="form-control">
                                                            <option value="1">Active</option>
                                                            <option value="0">Hidden</option>
                                                        </select>
                                                    </div>
                                                    <button id="btn-add-new-company" name="btn-add-company" type="submit" class="btn btn-group-justified btn-primary">Add</button>
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
                    Introduction company
                </div>
                <div class="row w3-res-tb">
                    <div class="col-sm-5 m-b-xs">
                        <select onchange="filterStatus('company', this.value)" class="input-sm form-control w-sm inline v-middle">
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
                    <form method="post" action="/admin/company">
                        <div class="wrapper-box-btn">
                            <button name="btn-delete-companies" onclick="return confirm('Are you sure to delete?')" class="btn btn-sm btn-danger">
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
                                    <th>Company name</th>
                                    <th>Address</th>
                                    <th>Email</th>
                                    <th>Phone</th>
                                    <th>Introduce</th>
                                    <th>Date</th>
                                    <th>Status</th>
                                    <th style="width:30px;">Action</th>
                                </tr>
                            </thead>
                            <tbody class="render-data">
                                <c:forEach items="${company}" var="company">
                                    <tr>
                                        <td><input type="checkbox" name="delete-company-item" value="${company.id}"></td>
                                        <td>${company.name}</td>
                                        <td><span class="text-ellipsis">${company.address}</span></td>
                                        <td><span class="text-ellipsis">${company.email}</span></td>
                                        <td><span class="text-ellipsis">${company.phone}</span></td>
                                        <td><span class="text-ellipsis">${company.introduce}</span></td>
                                        <td><span class="text-ellipsis">${company.date}</span></td>
                                        <td>
                                            <c:if test="${company.status == 1}">
                                                <span class="label label-success">Active</span>
                                            </c:if>
                                            <c:if test="${company.status == 0}">
                                                <span class="label label-default">Hidden</span>
                                            </c:if>
                                        </td>
                                        <td class="box-action">
                                            <a href="/admin/company/update/${company.id}" class="active btn-action green">
                                                <i class="bx bx-edit"></i>
                                            </a>
                                            <a onclick="return confirm('Are you sure to delete?')" 
                                               href="/admin/company/delete/${company.id}" 
                                               class="active btn-action orange">
                                                <i class='bx bx-trash'></i>
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </form>
                </div>
            </div>
        </div>
    </section>
    <script src="/admin/assets/js/validation.js"></script>
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
                                                validation(inputsToValidate, document.querySelector("#btn-add-new-company"));
    </script>  
    <%@include file="../components/footer.jsp" %>