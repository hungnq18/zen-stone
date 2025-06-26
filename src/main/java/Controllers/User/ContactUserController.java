package Controllers.User;

import Util.Email;
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
public class ContactUserController extends HttpServlet {

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
        if (path.endsWith("/contact")) {
            request.getRequestDispatcher("/user/contact.jsp").forward(request, response);
        } else {
            if (path.endsWith("/contact/success")) {
                request.setAttribute("success", true);
            } else {
                request.setAttribute("fail", true);
            }
             request.getRequestDispatcher("/user/sendContactStatus.jsp").forward(request, response);
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
        if (request.getParameter("btn-send-contact") != null) {
            String fullname = request.getParameter("fullname");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String message = request.getParameter("message");
            Email emailSend = new Email();
            TemplateEmail template = new TemplateEmail();
            String templateSendContact = template.sendContactMessage(fullname, email, phone, message);
            boolean isSend = emailSend.sendEmail("thaydoi@gmail.com", "User send contact", templateSendContact, null);
            if (isSend) {
                response.sendRedirect("/contact/success");
            } else {
                response.sendRedirect("/contact/fail");
            }
        }
    }
}
