/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers.Admin;

import DAO.AccountDao;
import Model.Account;
import Util.Authentication;
import Util.Email;
import Util.MD5Hashing;
import Util.TemplateEmail;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author HP
 */
public class ForgetPasswordController extends HttpServlet {

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
        request.getRequestDispatcher("/admin/view/auth/forget.jsp").forward(request, response);
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
        if (request.getParameter("reset-password") != null) {
            String email = request.getParameter("email");
            AccountDao accountDao = new AccountDao();
            Account a = accountDao.getAccountByEmail(email);
            Authentication auth = new Authentication();
            MD5Hashing md5 = new MD5Hashing();
            Email sendMail = new Email();
            TemplateEmail template = new TemplateEmail();
            String message = "";
            if (a == null) {
                message = "Account is not exist";
                this.goToLoginPage(request, response, message);
                return;
            }
            String newPassword = auth.generatePassword(8);
            String passwordHashing = md5.hashPassword(newPassword);
            String mailTemplate = template.resetPassword(a, newPassword);
            boolean isSendMail = sendMail.sendEmail(email, "Reset password", mailTemplate, null);
            if (isSendMail && accountDao.updatePassword(passwordHashing, a.getId()) == 1) {
                message = "Reset password successfully. Please check your email to get a new password to log in";

            } else {
                message = "The system is busy, please try again";
            }
            goToLoginPage(request, response, message);
        }
    }

    private void goToLoginPage(HttpServletRequest request, HttpServletResponse response, String message) {
        try {
            request.setAttribute("message", message);
            request.getRequestDispatcher("/admin/view/auth/forget.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println("Go to login page: " + e);
        }
    }

}
