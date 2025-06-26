<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="currency" scope="page" class="Util.CurrencyConverter"></jsp:useBean>
<jsp:useBean id="accountDao" scope="page" class="DAO.AccountDao"></jsp:useBean>
<%--<%@page contentType="text/html" pageEncoding="UTF-8"%>--%>
<c:set var="product" value="${product}" />
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <base href="http://localhost:8080/" />
        <link rel="icon" type="image/x-icon" href="./uploads/base/favicon.png">
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>${product.name}</title>
        <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet" />  
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"  rel="stylesheet" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css" integrity="sha512-HK5fgLBL+xu6dm/Ii3z4xhlSUyZgTT9tuc/hSrtw6uzJOvgRr2a9jyxxT1ely+B+xFAmJKVSTbpM/CuL7qxO8w==" crossorigin="anonymous" />
        <link rel="stylesheet" href="./user/assets/css/style.css" />
        <link rel="stylesheet" href="./user/assets/css/slick.css" />
        <link rel="stylesheet" href="./user/assets/css/slick-theme.css" />
        <link rel="stylesheet" href="./user/assets/css/base.css" />
        <link rel="stylesheet" href="./user/assets/css/inputNumber.css">
        <link rel="stylesheet" href="./user/assets/css/itemProduct.css">
        <link rel="stylesheet" href="./user/assets/css/footer.css">
        <link rel="stylesheet" href="./user/assets/css/toast.css">
        <link rel="stylesheet" href="./user/assets/css/responsive.css" />
    </head>
    <body>
        <%@include file="components/header.jsp" %>       
        <div id="toast"></div>
        <main>
            <section class="header-navigation">
                <div class="container">
                    <ul class="list-redirect">
                        <li class="redirect-item">
                            <a href="" class="redirect-link">Trang chủ</a>
                        </li>
                        <span>/</span>
                        <li class="redirect-item">
                            <a href="/category/${category.slug}" class="redirect-link">${category.name}</a>
                        </li>
                        <span>/</span>
                        <li class="redirect-item">
                            <a href="/product/detail/${product.slug}" class="redirect-link">${product.name}</a>
                        </li>
                    </ul>
                </div>
            </section>
            <section class="main-product">
                <div class="container">
                    <div class="row m-0 wrapper-detail-product">
                        <div class="img-product col-12 col-lg-6">
                            <div class="row p-0 m-0">
                                <div class="img-product-desc col-12 col-lg-2">
                                    <ul class="list-img-desc">
                                        <li class="item-img-desc active" onclick="changeImgPreview(this)">
                                            <img src="${product.mainImg}" alt="${product.name}">
                                        </li>
                                        <c:forEach var="image" items="${imgDesc}" varStatus="loopIndex">
                                            <li class="item-img-desc" onclick="changeImgPreview(this)">
                                                <img src="${image.imgUrl}" alt="Image description ${loopIndex.index}">
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>
                                <div class="main-img-product col-12 col-lg-10">
                                    <div class="box-main-product">
                                        <img src="${product.mainImg}" alt="${product.name}">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="info-product col-12 col-lg-6">
                            <div class="box-info-product">
                                <h2 class="name-product">${product.name}</h2>
                                <h3 class="price-product">
                                    <c:if test="${product.newPrice > 0}">
                                        <span class="new-price">
                                            ${currency.currencyFormat(product.newPrice, "VND")}
                                        </span>
                                        <span class="old-price">
                                            ${currency.currencyFormat(product.oldPrice, "VND")}
                                        </span>
                                    </c:if>
                                    <c:if test="${product.newPrice <= 0}">
                                        ${currency.currencyFormat(product.oldPrice, "VND")}
                                    </c:if>
                                </h3>
                            </div>
                            <div class="box-buy-product">
                                <div class="buy-now">
                                    <c:set value="0" var="quantityTotal" />
                                    <div class="color-product">
                                        <h4>Màu:  </h4>
                                        <ul class="list-color">
                                            <c:set var="firstQuantity" value="0" />
                                            <c:forEach var="color" items="${colors}" varStatus="loopIndex">
                                                <c:if test="${loopIndex.index == 0}">
                                                    <c:set var="firstQuantity" value="${firstQuantity + color.quantity}" />
                                                </c:if>                                            
                                                <c:set value="${quantityTotal + color.quantity}" var="quantityTotal" />
                                                <li class="item-color">
                                                    <label for="${color.ID}" class="yellow ${loopIndex.index == 0 ? 'active' : ''}" onclick="changeColorProductDetail(this, ${color.quantity}); changeImgPreview(this);">
                                                        <img src="${color.imgColor}" alt="Image description ${loopIndex.index}" data-quantity="${color.quantity}">
                                                        <span class="desc-color">${color.name}</span>
                                                    </label>
                                                    <input onchange="changeColor(this.value)" value="${color.ID}" type="radio" name="color" id="${color.ID}">
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                    <div class="available-product">
                                        <span class="available-title">Số lượng: </span>
                                        <c:if test="${quantityTotal <=0}">
                                            <span class="available-number">Đã hết hàng</span>
                                        </c:if>
                                        <c:if test="${firstQuantity >0}">
                                            <span class="available-number">${firstQuantity}</span>
                                        </c:if>
                                    </div>
                                    <c:if test="${quantityTotal > 0}">
                                        <div class="box-add-to-cart">
                                            <div class="number-of-product-buy">
                                                <div class="number-input">
                                                    <button onclick="downInput()" class="minus"></button>
                                                    <input class="quantity" min="1" max="${firstQuantity}" name="quantity" value="1" type="number" readonly>
                                                    <button onclick="upInput()" class="plus"></button>
                                                </div>
                                            </div>
                                            <div class="box-btn-add-to-cart">
                                                <div class="add-to-cart">
                                                    <form action="/cart" method="post">
                                                        <input type="hidden" name="color" class="color-add-to-cart" value="${colors.get(0).ID}"/>
                                                        <input type="hidden" name="idProduct" value="${product.ID}"/>
                                                        <input type="hidden" name="quantityCart" id="quantityCart" value="1"/>
                                                        <button class="btn btn-mini-cart onslu-car" name="add-to-cart">
                                                            Thêm vào giỏ hàng
                                                        </button>
                                                    </form>
                                                </div>
                                                <div class="add-to-cart">
                                                    <form action="/checkout" method="post">
                                                        <input type="hidden" name="color" class="color-add-to-buy" value="${colors.get(0).ID}"/>
                                                        <input type="hidden" name="idProduct" value="${product.ID}"/>
                                                        <input type="hidden" name="numberOfProduct" id="numberOfProduct" value="1"/>
                                                        <button class="btn btn-mini-cart active" name="btn-checkout">
                                                            Mua ngay
                                                        </button>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                        <script>
                                            const changeColorProductDetail = (element, quantity) => {
                                                if (quantity === 0) {
                                                    var miniCartBtn = document.querySelector(".btn.btn-mini-cart.active");
                                                    if (miniCartBtn) {
                                                        miniCartBtn.disabled = true;
                                                    }
                                                    var miniCartBtn = document.querySelector(".btn.btn-mini-cart.onslu-car");
                                                    if (miniCartBtn) {
                                                        miniCartBtn.disabled = true;
                                                    }
                                                } else {
                                                    var miniCartBtn = document.querySelector(".btn.btn-mini-cart.active");
                                                    if (miniCartBtn) {
                                                        miniCartBtn.disabled = false;
                                                    }
                                                    var miniCartBtn = document.querySelector(".btn.btn-mini-cart.onslu-car");
                                                    if (miniCartBtn) {
                                                        miniCartBtn.disabled = false;
                                                    }
                                                }
                                                // Reset all active states
                                                const colorLabels = document.querySelectorAll('.list-color .item-color label');
                                                colorLabels.forEach(label => label.classList.remove('active'));

                                                // Set the clicked label as active
                                                element.classList.add('active');

                                                // Update product image or other related actions
                                                const imgSrc = element.querySelector('img').getAttribute('src');
                                                const imgAlt = element.querySelector('img').getAttribute('alt');
                                                const quantityAvailable = element.querySelector('img').getAttribute('data-quantity');

                                                // Example: Update main product image
                                                const mainProductImg = document.querySelector('.main-product-img');
                                                if (mainProductImg) {
                                                    mainProductImg.src = imgSrc;
                                                    mainProductImg.alt = imgAlt;
                                                }

                                                // Example: Update product description or other elements
                                                const productDescription = document.querySelector('.product-description');
                                                if (productDescription) {
                                                    productDescription.textContent = imgAlt; // Or update with other relevant information
                                                }

                                                // Update the available quantity
                                                document.querySelector('.quantity').max = quantityAvailable;
                                                document.querySelector('.available-number').textContent = quantityAvailable > 0 ? quantityAvailable : 'Sold out';

                                                // Adjust the quantity input if the current value exceeds the new maximum
                                                const currentQuantity = parseInt(document.querySelector('.quantity').value);
                                                if (currentQuantity > quantityAvailable) {
                                                    document.querySelector('.quantity').value = quantityAvailable;
                                                }
                                            }

// The other functions remain the same
                                            const downInput = () => {
                                                const inputNumber = document.querySelector('.quantity');
                                                inputNumber.stepDown();
                                                document.querySelector('#numberOfProduct').value = inputNumber.value;
                                                document.querySelector('#quantityCart').value = inputNumber.value;
                                            }

                                            const upInput = () => {
                                                const inputNumber = document.querySelector('.quantity');
                                                inputNumber.stepUp();
                                                document.querySelector('#numberOfProduct').value = inputNumber.value;
                                                document.querySelector('#quantityCart').value = inputNumber.value;
                                            }

                                            const changeColor = (value) => {
                                                document.querySelector('.color-add-to-buy').value = value;
                                                document.querySelector('.color-add-to-cart').value = value;
                                            }
                                        </script>
                                    </c:if>
                                </div>
                            </div>
                            <div class="wrapper-info-more">
                                <div class="box-info-more">
                                    <span class="box-info-more-title">Kích thước:</span>
                                    <span class="box-info-more-content">${product.model}</span>
                                </div>
                            </div>
                        </div>
                        <div class="wrapper-desc-product">
                            <div class="box-header-desc-product">
                                <ul class="list-header-desc">
                                    <li class="item-header-desc active" onclick="changeContent('.desc-content', this)">
                                        Thông tin sản phẩm
                                    </li>
<!--                                    <li class="item-header-desc" onclick="changeContent('.config-content', this)">
                                        Config
                                    </li>-->
                                    <li class="item-header-desc" onclick="changeContent('.comment', this)">
                                        Bình luận
                                    </li>
                                </ul>
                            </div>
                            <div class="box-desc-content">
                                <div class="desc-content main-desc-content active">
                                    ${product.description}
                                </div>
                                <div class="config-content main-desc-content">
                                    ${product.configProduct}
                                </div>
                                <div class="comment main-desc-content">
                                    <div class="box-comment">
                                        <c:if test="${usernameUser != null}">
                                            <form action="/comment" method="post">
                                                <input type="text" placeholder="Comment" name="comment" required="">
                                                <input type="hidden" name="idProduct" value="${product.ID}">
                                                <input type="hidden" name="slugProduct" value="${product.slug}">
                                                <input type="hidden" name="idUser" value="${accountDao.getAccountByUsername(usernameUser).id}">
                                                <button class="btn btn-add-comment" name="btn-add-comment">Thêm bình luận</button>
                                            </form>
                                        </c:if>
                                        <c:if test="${usernameUser == null}">
                                            <a href="/auth/login" class="go-to-login">Hãy đăng nhập trước khi bình luận!</a>
                                        </c:if>
                                    </div>
                                    <div class="wrapper-comments">
                                        <div class="statistic-comment">
                                            <span class="number-of-comment">${comments != null ? comments.size() : 0} bình luận cho sản phẩm này</span>
                                        </div>
                                        <ul class="list-comment">
                                            <c:forEach items="${comments}" var="comment">
                                                <c:set var="author" value="${accountDao.getAccountByID(comment.userID).username}" />
                                                <li class="comment-item">
                                                    <div class="main-comment">
                                                        <div class="box-author">
                                                            <span class="comment-author">
                                                                ${author}
                                                            </span>
                                                            <span class="comment-time">
                                                                <c:if test="${comment.dateUpdate != null}">
                                                                    Update at ${comment.dateUpdate}
                                                                </c:if>
                                                                <c:if test="${comment.dateUpdate == null}">
                                                                    Post at ${comment.datePost}
                                                                </c:if>
                                                            </span>
                                                        </div>
                                                        <form action="/comment" method="post">
                                                            <input type="hidden" name="idComment" value="${comment.id}"/>
                                                            <input type="hidden" name="slugProduct" value="${product.slug}"/>
                                                            <span class="comment-content">
                                                                ${comment.comment}
                                                            </span>
                                                        </form>
                                                    </div>
                                                    <c:if test="${author == usernameUser}">
                                                        <div class="comment-action">
                                                            <span title="Update comment" class="comment-action-item" onclick="updateCommentForm(this)">
                                                                <i class='bx bxs-edit-alt'></i>
                                                            </span>
                                                            <a title="Delet comment" onclick="return confirm('Are you sure to delete?')" href="/comment/delete/${product.slug}/${comment.id}" class="comment-action-item">
                                                                <i class='bx bx-x'></i>
                                                            </a>
                                                        </div>
                                                    </c:if>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </main>
        <%@include file="components/footer.jsp" %>
        <script src="https://code.jquery.com/jquery-3.7.0.js" ></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="./user/assets/js/app.js"></script>
        <script src="./user/assets/js/itemProduct.js"></script>
        <script src="./user/assets/js/toast.js"></script>
        <script>
            <c:if test="${param.status != null && param.status == 1}">
                                                                showSuccess("Thêm bình luận thành công");
            </c:if>
            <c:if test="${param.status != null && param.status==0}">
                                                                showError("Thêm bình luận thất bại. Hãy thử lại");
            </c:if>
        </script>
    </body>
</html>