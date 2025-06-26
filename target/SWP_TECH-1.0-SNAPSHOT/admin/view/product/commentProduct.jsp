<%-- 
    Document   : commentProduct
    Created on : Nov 1, 2023, 2:49:39 PM
    Author     : Le Tan Kim
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="accountDao" class="DAO.AccountDao"></jsp:useBean>
<jsp:useBean id="productDao" class="DAO.ProductDao"></jsp:useBean>
<%@include file="../components/header.jsp" %>
<c:set value="${product}" var="product"></c:set>
    <section id="main-content">
        <section class="wrapper">
            <div class="mail-w3agile">
                <!-- page start-->
                <form action="/admin/product" method="post">
                    <div class="row">
                        <div class="col-sm-12 mail-w3agile">
                            <section class="panel">
                                <header class="panel-heading wht-bg">
                                    <h4 class="gen-case comment-header-custom">All comment for ${product.name}</h4>
                                <h4 class="gen-case">Comment (${commentProduct.size()})</h4>
                            </header>
                            <div class="panel-body minimal">
                                <div class="mail-option">
                                    <div class="chk-all">
                                        <div class="pull-left mail-checkbox all-check">
                                            <input type="checkbox" class="btn-check-all">
                                        </div>
                                    </div>
                                    <div class="btn-group hidden-phone">
                                        <a data-toggle="dropdown" href="#" class="btn mini blue">
                                            Option
                                            <i class="fa fa-angle-down "></i>
                                        </a>
                                        <input type="hidden" name="slug" value="${product.slug}" />
                                        <ul class="dropdown-menu dropdown-menu-customer">
                                            <li>
                                                <button class="btn btn-success" name="btn-active-comment">Active all choose</button>
                                            </li>
                                            <li>
                                                <button class="btn btn-danger" name="btn-ban-comment">Ban all choose</button>
                                            </li>
                                            <li>
                                                <button class="btn btn-warning" name="btn-delete-comment">Delete all choose</button>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="table-inbox-wrap ">
                                    <table class="table table-inbox table-hover">
                                        <tbody>
                                            <c:forEach items="${commentProduct}" var="comment">
                                                <c:set value="${accountDao.getAccountByID(comment.userID)}" var="accountComment" />
                                                <tr class="unread">
                                                    <td class="inbox-small-cells">
                                                        <input type="checkbox" class="mail-checkbox" name="item-comment" value="${comment.id}">
                                                    </td>
                                                    <td class="inbox-small-cells"><i class="fa fa-star"></i></td>
                                                    <td class="view-message  dont-show">${accountComment.username}</td>
                                                    <td class="view-message ">${comment.comment}</td>
                                                    <td class="view-message  inbox-small-cells"><i class="fa fa-paperclip"></i></td>
                                                    <td class="view-message  text-right">${comment.datePost}</td>
                                                    <td class="view-message text-right">
                                                        <c:if test="${comment.status == 1}">
                                                            <span class="label label-success">Active</span>
                                                        </c:if>
                                                        <c:if test="${comment.status == 0}">
                                                            <span class="label label-primary">New</span>
                                                        </c:if>
                                                        <c:if test="${comment.status == 2}">
                                                            <span class="label label-danger">Ban</span>
                                                        </c:if>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </section>
                    </div>
                </div>
            </form>
            <!-- page end-->
        </div>
    </section>
    <%@include file="../components/footer.jsp" %>