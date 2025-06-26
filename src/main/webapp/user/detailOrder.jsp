<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:useBean id="currency" class="Util.CurrencyConverter"></jsp:useBean>
    <!DOCTYPE html>
    <html lang="en">
        <head>
            <meta charset="UTF-8">
            <base href="http://localhost:8080/" />
            <link rel="icon" type="image/x-icon" href="./uploads/base/favicon.png">
            <meta name="viewport" content="width=device-width, initial-scale=1.0" />
            <title>Detail bill</title>
            <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet" />
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
            <link rel="stylesheet" href="./user/assets/css/style.css" />
            <link rel="stylesheet" href="./user/assets/css/base.css" />
            <link rel="stylesheet" href="./user/assets/css/footer.css">
            <link rel="stylesheet" href="./user/assets/css/yourCart.css">
            <link rel="stylesheet" href="./user/assets/css/form.css">
            <link rel="stylesheet" href="./user/assets/css/personal.css">
            <link rel="stylesheet" href="./user/assets/css/responsive.css" />
        </head>
        <body>
        <%@include file="components/header.jsp" %>
        <c:if test="${usernameUser == null}">
            <c:redirect url="/404"/>
        </c:if>
        <c:if test="${usernameUser != personalCurrentUser.username}">
            <c:redirect url="/404"/>
        </c:if>
        <c:set value="${currentBill}" var="currentBill" />
        <main>
            <div class="container">
                <ul class="list-redirect">
                    <li class="redirect-item">
                        <a href="" class="redirect-link">Trang chủ</a>
                    </li>
                    <span>/</span>
                    <li class="redirect-item">
                        <a href="/personal/${personalCurrentUser.username}" class="redirect-link">Trang cá nhân</a>
                    </li>
                    <span>/</span>
                    <li class="redirect-item">
                        <a href="personal/${personalCurrentUser.username}/view-bill/${currentBill.id}" class="redirect-link">Xem hóa đơn</a>
                    </li>
                </ul>
                <div class="box-info-personal">
                    <h2 class="personal-title">Thông tin đặt hàng</h2>            
                    <div class="main-info-personal">
                        <div class="group-info-personal">
                            <span class="group-title">
                                Họ và tên: 
                            </span>
                            <span class="group-content">
                                ${currentBill.customerName}
                            </span>
                        </div>
                        <div class="group-info-personal">
                            <span class="group-title">
                                Số điện thoại:  
                            </span>
                            <span class="group-content">
                                ${currentBill.phone}
                            </span>
                        </div>
                        <div class="group-info-personal">
                            <span class="group-title">
                                Email:   
                            </span>
                            <span class="group-content">
                                ${currentBill.email}
                            </span>
                        </div>
                        <div class="group-info-personal">
                            <span class="group-title">
                                Địa chỉ:   
                            </span>
                            <span class="group-content">
                                ${currentBill.address}
                            </span>
                        </div>
                        <div class="group-info-personal">
                            <span class="group-title">
                                Địa chỉ chi tiết:   
                            </span>
                            <span class="group-content">
                                ${currentBill.detailAddress}
                            </span>
                        </div>
                        <div class="group-info-personal">
                            <span class="group-title">
                                Tổng:  
                            </span>
                            <span class="group-content">
                                ${currency.currencyFormat(currentBill.total, "VND")}
                            </span>
                        </div>
                        <div class="group-info-personal">
                            <span class="group-title">
                                Trạng thái đơn hàng:   
                            </span>
                            <span class="group-content" style="font-size: 1.7rem;">
                                <c:choose>
                                    <c:when test="${currentBill.status == 1}">
                                        <span class="label label-primary">
                                            Mới nhận đơn
                                        </span>
                                    </c:when>
                                    <c:when test="${currentBill.status == 2}">
                                        <span class="label label-info">
                                            Đã nhận đơn
                                        </span>
                                    </c:when>
                                    <c:when test="${currentBill.status == 3}">
                                        <span class="label label-danger">
                                            Chuẩn bị hàng
                                        </span>
                                    </c:when>
                                    <c:when test="${currentBill.status == 4}">
                                        <span class="label label-warning">
                                            Vận chuyển hàng
                                        </span>
                                    </c:when>
                                    <c:when test="${currentBill.status == 5}">
                                        <span class="label label-success">
                                            Đã nhận hàng
                                        </span>
                                    </c:when>
                                    <c:when test="${currentBill.status == 0}">
                                        <span class="label label-default">
                                            Hủy đơn hàng
                                        </span>
                                    </c:when>
                                </c:choose>
                            </span>
                        </div>
                    </div>
                    <div class="box-history-order">
                        <h2 class="personal-title">Chi tiết đơn hàng</h2>
                        <div class="yourCart-table p-0">
                            <div class="container">
                                <div class="wrapper-cart">
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <th scope="col">.No</th>
                                                <th class="with-img" scope="col with-img">Tên</th>
                                                <th class="width-20" scope="col">Ảnh</th>
                                                <th class="width-15" scope="col">Màu</th>
                                                <th scope="col">Giá</th>
                                                <th class="width-15" scope="col">Số lượng</th>
                                                <th class="width-10" scope="col">Tổng</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:set var="totalBeforeVoucher" value="0" />
                                            <c:forEach items="${billDetail}" var="b" varStatus="loop">
                                                <tr>
                                                    <td>${loop.index + 1}</td>
                                                    <td>
                                                        ${b.nameProduct}
                                                    </td>
                                                    <td>
                                                        <img class="img-yourCard" src="${b.imgProduct}" alt="product deleted">
                                                    </td>  
                                                    <td>
                                                        ${b.color}
                                                    </td>
                                                    <td>${currency.currencyFormat(b.priceProduct, "")}</td>
                                                    <td>${b.numberOfProduct}</td>
                                                    <c:set var="totalBeforeVoucher" value="${totalBeforeVoucher + (b.priceProduct * b.numberOfProduct)}" />
                                                    <td>${currency.currencyFormat(b.priceProduct * b.numberOfProduct, "VND")}</td>
                                                </tr>
                                            </c:forEach>
                                            <tr>
                                                <td colspan="6">Giảm giá: </td>     
                                                <td colspan="4">${currency.currencyFormat(totalBeforeVoucher - currentBill.total, "VND")}</td>
                                            </tr>
                                            <tr>
                                                <td colspan="6">Tổng tiền sau khi giảm giá: </td>     
                                                <td colspan="4">${currency.currencyFormat(currentBill.total, "VND")}</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>  
                        </div>   
                    </div>
                </div>
            </div>
        </main>
        <%@include file="components/footer.jsp" %>
        <script src="https://code.jquery.com/jquery-3.7.0.js" ></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="./user/assets/js/app.js"></script>
    </body>
</html>