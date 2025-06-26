/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers.User;

import DAO.CommentDao;
import Model.Comment;
import Util.Validation;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author Le Tan Kim
 */
public class CommentUserController extends HttpServlet {

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
        CommentDao commentDao = new CommentDao();
        if (path.startsWith("/comment/delete/")) {
            String paths[] = path.split("/");
            int idComment = validate.getInt(paths[paths.length - 1]);
            int result = commentDao.deleteComment(idComment);
            String slugProduct = paths[paths.length - 2];
            String url = "/product/detail/" + slugProduct;
            if (result >= 1) {
                url += "?status=1";
            } else {
                url += "?status=0";
            }
            response.sendRedirect(url);
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
        CommentDao commentDao = new CommentDao();
        HttpSession session = request.getSession();
        LocalDateTime dateTime = LocalDateTime.now();
        Timestamp currentDate = Timestamp.valueOf(dateTime);
        String slugProduct = request.getParameter("slugProduct");
        if (request.getParameter("btn-add-comment") != null) {
            String comment = request.getParameter("comment");
            int idProduct = validate.getInt(request.getParameter("idProduct"));
            int idUser = validate.getInt(request.getParameter("idUser"));
            Comment c = new Comment(0, idUser, idProduct, comment, 0, currentDate, null);
            int result = commentDao.insertComment(c);
            String url = "/product/detail/" + slugProduct;
            if (result >= 1) {
                url += "?status=1";
            } else {
                url += "?status=0";
            }
            response.sendRedirect(url);
        } else if (request.getParameter("btn-update-comment") != null) {
            String comment = request.getParameter("comment");
            int idComment = validate.getInt(request.getParameter("idComment"));
            Comment c = new Comment(idComment, 0, 0, comment, 0, null, currentDate);
            int result = commentDao.updateComment(c);
            String url = "/product/detail/" + slugProduct;
            if (result >= 1) {
                url += "?status=1";
            } else {
                url += "?status=0";
            }
            response.sendRedirect(url);
        }
    }

}
