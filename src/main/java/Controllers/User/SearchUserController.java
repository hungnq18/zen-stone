package Controllers.User;

import DAO.CompanyDao;
import DAO.ProductDao;
import Model.Product;
import Util.Validation;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

public class SearchUserController extends HttpServlet {

    private static final int numberProductInPage = 8;

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
        HttpSession session = request.getSession();
        Validation validate = new Validation();
        if (path.endsWith("/search")) {
            String keyword = request.getParameter("keyword");
            session.setAttribute("key", keyword);
            this.searchPage(request, response, keyword, 1, numberProductInPage);
        } else if (path.startsWith("/search/page-")) {
            String key = (String) session.getAttribute("key");
            String paths[] = path.split("/");
            String pages[] = paths[paths.length - 1].split("page-");
            int page = validate.getInt(pages[pages.length - 1]);
            if (page == -1) {
                response.sendRedirect("/404");
            }
            this.searchPage(request, response, key, page, numberProductInPage);
        }
    }

    private void searchPage(HttpServletRequest request, HttpServletResponse response, String keyword, int page, int pageSize) {
        try {
            ProductDao productDao = new ProductDao();
            CompanyDao companyDao = new CompanyDao();
            List<Product> products = productDao.seachProduct(keyword, page, pageSize);
            request.setAttribute("keyword", keyword);
            request.setAttribute("products", products);
            request.setAttribute("page", page);
            request.setAttribute("sizeProduct", productDao.seachProduct(keyword).size());
            request.getRequestDispatcher("/user/search.jsp").forward(request, response);
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
