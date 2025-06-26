/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers.User;

import DAO.CategoryDao;
import DAO.CompanyDao;
import DAO.ProductDao;
import Model.Category;
import Model.Company;
import Model.Product;
import Util.Validation;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;


public class CategoryUserController extends HttpServlet {

    private static CategoryDao categoryDao;
    private static CompanyDao companyDao;
    private static ProductDao productDao;
    private static Validation validate;
    private static final int numberProductInPage = 8;

    public CategoryUserController() {
        this.categoryDao = new CategoryDao();
        this.companyDao = new CompanyDao();
        this.productDao = new ProductDao();
        this.validate = new Validation();
    }

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
        Company company = companyDao.getTop1CompanyActive();
        request.setAttribute("company", company);
        request.setAttribute("categories", categoryDao.getCategoryByStatus(1));
        String paths[] = path.split("/");
        if (path.startsWith("/category/") && paths.length == 4 && paths[paths.length - 1].startsWith("page-")) {
            String slugCategory = paths[paths.length - 2];
            String pages[] = paths[paths.length - 1].split("page-");
            int page = validate.getInt(pages[pages.length - 1]);
            if (page == -1) {
                response.sendRedirect("/404");
            }
            this.productPage(request, response, slugCategory, page, numberProductInPage);
        } else if (path.startsWith("/category/")) {
            String slugCategory = paths[paths.length - 1];
            this.productPage(request, response, slugCategory, 1, numberProductInPage);
        }
    }

    private void productPage(HttpServletRequest request, HttpServletResponse response, String slugCategory, int page, int pageSize) {
        try {
            List<Product> products = productDao.getProductsCategoryByPage(slugCategory, page, pageSize);
            if (products.size() == 0 && page != 1) {
                response.sendRedirect("/404");
            } else {
                request.setAttribute("products", products);
                request.setAttribute("page", page);
                request.setAttribute("sizeProduct", productDao.getAllProductActive(slugCategory).size());
                request.setAttribute("category", categoryDao.getCategoryBySlug(slugCategory));
                request.getRequestDispatcher("/user/categoryProduct.jsp").forward(request, response);
            }
        } catch (Exception e) {
            System.out.println("Product category page: " + e);
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
