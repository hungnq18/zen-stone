<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <link rel="icon" type="image/x-icon" href="/uploads/base/favicon.png">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Help center</title>
        <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>        
        <link rel="stylesheet" href="/user/assets/css/aboutus.css" />
        <link rel="stylesheet" href="/user/assets/css/style.css" />
        <link rel="stylesheet" href="/user/assets/css/base.css" />
        <link rel="stylesheet" href="/user/assets/css/about.css" />
        <link rel="stylesheet" href="/user/assets/css/footer.css">
        <link rel="stylesheet" href="/user/assets/css/404.css">
        <link rel="stylesheet" href="/user/assets/css/contact.css" />
        <link rel="stylesheet" href="/user/assets/css/responsive.css" />
    </head>
    <body>
        <%@include file="components/header.jsp" %>
        <main>
            <!--<div class="page-banner-header" style="background-image: url('	https://wphix.com/template/dukamarket/dukamarket/assets/img/banner/page-banner-3.jpg')">-->
<!--                <div class="box-content-banner">
                    <h1>FAQs</h1>
                </div>-->
            <!--</div>-->
            <div class="main-content-contact">
                <div class="container">
                    <div class="row">
                        <div class="col-lg-6">
                            <div class="single-faqs mb-40">
                                <div class="faq-title">
                                    <h5>Đặt hàng hoặc trả hàng</h5>
                                </div>
                                <div class="faq-content mt-10">
                                    <div class="accordion" id="accordionExample1">
                                        <div class="accordion-item">
                                            <h2 class="accordion-header" id="headingOne">
                                                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="false" aria-controls="collapseOne">
                                                    Làm thế nào tôi có thể đặt hàng?
                                                </button>
                                            </h2>
                                            <div id="collapseOne" class="accordion-collapse collapse" aria-labelledby="headingOne" data-bs-parent="#accordionExample">
                                                <div class="accordion-body">
                                                    <p>Bạn có thể đặt hàng trực tiếp trên website ZENSTONE. Chỉ cần chọn sản phẩm mong muốn, thêm vào giỏ hàng và tiến hành thanh toán theo hướng dẫn.</p>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="accordion-item">
                                            <h2 class="accordion-header" id="headingTwo">
                                                <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseTwo" aria-expanded="true" aria-controls="collapseTwo">
                                                    Chính sách đổi/trả hàng như thế nào?
                                                </button>
                                            </h2>
                                            <div id="collapseTwo" class="accordion-collapse collapse show" aria-labelledby="headingTwo" data-bs-parent="#accordionExample">
                                                <div class="accordion-body">
                                                    <p>ZENSTONE hỗ trợ đổi/trả hàng trong vòng 7 ngày kể từ khi nhận hàng nếu sản phẩm bị lỗi từ nhà sản xuất hoặc giao sai mẫu. Vui lòng giữ nguyên bao bì sản phẩm và liên hệ với chúng tôi để được hỗ trợ.</p>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="accordion-item">
                                            <h2 class="accordion-header" id="headingThree">
                                                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                                                    Thời gian giao hàng mất bao lâu?
                                                </button>
                                            </h2>
                                            <div id="collapseThree" class="accordion-collapse collapse" aria-labelledby="headingThree" data-bs-parent="#accordionExample">
                                                <div class="accordion-body">
                                                    <p>Thời gian giao hàng thông thường từ 2–5 ngày làm việc, tùy thuộc vào địa chỉ nhận hàng và phương thức vận chuyển bạn chọn.</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-6">
                            <div class="single-faqs mb-40">
                                <div class="faq-title">
                                    <h5>Đặt hàng từ ZENSTONE</h5>
                                </div>
                                <div class="faq-content mt-10">
                                    <div class="accordion" id="accordionExample2">
                                        <div class="accordion-item">
                                            <h2 class="accordion-header" id="heading1">
                                                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapse1" aria-expanded="false" aria-controls="collapse1">
                                                    Phương thức thanh toán có những gì?
                                                </button>
                                            </h2>
                                            <div id="collapse1" class="accordion-collapse collapse" aria-labelledby="heading1" data-bs-parent="#accordionExample">
                                                <div class="accordion-body">
                                                    <p>Chúng tôi hỗ trợ thanh toán qua chuyển khoản ngân hàng, hoặc thanh toán khi nhận hàng (COD).</p>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="accordion-item">
                                            <h2 class="accordion-header" id="heading2">
                                                <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapse2" aria-expanded="true" aria-controls="collapseTwo">
                                                    Tôi cần thanh toán bao nhiêu?
                                                </button>
                                            </h2>
                                            <div id="collapse2" class="accordion-collapse collapse show" aria-labelledby="heading2" data-bs-parent="#accordionExample">
                                                <div class="accordion-body">
                                                    <p>Số tiền thanh toán sẽ được hiển thị rõ ràng tại trang giỏ hàng bao gồm giá sản phẩm, phí vận chuyển và các loại phí (nếu có) như COD hoặc phụ phí vùng xa.</p>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="accordion-item">
                                            <h2 class="accordion-header" id="heading3">
                                                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapse3" aria-expanded="false" aria-controls="collapse3">
                                                    Tôi có nhận được xác nhận sau khi đặt hàng không?
                                                </button>
                                            </h2>
                                            <div id="collapse3" class="accordion-collapse collapse" aria-labelledby="heading3" data-bs-parent="#accordionExample">
                                                <div class="accordion-body">
                                                    <p>Có. Sau khi thanh toán, bạn sẽ nhận được một cuộc gọi hoặc email xác nhận từ ZENSTONE để đảm bảo đơn hàng được xử lý chính xác.</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
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
    </body>
</html>
