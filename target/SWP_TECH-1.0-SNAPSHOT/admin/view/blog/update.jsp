<%-- 
    Document   : category
    Created on : Oct 1, 2023, 12:02:09 AM
    Author     : Le Tan Kim
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../components/header.jsp" %>
<c:set value="${category}" var="category"></c:set>
    <section id="main-content">
        <section class="wrapper">
            <div class="gallery">
                <h2 class="w3ls_head">Update blog</h2>
                <a class="btn btn-info btn-back-to-page" href="/admin/blog"><< Back to product</a>
                <div class="box-content">
                    <form class="cmxform form-horizontal form-validate" accept-charset="UTF-8" method="post" action="/admin/blog" enctype="multipart/form-data">
                        <div class="row">
                            <div class="form-group col-lg-12">
                                <label class="control-label col-lg-12">Title</label>
                                <div class="col-lg-12">
                                    <input name="id" value="${currentBlog.ID}" type="hidden"/>
                                <input id="title" value="${currentBlog.title}" name="title" class=" form-control">
                                <span class="message_error"></span>
                            </div
                        </div>
                        <div class="form-group col-lg-12">
                            <label>Short description</label>
                            <div class="col-lg-12">
                                <textarea required class="form-control" name="shortDesc" id="shortDesc" cols="30" rows="10">${currentBlog.shortDesc}</textarea>
                                <span class="message_error"></span>
                            </div
                        </div>
                        <div class="form-group col-lg-6">
                            <label class="control-label col-lg-12">Image</label>
                            <div class="col-lg-12">
                                <input id="image" onchange="previewImage(event, '.box-img-preview.main')" type="file" name="image" class="form-control main-img">
                                <input name="oldImage" value="${currentBlog.image}" type="hidden"/>
                                <div class="box-img-preview main">
                                    <img class="preview-main" src="${currentBlog.image}" alt="Image preview" />
                                </div>
                                <span class="message_error"></span>
                            </div>
                        </div>
                        <div class="form-group col-lg-6">
                            <label class="control-label col-lg-12">Category</label>
                            <div class="col-lg-12">
                                <select name="categoryID" class="form-control">
                                    <c:forEach var="cat" items="${categories}">
                                        <option ${currentBlog.categoryID == cat.ID ? "selected": ""} value="${cat.ID}">${cat.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="box-content-desc form-group col-lg-12">
                            <label class="control-label col-lg-12">Description</label>
                            <div class="col-lg-12">
                                <textarea required class="form-control" name="desc" id="ck-editor1" cols="30" rows="10">
                                    ${currentBlog.description}
                                </textarea>
                                <span class="message_error"></span>
                            </div>
                        </div>
                        <div class="form-group col-lg-6">
                            <label class="control-label col-lg-12">Status</label>
                            <div class="col-lg-12">
                                <select name="status" class="form-control">
                                    <option ${currentBlog.status == 1 ? "selected": ""} value="1">Active</option>
                                    <option ${currentBlog.status == 0 ? "selected": ""} value="0">Hidden</option>
                                </select>
                            </div>
                        </div>
                        <button id="update-blog" name="btn-update-blog" type="submit" class="btn btn-group-justified btn-primary">Update</button>
                        <a class="btn btn-info btn-back-to-page" href="/MomAndBaby/staff/blog"><< Back to blog</a>
                    </div>
                </form>
            </div>
        </div>
    </section>
    <script src="./static-admin/ckeditor/ckeditor.js"></script> 
    <script>
                                    CKEDITOR.replace('ck-editor1',{
                                                    language: 'en'
                                                });
    </script>
    <script src="./static-admin/assets/js/validation.js"></script>
    <script>
                                    // validation form
                                    const title = document.getElementById('title'),
                                            desc = document.getElementById('ck-editor1'),
                                            shortDesc = document.getElementById('shortDesc'),
                                            image = document.getElementById('image');
                                    const messageEmpty = "This field can not empty",
                                            messageImage = "Please choose a image";

                                    // array to save all input to check, message show error of each and a string regex to check 
                                    const inputsToValidate = [
                                        {element: shortDesc, message: messageEmpty, regex: /^.{1,}$/m, type: "text", isEmpty: false},
                                        {element: title, message: messageEmpty, regex: /^.{1,}$/m, type: "text", isEmpty: false},
                                        {element: desc, message: messageEmpty, regex: "ck-editor1", type: "ckeditor", isEmpty: false},
                                        {element: image, message: messageImage, regex: null, type: "image", isEmpty: true},
                                    ];
                                    validation(inputsToValidate, document.querySelector("#update-blog"));
    </script>
    <%@include file="../components/footer.jsp" %>