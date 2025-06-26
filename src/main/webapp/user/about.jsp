<%--<%@page contentType="text/html" pageEncoding="UTF-8"%>--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>About</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
        <link rel="stylesheet" href="./user/assets/css/aboutus.css" />
        <link rel="stylesheet" href="./user/assets/css/style.css" />
        <link rel="stylesheet" href="./user/assets/css/base.css" />
        <link rel="stylesheet" href="./user/assets/css/about.css" />
        <link rel="stylesheet" href="./user/assets/css/slide.css" />
        <link rel="stylesheet" href="./user/assets/css/footer.css">
        <link rel="stylesheet" href="./user/assets/css/404.css">
        <link rel="stylesheet" href="./user/assets/css/contact.css" />
        <link rel="stylesheet" href="./user/assets/css/responsive.css" />
    </head>

    <body>
        <%@include file="components/header.jsp" %>
        <main class="about-content">
            <div class="page-banner-area page-banner-height">
                <div class="container">
                    <div class="row">
                        <div class="col-xl-12">
                            <div class="page-banner-content text-center">
                                <h3>Về chúng tôi</h3>
                                <p>${companyBean.top1CompanyActive.introduce}</p>
                                <div class="page-bottom-btn mt-55">
                                    <a href="/product" class="st-btn-4">Mua sắm</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
                                <div class="about-area pt-80 pb-80" data-background="https://wphix.com/template/dukamarket/dukamarket/assets/img/bg/about-bg.png.png" style="background-image: url(&quot;https://wphix.com/template/dukamarket/dukamarket/assets/img/bg/about-bg.png&quot;);">
            <div class="container">
                <div class="row align-items-center">
                   <div class="col-xl-8 col-lg-8">
                       <div class="about-content">
                           <span>VỀ CỬA HÀNG TRỰC TUYẾN CỦA CHÚNG TÔI</span>
                           <h4>Xin chào,</h4>
                           <h5 class="banner-t mb-30">ZENSTONE - Bước Khởi Đầu Của Sự Đổi Mới</h5>
                           <p class="about-text">Mặc dù là một công ty khởi nghiệp mới, ZENSTONE cam kết mang đến những sản phẩm và dịch vụ chất lượng, với khát vọng đồng hành cùng khách hàng trong hành trình xây dựng phong cách sống hiện đại và bền vững.</p>
                           <p>Cách tuyệt vời nhất để bắt đầu là dám nghĩ lớn và hành động từng bước nhỏ. Chúng tôi tin rằng sự chân thành, sáng tạo và tập trung vào trải nghiệm khách hàng sẽ là nền tảng giúp ZENSTONE phát triển trong tương lai. Với từng sản phẩm được chọn lọc kỹ lưỡng, chúng tôi muốn gửi gắm giá trị thực đến từng khách hàng của mình.</p>
                       </div>
                   </div>
                   <div class="col-xl-6 col-lg-6">
                        <div class="about-image w-img">
                            <img src="https://wphix.com/template/dukamarket/dukamarket/assets/img/about/about-b.png" alt="">
                        </div>
                    </div>
                </div>
            </div>
        </div>
                                <div class="services-area pt-70 light-bg-s pb-50">
            <div class="container">
                <div class="row">
                    <div class="col-xl-12">
                        <div class="abs-section-title text-center">
                            <span>CÁCH CHÚNG TÔI HOẠT ĐỘNG</span>
                            <h4>Ý Tưởng Khách Hàng – Được Hiện Thực Hóa Trọn Vẹn</h4>
                            <p>Chúng tôi tin rằng mọi sản phẩm tuyệt vời đều bắt đầu từ nhu cầu thực tế của khách hàng. <br> Dù là một thương hiệu mới, ZENSTONE luôn nỗ lực lắng nghe và hoàn thiện mỗi ngày để mang đến giải pháp phù hợp nhất.</p>
                        </div>
                    </div>
                </div>
                <div class="row mt-40">
                    <div class="col-xl-4 col-lg-4 col-md-6">
                        <div class="services-item mb-30">
                            <div class="services-icon mb-25">
                                <i class="fal fa-share-square"></i>
                            </div>
                            <h6>Xác Minh Thông Tin</h6>
                            <p>Chúng tôi xác minh thông tin đơn hàng và phản hồi nhanh chóng với khách hàng để đảm bảo mọi thứ rõ ràng và minh bạch ngay từ đầu.</p>
                            <div class="s-count-number">
                                <span>01</span>
                            </div>
                        </div>
                    </div>
                    <div class="col-xl-4 col-lg-4 col-md-6">
                        <div class="services-item mb-30">
                            <div class="services-icon mb-25">
                                <i class="fal fa-thumbs-up"></i>
                            </div>
                            <h6>Gửi Giải Pháp</h6>
                            <p>Dựa trên nhu cầu của bạn, ZENSTONE cung cấp sản phẩm hoặc tư vấn phù hợp – linh hoạt, đơn giản và hiệu quả.</p>
                            <div class="s-count-number">
                                <span>02</span>
                            </div>
                        </div>
                    </div>
                    <div class="col-xl-4 col-lg-4 col-md-6">
                        <div class="services-item mb-30">
                            <div class="services-icon mb-25">
                                <i class="fal fa-thumbs-up"></i>
                            </div>
                            <h6>Hoàn Thiện Hồ Sơ</h6>
                            <p>Chúng tôi giúp khách hàng hoàn tất thông tin một cách nhanh chóng và bảo mật, sẵn sàng cho mọi giao dịch tiếp theo.</p>
                            <div class="s-count-number">
                                <span>03</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
                        <div class="technolgy-index mt-70 mb-10">
            <div class="container">
                <div class="row mb-15">
                    <div class="col-xl-12">
                        <div class="abs-section-title text-center">
                            <span>CHỈ SỐ CÔNG NGHỆ</span>
                            <h4>Cùng Sáng Tạo Với ZENSTONE</h4>
                            <p>Cách tốt nhất để tạo ra giá trị bền vững là bắt đầu từ những ý tưởng đơn giản nhưng thiết thực. ZENSTONE luôn hướng đến việc ứng dụng công nghệ và sáng tạo để từng bước hoàn thiện sản phẩm, đồng hành cùng khách hàng trong hành trình khám phá cái đẹp của sự tối giản và hiện đại.</p>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xl-6 col-lg-6">
                        <div class="main-abs mb-30">
                            <div class="ab-counter-image w-img mb-40">
                                <img src="https://wphix.com/template/dukamarket/dukamarket/assets/img/about/ab-01.jpg" alt="">
                            </div>
                            <div class="row">
                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6">
                                    <div class="ab-counter-item mb-30">
                                        <div class="ab-counter mb-20">
                                            <div class="counter-icon mr-10">
                                                <img src="https://wphix.com/template/dukamarket/dukamarket/assets/img/about/c-icon-01.png" alt="">
                                            </div>
                                            <div class="counter_info">
                                                <span class="counter">89+</span>
                                           </div>
                                        </div>
                                        <h5>Khách Hàng Đang Hoạt Động</h5>
                                        <p>Chúng tôi đã có cơ hội phục vụ hàng chục khách hàng đầu tiên và tiếp tục nhận được sự tin tưởng từ cộng đồng sử dụng.</p>
                                    </div>
                                </div>
                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6">
                                    <div class="ab-counter-item mb-30">
                                        <div class="ab-counter mb-20">
                                            <div class="counter-icon mr-10">
                                                <img src="https://wphix.com/template/dukamarket/dukamarket/assets/img/about/c-icon-01.png" alt="">
                                            </div>
                                            <div class="counter_info">
                                                <span class="counter">25+</span>
                                           </div>
                                        </div>
                                        <h5>Dự Án Đã Hoàn Thành</h5>
                                        <p>Mỗi dự án là một bước tiến – từ thiết kế, thử nghiệm đến triển khai – đều mang trong đó giá trị cốt lõi của ZENSTONE.</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-xl-6 col-lg-6">
                        <div class="main-abs mb-30">
                            <div class="ab-counter-image w-img mb-40">
                                <img src="https://wphix.com/template/dukamarket/dukamarket/assets/img/about/ab-02.jpg" alt="">
                            </div>
                            <div class="row">
                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6">
                                    <div class="ab-counter-item mb-30">
                                        <div class="ab-counter mb-20">
                                            <div class="counter-icon mr-10">
                                                <img src="https://wphix.com/template/dukamarket/dukamarket/assets/img/about/c-icon-01.png" alt="">
                                            </div>
                                            <div class="counter_info">
                                                <span class="counter">6+</span>
                                           </div>
                                        </div>
                                        <h5>Cố Vấn Đồng Hành</h5>
                                        <p>Dù là công ty mới, chúng tôi vẫn nhận được sự hỗ trợ quý báu từ các cố vấn giàu kinh nghiệm trong ngành thiết kế và công nghệ.</p>
                                    </div>
                                </div>
                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6">
                                    <div class="ab-counter-item mb-30">
                                        <div class="ab-counter mb-20">
                                            <div class="counter-icon mr-10">
                                                <img src="https://wphix.com/template/dukamarket/dukamarket/assets/img/about/c-icon-01.png" alt="">
                                            </div>
                                            <div class="counter_info">
                                                <span class="counter">1.2k</span>
                                           </div>
                                        </div>
                                        <h5>Lượt Truy Cập Trực Tuyến</h5>
                                        <p>Chỉ trong thời gian ngắn, website ZENSTONE đã thu hút hơn 1.200 lượt truy cập nhờ sự quan tâm và chia sẻ từ cộng đồng.</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>        
<!--            <div class="team-area light-bg-s pt-70 pb-40">       
                <div class="container">
                    <div class="abs-section-title text-center">
                        <span>THE TEAM</span>
                        <h4>Meet Our Team</h4>
                        <p>The perfect way to enjoy brewing tea on low hanging fruit to identify. Duis autem vel eum iriure dolor in hendrerit <br> in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis.</p>
                    </div>
                    <div class="row">
                        <div class="col-xl-4 col-lg-4 col-md-6 col-sm-6">
                            <div class="single-team text-center mb-30">
                                <div class="team-image mb-35 w-img">
                                    <div class="inner-timg">
                                        <a href="/about">
                                            <img src="https://cdn.sforum.vn/sforum/wp-content/uploads/2023/08/hinh-nen-luffy-gear-5-8.jpg" alt="">
                                        </a>
                                    </div>
                                </div>
                                <div class="tauthor-degination mb-15">
                                    <h5><a href="/about">Iqbal Hossain</a></h5>
                                    <span>CEO &amp; Pounder</span>
                                </div>
                                <div class="team-social">
                                    <a href="#"><i class="fab fa-facebook-f"></i></a>
                                    <a href="#"><i class="fab fa-twitter"></i></a>
                                    <a href="#"><i class="fab fa-dribbble"></i></a>
                                    <a href="#"><i class="fab fa-youtube"></i></a>
                                </div>
                            </div>
                        </div>
                        <div class="col-xl-4 col-lg-4 col-md-6 col-sm-6">
                            <div class="single-team text-center mb-30">
                                <div class="team-image mb-35 w-img">
                                    <div class="inner-timg">
                                        <a href="/about">
                                            <img src="https://gamek.mediacdn.vn/zoom/600_315/2019/3/10/avata-1552224321203384823192.jpg" alt="">
                                        </a>
                                    </div>
                                </div>
                                <div class="tauthor-degination mb-15">
                                    <h5><a href="/about">Robin Gomes</a></h5>
                                    <span>CEO &amp; Pounder</span>
                                </div>
                                <div class="team-social">
                                    <a href="#"><i class="fab fa-facebook-f"></i></a>
                                    <a href="#"><i class="fab fa-twitter"></i></a>
                                    <a href="#"><i class="fab fa-dribbble"></i></a>
                                    <a href="#"><i class="fab fa-youtube"></i></a>
                                </div>
                            </div>
                        </div>
                        <div class="col-xl-4 col-lg-4 col-md-6 col-sm-6">
                            <div class="single-team text-center mb-30">
                                <div class="team-image mb-35 w-img">
                                    <div class="inner-timg">
                                        <a href="/about">
                                            <img src="https://cdn.popsww.com/blog/sites/2/2022/02/Hoa-quyen-Ace.jpg" alt="">
                                        </a>
                                    </div>
                                </div>
                                <div class="tauthor-degination mb-15">
                                    <h5><a href="/about">Merry Bob</a></h5>
                                    <span>CEO &amp; Pounder</span>
                                </div>
                                <div class="team-social">
                                    <a href="#"><i class="fab fa-facebook-f"></i></a>
                                    <a href="#"><i class="fab fa-twitter"></i></a>
                                    <a href="#"><i class="fab fa-dribbble"></i></a>
                                    <a href="#"><i class="fab fa-youtube"></i></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>-->
        </main>
        <%@include file="components/footer.jsp" %>
        <script src="https://code.jquery.com/jquery-3.7.0.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>