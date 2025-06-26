<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <!--<meta charset="UTF-8">-->
        <base href="http://localhost:8080/" />
        <link rel="icon" type="image/x-icon" href="./uploads/base/favicon.png">
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Cart</title>
        <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css"/>
         <link rel="stylesheet" href="./user/assets/css/inputNumber.css" />
        <link rel="stylesheet" href="./user/assets/css/style.css" />
        <link rel="stylesheet" href="./user/assets/css/base.css" />
        <link rel="stylesheet" href="./user/assets/css/footer.css">
        <link rel="stylesheet" href="./user/assets/css/yourCart.css">
        <link rel="stylesheet" href="./user/assets/css/responsive.css" />
    </head>
    <body>
        <%@include file="components/header.jsp" %>
        <main>
            <section class="img-header-cart">
                <img src="https://wphix.com/template/dukamarket/dukamarket/assets/img/banner/page-banner-4.jpg" alt="">
<!--                <div class="box-img-header">
                    <h1 >Giỏ hàng</h1>
                    <ul>
                        <li><a style="color: #666;" href="">Trang chủ</a></li>
                        &#47;
                        <li><a href="">Cart</a></li>
                    </ul>
                </div>-->
            </section>
            <!-- slider -->
            <section class="yourCart-table">
                <div class="container">
                    ${cart}
                </div>  
            </section>   
        </main>
        <%@include file="components/footer.jsp" %>
        <script
            src="https://code.jquery.com/jquery-3.7.0.js"
            integrity="sha256-JlqSTELeR4TLqP0OG9dxM7yDPqX1ox/HfgiSLBj8+kM="
            crossorigin="anonymous"
        ></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="./user/assets/js/app.js"></script>
    </body>
</html>