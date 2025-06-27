<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="currency" scope="page" class="Util.CurrencyConverter"></jsp:useBean>
<jsp:useBean id="sale" scope="page" class="Util.Sale"></jsp:useBean>
    <!DOCTYPE html>
    <html lang="en">
        <head>
            <!--<base href="http://localhost:8080/" />-->
            <link rel="icon" type="image/x-icon" href="/uploads/base/favicon.png">
            <meta charset="UTF-8" />
            <meta name="viewport" content="width=device-width, initial-scale=1.0" />
            <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet" />
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
            <link rel="stylesheet" href="/user/assets/css/style.css" />
            <link rel="stylesheet" href="/user/assets/css/base.css" />
            <link rel="stylesheet" href="/user/assets/css/footer.css">
            <link rel="stylesheet" href="/user/assets/css/slick.css" />
            <link rel="stylesheet" href="/user/assets/css/slick-theme.css" />
            <link rel="stylesheet" href="/user/assets/css/responsive.css" />
            <title>Home</title>
            <style>
                .chat-popup {
                    display: none;
                    position: fixed;
                    bottom: 80px;
                    right: 30px;
                    border: 1px solid #ccc;
                    z-index: 1000;
                    background-color: white;
                    width: 350px;
                    box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
                    border-radius: 10px;
                }
                .chat-popup .chat-box {
                    max-width: 100%;
                    padding: 1rem;
                    border-radius: 8px;
                    box-shadow: none;
                }
                .chat-popup .messages {
                    min-height: 200px;
                    max-height: 300px;
                }
                .open-button {
                    background-color: #555;
                    color: white;
                    padding: 16px 20px;
                    border: none;
                    cursor: pointer;
                    opacity: 0.8;
                    position: fixed;
                    bottom: 23px;
                    right: 28px;
                    width: 60px;
                    height: 60px;
                    border-radius: 50%;
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    font-size: 24px;
                    z-index: 999;
                }
                .open-button:hover {
                    opacity: 1;
                }
            </style>
        </head>
    <body>
        <button class="open-button" onclick="toggleChat()"><i class='bx bxs-chat'></i></button>

        <div class="chat-popup" id="chatPopup">
            <div class="chat-box">
                <h2>ZenStone</h2>
                <div id="messages" class="messages" style="min-height: 200px; padding: 0.5rem; background: #f0f0f0; border-radius: 4px; overflow-y: auto; max-height: 400px; margin-bottom: 1rem;"></div>
                <div class="input-box" style="display: flex; gap: 0.5rem;">
                    <input type="text" id="userMessage" placeholder="Nhập tin nhắn..." onkeydown="if (event.key === 'Enter') {
                                sendMessage();
                            }" style="flex: 1; padding: 0.5rem;">
                    <button onclick="sendMessage()" style="padding: 0.5rem 1rem; cursor: pointer;">Gửi</button>
                </div>
            </div>
        </div>

        <%@include file="user/components/header.jsp" %>
        <main>
            <!-- banner -->
            <div class="banner">
                <div id="carouselExampleIndicators" >
                    <div class="carousel-indicators">
                        <c:forEach var="button" items="${banners}" varStatus="indexLoop">
                            <c:choose>
                                <c:when test="${indexLoop.index == 0}">
                                    <button
                                        type="button"
                                        data-bs-target="#carouselExampleIndicators"
                                        data-bs-slide-to="${indexLoop.index}"
                                        class="active"
                                        aria-current="true"
                                        aria-label="Slide ${indexLoop.index + 1}"
                                        ></button>
                                </c:when>
                                <c:otherwise>
                                    <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="${indexLoop.index}"
                                            aria-label="Slide ${indexLoop.index + 1}"></button>
                                </c:otherwise>
                            </c:choose> 
                        </c:forEach>
                    </div>
                    <div class="carousel-inner">
                        <c:forEach var="banner" items="${banners}" varStatus="indexLoop">
                            <c:choose>
                                <c:when test="${indexLoop.index == 0}">
                                    <div class="carousel-item active">
                                        <img src=" " />
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="carousel-item">
                                        <img src="" />
                                    </div>
                                </c:otherwise>
                            </c:choose> 
                        </c:forEach>
                    </div>
<!--                    <button
                        class="carousel-control-prev"
                        type="button"
                        data-bs-target="#carouselExampleIndicators"
                        data-bs-slide="prev"
                        >
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Previous</span>
                    </button>
                    <button
                        class="carousel-control-next"
                        type="button"
                        data-bs-target="#carouselExampleIndicators"
                        data-bs-slide="next"
                        >
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Next</span>
                    </button>-->
                </div>
            </div>

            <!-- banner product -->
            <!--            <section class="banner-product">
                            <div class="container p-0">
                                <div class="row p-0 m-0">
            <c:forEach begin="0" end="2" items="${bannerTexts}" var="bannerText">
                <div class="card card-banner-item border-0 col-12 col-md-4 col-lg-4 p-2">
                    <div class="container-banner-product">
                        <img src="${bannerText.img}" class="card-img" alt="${bannerText.img}"/>
                        <div class="card-img-overlay">
                            <h5 class="card-title">${bannerText.title}</h5>
                            <p class="card-text">${bannerText.description}</p>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
    </section>-->
            <!-- product top seller -->
            <c:if test="${productsDeal.size() != 0}">
                <section class="top-seller">
                    <div class="container p-0">

                        <div class="header-product flex-column-mobile">
                            <h2 class="title-products">Ưu đãi hàng đầu trong ngày</h2>
                        </div>
                        <div class="wrapper-product">
                            <div class="products">
                                <c:forEach var="productDeal" items="${productsDeal}">
                                    <div class="card col-6 col-md-3 col-lg-3 border-0" onclick="changePage(this, event)">
                                        <div class="box-img">
                                            <img src="${productDeal.mainImg}" alt="${productDeal.name}" />
                                        </div>
                                        <a class="link-page" href="/product/detail/${productDeal.slug}"></a>
                                        <div class="card-body">
                                            <h5 class="card-text name">
                                                ${productDeal.name}
                                            </h5>
                                            <p class="card-text price">
                                                <c:if test="${productDeal.newPrice > 0}">
                                                    <span class="new-price">${currency.currencyFormat(productDeal.newPrice, "VND")}</span>
                                                    <span class="old-price">${currency.currencyFormat(productDeal.oldPrice, "VND")}</span>
                                                </c:if>
                                                <c:if test="${productDeal.newPrice <= 0}">
                                                    <span>${currency.currencyFormat(productDeal.oldPrice, "VND")}</span>
                                                </c:if>
                                            </p>
                                            <div class="add-to-cart">
                                                <a href="/product/detail/${productDeal.slug}" class="btn btn-mini-cart active">
                                                    Chi tiết
                                                </a>
                                            </div>
                                        </div>
                                        <c:if test="${productDeal.newPrice != null && productDeal.newPrice != 0}">
                                            <div class="sale">
                                                <span>
                                                    ${sale.calculateSale(productDeal.newPrice, productDeal.oldPrice)}
                                                </span>
                                            </div>
                                        </c:if>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </section>
            </c:if>
            <!-- banner product -->
            <!--            <section class="banner-product">
                            <div class="container p-0">
                                <div class="row m-0">
            <c:forEach begin="3" end="4" items="${bannerTexts}" var="bannerTextSecond">
                <div class="card card-banner-item border-0 col-12 col-md-6 col-lg-6 p-2">
                    <div class="container-banner-product">
                        <img src="${bannerTextSecond.img}" class="card-img" alt${bannerTextSecond.img}/>
                        <div class="card-img-overlay">
                            <h5 class="card-title">${bannerTextSecond.title}</h5>
                            <p class="card-text">${bannerTextSecond.description}</p>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
    </section>-->

            <section class="banner-product">
                <c:if test="${productsFeature.size() > 0}">
                    <section class="top-seller">
                        <div class="container p-0">
                            <div class="header-product flex-column-mobile">
                                <h2 class="title-products">Sản phẩm nổi bật</h2>
                                <ul class="list-category ">
                                    <li class="category-item">
                                        <a href="/product" >Tất cả sản phẩm</a>
                                    </li>
                                </ul>
                            </div>
                            <div class="wrapper-product">
                                <div class="products product-orderst">
                                    <c:forEach items="${productsFeature}" var="product">
                                        <div class="card col-6 col-md-3 col-lg-3 border-0" onclick="changePage(this, event)">
                                            <div class="box-img">
                                                <img src="${product.mainImg}" alt="${product.name}" />
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
                                                        <span >${currency.currencyFormat(product.oldPrice, "VND")}</span>
                                                    </c:if>
                                                </p>
                                                <div class="add-to-cart">
                                                    <a href="/product/detail/${product.slug}" class="btn btn-mini-cart active">
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
                        </div>
                    </section>
                </c:if>
                <!-- product featured -->
                <!-- render 5 product -->
                <c:if test="${productsNormal.size() > 0}">
                    <section class="product-feature">
                        <div class="container p-0">
                            <div class="header-product">
                                <h2 class="title-products">Sản phẩm bán chạy</h2>
                                <ul class="list-category">
                                    <li class="category-item">
                                        <a href="/product">Tất cả sản phẩm
                                            <i class='bx bx-chevron-right'></i>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                            <div class="box-product-feature row m-0 p-0">
                                <div class="col-12 col-md-6 col-lg-6 left-product-feature">
                                    <div class="card mb-3 border-0 h-100" onclick="changePage(this)">
                                        <div class="row g-0 m-0 h-100">
                                            <a href="/product/detail/${productsNormal.get(0).slug}" class="link-page"></a>
                                            <div class="col-md-4 img-main-feature">
                                                <img src="${productsNormal.get(0).mainImg}" class="img-fluid rounded-start" alt="${productsNormal.get(0).name}">
                                            </div>
                                            <div class="col-md-8">
                                                <div class="card-body">
                                                    <h5 class="name-product-feature">${productsNormal.get(0).name}</h5>
                                                    <p class="price-product-feature">
                                                        <c:if test="${productsNormal.get(0).newPrice > 0}">
                                                            <span class="new-price">${currency.currencyFormat(productsNormal.get(0).newPrice, "VND")}</span>
                                                            <span class="old-price">${currency.currencyFormat(productsNormal.get(0).oldPrice, "VND")}</span>
                                                        </c:if>
                                                        <c:if test="${productsNormal.get(0).newPrice <= 0}">
                                                            <span>${currency.currencyFormat(productsNormal.get(0).oldPrice, "VND")}</span>
                                                        </c:if>
                                                    </p>
                                                    <div class="add-to-cart on-active">
                                                        <a href="/product/detail/${productsNormal.get(0).slug}" class="btn btn-mini-cart active">
                                                            Chi tiết
                                                        </a>
                                                    </div>
                                                </div>
                                                <c:if test="${productsNormal.get(0).newPrice > 0}">
                                                    <div class="sale">
                                                        <span>
                                                            ${sale.calculateSale(productsNormal.get(0).newPrice, productsNormal.get(0).oldPrice)}
                                                        </span>
                                                    </div>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12 col-md-6 col-lg-6 row p-0 m-0 right-product-feature">
                                    <c:forEach begin="1" end="4" items="${productsNormal}" var="productNormal">
                                        <div class="col-6 col-lg-6">
                                            <div class="card mb-3 border-0 h-100" onclick="changePage(this)">
                                                <div class="row g-0 m-0">
                                                    <a href="/product/detail/${productNormal.slug}" class="link-page"></a>
                                                    <div class="col-md-4">
                                                        <img src="${productNormal.mainImg}" class="img-fluid rounded-start" alt="${productNormal.mainImg}">
                                                    </div>
                                                    <div class="col-md-8">
                                                        <div class="card-body">
                                                            <h5 class="name-product-feature">${productNormal.name}</h5>
                                                            <p class="price-product-feature">
                                                                <c:if test="${productNormal.newPrice > 0}">
                                                                    <span class="new-price">${currency.currencyFormat(productNormal.newPrice, "VND")}</span>
                                                                    <span class="old-price">${currency.currencyFormat(productNormal.oldPrice, "VND")}</span>
                                                                </c:if>
                                                                <c:if test="${productNormal.newPrice <= 0}">
                                                                    <span>${currency.currencyFormat(productNormal.oldPrice, "VND")}</span>
                                                                </c:if>
                                                            </p>
                                                        </div>
                                                        <c:if test="${productNormal.newPrice > 0}">
                                                            <div class="sale">
                                                                <span>
                                                                    ${sale.calculateSale(productNormal.newPrice, productNormal.oldPrice)}
                                                                </span>
                                                            </div>
                                                        </c:if>
                                                    </div>
                                                </div>
                                            </div>
                                        </div> 
                                    </c:forEach> 
                                </div>
                            </div>
                        </div>
                    </section>
                    <!-- features -->
                    <section class="features-area">
                        <div class="container p-0">
                            <div class="row p-0 m-0">
                                <div class="feature-item col-6 col-md-6 col-lg-3">
                                    <div class="feature-icon">
                                        <i class="bx bxs-truck"></i>
                                    </div>
                                    <div class="feature-content">
                                        <h5 class="feature-content-title">Miễn phí vận chuyển</h5>
                                        <p>Cho tất cả đơn hàng trên 500,000 VND</p>
                                    </div>
                                </div>
                                <div class="feature-item col-6 col-md-6 col-lg-3">
                                    <div class="feature-icon">
                                        <i class="bx bxs-credit-card"></i>
                                    </div>
                                    <div class="feature-content">
                                        <h5 class="feature-content-title">Thanh toán an toàn</h5>
                                        <p>Thanh toán an toàn 100%</p>
                                    </div>
                                </div>
                                <div class="feature-item col-6 col-md-6 col-lg-3">
                                    <div class="feature-icon">
                                        <i class="bx bxs-chat"></i>
                                    </div>
                                    <div class="feature-content">
                                        <h5 class="feature-content-title">Trung tâm hỗ trợ 24/7</h5>
                                        <p>Trung tâm hỗ trợ 24/7</p>
                                    </div>
                                </div>
                                <div class="feature-item col-6 col-md-6 col-lg-3">
                                    <div class="feature-icon">
                                        <i class="bx bx-support"></i>
                                    </div>
                                    <div class="feature-content">
                                        <h5 class="feature-content-title">Dịch vụ thân thiện</h5>
                                        <p>Cam kết hoàn tiền trong 30 ngày nếu không hài lòng</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>
                    <!-- moving text -->
                    <section class="moveing-text-area">
                        <div class="">
                            <div class="ovic-running">
                                <div class="wrap">
                                    <div class="inner">
                                        <p class="item">Tất cả thông tin sản phẩm được cung cấp bởi ZENSTONE   |   Tất cả thông tin sản phẩm được cung cấp bởi ZENSTONE</p>
                                        <p class="item">Tất cả thông tin sản phẩm được cung cấp bởi ZENSTONE</p>
                                        <p class="item">Tất cả thông tin sản phẩm được cung cấp bởi ZENSTONE</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>
                </c:if>

        </main>
        <%@include file="user/components/footer.jsp" %>
        <script src="https://code.jquery.com/jquery-3.7.0.js" integrity="sha256-JlqSTELeR4TLqP0OG9dxM7yDPqX1ox/HfgiSLBj8+kM=" crossorigin="anonymous" ></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="/user/assets/js/app.js"></script>
        <script type="text/javascript" src="/user/assets/js/slick.js"></script>
        <script src="/user/assets/js/countDown.js"></script>
        <script>
                                                $(document).ready(function () {
                                                    $(".products").slick({
                                                        dots: false,
                                                        infinite: false,
                                                        speed: 400,
                                                        slidesToShow: 5,
                                                        slidesToScroll: 5,
                                                        autoplay: true,
                                                        responsive: [
                                                            {
                                                                breakpoint: 1024,
                                                                settings: {
                                                                    slidesToShow: 3,
                                                                    slidesToScroll: 3,
                                                                    infinite: true,
                                                                    dots: true,
                                                                },
                                                            },
                                                            {
                                                                breakpoint: 600,
                                                                settings: {
                                                                    slidesToShow: 2,
                                                                    slidesToScroll: 2,
                                                                },
                                                            },
                                                            {
                                                                breakpoint: 480,
                                                                settings: {
                                                                    slidesToShow: 2,
                                                                    slidesToScroll: 2,
                                                                },
                                                            },
                                                        ],
                                                    });
                                                });

        </script>
        <script>
            function toggleChat() {
                var popup = document.getElementById("chatPopup");
                if (popup.style.display === "block") {
                    popup.style.display = "none";
                } else {
                    popup.style.display = "block";
                }
            }
            function addMessage(content, sender) {
                var div = document.createElement('div');
                div.className = 'message ' + sender;
                div.innerText = content;
                var messagesContainer = document.getElementById('messages');
                messagesContainer.appendChild(div);
                messagesContainer.scrollTop = messagesContainer.scrollHeight;

                var messageStyle = "margin-bottom: 0.5rem; padding: 0.5rem; border-radius: 4px; max-width: 80%; word-wrap: break-word;";
                if (sender === 'user') {
                    div.style.cssText = messageStyle + "background: #cfe9ff; text-align: right; margin-left: auto;";
                } else {
                    div.style.cssText = messageStyle + "background: #e6e6e6; text-align: left; margin-right: auto;";
                }
            }

            function sendMessage() {
                var msgInput = document.getElementById('userMessage');
                var message = msgInput.value.trim();
                if (message === '')
                    return;
                addMessage(message, 'user');
                msgInput.value = '';
                // Gửi request AJAX
                fetch('chat-bot', {
                    method: 'POST',
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                    body: 'message=' + encodeURIComponent(message)
                })
                        .then(res => res.json())
                        .then(data => {
                            if (data.reply) {
                                addMessage(data.reply, 'bot');
                            } else if (data.error) {
                                addMessage('Error: ' + data.error, 'bot');
                            }
                        })
                        .catch(err => addMessage('Error: ' + err.message, 'bot'));
            }
        </script>
    </body>
</html>
