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
                        Update banner
                    </header>
                    <c:set var="banner" value="${currentBanner}"></c:set>
                        <div class="panel-body">
                            <div class=" form">
                                <form class="cmxform form-horizontal" method="post" action="/admin/banner" enctype="multipart/form-data">
                                    <div class="form-group ">
                                        <label for="cname" class="control-label col-lg-3">Banner image</label>
                                        <div class="col-lg-6">
                                            <input onchange="previewImage(event, '.box-img-preview')" id="image" type="file" name="img" class="form-control input-img">
                                            <div class="box-img-preview">
                                                <img class="show-img-preview" style="width: 100px;" src="${banner.img}" alt="${banner.datePost}"/>
                                        </div>
                                        <span class="message_error"></span>
                                        <input value="${banner.img}" type="hidden" name="oldImg">
                                        <input value="${banner.ID}" type="hidden" name="id">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-3">Status</label>
                                    <div class="col-lg-6">
                                        <select name="status" class="form-control">
                                            <option value="1"
                                                    <c:if test="${banner.status == 1}">selected</c:if>
                                                        >Active
                                                    </option>
                                                    <option value="0"
                                                    <c:if test="${banner.status == 0}">selected</c:if>>Hidden</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-lg-offset-3 col-lg-6">
                                            <button class="btn btn-primary" id="btn-update-banner" name="btn-update-banner" type="submit">Save</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <a class="btn btn-info btn-back-to-page" href="/admin/banner"><< Back to banner</a>
                        </div>
                    </section>
                </div>
            </div>
        </section>     
        <script src="/admin/assets/js/validation.js"></script>
        <script>
                                                const image = document.getElementById('image');
                                                const imageMessage = "Please choose a image valid";
                                                // array to save all input to check, message show error of each and a string regex to check 
                                                const inputsToValidate = [
                                                    {element: image, message: imageMessage, regex: "image"},
                                                ];
                                                validation(inputsToValidate, document.querySelector("#btn-update-banner"));
        </script>
    <%@include file="../components/footer.jsp" %>
