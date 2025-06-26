<%-- 
    Document   : category
    Created on : Oct 1, 2023, 12:02:09 AM
    Author     : Le Tan Kim
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../components/header.jsp" %>
<c:set var="category" value="${getDao.getCategoryById(currentBlog.categoryID)}" />
<section id="main-content">
    <section class="wrapper">
        <div class="gallery">
            <a class="btn btn-info" href="/admin/blog"><< Back to blog</a>
            <h2 class="w3ls_head">Details Blog</h2>
            <div class="box-content">
                <form class="cmxform form-horizontal " id="signupForm">
                    <div class="row">
                        <div class="form-group col-lg-12">
                            <label class="control-label col-lg-12">Title</label>
                            <div class="col-lg-12">
                                <input value="${currentBlog.title}" disabled class=" form-control">
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group col-lg-12">
                                <label class="control-label col-lg-12">Short description</label>
                                <div class="col-lg-12">
                                    <textarea required class="form-control" name="shortDesc" id="shortDesc" cols="30" rows="10">${currentBlog.shortDesc}</textarea>
                                </div>
                            </div>
                            <div class="form-group col-lg-6">
                                <label class="control-label col-lg-12">Category</label>
                                <div class="col-lg-12">
                                    <input value="${category != null ? category.name : ""}" disabled class=" form-control">
                                </div>
                            </div>
                            <div class="form-group col-lg-6">
                                <label class="control-label col-lg-12">Date post</label>
                                <div class="col-lg-12">
                                    <input value="${currentBlog.datePost}" disabled class=" form-control">
                                </div>
                            </div>
                            <div class="form-group col-lg-6">
                                <label class="control-label col-lg-12">Date update</label>
                                <div class="col-lg-12">
                                    <input value="${currentBlog.dateUpdate == null ? "No data" : currentBlog.dateUpdate}" disabled class=" form-control">
                                </div>
                            </div>
                            <div class="form-group col-lg-6">
                                <label class="control-label col-lg-12">Status</label>
                                <div class="col-lg-12">
                                    <input  value="${currentBlog.status == 0 ? 'Hidden' : 'Active'}" disabled class=" form-control">
                                </div>
                            </div>
                            <div class="box-content-desc form-group col-lg-12">
                                <label class="control-label col-lg-12">Description</label>
                                <div class="col-lg-12">
                                    ${currentBlog.description}
                                </div>
                            </div>
                        </div>
                </form>
            </div>
            <div class="gallery-grids">
                <div class="gallery-top-grids">
                    <label class="control-label col-lg-12">Image blog</label>
                    <div class="col-sm-3 gallery-grids-left">
                        <div class="gallery-grid">
                            <img src="${currentBlog.image}" alt="Main img" />
                        </div>
                    </div>
                    <div class="clearfix"></div>
                </div>
            </div>               
            <a class="btn btn-info btn-back-to-page" href="/admin/blog"><< Back to blog</a>
        </div>
    </section>
    <%@include file="../components/footer.jsp" %>