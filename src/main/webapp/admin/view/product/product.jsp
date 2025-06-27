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
                                                    <h4 class="modal-title">Add product</h4>
                                                </div>
                                                <div class="modal-body">
                                                    <form accept-charset="UTF-8" method="post" action="/admin/product" enctype="multipart/form-data">
                                                        <div class="form-group">
                                                            <label>Product name</label>
                                                            <input id="name" required type="text" name="name" class="form-control">
                                                            <span class="message_error"></span>
                                                        </div>
                                                        <div class="form-group">
                                                            <label>Product model</label>
                                                            <input id="model" required type="text" name="model" class="form-control">
                                                            <span class="message_error"></span>
                                                        </div>
                                                        <div class="form-group">
                                                            <label>Product price</label>
                                                            <input oninput="formatCurrency(this)" id="price" onchange="document.querySelector('.price-sale').max = this.value" pattern="[0-9]+([.,][0-9]+)?" min="0" required type="text" name="price" class="form-control">
                                                            <span class="formattedValue"></span>
                                                            <span class="message_error"></span>
                                                        </div>
                                                        <div class="form-group">
                                                            <label>Product price sale</label>
                                                            <input oninput="formatCurrency(this)" id="price-sale" pattern="[0-9]+([.,][0-9]+)?" min="0" value="0" required type="text" name="priceSale" class="form-control price-sale">
                                                            <span class="formattedValue"></span>
                                                            <span class="message_error"></span>
                                                        </div>
                                                        <input id="available" value="1" type="hidden" name="numberOfProduct" class="form-control">
                                                        <div class="form-group">
                                                            <label>Main image</label>
                                                            <input id="mainImage" onchange="previewImage(event, '.box-img-preview.main')" required type="file" name="imgMain" class="form-control">
                                                            <div class="box-img-preview main">

                                                            </div>
                                                            <span class="message_error"></span>
                                                        </div>
                                                        <div class="form-group">
                                                            <label>Description image</label>
                                                            <input id="descImage" onchange="previewImage(event, '.box-img-preview.desc')" required type="file" multiple="true" name="imgDesc" class="form-control">
                                                            <div class="box-img-preview desc">

                                                            </div>
                                                            <span class="message_error"></span>
                                                        </div>
                                                        <div class="form-group">
                                                            <label>Color image</label>
                                                            <input id="colorImage" onchange="previewImagesColor(event, '.box-img-preview.color')" required type="file" multiple="true" name="imgColor" class="form-control">
                                                            <div class="box-img-preview color">

                                                            </div>
                                                            <span class="message_error"></span>
                                                        </div>
                                                        <div class="form-group">
                                                            <label>Description</label>
                                                            <textarea required class="form-control" name="desc" id="ck-editor1" cols="30" rows="10"></textarea>
                                                        </div>
                                                        <div class="form-group">
                                                            <label>Config</label>
                                                            <textarea required class="form-control" name="config" id="ck-editor2" cols="30" rows="10"></textarea>
                                                        </div>
                                                        <div class="form-group">
                                                            <label class="control-label">Priority</label>
                                                            <select name="priority" class="form-control">
                                                                <option value="1">Normal</option>
                                                                <option value="2">Deal</option>
                                                                <option value="3">Feature</option> 
                                                            </select>
                                                        </div>
                                                        <div class="form-group">
                                                            <label class="control-label">Category</label>
                                                            <select name="categoryID" class="form-control">
                                                            <c:forEach var="category" items="${categories}">
                                                                <option value="${category.getID()}">${category.getName()}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="control-label">Producer</label>
                                                        <select name="producerID" class="form-control">
                                                            <c:forEach var="producer" items="${producers}">
                                                                <option value="${producer.getID()}">${producer.getName()}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="control-label">Status</label>
                                                        <select name="status" class="form-control">
                                                            <option value="1">Active</option>
                                                            <option value="0">Hidden</option>
                                                        </select>
                                                    </div>
                                                    <c:set value="${categories}" var="category"></c:set>
                                                    <c:set value="${producers}" var="producer"></c:set>
                                                    <c:if test="${category.size() == 0 || producer.size() == 0}">
                                                        <span  class="btn btn-group-justified btn-danger">
                                                            Please add category and producer before add product
                                                        </span>
                                                    </c:if>
                                                    <c:if test="${category.size() > 0 && producer.size() > 0}">
                                                        <button id="add-product" name="btn-add-product" type="submit" class="btn btn-group-justified btn-primary">Add</button>
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
                    Products
                </div>
                <div class="row w3-res-tb">
                    <div class="col-sm-5 m-b-xs">
                        <select onchange="filterStatus('product', this.value)" class="input-sm form-control w-sm inline v-middle">
                            <option value="1">All</option>
                            <option value="1">Active</option>
                            <option value="0">Hidden</option>
                        </select>   
                    </div>
                </div>
                <div class="table-responsive" style="padding: 5px;">
                    <script>
                        $(document).ready(function () {
                            $("#product").DataTable();
                        });
                    </script>
                    <form method="post" action="/admin/product">
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
                                    <th>Product name</th>
                                    <th>Image</th>
                                    <th>Price</th>
                                    <th>Price sale</th>
                                    <th>Sold</th>
                                    <th>Date at</th>
                                    <th>Date update</th>
                                    <th>Slug</th>
                                    <th>Status</th>
                                    <th style="width:30px;">Action</th>
                                </tr>
                            </thead>
                            <tbody class="render-data">
                                <c:forEach items="${products}" var="product">
                                    <tr>
                                        <td><input type="checkbox" name="delete-product-item" value="${product.slug}"></td>
                                        <td>${product.name}</td>
                                        <td>
                                            <img src="${product.mainImg}" alt="alt"/>
                                        </td>
                                        <td><span class="text-ellipsis">${currency.currencyFormat(product.oldPrice, "")}</span></td>
                                        <td><span class="text-ellipsis">${currency.currencyFormat(product.newPrice, "")}</span></td>
                                        <td><span class="text-ellipsis">${product.sold}</span></td>
                                        <td><span class="text-ellipsis">${product.datePost}</span></td>
                                        <td><span class="text-ellipsis">${product.dateUpdate}</span></td>
                                        <td><span class="text-ellipsis">${product.slug}</span></td>
                                        <td>
                                            <c:if test="${product.status == 1}">
                                                <span class="label label-success">Active</span>
                                            </c:if>
                                            <c:if test="${product.status == 0}">
                                                <span class="label label-default">Hidden</span>
                                            </c:if>
                                        </td>
                                        <td class="box-action">
                                            <a href="/admin/product/view/${product.slug}" class="btn-action blue" title="Detail">
                                                <i class='bx bx-slider'></i>
                                            </a>
                                            <a href="/admin/product/comment/${product.slug}" class="btn-action red" title="Comment">
                                                <i class='bx bxs-comment-detail'></i>
                                            </a>
                                            <a href="/admin/product/update/${product.slug}" class="btn-action green" title="Edit">
                                                <i class="bx bx-edit"></i>
                                            </a>
                                            <a onclick="return confirm('Are you sure to delete?')" 
                                               href="/admin/product/delete/${product.slug}" 
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
    <script src="./admin/ckeditor/ckeditor.js"></script> 
    <script>
                                                CKEDITOR.replace('ck-editor1');
                                                CKEDITOR.replace('ck-editor2');
    </script>
    <script src="/admin/assets/js/validation.js"></script>
    <script>
                                                function formatCurrency(input) {
                                                    let value = input.value.replace(/[^\d]/g, '');
                                                    value = new Intl.NumberFormat('vi-VN', {style: 'currency', currency: 'VND'}).format(value);
                                                    const formattedValueElem = input.parentElement.querySelector('.formattedValue');
                                                    formattedValueElem.textContent = value;
                                                }
                                                // validation form
                                                const name = document.getElementById('name'),
                                                        model = document.getElementById('model'),
                                                        price = document.getElementById('price'),
                                                        priceSale = document.getElementById('price-sale'),
                                                        available = document.getElementById('available'),
                                                        mainImage = document.getElementById('mainImage'),
                                                        descImage = document.getElementById('descImage'),
                                                        colorImage = document.getElementById('colorImage');
                                                const messageName = "Name product can not empty",
                                                        messageModel = "Model product can not empty",
                                                        messagePrice = "Price product must be a postitive number",
                                                        messagePriceSale = "Price sale product must be a postitive number",
                                                        messageAvailable = "Available must be a integer number",
                                                        messageMainImage = "Please choose a image",
                                                        messageDescImage = "Please choose a image",
                                                        messageColorImage = "Please choose a image";

                                                // array to save all input to check, message show error of each and a string regex to check 
                                                const inputsToValidate = [
                                                    {element: name, message: messageName, regex: /^.{1,}$/},
                                                    {element: model, message: messageModel, regex: /^.{1,}$/},
                                                    {element: price, message: messagePrice, regex: /^[0-9]+(?:\.[0-9]+)?$/},
                                                    {element: priceSale, message: messagePriceSale, regex: /^[0-9]+(?:\.[0-9]+)?$/},
                                                    {element: available, message: messageAvailable, regex: /^\d+$/},
                                                    {element: mainImage, message: messageMainImage, regex: "image"},
                                                    {element: descImage, message: messageDescImage, regex: "image"},
                                                    {element: colorImage, message: messageColorImage, regex: "image"}
                                                ];
                                                validation(inputsToValidate, document.querySelector("#add-product"));
    </script>
    <%@include file="../components/footer.jsp" %>