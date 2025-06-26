<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../components/header.jsp" %>
<style>
    .bill-header {
        display: none;
    }
    @media print {
        .leftside-navigation,
        .top-nav.clearfix{
            display: none;
        }
        .bill-header {
            display: flex;
            justify-content: space-between;
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
        header,
        aside{
            display: none;
        }
        .hidden-print {
            display: none;
        }
        .border-print,
        .border-print th,
        .border-print td,
        .border-print tr{
            border: 1px solid #ddd;
        }
        .footer {
            display: none;
        }
        body {
            margin: 0;
            padding: 0;
            line-height: 1.4;
            color: #000;
        }

        .bill-header {
            margin-bottom: 20px;
        }

        .box-content {
            padding: 10px;
        }

        .btn {
            display: none;
        }

        .hidden-print {
            display: none;
        }

        .footer {
            display: none;
        }

        .table th, .table td {
            padding: 6px;
            font-size: 12px;
        }
    }
    @page {
        size: A4;
        margin: 20mm 15mm;
    }

</style>
<jsp:useBean id="currency" class="Util.CurrencyConverter"></jsp:useBean>
<c:set value="${bill}" var="bill"></c:set>
<section id="main-content">
    <section class="wrapper">
        <div class="bill-header">
            <div class="bill-header-company">
                <div class="bill-header-logo">
                    <img src="./uploads/base/logo.png" alt="logo">
                    <span class="bill-header-name">Group 2</span>
                </div>
            </div>
            <div class="bill-header-date">
                <span class="bill-header-id">Bill code: ${bill.id}</span>
                <span class="bill-header-time">
                    <script>
                        document.write((new Date()).toLocaleString());
                    </script>
                </span>
            </div>
        </div>
        <div class="box-content" id="invoiceContent">
            <div class="table-agile-info">
                <a class="btn btn-info hidden-print" href="/admin/bill"><< Back to bill</a>
                <h2 class="w3ls_head">Details order</h2>
                <button onclick="printAndSendInvoice()" class="btn btn-warning hidden-print">
                    <i class='bx bxs-printer'></i>
                    Print and Send Invoice
                </button>
                <form class="cmxform form-horizontal" method="post" action="/admin/bill">
                    <div class="row">
                        <div class="form-group col-lg-6">
                            <label class="control-label col-lg-12">Customer name</label>
                            <div class="col-lg-12">
                                <input value="${bill.customerName}" disabled class="form-control">
                            </div>
                        </div>
                        <div class="form-group col-lg-6">
                            <label class="control-label col-lg-12">Email</label>
                            <div class="col-lg-12">
                                <input value="${bill.email}" disabled class="form-control">
                            </div>
                        </div>
                        <div class="form-group col-lg-6">
                            <label class="control-label col-lg-12">Phone</label>
                            <div class="col-lg-12">
                                <input value="${bill.phone}" disabled class="form-control">
                            </div>
                        </div>
                        <div class="form-group col-lg-6 p-0">
                            <label class="control-label col-lg-12">Address</label>
                            <div class="col-lg-12">
                                <input value="${bill.address}" disabled class="form-control">
                            </div>
                        </div>
                        <div class="form-group col-lg-6 p-0">
                            <label class="control-label col-lg-12">Detail address</label>
                            <div class="col-lg-12">
                                <input value="${bill.detailAddress}" disabled class="form-control">
                            </div>
                        </div>
                        <div class="form-group col-lg-6">
                            <label class="control-label col-lg-12">Total</label>
                            <div class="col-lg-12">
                                <input value="${currency.currencyFormat(bill.total, "")}" disabled class="form-control">
                            </div>
                        </div>
                        <div class="form-group col-lg-6">
                            <label class="control-label col-lg-12">Payment method</label>
                            <div class="col-lg-12">
                                <input value="${bill.payment == 1 ? "Banking": "Cash"}" disabled class="form-control">
                            </div>
                        </div>
                        <c:if test="${bill.payment == 1}">
                            <div class="form-group col-lg-6">
                                <label class="control-label col-lg-12">Transaction code of VNPAY</label>
                                <div class="col-lg-12">
                                    <input value="${bill.transactionCode}" disabled class="form-control">
                                </div>
                            </div>
                        </c:if>
                        <div class="form-group col-lg-6">
                            <label class="control-label col-lg-12">Date order</label>
                            <div class="col-lg-12">
                                <input value="${bill.dateOrder}" disabled class="form-control">
                            </div>
                        </div>
                        <div class="form-group col-lg-6">
                            <label class="control-label col-lg-12">Status</label>
                            <div class="col-lg-12">
                                <input name="idBill" value="${bill.id}" type="hidden"/>
                                <select name="status">
                                    <option value="1" ${bill.status == 1 ? "selected" : ""}>New</option>
                                    <option value="2" ${bill.status == 2 ? "selected" : ""}>Accept</option>
                                    <option value="3" ${bill.status == 3 ? "selected" : ""}>Preparing</option>
                                    <option value="4" ${bill.status == 4 ? "selected" : ""}>Delivery</option>
                                    <option value="5" ${bill.status == 5 ? "selected" : ""}>Finish</option>
                                    <option value="0" ${bill.status == 0 ? "selected" : ""}>Cancel</option>
                                </select>
                                <button class="btn btn-info hidden-print" name="btn-update-bill-status">Update status</button>
                            </div>
                        </div>
                    </div>
                </form>        
            </div>
            <div class="panel panel-default">
                <div class="panel-heading">
                    Bill details
                </div>
                <div class="table-responsive" style="padding: 5px;">
                    <table class="table border-print">
                        <thead>
                            <tr>
                                <th>.No</th>
                                <th>Bill code</th>
                                <th>Name product</th>
                                <th>Model</th>
                                <th>Image</th>
                                <th>Color</th>
                                <th>Number of product</th>
                                <th>Price</th>  
                                <th>Total</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${billDetails}" var="billDetail" varStatus="loopIndex">
                                <tr>
                                    <td>${loopIndex.index + 1}</td>
                                    <td>${billDetail.billID}</td>
                                    <td>${billDetail.nameProduct}</td>
                                    <td>${billDetail.modelProduct}</td>
                                    <td>
                                        <img src="${billDetail.imgProduct}" alt="${billDetail.nameProduct}">
                                    </td>
                                    <td>${billDetail.color}</td>
                                    <td>${billDetail.numberOfProduct}</td>
                                    <td>${currency.currencyFormat(billDetail.priceProduct, "")}</td>
                                    <td>${currency.currencyFormat(billDetail.numberOfProduct * billDetail.priceProduct, "")}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <a class="btn btn-info hidden-print" href="/admin/bill"><< Back to bill</a>
    </section>
    <form id="sendInvoiceForm" action="InvoiceServlet" method="post">
        <input type="hidden" id="customerEmail" name="customerEmail" value="${bill.email}">
        <input type="hidden" id="htmlContent" name="billId" value="${bill.id}">
    </form>
    <script>
        function printAndSendInvoice() {
            document.getElementById('sendInvoiceForm').submit();
            window.print();
        }
    </script>
    <c:if test="${param.act == 'print-bill'}">
        <script>
            printAndSendInvoice();
        </script>
    </c:if>
<%@include file="../components/footer.jsp" %>