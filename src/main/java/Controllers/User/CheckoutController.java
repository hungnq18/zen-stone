package Controllers.User;

import DAO.AccountDao;
import DAO.ColorDao;
import DAO.ProductDao;
import DAO.ProvinceDao;
import Model.Account;
import Model.CartModel;
import Model.Color;
import Model.Product;
import Model.Province;
import Util.Authentication;
import Util.Cart;
import Util.Validation;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Le Tan Kim
 */
public class CheckoutController extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        String path = request.getRequestURI();
        Validation validate = new Validation();
        ProvinceDao provinceDao = new ProvinceDao();
        ProductDao productDao = new ProductDao();
        List<Province> provinces = provinceDao.getProvinces();
        request.setAttribute("provinces", provinces);
        Authentication auth = new Authentication();
        String usernameLogin = auth.isLogigUser(request);
        if(usernameLogin != null) {
            AccountDao accountDao = new AccountDao();
            Account a = accountDao.getAccountByUsername(usernameLogin);
            request.setAttribute("accountUserLogin", a);
        } else {
            response.sendRedirect("/auth/login");
            return;
        }
        if (path.endsWith("/checkout/buy-now")) {
            HttpSession session = request.getSession();
            CartModel cartOrder = (CartModel) session.getAttribute("cartOrder");
            if(cartOrder == null) {
                response.sendRedirect("/404");
                return;
            }
            Product p = productDao.getProductByID(cartOrder.getId());
            request.setAttribute("buyNow", true);
            request.setAttribute("product", p);
            request.setAttribute("cartProductByNow", cartOrder);
            request.getRequestDispatcher("/user/checkout.jsp").forward(request, response);
        } else if (path.endsWith("/checkout/buy-cart")) {
            Cookie cookie[] = request.getCookies();
            Cart cart = new Cart();
            String cartCookie = "";
            for (Cookie cook : cookie) {
                if (cook.getName().equals("cart")) {
                    cartCookie = cook.getValue();
                    break;
                }
            }
            List<CartModel> cartModel = cart.loadCart(cartCookie);
            List<Product> products = new ArrayList<>();
            for (CartModel c : cartModel) {
                products.add(productDao.getProductByID(c.getId()));
            }
            request.setAttribute("productsOrder", products);
            request.setAttribute("numberProductsOrder", cartModel);
            request.setAttribute("buyCart", true);
            request.getRequestDispatcher("/user/checkout.jsp").forward(request, response);
        }
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
        Validation validate = new Validation();
        if (request.getParameter("btn-checkout") != null) {
            int id = validate.getInt(request.getParameter("idProduct"));
            int numberOfProduct = validate.getInt(request.getParameter("numberOfProduct"));
            ColorDao colorDao = new ColorDao();
            int colorID = validate.getInt(request.getParameter("color"));
            Color c = colorDao.getColorByID(colorID);
            HttpSession session = request.getSession();
            CartModel cartOrder = new CartModel(id, numberOfProduct, c.getName());
            session.setAttribute("cartOrder", cartOrder);
            response.sendRedirect("/checkout/buy-now");
        }
    }

}
