package Controllers.User;

import DAO.AccountDao;
import DAO.BillDao;
import DAO.BillDetailsDao;
import DAO.DistrictDao;
import DAO.ProductDao;
import DAO.ProvinceDao;
import DAO.VoucherDao;
import DAO.WardDao;
import Model.Bill;
import Model.BillDetails;
import Model.CartModel;
import Model.Product;
import Model.Voucher;
import Util.Authentication;
import Util.Cart;
import Util.Email;
import Util.Slug;
import Util.TemplateEmail;
import Util.Validation;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Le Tan Kim
 */
public class OrderUserController extends HttpServlet {

    private static BillDetailsDao billDetailDao;
    private static ProductDao productDao;
    private static BillDao billDao;
    private static Email email;
    private static TemplateEmail templateEmail;

    public OrderUserController() {
        this.billDetailDao = new BillDetailsDao();
        this.productDao = new ProductDao();
        this.billDao = new BillDao();
        this.email = new Email();
        this.templateEmail = new TemplateEmail();
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Validation validate = new Validation();
        String transactionStatus = request.getParameter("vnp_TransactionStatus");
        HttpSession session = request.getSession();
        Bill billOrderNow = (Bill) session.getAttribute("orderNow");
        Bill billOrderCart = (Bill) session.getAttribute("orderCart");
        int result = 0;
        if (billOrderNow != null) {
            CartModel cart = (CartModel) session.getAttribute("cartOrder");
            if (transactionStatus != null) {
                if (transactionStatus.equals("00")) {
                    String transactionCode = request.getParameter("vnp_TxnRef");
                    billOrderNow.setTransactionCode(transactionCode);
                    result = this.addToBillByOrderNow(request, billOrderNow, cart);
                }
            } else {
                result = this.addToBillByOrderNow(request, billOrderNow, cart);
            }
            session.removeAttribute("orderNow");
            session.removeAttribute("cartOrder");
        } else if (billOrderCart != null) {
            if (transactionStatus != null) {
                if (transactionStatus.equals("00")) {
                    String transactionCode = request.getParameter("vnp_TxnRef");
                    billOrderCart.setTransactionCode(transactionCode);
                    result = this.addToBillByCart(request, billOrderCart);
                }
            } else {
                result = this.addToBillByCart(request, billOrderCart);
            }
            if (result == 1) {
                Cookie cookie = new Cookie("cart", null);
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        if (result > 0) {
            VoucherDao voucherDao = new VoucherDao();
            int sesionVoucherId = validate.getInt((String) session.getAttribute("voucherId"));
            int userLoginToOrder = validate.getInt((String) session.getAttribute("userLoginToOrder"));
            Voucher v = voucherDao.currentVoucher(sesionVoucherId);
            if (v != null) {
                voucherDao.updateUsedVouhcer(v.getUsed() + "," + userLoginToOrder, sesionVoucherId);
            }
            session.removeAttribute("voucherId");
            session.removeAttribute("userLoginToOrder");
            response.sendRedirect("/order-status/success");
        } else {
            session.removeAttribute("voucherId");
            session.removeAttribute("userLoginToOrder");
            response.sendRedirect("/order-status/fail");
        }
    }

    private int addToBillByOrderNow(HttpServletRequest request, Bill billOrderNow, CartModel cart) {
        int result = 0;
        try {
            Product product = productDao.getProductByID(cart.getId());
            Slug slugT = new Slug();
            float price = product.getNewPrice() > 0 ? product.getNewPrice() : product.getOldPrice();
            int idBill = billDao.addBill(billOrderNow);
            BillDetails billDetail = new BillDetails(0, idBill, product.getMainImg(), cart.getNumberOfProduct(), price, product.getModel(), product.getName(), cart.getColor());
            result = billDetailDao.addBillDetail(billDetail);
            if (result == 1) {
                List<CartModel> carts = new ArrayList<>();
                carts.add(cart);
                String template = templateEmail.emailConfirmOrder(billOrderNow, carts, "Order confirmation");
                email.sendEmail(billOrderNow.getEmail(), "Confirm order", template, "kimltce170469@fpt.edu.vn");
                email.sendEmail("kimltce170469@fpt.edu.vn", "New order", template, "kimltce170469@fpt.edu.vn");
                String slug = slugT.toSlug(product.getName());
                productDao.updateSold(product.getAvailable() - cart.getNumberOfProduct(), product.getSold() + cart.getNumberOfProduct(), product.getID(), slug);
                HttpSession session = request.getSession();
                session.setAttribute("billJustOrder", idBill);
            }
        } catch (Exception e) {
            System.out.println("Order now: " + e);
        }
        return result;
    }

    private int addToBillByCart(HttpServletRequest request, Bill billOrderCart) {
        int idBill = billDao.addBill(billOrderCart);
        Slug slugT = new Slug();
        int result = 0;
        try {
            List<CartModel> cartToOrder = loadCartCookie(request);
            Float total = 0f;
            for (CartModel ca : cartToOrder) {
                Product p = productDao.getProductByID(ca.getId());
                Float price = p.getNewPrice() > 0 ? p.getNewPrice() : p.getOldPrice();
                BillDetails billDetail = new BillDetails(0, idBill, p.getMainImg(), ca.getNumberOfProduct(), price, p.getModel(), p.getName(), ca.getColor());
                result = billDetailDao.addBillDetail(billDetail);
                if (result == 1) {
                    String slug = slugT.toSlug(p.getName());
                    productDao.updateSold(p.getAvailable() - ca.getNumberOfProduct(), p.getSold() + ca.getNumberOfProduct(), ca.getId(), slug);
                }
            }
            if (result == 1) {
                String template = templateEmail.emailConfirmOrder(billOrderCart, cartToOrder, "Order confirmation");
                email.sendEmail(billOrderCart.getEmail(), "Confirm order", template, "kimltce170469@fpt.edu.vn");
                email.sendEmail("kimltce170469@fpt.edu.vn", "New order", template, "kimltce170469@fpt.edu.vn");
                HttpSession session = request.getSession();
                session.setAttribute("billJustOrder", idBill);
            }
        } catch (Exception e) {
            System.out.println("Order cart: " + e);
        }
        return result;
    }

    private List<CartModel> loadCartCookie(HttpServletRequest request) {
        Cart cart = new Cart();
        Cookie[] cookie = request.getCookies();
        try {
            String cartCookie = "";
            for (Cookie cook : cookie) {
                if (cook.getName().equals("cart")) {
                    cartCookie = cook.getValue();
                    break;
                }
            }
            List<CartModel> cartToOrder = cart.loadCart(cartCookie);
            return cartToOrder;
        } catch (Exception e) {
            System.out.println("Order cart: " + e);
        }
        return null;
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Validation validate = new Validation();
        ProvinceDao provinceDao = new ProvinceDao();
        DistrictDao districtDao = new DistrictDao();
        ProductDao productDao = new ProductDao();
        Authentication auth = new Authentication();
        AccountDao accountDao = new AccountDao();
        WardDao wardDao = new WardDao();
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        int province = validate.getInt(request.getParameter("province"));
        int district = validate.getInt(request.getParameter("district"));
        int ward = validate.getInt(request.getParameter("ward"));
        String address = wardDao.getWardByID(ward).getName() + ", "
                + districtDao.getDistrictByID(district).getName() + ", " + provinceDao.getProvinceByID(province).getName();
        String detailAddress = request.getParameter("details-address");
        String voucher = request.getParameter("voucher");
        int payMethod = validate.getInt(request.getParameter("method-payment"));
        LocalDateTime dateTime = LocalDateTime.now();
        Timestamp dateOrder = Timestamp.valueOf(dateTime);
        String usernameLogin = auth.isLogigUser(request);
        int idUser = -1;
        if (usernameLogin != null) {
            idUser = accountDao.getAccountByUsername(usernameLogin).getId();
            session.setAttribute("userLoginToOrder", idUser + "");
        }
        if (request.getParameter("btn-order-now") != null) {
            CartModel c = (CartModel) session.getAttribute("cartOrder");
            Product p = productDao.getProductByID(c.getId());
            Float total = p.getNewPrice() > 0 ? p.getNewPrice() * c.getNumberOfProduct() : p.getOldPrice() * c.getNumberOfProduct();
            VoucherDao voucherDao = new VoucherDao();
            Voucher voucherActive = voucherDao.getVoucherByCode(voucher, total);
            if (voucherActive != null) {
                total = total - voucherActive.getValue();
                session.setAttribute("voucherId", voucherActive.getId() + "");
            }
            System.out.println("total: " + total);
            Bill bill = null;
            try {
                bill = new Bill(0, idUser, email, fullname, phone, address, detailAddress, total, 1, payMethod, dateOrder, null);
            } catch (Exception e) {
                System.out.println("Lôi: " + e);
            }
            session.setAttribute("orderNow", bill);
            int totalAmount = (int) (total / 10) * 10;
            if (payMethod == 1) {
                session.setAttribute("billOrderNow", bill);
//session.setAttribute("listDetailOrderNow", listDetail);
              response.sendRedirect("/createPayment");
            } else {
                response.sendRedirect("/order");
            }
        } else if (request.getParameter("btn-order-cart") != null) {
            try {
                List<CartModel> cartToOrder = this.loadCartCookie(request);
                Float total = 0f;
                for (CartModel ca : cartToOrder) {
                    Product p = productDao.getProductByID(ca.getId());
                    Float totalItem = p.getNewPrice() > 0 ? p.getNewPrice() * ca.getNumberOfProduct() : p.getOldPrice() * ca.getNumberOfProduct();
                    total += totalItem;
                }
                VoucherDao voucherDao = new VoucherDao();
                Voucher voucherActive = voucherDao.getVoucherByCode(voucher, total);
                if (voucherActive != null) {
                    total = total - voucherActive.getValue();
                    session.setAttribute("voucherId", voucherActive.getId() + "");
                }
                int totalAmount = (int) (total / 10) * 10;
                Bill bill = null;
                try {
                    bill = new Bill(0, idUser, email, fullname, phone, address, detailAddress, totalAmount, 1, payMethod, dateOrder, null);
                } catch (Exception e) {
                    System.out.println("Lôi: " + e);
                }
                session.setAttribute("orderCart", bill);
                if (payMethod == 1) {
                    session.setAttribute("billOrderNow", bill);
//                    session.setAttribute("listDetailOrderNow", listDetail);
                    response.sendRedirect("/createPayment");
                } else {
                    response.sendRedirect("/order");
                }
            } catch (Exception e) {
                System.out.println("Order cart: " + e);
            }

        }
    }
}
