<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8"></meta>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"></meta>
        <title>Xác nhận hóa đơn</title>
        <style>
            .container {
                width: 1200px;
                margin: 0 auto;
            }
            .header {
                margin: 10px 0px;
                font-size: 20px;
                text-align: center;
                text-transform: uppercase;
                color: #fcbe00;
            }
            .list-info {
                list-style: none;
                padding: 0 5px;
                margin: 0;
            }
            .item-info {
                padding: 5px 0px;
            }
            .title-info {
                font-weight: 600;
                font-size: 16px;
            }
            .content-info {
                font-style: italic;
                font-size: 16px;
            }
            .order-product {
                width: 100%;
            }
            .order-product table  {
                width: 100%;
                text-align: center;
                border-collapse: collapse;
                border: 1px solid #333;
            }
            .order-product table th {
                font-weight: bold;
                color: #fff;
                background-color: #fcbe00;
            }
            .order-product table th,
            .order-product table td {
                border: 1px solid #333;
                padding: 7px 0px;
            }
            .order-product table .total {
                background-color: #e4e4e4;
                font-weight: bold;
                font-size: 16px;
                text-transform: uppercase;
            }
            .thank p {
                font-size: 16px;
                font-weight: bold;
            }
            .bill-header-logo {
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
            }
            .bill-header-logo img {
                max-width: 200px;
                margin-bottom: 5px;
            }
            .bill-header-logo .bill-header-name {
                text-transform: uppercase;
            }
            .bill-header-date {
                display: flex;
                flex-direction: column;
                align-items: flex-end;
            }
            .btn-primary {
                background-color: #fcbe00;
                border: none;
                padding: 10px 20px;
                font-size: 16px;
                border-radius: 5px;
                cursor: pointer;
                color: #fff;

            }
            a.btn-primary {
                text-decoration: none;
            }
            .btn-primary:hover {
                background-color: #fcbe00;
            }
            .print-time {
                display: none;
            }
            @media print {
                .footer,
                .bill-header-date .bill-header-time {
                    display: none;
                }
                .print-time {
                    display: block;
                    position: fixed;
                    bottom: 10px;
                    right: 10px;
                    font-size: 12px;
                }
            }

        </style>
        <script>
            function printBill() {
                var now = new Date();
                var printTime = now.toLocaleString();
                document.getElementById('printTime').innerText = 'Printed at: ' + printTime;
                window.print();
            }
        </script>
    </head>
    <body>
        <div class="container">
            <div class="bill-header">
                <div class="bill-header-company">
                    <div class="bill-header-logo">
                        <img src="${pageContext.request.contextPath}/user/assets/img/zen_logo.png" alt="logo"></img>
                        <!--<span class="bill-header-name">ZENSTONE</span>-->
                    </div>
                </div>
                <div class="bill-header-date">
                    <p class="bill-header-id">Mã đơn hàng: #ORDER${bill.id}</p>
                </div>
            </div>
            <div class="header">
                <h2>${title}</h2>
            </div>
            <div class="main-order">
                <div class="info-order">
                    <ul class="list-info">
                        <li class="item-info">
                            <span class="title-info">Họ và tên: </span>
                            <span class="content-info">${bill.customerName}</span>
                        </li>
                        <li class="item-info">
                            <span class="title-info">Email: </span>
                            <span class="content-info">${bill.email}</span>
                        </li>
                        <li class="item-info">
                            <span class="title-info">Số điện thoại: </span>
                            <span class="content-info">${bill.phone}</span>
                        </li>
                        <li class="item-info">
                            <span class="title-info">Địa chỉ: </span>
                            <span class="content-info">${bill.address}</span>
                        </li>
                        <li class="item-info">
                            <span class="title-info">Thông tin địa chỉ: </span>
                            <span class="content-info">${bill.detailAddress}</span>
                        </li>
                        <li class="item-info">
                            <span class="title-info">Phương thức thanh toán: </span>
                            <span class="content-info">${bill.payment == 1 ? 'Banking' : 'Cash'}</span>
                        </li>
                    </ul>
                </div>
                <div class="order-product">
                    <h2>Sản phẩm</h2>
                    <table>
                        <thead>
                            <tr>
                                <th>.No</th>
                                <th>Tên sảm phẩm</th>
                                <th>Số lượng</th>
                                <th>Màu</th>
                                <th>Giá</th>
                                <th>Tổng tiền</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:set var="total" value="0"  />
                            <c:forEach var="item" items="${listDetail}" varStatus="status">
                                <c:set var="total" value="${(item.priceProduct * item.numberOfProduct) + total}"  />
                                <tr>
                                    <td>${status.index + 1}</td>
                                    <td>${item.nameProduct}</td>
                                    <td>${item.numberOfProduct}</td>
                                    <td>${item.color}</td>
                                    <td><fmt:formatNumber value="${item.priceProduct}" type="currency" currencySymbol="VND"/></td>
                                    <td><fmt:formatNumber value="${item.priceProduct * item.numberOfProduct}" type="currency" currencySymbol="VND"/></td>
                                </tr>
                            </c:forEach>
                            <tr class="total">
                                <td colspan="4">Giảm giá: </td>
                                <td colspan="4"><fmt:formatNumber value="${total - bill.total}" type="currency" currencySymbol="VND"/></td>
                            </tr>
                            <tr class="total">
                                <td colspan="4">Tổng tiền sau khi áp dụng mã giảm giá: </td>
                                <td colspan="4"><fmt:formatNumber value="${bill.total}" type="currency" currencySymbol="VND"/></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="thank">
                <p>Cảm ơn bạn đã đặt hàng từ ZENSTONE.</p>
            </div>
            <div class="footer">
                <button class="btn btn-primary" onclick="printBill()">In đơn hàng</button>
                <a class="btn btn-primary" href="../">Trang chủ</a>
            </div>
            <div class="print-time" id="printTime"></div>
        </div>
    </body>
</html>
