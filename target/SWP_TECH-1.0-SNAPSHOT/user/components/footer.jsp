<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<footer>
    <section class="connectpp">
        <div class="section-contect">
            <div class="container">
                <div class="row m-0 p-0">
                    <div class="followst col-12 col-md-6 col-lg-4">
                        <h5>Theo dõi chúng tôi</h5>
                        <p class="followft-p">
                            Chúng tôi giúp bạn dễ dàng hợp nhất, tiếp thị và theo dõi trang mạng xã hội của mình.
                        </p>
                        <section class="mb-4">
                            <!-- Facebook -->
                            <a class="contact-social facebook" href="#">
                                <i class='bx bxl-facebook'></i>
                            </a>
                            <a class="contact-social twitter" href="#">
                                <i class='bx bxl-twitter' ></i>
                            </a>
                            <a class="contact-social youtube" href="#">
                                <i class='bx bxl-youtube' ></i>
                            </a>
                            <a class="contact-social linkedin" href="#">
                                <i class='bx bxl-linkedin' ></i>
                            </a>
                        </section>
                    </div>
                    <div class="followst col-12 col-md-6 col-lg-4">
                        <h5>Makerting</h5>
                        <p class="followft-p">
                            Chúng tôi giúp bạn dễ dàng hợp nhất, tiếp thị và theo dõi trang mạng xã hội của mình.
                        </p>
                    </div>
                    <div class="followst col-12 col-md-6 col-lg-4">
<!--                        <h5>Download</h5>
                        <p class="followft-p">
                            Group 5 App is now available on App Store & Google Play. Get it now.
                        </p>-->
                        <div class="boxst subboxst">
                            <img src="https://wphix.com/template/dukamarket/dukamarket/assets/img/brand/app_ios.png" alt="">
                            <img src="https://wphix.com/template/dukamarket/dukamarket/assets/img/brand/app_android.png" alt="">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <div class="footer-about">
        <div class="container">
            <div class="row m-0 p-0">
                <div class="customer col-12 col-md-6 col-lg-4">
                    <h5>Dịch vụ khách hàng</h5>
                    <div class="sub-customer">
                        <ul>
                            <li><a href="/help-center">Trung tâm giúp đỡ</a></li>
                            <li><a href="/contact">Liên hệ</a></li>
                            <li><a href="/about">Về chúng tôi</a></li>
                        </ul>
                    </div>
                </div>
<!--                <div class="customer col-12 col-md-6 col-lg-4">
                    <h5>Category</h5>
                    <div class="sub-customer">
                        <ul>
                            <c:forEach begin="0" end="6" items="${categoriesBean.getCategoryByStatus(1)}" var="category">
                                <li><a href="/category/${category.slug}">${category.name}</a></li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>-->
                <div class="customer navcustomer col-12 col-md-6 col-lg-4">
                    <h5>Về ZENSTONE</h5>
                    <div class="sub-customer">
                        <div class="introduce-company">${companyBean.top1CompanyActive.introduce}</div>
                        <div class="navcustomer-img">
                            <i class='bx bxs-phone'></i>
                            <ul>
                                <li>
                                    <a href="tel:${companyBean.top1CompanyActive.phone}">
                                        Bạn có câu hỏi? Hãy gọi cho chúng tôi bất cứ lúc nào!
                                    </a>
                                </li>
                                <li>
                                    <a style="color: yellow;font-size: 2.1rem;" href="tel:${companyBean.top1CompanyActive.phone}">
                                        ${companyBean.top1CompanyActive.phone}
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="aboutUS">
        <div class="navaboutus">
            <p>
                ©
                <script>
                    let date = new Date();
                    document.write(date.getFullYear());
                </script>
                Bản quyền thuộc ZENSTONE. Đã đăng ký mọi quyền. Phát triển bởi ZENSTONE.</p>
        </div>
    </div>
</footer>
