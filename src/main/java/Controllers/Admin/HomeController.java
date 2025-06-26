package Controllers.Admin;

import DAO.AccountDao;
import DAO.BillDao;
import DAO.CategoryDao;
import DAO.CommentDao;
import DAO.ProducerDao;
import DAO.ProductDao;
import Model.Bill;
import Model.Category;
import Model.Comment;
import Model.Product;
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
public class HomeController extends HttpServlet {

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
        if (path.endsWith("/admin")) {
            AccountDao accountDao = new AccountDao();
            ProductDao productDao = new ProductDao();
            CategoryDao categoryDao = new CategoryDao();
            ProducerDao producerDao = new ProducerDao();
            CommentDao commentDao = new CommentDao();
            BillDao billDao = new BillDao();
            List<Category> categories = categoryDao.allCategory();
            List<Bill> newBills = billDao.getBillByStatus(1);
            int numberOfAccount = accountDao.allAccount().size();
            int numberOfProduct = productDao.getAllProduct().size();
            int numberOfProducer = producerDao.allProducer().size();
            int numberOfCategory = categories.size();
            List<Product> topFiveProduct = productDao.getTopFiveProduct();
            List<Comment> comments = commentDao.allCommentActive(0);
            request.setAttribute("newBills", newBills);
            request.setAttribute("categories", categories);
            request.setAttribute("comments", comments);
            request.setAttribute("numberOfProduct", numberOfProduct);
            request.setAttribute("numberOfAccount", numberOfAccount);
            request.setAttribute("numberOfCategory", numberOfCategory);
            request.setAttribute("numberOfProducer", numberOfProducer);
            request.setAttribute("topFiveProduct", topFiveProduct);
            request.getRequestDispatcher("./admin/view/home/home.jsp").forward(request, response);
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

    }
}
