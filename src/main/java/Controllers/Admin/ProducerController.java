/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers.Admin;

import DAO.ProducerDao;
import Model.Producer;
import Util.Slug;
import Util.Validation;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author HP
 */
public class ProducerController extends HttpServlet {

    private static ProducerDao producerDao;

    public ProducerController() {
        this.producerDao = new ProducerDao();
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
        if (path.endsWith("/admin/producer")) {
            this.showAllProducer(request, response);
        } else {
            String paths[] = path.split("/");
            String slug = paths[paths.length - 1];
            if (path.startsWith("/admin/producer/update/")) {
                this.getCurrentProducerUpdate(request, response, slug);
            } else if (path.startsWith("/admin/producer/delete/")) {
                this.deleteProducer(request, response, slug);
            }
        }
    }

    private void showAllProducer(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Producer> producers = producerDao.allProducer();
            request.setAttribute("producers", producers);
            request.getRequestDispatcher("/admin/view/producer/producer.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println("Show producer: " + e);
        }
    }

    private void getCurrentProducerUpdate(HttpServletRequest request, HttpServletResponse response, String slug) {
        try {
            Producer c = producerDao.currentProducer(slug);
            if (c != null) {
                request.setAttribute("currentProducer", c);
                request.getRequestDispatcher("/admin/view/producer/updateProducer.jsp").forward(request, response);
            } else {
                response.sendRedirect("/admin/404");
            }

        } catch (Exception e) {
            System.out.println("Get current category update: " + e);
        }
    }

    private void deleteProducer(HttpServletRequest request, HttpServletResponse response, String slug) {
        try {
            int result = producerDao.delete(slug);
            String url = "/admin/producer?status=" + result;
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
        if (request.getParameter("btn-add-producer") != null) {
            this.addProducer(request, response);
        } else if (request.getParameter("btn-update-producer") != null) {
            this.updateProducer(request, response);
        } else if (request.getParameter("btn-delete-producers") != null) {
            this.deleteProducers(request, response);
        }
    }

    private void addProducer(HttpServletRequest request, HttpServletResponse response) {
        try {
            Validation validate = new Validation();
            Slug createSlug = new Slug();
            String name = request.getParameter("name");
            int status = validate.getInt(request.getParameter("status"));
            LocalDateTime dateTime = LocalDateTime.now();
            Timestamp date = Timestamp.valueOf(dateTime);
            String slug = createSlug.toSlug(name);
            Producer c = new Producer(-1, name, date, null, status, slug);
            int result = producerDao.insert(c);
            String url = "/admin/producer?status=" + result;
            response.sendRedirect(url);
        } catch (Exception e) {
            System.out.println("Insert producer: " + e);
        }
    }

    private void updateProducer(HttpServletRequest request, HttpServletResponse response) {
        try {
            Validation validate = new Validation();
            ProducerDao pDao = new ProducerDao();
            int id = validate.getInt(request.getParameter("id"));
            String name = request.getParameter("name");
            int status = validate.getInt(request.getParameter("status"));
            LocalDateTime dateTime = LocalDateTime.now();
            Timestamp dateUpdate = Timestamp.valueOf(dateTime);
            Slug createSlug = new Slug();
            String slug = createSlug.toSlug(name);
            Producer c = new Producer(id, name, null, dateUpdate, status, slug);
            int result = pDao.update(c);
            String url = "/admin/producer?status=" + result;
            response.sendRedirect(url);
        } catch (Exception e) {
            System.out.println("Update producer: " + e);
        }
    }

    private void deleteProducers(HttpServletRequest request, HttpServletResponse response) {
        try {
            String[] allSlugChecked = request.getParameterValues("delete-producer-item");
            if (allSlugChecked == null) {
                response.sendRedirect("/admin/producer?status=0&message=Please choose producer to delete");
                return;
            }
            int status = 0;
            for (String slug : allSlugChecked) {
                int result = producerDao.delete(slug);
                if (result == 1) {
                    status = 1;
                }
            }
            String url = "/admin/producer?status=" + status;
            response.sendRedirect(url);
        } catch (Exception e) {
            System.out.println("Delete categories:  " + e);
        }
    }

}
