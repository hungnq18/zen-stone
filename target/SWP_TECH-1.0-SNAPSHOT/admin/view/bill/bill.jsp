<%-- 
    Document   : category
    Created on : Oct 1, 2023, 12:02:09 AM
    Author     : Le Tan Kim
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../components/header.jsp" %>
<jsp:useBean id="currency" class="Util.CurrencyConverter"></jsp:useBean>
    <section id="main-content">
        <section class="wrapper">
            <div class="table-agile-info">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        Bills
                    </div>
                    <div class="row w3-res-tb">
                        <div class="row w3-res-tb">
                            <div class="col-sm-5 m-b-xs">
                                <select onchange="filterStatus('bill', this.value)" class="input-sm form-control w-sm inline v-middle">
                                    <option value="-1">All</option>
                                    <option value="1">New</option>
                                    <option value="2">Accept</option>
                                    <option value="3">Prepare</option>
                                    <option value="4">Delivery</option>
                                    <option value="4">Finish</option>
                                    <option value="0">Cancel</option>
                                </select>   
                            </div>
                        </div>
                    </div>
                    <div class="table-responsive" style="padding: 5px;">
                        <script>
                            $(document).ready(function () {
                                $("#product").DataTable();
                            });
                        </script>
                        <form method="post" action="/admin/bill">
                            <div class="wrapper-box-btn">
                                <button name="btn-delete-bills" onclick="return confirm('Are you sure to delete?')" class="btn btn-sm btn-danger">
                                    Delete all checked
                                </button> 
                            </div>
                            <table id="product" class="table table-striped b-t b-light">
                                <thead>
                                    <tr>
                                        <th style="width:20px;">
                                            <label class="i-checks all-check m-b-none">
                                                <input name="delete-item-bill" type="checkbox"><i></i>
                                            </label>
                                        </th>
                                        <th>Bill code</th>
                                        <th>Customer name</th>
                                        <th>Phone</th>
                                        <th>Address</th>
                                        <th>Total</th>
                                        <th>Pay method</th>
                                        <th>Status</th>
                                        <th>Order at</th>
                                        <th>Update at</th>
                                        <th style="width:30px;">Action</th>
                                    </tr>
                                </thead>
                                <tbody class="render-data">
                                <c:forEach items="${bills}" var="bill">
                                    <tr>
                                        <td><input type="checkbox" name="delete-item-bill" value="${bill.id}"></td>
                                        <td>Bill${bill.id}</td>
                                        <td>${bill.customerName}</td>
                                        <td>${bill.phone}</td>
                                        <td>${bill.address}</td>
                                        <td>${currency.currencyFormat(bill.total, "")}</td>
                                        <td>
                                            <c:if test="${bill.payment == 0}">
                                                <span class="label label-info">Cash</span>
                                            </c:if>
                                            <c:if test="${bill.payment == 1}">
                                                <span class="label label-danger">Banking</span>
                                            </c:if>
                                        </td>
                                        <td>
                                            <c:if test="${bill.status == 1}">
                                                <span class="label label-primary">
                                                    <i class='bx bxs-message-rounded-add'></i>
                                                    New
                                                </span>
                                            </c:if>
                                            <c:if test="${bill.status == 2}">
                                                <span class="label label-warning">
                                                    <i class='bx bx-show' ></i>
                                                    Accept
                                                </span>
                                            </c:if>
                                            <c:if test="${bill.status == 3}">
                                                <span class="label label-info">
                                                    <i class='bx bx-package' ></i>
                                                    Preparing
                                                </span>
                                            </c:if>
                                            <c:if test="${bill.status == 4}">
                                                <span class="label label-danger">
                                                    <i class='bx bxs-truck' ></i>
                                                    Delivery
                                                </span>
                                            </c:if>
                                            <c:if test="${bill.status == 5}">
                                                <span class="label label-success">
                                                    <i class='bx bxs-check-circle' ></i>
                                                    Finish
                                                </span>
                                            </c:if>
                                            <c:if test="${bill.status == 0}">
                                                <span class="label label-default">
                                                    <i class='bx bx-block' ></i>
                                                    Cancel
                                                </span>
                                            </c:if>
                                        </td>
                                        <td><span class="text-ellipsis">${bill.dateOrder}</span></td>
                                        <td><span class="text-ellipsis">${bill.dateUpdate}</span></td>
                                        <td class="box-action">
                                            <a title="Detail" href="/admin/bill/view/${bill.id}" class="btn-action blue">
                                                <i class='bx bx-slider' ></i>
                                            </a>
                                            <a title="Delete" onclick="return confirm('Are you sure to delete?')" 
                                               href="/admin/bill/delete/${bill.id}" 
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
    <%@include file="../components/footer.jsp" %>