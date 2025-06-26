<%-- 
    Document   : blog
    Created on : May 23, 2024, 8:51:18 AM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="getDaoCat" class="DAO.CategoryDao" />
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
                                    <div class="modal-dialog" style="width: 90%">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">×</button>
                                                <h4 class="modal-title">Add blog</h4>
                                            </div>
                                            <div class="modal-body">
                                                <form accept-charset="UTF-8" method="post" action="/admin/blog" enctype="multipart/form-data">
                                                    <div class="form-group">
                                                        <label>Title</label>
                                                        <input id="title" required type="text" name="title" class="form-control">
                                                        <span class="message_error"></span>
                                                    </div>
                                                    <div class="form-group">
                                                        <label>Short description</label>
                                                        <textarea required class="form-control" name="shortDesc" id="shortDesc" cols="30" rows="10"></textarea>
                                                        <span class="message_error"></span>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="control-label">Category</label>
                                                        <select name="categoryID" class="form-control">
                                                            <c:forEach var="category" items="${categories}">
                                                                <option value="${category.getID()}">${category.getName()}</option>
                                                            </c:forEach>
                                                        </select>
                                                        <div class="form-group">
                                                            <label>Image</label>
                                                            <input id="image" onchange="previewImage(event, '.box-img-preview.main')" required type="file" name="image" class="form-control">
                                                            <div class="box-img-preview main">

                                                            </div>
                                                            <span class="message_error"></span>
                                                        </div>
                                                        <div class="form-group">
                                                            <label>Description</label>
                                                            <textarea required class="form-control" name="desc" id="ck-editor1" cols="30" rows="10"></textarea>
                                                            <span class="message_error"></span>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="control-label">Status</label>
                                                        <select name="status" class="form-control">
                                                            <option value="1">Active</option>
                                                            <option value="0">Hidden</option>
                                                        </select>
                                                    </div>
                                                    <c:set value="${categories}" var="category"></c:set>
                                                    <c:if test="${category.size() == 0}">
                                                        <span  class="btn btn-group-justified btn-danger">
                                                            Please add category, producer and brand before add product
                                                        </span>
                                                    </c:if>
                                                    <c:if test="${category.size() > 0}">
                                                        <button id="add-blog" name="btn-add-blog" type="submit" class="btn btn-group-justified btn-primary">Add</button>
                                                    </c:if>
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
                    Blogs
                </div>

                <div class="table-responsive" style="padding: 5px;">
                    <script>
                        $(document).ready(function () {
                            $("#product").DataTable();
                        });
                    </script>
                    <form method="post" action="/MomAndBaby/staff/blog">
                        <div class="wrapper-box-btn">
                            <button name="btn-delete-products" onclick="return confirm('Are you sure to delete?')" class="btn btn-sm btn-danger">
                                Delete all checked
                            </button> 
                        </div>
                        <table id="product" class="table table-striped b-t b-light">
                            <thead>
                                <tr>
                                    <th style="width:20px;">
                                        <label class="i-checks all-check m-b-none">
                                            <input type="checkbox"><i></i>
                                        </label>
                                    </th>
                                    <th>Title</th>
                                    <th>Image</th>
                                    <th>Date at</th>
                                    <th>Date update</th>
                                    <th>Status</th>
                                    <th>View</th>
                                    <th>Category</th>
                                    <th style="width:30px;">Action</th>
                                </tr>
                            </thead>
                            <tbody class="render-data">
                                <c:forEach items="${blogs}" var="blog">
                                    <c:set var="category" value="${getDaoCat.getCategoryByID(blog.categoryID)}" />
                                    <tr>
                                        <td><input type="checkbox" name="delete-product-item" value="${blog.ID}"></td>
                                        <td>${blog.title}</td>
                                        <td>
                                            <img src="${blog.image}" alt="${blog.title}"/>
                                        </td>
                                        <td><span class="text-ellipsis">${blog.datePost}</span></td>
                                        <td><span class="text-ellipsis">${blog.dateUpdate == null ? "N/A" : blog.dateUpdate}</span></td>
                                        <td>
                                            <c:if test="${blog.status == 1}">
                                                <span class="label label-success">Active</span>
                                            </c:if>
                                            <c:if test="${blog.status == 0}">
                                                <span class="label label-default">Hidden</span>
                                            </c:if>
                                        </td>
                                        <td><span class="text-ellipsis">${blog.view}</span></td>
                                        <td><span class="text-ellipsis">${category != null ? category.name : "N/A"}</span></td>

                                        <td class="box-action">
                                            <a href="/admin/blog/view/${blog.ID}" class="btn-action blue" title="Detail">
                                                <i class='bx bx-slider'></i>
                                            </a>
                                            <a href="/admin/blog/update/${blog.ID}" class="btn-action green" title="Edit">
                                                <i class="bx bx-edit"></i>
                                            </a>
                                            <a onclick="return confirm('Are you sure to delete?')" 
                                               href="/admin/blog/delete/${blog.ID}" 
                                               class="btn-action orange" title="Delete">
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
    <script src="./static-admin/ckeditor/ckeditor.js"></script> 
    <script>
                                                CKEDITOR.replace(
                                                        'ck-editor1',
                                                        {
                                                            language: 'en'
                                                        }
                                                );
    </script>
    <script src="./static-admin/assets/js/validation.js"></script>
    <script>
                                                // validation form
                                                const title = document.getElementById('title'),
                                                        shortDesc = document.getElementById('shortDesc'),
                                                        desc = document.getElementById('ck-editor1'),
                                                        image = document.getElementById('image');
                                                const messageEmpty = "This field can not empty",
                                                        messageImage = "Please choose a image";

                                                // array to save all input to check, message show error of each and a string regex to check 
                                                const inputsToValidate = [
                                                    {element: title, message: messageEmpty, regex: /^.{1,}$/m, type: "text", isEmpty: false},
                                                    {element: shortDesc, message: messageEmpty, regex: /^.{1,}$/m, type: "text", isEmpty: false},
                                                    {element: desc, message: messageEmpty, regex: "ck-editor1", type: "ckeditor", isEmpty: false},
                                                    {element: image, message: messageImage, regex: null, type: "image", isEmpty: false},
                                                ];
                                                validation(inputsToValidate, document.querySelector("#add-blog"));
    </script>
    <%@include file="../components/footer.jsp" %>