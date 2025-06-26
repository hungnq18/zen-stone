package Controllers.User;

import DAO.ColorDao;
import DAO.ProductDao;
import Model.CartModel;
import Model.Color;
import Model.Product;
import Util.Cart;
import Util.Validation;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

public class CartUserController extends HttpServlet {

    private static Cart cart;

    public CartUserController() {
        this.cart = new Cart();
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
        String path = request.getRequestURI();
        Validation validate = new Validation();
        if (path.endsWith("cart")) {
            this.showCart(request, response);
        } else if (path.startsWith("/cart/delete/")) {
            String paths[] = path.split("/");
            int id = validate.getInt(paths[paths.length - 1]);
            this.deleteCartItem(request, response, id);
        }
    }

    private void deleteCartItem(HttpServletRequest request, HttpServletResponse response, int id) {
        try {
            Cookie cookie[] = request.getCookies();
            String cartCookie = "";
            for (Cookie cook : cookie) {
                if (cook.getName().equals("cart")) {
                    cartCookie = cook.getValue();
                    break;
                }
            }
            String cartAfterDelete = cart.deleteItemCart(cartCookie, id);
            this.addCartToCookie(response, cartAfterDelete);
            response.sendRedirect("/cart");
        } catch (Exception e) {
            System.out.println("Delete cart: " + e);
        }
    }

    private void showCart(HttpServletRequest request, HttpServletResponse response) {
        try {
            Cookie cookie[] = request.getCookies();
            String cartCookie = "";
            for (Cookie cook : cookie) {
                if (cook.getName().equals("cart")) {
                    cartCookie = cook.getValue();
                    break;
                }
            }
            request.setAttribute("cart", cart.showCart(cartCookie));
            request.getRequestDispatcher("/user/cart.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println("Cart: " + e);
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
        ProductDao productDao = new ProductDao();
        Validation validate = new Validation();
        Cookie cookie[] = request.getCookies();
        ColorDao colorDao = new ColorDao();
        Cart cart = new Cart();
        String cartCookie = "";
        for (Cookie cook : cookie) {
            if (cook.getName().equals("cart")) {
                cartCookie = cook.getValue();
                break;
            }
        }
        if (request.getParameter("add-to-cart") != null) {
            String idProduct = request.getParameter("idProduct");
            Product p = productDao.getProductByID(validate.getInt(idProduct));
            int quantityCart = validate.getInt(request.getParameter("quantityCart"));
            int colorID = validate.getInt(request.getParameter("color"));
            Color color = colorDao.getColorByID(colorID);
            CartModel c = new CartModel(p.getID(), quantityCart, color.getName());
            c.setColorId(colorID);
            c.setQuantityColor(color.getQuantity());
            List<CartModel> cartsAfterAdd = cart.createCart(cartCookie, c);
            String cartUp = cart.convertCart(cartsAfterAdd);
            this.addCartToCookie(response, cartUp);
            response.sendRedirect("/cart");
        } else if (request.getParameter("btn-minus") != null) {
            int id = validate.getInt(request.getParameter("id"));
            String cartAfterMinus = cart.minusItemCart(cartCookie, id);
            this.addCartToCookie(response, cartAfterMinus);
            response.sendRedirect("/cart");
        } else if (request.getParameter("btn-add") != null) {
            
        }
    }

    private void addCartToCookie(HttpServletResponse response, String cartToUp) {
        try {
            Cookie cookieCartAfterAdd = new Cookie("cart", cartToUp);
            cookieCartAfterAdd.setMaxAge(24 * 60 * 60 * 30);
            cookieCartAfterAdd.setPath("/");
            response.addCookie(cookieCartAfterAdd);
        } catch (Exception e) {
            System.out.println("Add to cart: " + e);
        }

    }
}
