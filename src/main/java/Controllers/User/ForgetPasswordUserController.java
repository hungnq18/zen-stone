package Controllers.User;

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
 * @author Le Tan Kim
 */
public class ForgetPasswordUserController extends HttpServlet {

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
        request.getRequestDispatcher("/user/forgetPassword.jsp").forward(request, response);
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
        AccountDao accountDao = new AccountDao();
        MD5Hashing md5 = new MD5Hashing();
        if (request.getParameter("btn-forget-password") != null) {
            String email = request.getParameter("email");
            Account account = accountDao.getAccountByEmail(email);
            if (account != null && account.getRole() == 0) {
                Authentication auth = new Authentication();
                TemplateEmail templateEmail = new TemplateEmail();
                Email emailSend = new Email();
                String newPasword = auth.generatePassword(9);
                boolean isResetPassword = emailSend.sendEmail(email, "Reset password", templateEmail.resetPassword(account, newPasword), "letankim2003@gmail.com");
                int result = 0;
                if (isResetPassword) {
                    result = accountDao.updatePassword(md5.hashPassword(newPasword), account.getId());
                }
                if (result > 0) {
                    request.setAttribute("resetPasswordSuccess", "Reset password successfully. Check email to get it.");
                } else {
                    request.setAttribute("forgetPasswordMessage", "Reset password fail. Try again.");
                }
                request.getRequestDispatcher("/user/forgetPassword.jsp").forward(request, response);
            } else {
                request.setAttribute("forgetPasswordMessage", "This account is not exist");
                request.getRequestDispatcher("/user/forgetPassword.jsp").forward(request, response);
            }
        }
    }
}
