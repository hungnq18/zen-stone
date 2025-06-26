
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="currency" scope="page" class="Util.CurrencyConverter"></jsp:useBean>
<jsp:useBean id="sale" scope="page" class="Util.Sale"></jsp:useBean>
<jsp:useBean id="pagination" scope="page" class="Util.Pagination"></jsp:useBean>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <base href="http://localhost:8080/" />
        <link rel="icon" type="image/x-icon" href="./uploads/base/favicon.png">
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Product</title>
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
        <script src="https://cdn.jsdelivr.net/npm/wnumb@1.1.0/wNumb.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/nouislider@15.5.1/distribute/nouislider.min.js"></script>

    </head>
    <script>
        $(document).ready(function () {
            $('.noUi-handle').on('click', function () {
                $(this).width(50);
            });
            var rangeSlider = document.getElementById('slider-range');
            var moneyFormat = wNumb({
                decimals: 0,
                thousand: ',',
                prefix: 'VND'
            });
            noUiSlider.create(rangeSlider, {
                start: [200000, 4000000],
                step: 1,
                range: {
                    'min': [200000],
                    'max': [4000000]
                },
                format: moneyFormat,
                connect: true
            });
            // Set visual min and max values and also update value hidden form inputs
            rangeSlider.noUiSlider.on('update', function (values, handle) {
                document.getElementById('slider-range-value1').innerHTML = values[0];
                document.getElementById('slider-range-value2').innerHTML = values[1];
                document.getElementById('input-from-price').value = parseFloat(values[0].replace(/[^\d]/g, ""));
                document.getElementById('input-to-price').value = parseFloat(values[1].replace(/[^\d]/g, ""));
//        document.getElementById('input-from-price').value = parseFloat(values[0].replace(/[VND,]/g, ""));
//        document.getElementById('input-to-price').value = parseFloat(values[1].replace(/[VND,]/g, ""));
        document.getElementsByName('min-value').value = moneyFormat.from(
            values[0]);
        document.getElementsByName('max-value').value = moneyFormat.from(
            values[1]);
            });
        });
    </script>
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
                                <div class="filter-category">
                                    <div class="header-filter">
                                        <h2>Màu sắc</h2>
                                    </div>
                                    <div class="list-filter">
                                        <ul class="list">
                                            <c:forEach items="${categories}" var="category" varStatus="loopIndex">
                                                <li class="filter-item">
                                                    <input value= "${category.ID}" type="checkbox" name="category-filter" class="category-filter" id="category-filter-${loopIndex.index}">
                                                    <label for="category-filter-${loopIndex.index}">
                                                        ${category.name}
                                                        <span class="category-number-product">(${category.numberOfProduct})</span>
                                                    </label>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </div>
                                <div class="filter-by-price">
                                    <div class="header-filter">
                                        <h2>Giá</h2>
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
                                <div class="filter-by-price">
                                    <div class="header-filter">
                                        <h2>Thời gian</h2>
                                    </div>
                                    <div class="wraper-filler-range">
                                        <div class="filter-by-time">
                                            <select name="filter-by-time" class="filter-time">
                                                <option value="1">Mới nhất</option>
                                                <option value="0">Cũ nhất</option>
                                            </select>
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
                                            <span> Hiển thị ${1}- ${sizeProduct} của ${sizeProduct} kết quả</span>
                                        </c:if>
                                        <c:if test="${sizeProduct >= 8}">
                                            <span>Hiển thị ${((page - 1) * 8) + 1}- ${((page - 1) * 8) + 8} của ${sizeProduct} kết quả</span>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="row m-0 render-product">
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
                                </div>
                            </div>
                            <!-- pagination -->
                            ${pagination.generatePagination(page, Math.ceil(sizeProduct / 8), "product")}
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
