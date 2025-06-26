/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers.Admin;

import Model.Account;
import Util.Authentication;
import Util.MD5Hashing;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author HP
 */
public class LoginController extends HttpServlet {

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
        Authentication auth = new Authentication();
        boolean isLogin = auth.isLoginAdmin(request);
        if (isLogin) {
            response.sendRedirect("/admin");
        } else {
            request.getRequestDispatcher("/admin/view/auth/login.jsp").forward(request, response);
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
        if (request.getParameter("login") != null) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            Authentication auth = new Authentication();
            Account account = auth.isExist(username);
            String message = "";
            if (account == null) {
                message = "Account does not exist";
                this.goToLoginPage(request, response, message);
                return;
            }

            if (account.getRole() != 1 && account.getRole() != 2) {
                message = "Account cannot log in here";
                this.goToLoginPage(request, response, message);
                return;
            }

            MD5Hashing md5 = new MD5Hashing();
            String passwordHashed = md5.hashPassword(password);

            if (!account.getPassword().equals(passwordHashed)) {
                message = "Password is not valid";
                this.goToLoginPage(request, response, message);
                return;
            }
            
            if (account.getStatus() != 1) {
                message = "Your account is locked";
                this.goToLoginPage(request, response, message);
                return;
            }
            HttpSession session = request.getSession();
            String fullNameOrUsername = account.getFullname() != null ? account.getFullname() : username;
            session.setAttribute("usernameAdmin", username);
            session.setAttribute("fullnameAdmin", fullNameOrUsername);
            session.setAttribute("role", account.getRole());
            String avatar = account.getAvatar() == null ? "./admin/assets/images/default_avatar.jpg" : account.getAvatar();
            session.setAttribute("avatar", avatar);
            System.out.println(account.getRole());
            response.sendRedirect("/admin");
        }
    }

    private void goToLoginPage(HttpServletRequest request, HttpServletResponse response, String message) {
        try {
            request.setAttribute("message", message);
            request.getRequestDispatcher("/admin/view/auth/login.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println("Go to login page: " + e);
        }
    }
}
