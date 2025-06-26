<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="./components/header.jsp" %>
<!-- END nav -->
<style>
    .container-blog img,
    .container-blog table{
        max-width: 100%;
    }
</style>
<div class="hero-wrap hero-bread" style="background-image: url('${currentBlog.image}');">
    <div class="container">
        <div class="row no-gutters slider-text align-items-center justify-content-center">
            <div class="col-md-9 ftco-animate text-center">
                <p class="breadcrumbs"><span class="mr-2"><a href="/">Trang chủ</a></span> <span>Blog</span></p>
                <h1 class="mb-0 bread">Blog</h1>
            </div>
        </div>
    </div>
</div>

<section class="ftco-section ftco-degree-bg">
    <div class="container">
        <div class="row">
            <div class="col-lg-8 ftco-animate" style="padding:10px;border: 1px solid #f7f7f7">
                <h2 style="font-weight: bold">${currentBlog.title}</h2>
                <div class="meta" style="display: flex; gap: 10px; font-size: 12px; font-style: italic">
                    <div>Đăng bởi admin ${currentBlog.datePost}</div>
                </div>
                <div class="container-blog" style="padding-top:10px;border-top: 1px solid #fafafa">
                    ${currentBlog.description}
                </div>
            </div> <!-- .col-md-8 -->
            <div class="col-lg-4 sidebar ftco-animate">
                <div class="sidebar-box">
                    <form action="/blog" method="post" class="search-form">
                        <div class="form-group">
                            <input type="text" name="key" class="form-control" placeholder="Tìm kiếm..." value="${key}">
                            <button name="btn-search" style="margin-top: 10px;" class="btn btn-warning">Tìm kiếm</button>
                        </div>
                    </form>
                </div>
                <div class="sidebar-box ftco-animate">
                    <h3 class="heading">Danh mục</h3>
                    <ul class="categories">
                        <c:forEach items="${categories}" var="cat">
                                <li><a href="/blog/category/${cat.ID}">${cat.name} </a></li>
       
                            </c:forEach>
                    </ul>
                </div>
            </div>

        </div>
    </div>
</section> <!-- .section -->
<%@include file="./components/footer.jsp" %>