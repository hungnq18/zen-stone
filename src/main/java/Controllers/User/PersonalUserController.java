package Controllers.User;

import DAO.AccountDao;
import DAO.BillDao;
import DAO.BillDetailsDao;
import Model.Account;
import Model.Bill;
import Model.BillDetails;
import Util.MD5Hashing;
import Util.Validation;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author Le Tan Kim
 */
public class PersonalUserController extends HttpServlet {

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
        String paths[] = path.split("/");
        if (path.startsWith("/personal/") && paths.length == 3) {
            String username = paths[paths.length - 1];
            AccountDao accountDao = new AccountDao();
            Account account = accountDao.getAccountByUsername(username);
            BillDao billDao = new BillDao();
            List<Bill> bills = billDao.getBillByCustomer(account.getId());
            request.setAttribute("personalCurrentUser", account);
            request.setAttribute("bills", bills);
            request.getRequestDispatcher("/user/personal.jsp").forward(request, response);
        } else if (path.startsWith("/personal/") && paths[paths.length - 2].equals("view-bill") && paths.length == 5) {
            Validation validate = new Validation();
            int idBill = validate.getInt(paths[paths.length - 1]);
            BillDao billDao = new BillDao();
            BillDetailsDao billDetailsDao = new BillDetailsDao();
            Bill bill = billDao.getBillByID(idBill);
            if (bill != null) {
                String username = paths[paths.length - 3];
                AccountDao accountDao = new AccountDao();
                Account account = accountDao.getAccountByUsername(username);
                request.setAttribute("personalCurrentUser", account);
                List<BillDetails> billDetail = billDetailsDao.getBillDetailByID(idBill);
                request.setAttribute("currentBill", bill);
                request.setAttribute("billDetail", billDetail);
                request.getRequestDispatcher("/user/detailOrder.jsp").forward(request, response);
            }

        } else {
            response.sendRedirect("/404");
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
        String path = request.getRequestURI();
        String paths[] = path.split("/");
        String username = paths[paths.length - 1];
        AccountDao accountDao = new AccountDao();
        Account a = accountDao.getAccountByUsername(username);
        if (request.getParameter("btn-update-personal") != null) {
            String fullname = request.getParameter("fullname");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            a.setFullname(fullname);
            a.setEmail(email);
            a.setPhone(phone);
            int result = accountDao.updatePersonalUser(a);
            if (result > 0) {
                request.setAttribute("updateMessageSuccess", "Update infomration success");
            } else {
                request.setAttribute("updateMessageFail", "Update infomration fail");
            }
            request.setAttribute("personalCurrentUser", a);
            request.getRequestDispatcher("/user/personal.jsp").forward(request, response);
        } else if (request.getParameter("btn-update-password") != null) {
            MD5Hashing md5 = new MD5Hashing();
            String oldPassword = request.getParameter("oldPassword");
            String newPassword = request.getParameter("newPassword");
            if (!a.getPassword().equals(md5.hashPassword(oldPassword))) {
                request.setAttribute("updateMessageFail", "Old password is invalid");
            } else {
                int result = accountDao.updatePassword(md5.hashPassword(newPassword), a.getId());
                if (result > 0) {
                    request.setAttribute("updateMessageSuccess", "Update password success");
                } else {
                    request.setAttribute("updateMessageFail", "Update password fail");
                }
            }
            request.setAttribute("personalCurrentUser", a);
            request.getRequestDispatcher("/user/personal.jsp").forward(request, response);
        } else {
            response.sendRedirect("/404");
        }
    }
}
