<%-- 
    Document   : header
    Created on : Oct 1, 2023, 12:09:55 AM
    Author     : Le Tan Kim
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="isLogout" value="${param.logout != null}" />
<c:set var="req" value="${pageContext.getRequest()}" />
<c:set var="session" value="${req.getSession()}" />
<jsp:useBean id="auth" class="Util.Authentication" scope="page"></jsp:useBean>
<c:if test="${isLogout}">
    <c:if test="${auth.Logout(session)}">
        <c:redirect url="/admin/login"/>
    </c:if>
</c:if>
<c:if test="${!auth.isLoginAdmin(req)}">
    <c:redirect url="/admin/login"/>
</c:if>
<!DOCTYPE html>
<head>
    <title>Admin | Tech</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <!-- bootstrap-css -->
    <!-- <base href="http://localhost:8080/" /> -->
    <link rel="icon" type="image/x-icon" href="/uploads/base/favicon.png">
    <link rel="stylesheet" href="/admin/assets/css/bootstrap.min.css" >
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"/>
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/morris.js/0.5.1/morris.css">
    <!-- //bootstrap-css -->
    <!-- Custom CSS -->
    <link href="/admin/assets/css/style.css" rel='stylesheet' type='text/css' />
    <link href="/admin/assets/css/style-responsive.css" rel="stylesheet"/>
    <!-- font CSS -->
    <link href='//fonts.googleapis.com/css?family=Roboto:400,100,100italic,300,300italic,400italic,500,500italic,700,700italic,900,900italic' rel='stylesheet' type='text/css'>
    <!-- font-awesome icons -->
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css"/>
    <link rel="stylesheet" href="/admin/assets/css/toast.css">
    <link rel="stylesheet" href="/admin/assets/css/myCustom.css">
    <!-- //font-awesome icons -->
    <script src="/admin/assets/js/jquery2.0.3.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.7.0.js"></script>
    <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
    <script src="/admin/assets/js/filter.js"></script>
</head>
<body>
    <section id="container">
        <div id="toast"></div>
        <!--header start-->
        <header class="header fixed-top clearfix">
            <!--logo start-->
            <div class="brand">
                <a href="/admin" class="logo">
                    <img src="Logo" alt="Logo" />
                </a>
                <div class="sidebar-toggle-box">
                    <div class="fa fa-bars">
                        <i class='bx bx-menu-alt-left'></i>
                    </div>
                </div>
            </div>
            <!--logo end-->
            <div class="top-nav clearfix">
                <!--search & user info start-->
                <ul class="nav pull-right top-menu">
                    <li class="dropdown">
                        <a data-toggle="dropdown" class="dropdown-toggle" href="/admin">
                            <img src="${avatar}" alt="default avatar">
                            <span class="username">${fullnameAdmin}</span>
                            <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu extended logout">
                            <li>
                                <a href="/admin/account/personal/${usernameAdmin}">
                                    <i class='bx bxs-user'></i>
                                    Personal
                                </a>
                            </li>
                            <li onclick="return confirm('Are you sure to logout?')">
                                <a href="/admin?logout=true">
                                    <i class='bx bx-log-out'></i>
                                    Logout
                                </a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </header>
        <!--header end-->
        <!--sidebar start-->
        <aside>
            <div id="sidebar" class="nav-collapse">
                <!-- sidebar menu start-->
                <div class="leftside-navigation">
                    <ul class="sidebar-menu" id="nav-accordion">
                        <li>
                            <a href="/admin">
                                <span>Home</span>
                            </a>
                        </li>
                        <li>
                            <a href="/admin/company">
                                <span>Company</span>
                            </a>
                        </li>
                        <li>
                            <a href="/admin/blog">
                                <span>Blog</span>
                            </a>
                        </li>
                        <li>
                            <a href="/admin/mail">
                                <span>Mail</span>
                            </a>
                        </li>
                        <li>
                            <a href="/admin/voucher">
                                <span>Voucher</span>
                            </a>
                        </li>
                        <li>
                            <a href="/admin/bill">
                                <span>Bill</span>
                            </a>
                        </li>
                        <li>
                            <a href="/admin/category">
                                <span>Category</span>
                            </a>
                        </li>
                        <li>
                            <a href="/admin/banner">
                                <span>Banner</span>
                            </a>
                        </li>
                        <li>
                            <a href="/admin/banner-text">
                                <span>Banner text</span>
                            </a>
                        </li>
                        <li>
                            <a href="/admin/producer">
                                <span>Producer</span>
                            </a>
                        </li>
                        <li>
                            <a href="/admin/product">
                                <span>Product</span>
                            </a>
                        </li>               
                        <c:if test="${auth.isLogigAdminWithHighPermission(req)}">
                            <li>
                                <a href="/admin/account">
                                    <span>Account</span>
                                </a>
                            </li>
                        </c:if>
                    </ul>            
                </div>
                <!-- sidebar menu end-->
            </div>
        </aside>
        <!--sidebar end-->
        <!--main content start-->