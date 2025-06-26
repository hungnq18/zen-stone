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
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">×</button>
                                                <h4 class="modal-title">Add producer</h4>
                                            </div>
                                            <div class="modal-body">
                                                <form role="form" method="post" action="/admin/producer">
                                                    <div class="form-group">
                                                        <label>Producer name</label>
                                                        <input required="" type="text" id="name" name="name" class="form-control" placeholder="Category name">
                                                        <span class="message_error"></span>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="control-label">Status</label>
                                                        <select name="status" class="form-control">
                                                            <option value="1">Active</option>
                                                            <option value="0">Hidden</option>
                                                        </select>
                                                    </div>
                                                    <button name="btn-add-producer" id="add-producer" type="submit" class="btn btn-group-justified btn-primary">Add</button>
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
                    Producer
                </div>
                <div class="row w3-res-tb">
                    <div class="col-sm-5 m-b-xs">
                        <select onchange="filterStatus('producer', this.value)" class="input-sm form-control w-sm inline v-middle">
                            <option value="1">All</option>
                            <option value="1">Active</option>
                            <option value="0">Hidden</option>
                        </select>   
                        <span class="message-notice"> <span>*</span> Can not delete producer that having product</span>
                    </div>
                </div>
                <div class="table-responsive" style="padding: 5px;">
                    <script>
                        $(document).ready(function () {
                            $("#category").DataTable();
                        });
                    </script>
                    <form method="post" action="/admin/producer">
                        <div class="wrapper-box-btn">
                            <button name="btn-delete-producers" onclick="return confirm('Are you sure to delete?')" class="btn btn-sm btn-danger">
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
                                    <th>Producer name</th>
                                    <th>Date at</th>
                                    <th>Date update</th>
                                    <th>Number product</th>
                                    <th>Slug</th>
                                    <th>Status</th>
                                    <th style="width:30px;">Action</th>
                                </tr>
                            </thead>
                            <tbody class="render-data">
                                <jsp:useBean id="getNumberProduct" class="DAO.ProducerDao"></jsp:useBean>
                                <c:forEach items="${producers}" var="producer">
                                    <tr>
                                        <td><input type="checkbox" name="delete-producer-item" value="${producer.slug}"></td>
                                        <td>${producer.name}</td>
                                        <td><span class="text-ellipsis">${producer.datePost}</span></td>
                                        <td><span class="text-ellipsis">${producer.dateUpdate}</span></td>
                                        <td>${getNumberProduct.getNumberProductByProducer(producer.ID)}</td>
                                        <td><span class="text-ellipsis">${producer.slug}</span></td>
                                        <td>
                                            <c:if test="${producer.status == 1}">
                                                <span class="label label-success">Active</span>
                                            </c:if>
                                            <c:if test="${producer.status == 0}">
                                                <span class="label label-default">Hidden</span>
                                            </c:if>
                                        </td>
                                        <td class="box-action">
                                            <a href="/admin/producer/update/${producer.slug}" class="btn-action green">
                                                <i class="bx bx-edit"></i>
                                            </a>
                                            <a onclick="return confirm('Are you sure to delete?')" 
                                               href="/admin/producer/delete/${producer.slug}" 
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
    <script >
                                                const name = document.getElementById('name');
                                                const nameMessage = "This field can not empty";
                                                // array to save all input to check, message show error of each and a string regex to check 
                                                const inputsToValidate = [
                                                    {element: name, message: nameMessage, regex: /^.{1,}$/},
                                                ];
                                                validation(inputsToValidate, document.querySelector("#add-producer"));
                                                </script >
        <%@include file="../components/footer.jsp" %>