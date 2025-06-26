<%-- 
    Document   : category
    Created on : Oct 1, 2023, 12:02:09 AM
    Author     : Le Tan Kim
--%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.sql.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../components/header.jsp" %>
<%    Date now = Date.valueOf(LocalDate.now());
    request.setAttribute("now", now);
%>
<c:set var="now" value="${now}" />
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
                                                <h4 class="modal-title">Add voucher</h4>
                                            </div>
                                            <div class="modal-body">
                                                <form role="form" method="post" action="/admin/voucher">
                                                    <div class="form-group">
                                                        <label>Name </label>
                                                        <input id="name" required="" type="text" name="name" class="form-control">
                                                        <span class="message_error"></span>
                                                    </div>
                                                    <div class="form-group">
                                                        <label>Code(From 3-15 character) </label>
                                                        <input id="code" pattern="[a-zA-Z0-9]{3,}" type="text" name="code" class="form-control">
                                                        <span class="message_error"></span>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="control-label">Type of voucher </label>
                                                        <select name="type" class="form-control">
                                                            <option value="1">By price of bill</option>
                                                        </select>
                                                    </div>
                                                    <div class="form-group">
                                                        <label>Value</label>
                                                        <input id="value" required type="text" name="value" class="form-control" oninput="formatCurrency(this)">
                                                        <span class="formattedValue"></span>
                                                        <span class="message_error"></span>
                                                    </div>
                                                    <div class="form-group">
                                                        <label>Value limit:</label>
                                                        <input id="limit" required type="text" name="limit" class="form-control limit-voucher">
                                                        <span class="message_error"></span>
                                                    </div>
                                                    <div class="form-group">
                                                        <label>Date start </label>
                                                        <input id="start" required type="date" name="start"  class="form-control">
                                                        <span class="message_error"></span>
                                                    </div>
                                                    <div class="form-group">
                                                        <label>Date end </label>
                                                        <input id="end" required type="date" name="end" class="form-control">
                                                        <span class="message_error"></span>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="control-label">Status</label>
                                                        <select name="status" class="form-control">
                                                            <option value="1">Active</option>
                                                            <option value="0">Hidden</option>
                                                        </select>
                                                    </div>
                                                    <button id="btn-add-voucher" name="btn-add-voucher" type="submit" class="btn btn-group-justified btn-primary">Add</button>
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
                    Vouchers
                </div>
                <div class="row w3-res-tb">
                    <div class="col-sm-5 m-b-xs">
                        <select onchange="filterStatus('voucher', this.value)" class="input-sm form-control w-sm inline v-middle">
                            <option value="1">All</option>
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
                    <form method="post" action="/admin/voucher">
                        <div class="wrapper-box-btn">
                            <button name="btn-delete-vouchers" onclick="return confirm('Are you sure to delete?')" class="btn btn-sm btn-danger">
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
                                    <th>Voucher name</th>
                                    <th>Voucher code</th>
                                    <th>Type voucher</th>
                                    <th>Value</th>
                                    <th>Start</th>
                                    <th>End</th>
                                    <th>Time use</th>
                                    <th>Status</th>
                                    <th style="width:30px;">Action</th>
                                </tr>
                            </thead>
                            <tbody class="render-data">
                                <c:forEach items="${vouchers}" var="voucher">
                                    <tr>
                                        <td><input type="checkbox" name="delete-voucher-item" value="${voucher.id}"></td>
                                        <td>${voucher.name}</td>
                                        <td>
                                            <span class="label label-primary">${voucher.code}</span>
                                        </td>
                                        <td>
                                            <c:if test="${voucher.type == 1}">
                                                <span class="label label-success">By percent</span>
                                            </c:if>
                                            <c:if test="${voucher.type == 0}">
                                                <span class="label label-default">By price of bill</span>
                                            </c:if>
                                        </td>

                                        <td>${voucher.value}</td>
                                        <td><span class="text-ellipsis">${voucher.start}</span></td>
                                        <td><span class="text-ellipsis">${voucher.end}</span></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${voucher.end.getTime() >= now.getTime()}">
                                                    <span class="label label-warning">Activing</span>
                                                </c:when>
                                                <c:when test="${voucher.end.getTime() < now.getTime()}">
                                                    <span class="label label-danger">Expired</span>
                                                </c:when>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:if test="${voucher.status == 1}">
                                                <span class="label label-success">Active</span>
                                            </c:if>
                                            <c:if test="${voucher.status == 0}">
                                                <span class="label label-default">Hidden</span>
                                            </c:if>
                                        </td>
                                        <td class="box-action">
                                            <a href="/admin/voucher/update/${voucher.id}" class="btn-action green">
                                                <i class="bx bx-edit"></i>
                                            </a>
                                            <a onclick="return confirm('Are you sure to delete?')" 
                                               href="/admin/voucher/delete/${voucher.id}" 
                                               class="btn-action orange">
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
    <script src="./admin/assets/js/validation.js"></script>
    <script>
                                                function formatCurrency(input) {
                                                    let value = input.value.replace(/[^\d]/g, '');
                                                    value = new Intl.NumberFormat('vi-VN', {style: 'currency', currency: 'VND'}).format(value);
                                                    const formattedValueElem = input.parentElement.querySelector('.formattedValue');
                                                    formattedValueElem.textContent = value;
                                                }


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
                                                validation(inputsToValidate, document.querySelector("#btn-add-voucher"));
    </script>
    <%@include file="../components/footer.jsp" %>