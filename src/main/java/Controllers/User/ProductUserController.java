package Controllers.User;

import DAO.CategoryDao;
import DAO.ColorDao;
import DAO.CommentDao;
import DAO.ImgDescriptionDao;
import DAO.ProductDao;
import Model.Category;
import Model.Color;
import Model.Comment;
import Model.ImgDescription;
import Model.Product;
import Util.Validation;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

public class ProductUserController extends HttpServlet {

    private static CategoryDao categoryDao;
    private static ProductDao productDao;
    private static Validation validate;
    private static CommentDao commentDao;
    private static final int numberProductInPage = 8;

    public ProductUserController() {
        this.categoryDao = new CategoryDao();
        this.productDao = new ProductDao();
        this.validate = new Validation();
        this.commentDao = new CommentDao();
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
        if (path.endsWith("/product")) {
            this.productPage(request, response, 1, numberProductInPage);
        } else if (path.startsWith("/product/page-")) {
            String paths[] = path.split("/");
            String pages[] = paths[paths.length - 1].split("page-");
            int page = validate.getInt(pages[pages.length - 1]);
            if (page == -1) {
                response.sendRedirect("/404");
            }
            this.productPage(request, response, page, numberProductInPage);
        } else if (path.startsWith("/product/detail/")) {
            String paths[] = path.split("/");
            String slug = paths[paths.length - 1];
            Product p = productDao.statusIsActive(slug);
            if (p == null) {
                response.sendRedirect("/404");
            } else {
                int idProduct = p.getID();
                ImgDescriptionDao imgDescDao = new ImgDescriptionDao();
                ColorDao colorDao = new ColorDao();
                List<ImgDescription> imgDesc = imgDescDao.getAllImgDescriptionByProduct(idProduct);
                List<Color> colors = colorDao.getAllColorByProduct(idProduct);
                List<Comment> comments = commentDao.allAccountByProduct(idProduct);
                request.setAttribute("comments", comments);
                request.setAttribute("product", p);
                request.setAttribute("imgDesc", imgDesc);
                request.setAttribute("colors", colors);
                request.setAttribute("category", categoryDao.getCategoryByID(p.getCategoryID()));
                request.getRequestDispatcher("/user/detailProduct.jsp").forward(request, response);
            }
        }
    }

    private void productPage(HttpServletRequest request, HttpServletResponse response, int page, int pageSize) {
        try {
            List<Category> categories = categoryDao.getCategoryNumberByStatus();
            List<Product> products = productDao.getProductsByPage(page, pageSize);
            if (products.size() == 0 && page != 1) {
                response.sendRedirect("/404");
            } else {
                request.setAttribute("categories", categories);
                request.setAttribute("products", products);
                request.setAttribute("page", page);
                request.setAttribute("sizeProduct", productDao.getAllProductActive().size());
                request.getRequestDispatcher("/user/product.jsp").forward(request, response);
            }
        } catch (Exception e) {
            System.out.println("Product page: " + e);
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

    }
}
