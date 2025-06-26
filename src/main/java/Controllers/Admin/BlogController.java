/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers.Admin;

import DAO.BlogDAO;
import DAO.CategoryDao;
import Model.Banner;
import Model.Blog;
import Model.Category;
import Util.DateCustom;
import Util.Upload;
import Util.Validation;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author HP
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 50)   // 50 MB
public class BlogController extends HttpServlet {

    private static BlogDAO blogDao;
    private static CategoryDao categoryDao;

    public BlogController() {
        this.blogDao = new BlogDAO();
        this.categoryDao = new CategoryDao();
    }

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
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet BlogController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BlogController at " + request.getContextPath() + "</h1>");
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
        Validation validate = new Validation();
        String path = request.getRequestURI();
        if (path.endsWith("/admin/blog")) {
            this.getAllBlog(request, response);
        } else {
            String paths[] = path.split("/");
            int id = validate.getInt(paths[paths.length - 1]);
            if (path.startsWith("/admin/blog/view")) {
                this.getCurrentBlog(request, response, id);
            } else if (path.startsWith("/admin/blog/delete")) {
                this.delete(request, response, id);
            } else if (path.startsWith("/admin/blog/update")) {
                this.getCurrentBlogUpdate(request, response, id);
            }
        }
    }

    private void getCurrentBlogUpdate(HttpServletRequest request, HttpServletResponse response, int id) {
        try {
            Blog blog = blogDao.getBlogById(id);
            if (blog != null) {
                List<Category> categories = categoryDao.allCategory();
                request.setAttribute("categories", categories);
                request.setAttribute("currentBlog", blog);
                request.getRequestDispatcher("/admin/view/blog/update.jsp").forward(request, response);
            } else {
                response.sendRedirect("/admin/404");
            }
        } catch (Exception e) {
            System.out.println("Get current blog: " + e);
        }
    }

    private void getCurrentBlog(HttpServletRequest request, HttpServletResponse response, int id) {
        try {
            Blog blog = blogDao.getBlogById(id);
            if (blog != null) {
                request.setAttribute("currentBlog", blog);
                request.getRequestDispatcher("/admin/view/blog/view.jsp").forward(request, response);
            } else {
                response.sendRedirect("/admin/404");
            }
        } catch (Exception e) {
            System.out.println("Get current blog: " + e);
        }
    }

    private void getAllBlog(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Category> categories = categoryDao.allCategory();
            List<Blog> blogs = blogDao.getAllBlog();
            request.setAttribute("blogs", blogs);
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("/admin/view/blog/blog.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println("Get all blog: " + e);
        }
    }
    
    private void delete(HttpServletRequest request, HttpServletResponse response, int id) {
        try {
            BlogDAO blogDAO = new BlogDAO();
            Blog blog = blogDAO.getBlogById(id);
            if (blog != null) {
                int result = blogDAO.deleteBlog(id);
                int type_message = 0;
                if (result >= 1) {
                    type_message = 1;
                } else {
                    type_message = 0;
                }
                response.sendRedirect("/admin/blog?act=delete&status=" + type_message);
            } else {
                response.sendRedirect("/admin/404");
            }
        } catch (Exception e) {
            System.out.println("All blog: " + e);
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
        if (request.getParameter("btn-add-blog") != null) {
            this.addNew(request, response);
        } else if (request.getParameter("btn-update-blog") != null) {
            this.updateBlog(request, response);
        }
    }

    public void addNew(HttpServletRequest request, HttpServletResponse response) {
        Validation validate = new Validation();
        DateCustom dateCustom = new DateCustom();
        Upload upload = new Upload();
        try {
            String title = request.getParameter("title").trim();
            String shortDesc = request.getParameter("shortDesc").trim();
            int category = validate.getInt(request.getParameter("categoryID"));
            int status = validate.getInt(request.getParameter("status").trim());
            String desc = request.getParameter("desc").trim();
            Timestamp datePost = dateCustom.getCurrentTime();
            Part mainImgParth = request.getPart("image");
            String pathProduct = "./uploads/blog/";
            String uploadPath = getServletContext().getRealPath(pathProduct);
            String nameImgBlog = upload.uploadImg(mainImgParth, uploadPath);
            if (nameImgBlog != null) {
                String fileNameSaveDb = pathProduct + nameImgBlog;
                int view = 0;
                Blog blog = new Blog(0, title, shortDesc, desc, fileNameSaveDb, datePost, null, status, view, category);
                int result = blogDao.insert(blog);
                response.sendRedirect("/admin/blog?act=add-blog&status=" + result);
            } else {
                response.sendRedirect("/admin/blog?act=add-blog&status=2");
            }
        } catch (Exception e) {
            System.out.println("Add new blog: " + e);
        }
    }

    public void updateBlog(HttpServletRequest request, HttpServletResponse response) {
        Validation validate = new Validation();
        DateCustom dateCustom = new DateCustom();
        Upload upload = new Upload();
        try {
            int id = validate.getInt(request.getParameter("id"));
            String title = request.getParameter("title").trim();
            String shortDesc = request.getParameter("shortDesc").trim();
            int category = validate.getInt(request.getParameter("categoryID"));
            int status = validate.getInt(request.getParameter("status"));
            String desc = request.getParameter("desc").trim();
            Timestamp dateUpdate = dateCustom.getCurrentTime();
            Part mainImgParth = request.getPart("image");
            String pathProduct = "./uploads/blog/";
            String uploadPath = getServletContext().getRealPath(pathProduct);
            String nameImgBlog = upload.uploadImg(mainImgParth, uploadPath);
            String fileNameSaveDb = pathProduct + nameImgBlog;
            if (nameImgBlog == null) {
                fileNameSaveDb = request.getParameter("oldImage");
            }
            int view = 0;
            Blog blog = new Blog(id, title, shortDesc,desc, fileNameSaveDb, null, dateUpdate, status, view, category);
            int result = blogDao.update(blog);
            response.sendRedirect("/admin/blog?act=update-blog&status=" + result);
        } catch (Exception e) {
            System.out.println("update new blog: " + e);
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
