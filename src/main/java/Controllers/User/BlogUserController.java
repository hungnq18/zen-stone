/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers.User;

import DAO.BlogDAO;
import DAO.CategoryDao;
import Model.Blog;
import Model.Category;
import Model.Product;
import Util.Validation;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author HP
 */
public class BlogUserController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet BlogUserController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BlogUserController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
        BlogDAO blogDao = new BlogDAO();
        CategoryDao categoryDAO = new CategoryDao();
        if (path.endsWith("/blog")) {
            List<Category> categories = categoryDAO.getCategoryByStatus(1);
            request.setAttribute("categories", categories);
            List<Blog> blogs = blogDao.getAllBlogActive();
            request.setAttribute("blogs", blogs);
            request.getRequestDispatcher("/user/blog/blog.jsp").forward(request, response);
        } else if (path.startsWith("/blog/category")) {
            Validation validate = new Validation();
            String paths[] = path.split("/");
            int id = validate.getInt(paths[paths.length - 1]);
            List<Blog> blogs = blogDao.getBlogByCategoryActive(id);
            List<Category> categories = categoryDAO.getCategoryByStatus(1);
            request.setAttribute("blogs", blogs);
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("/user/blog/blog.jsp").forward(request, response);
        } else {
            Validation validate = new Validation();
            String paths[] = path.split("/");
            int id = validate.getInt(paths[paths.length - 1]);
            Blog blog = blogDao.getBlogByIdActive(id);
            if (blog != null) {
                List<Category> categories = categoryDAO.getCategoryByStatus(1);
                request.setAttribute("categories", categories);
                request.setAttribute("currentBlog", blog);
                request.getRequestDispatcher("/user/blog/view.jsp").forward(request, response);
            } else {
                response.sendRedirect("/404");
            }
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
        BlogDAO blogDao = new BlogDAO();
        if (request.getParameter("btn-search") != null) {
            String key = request.getParameter("key");
            Validation validate = new Validation();
            CategoryDao categoryDAO = new CategoryDao();
            List<Blog> blogs = blogDao.getBlogByKeyActive(key);
            List<Category> categories = categoryDAO.getCategoryByStatus(1);
            request.setAttribute("key", key);
            request.setAttribute("blogs", blogs);
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("/user/blog/blog.jsp").forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
