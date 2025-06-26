/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers.Admin;

import DAO.CategoryDao;
import Model.Category;
import Util.Slug;
import Util.Validation;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author HP
 */
public class CategoryController extends HttpServlet {
    
    private static CategoryDao categoryDao;
    
    public CategoryController() {
        this.categoryDao = new CategoryDao();
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
        if (path.endsWith("/admin/category")) {
            this.showAllCategory(request, response);
        } else {
            String paths[] = path.split("/");
            String slug = paths[paths.length - 1];
            if (path.startsWith("/admin/category/update/")) {
                this.getCurrentCategoryUpdate(request, response, slug);
            } else if (path.startsWith("/admin/category/delete/")) {
                this.deleteCategory(request, response, slug);
            }
        }
    }
    
    private void showAllCategory(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Category> categories = categoryDao.allCategory();
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("/admin/view/category/category.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println("Show category: " + e);
        }
    }
    
    private void getCurrentCategoryUpdate(HttpServletRequest request, HttpServletResponse response, String slug) {
        try {
            Category c = categoryDao.currentCategory(slug);
            if (c != null) {
                request.setAttribute("currentCategory", c);
                request.getRequestDispatcher("/admin/view/category/updateCategory.jsp").forward(request, response);
            } else {
                response.sendRedirect("/admin/404");
            }
        } catch (Exception e) {
            System.out.println("Get current category update: " + e);
        }
    }
    
    private void deleteCategory(HttpServletRequest request, HttpServletResponse response, String slug) {
        try {
            int result = categoryDao.delete(slug);
            String url = "/admin/category?status=" + result;
            response.sendRedirect(url);
        } catch (Exception e) {
            System.out.println("Delete category: " + e);
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
        if (request.getParameter("btn-add-category") != null) {
            this.addCategory(request, response);
        } else if (request.getParameter("btn-update-category") != null) {
            this.updateCategory(request, response);
        } else if (request.getParameter("btn-delete-categories") != null) {
            this.deleteCategories(request, response);
        }
    }
    
    private void addCategory(HttpServletRequest request, HttpServletResponse response) {
        try {
            Validation validate = new Validation();
            String name = request.getParameter("name");
            int status = validate.getInt(request.getParameter("status"));
            LocalDateTime dateTime = LocalDateTime.now();
            Timestamp date = Timestamp.valueOf(dateTime);
            Slug createSlug = new Slug();
            String slug = createSlug.toSlug(name);
            Category c = new Category(-1, name, date, null, status, slug);
            int result = categoryDao.insert(c);
            String url = "/admin/category?status=" + result;
            response.sendRedirect(url);
        } catch (Exception e) {
            System.out.println("Insert category: " + e);
        }
    }
    
    private void updateCategory(HttpServletRequest request, HttpServletResponse response) {
        try {
            Validation validate = new Validation();
            Slug createSlug = new Slug();
            int id = validate.getInt(request.getParameter("id"));
            String name = request.getParameter("name");
            int status = validate.getInt(request.getParameter("status"));
            LocalDateTime dateTime = LocalDateTime.now();
            Timestamp dateUpdate = Timestamp.valueOf(dateTime);
            String slug = createSlug.toSlug(name);
            Category c = new Category(id, name, null, dateUpdate, status, slug);
            int result = categoryDao.update(c);
            String url = "/admin/category?status=" + result;
            response.sendRedirect(url);
        } catch (Exception e) {
            System.out.println("Update category: " + e);
        }
    }
    
    private void deleteCategories(HttpServletRequest request, HttpServletResponse response) {
        try {
            String[] allSlugChecked = request.getParameterValues("delete-category-item");
            if(allSlugChecked == null) {
                response.sendRedirect("/admin/category?status=0&message=Please choose category to delete");
                return;
            }
            int status = 0;
            for (String slug : allSlugChecked) {
                int result = categoryDao.delete(slug);
                if (result == 1) {
                    status = 1;
                }
            }
            String url = "/admin/category?status=" + status;
            response.sendRedirect(url);
        } catch (Exception e) {
            System.out.println("Delete categories:  " + e);
        }
    }
    
}
