<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="currency" scope="page" class="Util.CurrencyConverter"></jsp:useBean>
<jsp:useBean id="sale" scope="page" class="Util.Sale"></jsp:useBean>
<jsp:useBean id="pagination" scope="page" class="Util.Pagination"></jsp:useBean>
<%--<%@page contentType="text/html" pageEncoding="UTF-8"%>--%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <base href="http://localhost:8080/" />
        <link rel="icon" type="image/x-icon" href="./uploads/base/favicon.png">
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>${category.name}</title>
        <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
        <link rel="stylesheet" href="./user/assets/css/style.css" />
        <link rel="stylesheet" href="./user/assets/css/slick.css" />
        <link rel="stylesheet" href="./user/assets/css/slick-theme.css" />
        <link rel="stylesheet" href="./user/assets/css/base.css" />
        <link rel="stylesheet" href="./user/assets/css/product.css">
        <link rel="stylesheet" href="./user/assets/css/footer.css">
        <link rel="stylesheet" href="./user/assets/css/sliderRange.css">
        <link rel="stylesheet" href="./user/assets/css/pagination.css">
        <link rel="stylesheet" href="./user/assets/css/responsive.css" />
        <script src="https://code.jquery.com/jquery-3.7.0.js"></script>
    </head>
    <body>
        <%@include file="components/header.jsp" %>
        <main>
            <div class="wrapper-product">
                <div class="container">
                    <div class="row m-0 p-0">
                        <div class="col-12 col-lg-3">
                            <!-- filter -->
                            <div class="box-show-filter-mobile hide-on-laptop">
                                <h3>Show filter</h3>
                                <i class='bx bx-chevron-down' onclick="toggleFilter(this)"></i>
                            </div>
                            <div class="wrapper-filter"> 
                                <div class="filter-by-price" style="margin-top: 0px">
                                    <div class="header-filter">
                                        <h2>Lọc theo giá</h2>
                                        <input value= "${category.ID}" type="hidden" class="category-filter" checked>
                                    </div>
                                    <div class="wraper-filler-range">
                                        <div class="row">
                                            <div class="col-sm-12">
                                                <div id="slider-range"></div>
                                            </div>
                                        </div>
                                        <div class="row slider-labels">
                                            <div class="col-xs-6 caption">
                                                <input type="hidden" name="from-price" id="input-from-price">
                                                <strong>Min:</strong> <span id="slider-range-value1"></span>
                                            </div>
                                            <div class="col-xs-6 text-right caption">
                                                <input type="hidden" name="to-price" id="input-to-price">
                                                <strong>Max:</strong> <span id="slider-range-value2"></span>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="box-send-filter">
                                    <button onclick="filterProduct('.render-product')" class="btn btn-filter" type="submit" name="btn-filter">Lọc</button>
                                </div>
                            </div>
                        </div>
                        <div class="col-12 col-lg-9">
                            <!-- products -->
                            <div class="list-product">
                                <!-- filter by time -->
                                <div class="wrapper-header-product">
                                    <div class="message-show-item">
                                        <c:if test="${sizeProduct < 8}">
                                            <span>Showing ${1}- ${sizeProduct} of ${sizeProduct} results</span>
                                        </c:if>
                                        <c:if test="${sizeProduct >= 8}">
                                            <span>Showing ${((page - 1) * 8) + 1}- ${((page - 1) * 8) + 8} of ${sizeProduct} results</span>
                                        </c:if>
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
                                            <img src="./uploads/base/no-product-found.png" alt="Not found">"
                                            <p class="text">Sorry, This category no have product</p>"
                                        </div>
                                    </c:if>
                                </div>
                            </div>
                            <!-- pagination -->
                            <c:set var="categorySlug" value="category/${category.slug}" />
                            ${pagination.generatePagination(page, Math.ceil(sizeProduct / 8), categorySlug)}
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <%@include file="components/footer.jsp" %>
        <script src="https://code.jquery.com/jquery-3.7.0.js" ></script>
        <script src="./user/assets/js/filter.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="./user/assets/js/app.js"></script>
        <script type="text/javascript" src="./user/assets/js/sliderRange.js"></script>
    </body>
</html>
