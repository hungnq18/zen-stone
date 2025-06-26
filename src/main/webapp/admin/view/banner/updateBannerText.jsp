<%-- 
    Document   : updateBannerText
    Created on : Oct 23, 2023, 2:29:18 PM
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
                    <c:set var="banner" value="${currentBannerText}"></c:set>
                        <div class="panel-body">
                            <div class=" form">
                                <form class="cmxform form-horizontal" method="post" action="/admin/banner-text" enctype="multipart/form-data">
                                    <div class="form-group ">
                                        <label for="cname" class="control-label col-lg-3">Banner image</label>
                                        <div class="col-lg-6">
                                            <input id="image" onchange="previewImage(event, '.box-img-preview')" type="file" name="img" class="form-control input-img">
                                            <div class="box-img-preview">
                                                <img class="show-img-preview" style="width: 100px;" src="${banner.img}" alt="${banner.datePost}"/>
                                        </div>
                                        <span class="message_error"></span>
                                        <input value="${banner.img}" type="hidden" name="oldImg">
                                        <input value="${banner.ID}" type="hidden" name="id">
                                    </div>
                                </div>
                                <div class="form-group ">
                                    <label for="cname" class="control-label col-lg-3">Banner title</label>
                                    <div class="col-lg-6">
                                        <input id="text-title" required value="${banner.title}" type="text" name="title" class="form-control">
                                        <span class="message_error"></span>
                                    </div>
                                </div>
                                <div class="form-group ">
                                    <label for="cname" class="control-label col-lg-3">Banner description</label>
                                    <div class="col-lg-6">
                                        <input id="text-content" required value="${banner.description}" type="text" name="description" class="form-control">
                                        <span class="message_error"></span>
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
                                            <button id="update-banner-text" class="btn btn-primary" name="btn-update-banner-text" type="submit">Save</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <a class="btn btn-info btn-back-to-page" href="/admin"><< Back to banner text</a>
                        </div>
                    </section>
                </div>
            </div>
        </section>        
        <script src="./admin/assets/js/validation.js"></script>
        <<script >
                                                const image = document.getElementById('image'),
                                                        title = document.getElementById('text-title'),
                                                        content = document.getElementById('text-content');
                                                const imageMessage = "Please choose a image valid",
                                                        titleMessage = "This field can not empty",
                                                        contentMessage = "This field can not empty";
                                                // array to save all input to check, message show error of each and a string regex to check 
                                                const inputsToValidate = [
                                                    {element: image, message: imageMessage, regex: "image"},
                                                    {element: title, message: titleMessage, regex: /^.{1,}$/},
                                                    {element: content, message: contentMessage, regex: /^.{1,}$/},
                                                ];
                                                validation(inputsToValidate, document.querySelector("#update-banner-text"));
        </script>
    <%@include file="../components/footer.jsp" %>
