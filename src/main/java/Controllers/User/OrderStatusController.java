package Controllers.User;

import DAO.BillDao;
import DAO.BillDetailsDao;
import Model.Bill;
import Model.BillDetails;
import Util.Email;
import Util.TemplateEmail;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 *
 * @author Le Tan Kim
 */
public class OrderStatusController extends HttpServlet {

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
        try {
            String path = request.getRequestURI();
            if (path.endsWith("/order-status/success")) {
                HttpSession session = request.getSession();
                int idBill = Integer.parseInt(session.getAttribute("billJustOrder") + "");
                BillDao billDao = new BillDao();
                BillDetailsDao billDetailDao = new BillDetailsDao();
                Bill bill = billDao.getBillByID(idBill);
                List<BillDetails> billDetals = billDetailDao.getBillDetailByID(idBill);
                request.setAttribute("bill", bill);
                request.setAttribute("listDetail", billDetals);
                request.getRequestDispatcher("/user/billOrder.jsp").forward(request, response);
            } else {
                request.setAttribute("fail", true);
                request.getRequestDispatcher("/user/orderStatus.jsp").forward(request, response);
            }
        } catch (Exception e) {
            response.sendRedirect("../404");
        }
    }

}
