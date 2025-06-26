package PayOS;

import Model.Bill;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.payos.PayOS;
import vn.payos.type.PaymentData;
import vn.payos.type.CheckoutResponseData;

import java.io.IOException;

@WebServlet("/createPayment")
public class CreatePaymentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response); // Xử lý GET như POST
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Bước 1: Cấu hình PayOS
        PayOS payOS = new PayOS(
            "9ed2a3d0-5ff6-426e-a387-89a70aed372a",
            "d7dea08f-b2be-47ba-a936-3bdbc94413d7",
            "c26c3194118cbe13f910321b771fb133dbab42d3d98d976accb49af5c39b0373"
        );

        HttpSession session = request.getSession();

        // Bước 2: Lấy bill từ session
        Bill bill = (Bill) session.getAttribute("bill");
        if (bill == null) {
            bill = (Bill) session.getAttribute("billOrderNow");
            System.out.println("[INFO] Sử dụng dữ liệu từ billOrderNow");
        }

        if (bill == null) {
            System.err.println("[ERROR] Không tìm thấy Bill trong session.");
            response.sendRedirect("/order?status=fail");
            return;
        }

        // Bước 3: Lấy total từ bill và tạo amount
        int amount = (int) Math.round(bill.getTotal());
long orderCode = System.currentTimeMillis() + (long) (Math.random() * 1000);

        System.out.println("[INFO] Đang tạo link thanh toán cho đơn hàng #" + orderCode + " với số tiền: " + amount);
        
        // Bước 4: Tạo dữ liệu thanh toán
        PaymentData paymentData = PaymentData.builder()
                .orderCode(orderCode)
                .amount(amount)
                .description("Payment" + orderCode)
                .cancelUrl("http://localhost:8080/order?status=fail")
                .returnUrl("http://localhost:8080/order?transactionStatus=success&orderId=" + orderCode)
                .build();

        // Bước 5: Tạo link thanh toán
        try {
            CheckoutResponseData result = payOS.createPaymentLink(paymentData);
            System.out.println("[SUCCESS] Link thanh toán được tạo: " + result.getCheckoutUrl());
            response.sendRedirect(result.getCheckoutUrl());
        } catch (Exception e) {
            System.err.println("[ERROR] Lỗi khi tạo link thanh toán:");
            e.printStackTrace();
            response.sendRedirect("/order?status=fail");
        }
    }
}
