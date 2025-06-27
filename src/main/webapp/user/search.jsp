<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="currency" scope="page" class="Util.CurrencyConverter"></jsp:useBean>
<jsp:useBean id="sale" scope="page" class="Util.Sale"></jsp:useBean>
<jsp:useBean id="pagination" scope="page" class="Util.Pagination"></jsp:useBean>
<%--<%@page contentType="text/html" pageEncoding="UTF-8"%>--%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <link rel="icon" type="image/x-icon" href="/uploads/base/favicon.png">
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Search</title>
        <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
        <link rel="stylesheet" href="/user/assets/css/style.css" />
        <link rel="stylesheet" href="/user/assets/css/slick.css" />
        <link rel="stylesheet" href="/user/assets/css/slick-theme.css" />
        <link rel="stylesheet" href="/user/assets/css/base.css" />
        <link rel="stylesheet" href="/user/assets/css/product.css">
        <link rel="stylesheet" href="/user/assets/css/footer.css">
        <link rel="stylesheet" href="/user/assets/css/sliderRange.css">
        <link rel="stylesheet" href="/user/assets/css/pagination.css">
        <link rel="stylesheet" href="/user/assets/css/responsive.css" />
    </head>
    <body>
        <%@include file="components/header.jsp" %>
        <main>
            <div class="wrapper-product">
                <div class="container">
                    <div class="row m-0 p-0">

                        <div class="col-12 col-lg-12">
                            <!-- products -->
                            <div class="list-product">
                                <!-- filter by time -->
                                <div class="wrapper-header-product">
                                    <div class="message-show-item">
                                        <span>Tìm thấy ${products.size()} kết quả của "${keyword}"</span>
                                    </div>
                                </div>
                                <div class="row m-0 render-product">
                                    <c:if test="${products.size() > 0}">
                                        <c:forEach items="${products}" var="product">
                                            <div class="card col-6 col-lg-3"  onclick="changePage(this, event)">
                                                <div class="box-img">
                                                    <img src="${product.mainImg}" class="card-img-top" alt="${product.name}"/>
                                                </div>
                                                <a class="link-page" href="/product/detail/${product.slug}"></a>
                                                <div class="card-body">
                                                    <h5 class="card-text name">
                                                        ${product.name}
                                                    </h5>
                                                    <p class="card-text price">
                                                        <c:if test="${product.newPrice > 0}">
                                                            <span class="new-price">${currency.currencyFormat(product.newPrice, "VND")}</span>
                                                            <span class="old-price">${currency.currencyFormat(product.oldPrice, "VND")}</span>
                                                        </c:if>
                                                        <c:if test="${product.newPrice <= 0}">
                                                            <span>${currency.currencyFormat(product.oldPrice, "VND")}</span>
                                                        </c:if>
                                                    </p>
                                                    <div class="add-to-cart">
                                                        <a href="/product/detail/${product.slug}"class="btn btn-mini-cart active">
                                                            Chi tiết
                                                        </a>
                                                    </div>
                                                </div>
                                                <c:if test="${product.newPrice > 0}">
                                                    <div class="sale">
                                                        <span>
                                                            ${sale.calculateSale(product.newPrice, product.oldPrice)}
                                                        </span>
                                                    </div>
                                                </c:if>
                                            </div> 
                                        </c:forEach>
                                    </c:if>
                                    <c:if test="${products.size() == 0}">
                                        <div class="box-no-found">"
                                            <img src="/uploads/base/no-product-found.png" alt="Not found">"
                                            <p class="text">Xin lỗi, sản phẩm này không có</p>"
                                        </div>
                                    </c:if>
                                </div>
                            </div>
                            <!-- pagination -->
                            ${pagination.generatePagination(page, Math.ceil(sizeProduct / 8), "search", key)}
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <%@include file="components/footer.jsp" %>
        <script
            src="https://code.jquery.com/jquery-3.7.0.js"
            integrity="sha256-JlqSTELeR4TLqP0OG9dxM7yDPqX1ox/HfgiSLBj8+kM="
            crossorigin="anonymous"
        ></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="/user/assets/js/app.js"></script>
        <script type="text/javascript" src="/user/assets/js/sliderRange.js"></script>
    </body>
</html>
