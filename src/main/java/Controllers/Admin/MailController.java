package Controllers.Admin;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
import DAO.AccountDao;
import DAO.VoucherDao;
import Model.Account;
import Model.Voucher;
import Util.Email;
import Util.TemplateEmail;
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
public class MailController extends HttpServlet {

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
        if (path.endsWith("/admin/mail")) {
            AccountDao accountDao = new AccountDao();
            VoucherDao voucherDao = new VoucherDao();
            List<Voucher> voucherActive = voucherDao.allVoucherActive();
            List<Account> accounts = accountDao.allAccount();
            request.setAttribute("accounts", accounts);
            request.setAttribute("vouchers", voucherActive);
            request.getRequestDispatcher("/admin/view/mail/mail.jsp").forward(request, response);
        } else {

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
        request.setCharacterEncoding("UTF-8");
        Validation validate = new Validation();
        VoucherDao voucherDao = new VoucherDao();
        TemplateEmail temp = new TemplateEmail();
        if (request.getParameter("btn-send-mail") != null) {
            String replyMail = request.getParameter("emailReply");
            String[] emails = request.getParameterValues("mail-user-item");
            emails = (emails == null ? new String[0] : emails);
            String title = request.getParameter("title");
            String message = request.getParameter("message");
            int idVoucher = validate.getInt(request.getParameter("voucher"));
            Voucher v = voucherDao.currentVoucher(idVoucher);
            String messageToSend = "";
            if (v != null) {
                messageToSend = temp.Voucher(title, message, v.getCode());
            } else {
                replyMail = null;
                messageToSend = message;
            }
            Email mail = new Email();
            boolean isSend = false;
            for (String email : emails) {
                if (!isSend) {
                    isSend = mail.sendEmail(email, title, messageToSend, replyMail);
                } else {
                    mail.sendEmail(email, title, messageToSend, replyMail);
                }
            }
            String url = "/admin/mail?status=";
            if (isSend) {
                url += "1&message=Send mail success";
            } else {
                if (emails.length == 0) {
                    url += "0&message=Please choose email before send";
                } else {
                    url += "0&message=Send mail fail";
                }
            }
            response.sendRedirect(url);
        }
    }

}
