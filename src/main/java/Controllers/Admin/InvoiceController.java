/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers.Admin;

import DAO.BillDao;
import DAO.BillDetailsDao;
import Model.Bill;
import Model.BillDetails;
import Util.Email;
import Util.TemplateEmail;
import com.lowagie.text.DocumentException;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.BodyPart;
import jakarta.mail.Multipart;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMultipart;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.xhtmlrenderer.pdf.ITextRenderer;

/**
 *
 * @author HP
 */
@WebServlet(name = "InvoiceController", urlPatterns = {"/InvoiceServlet"})
public class InvoiceController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet InvoiceController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet InvoiceController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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
        processRequest(request, response);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            String customerEmail = request.getParameter("customerEmail");
            String id = request.getParameter("billId");
            int billId = Integer.parseInt(id);
            BillDao billDao = new BillDao();
            Bill billCu = billDao.getBillByID(billId);
            BillDetailsDao billDetailDao = new BillDetailsDao();
            List<BillDetails> billde = billDetailDao.getBillDetailByID(billId);
            TemplateEmail temp = new TemplateEmail();
            String html = temp.emailConfirmOrderBill(billCu, billde, "#BILLCODE" + id);
            byte[] pdfBytes = generatePdfFromHtml(html);
            File tempPdfFile = saveToTempPdf(pdfBytes);
            Email email = new Email();
            email.sendEmailWithPDFAttachment(billCu.getEmail(), "Invoice", tempPdfFile, null);
            response.sendRedirect("/admin/bill");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    private byte[] generatePdfFromHtml(String htmlContent) throws IOException, DocumentException {
        ByteArrayOutputStream pdfOutput = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(htmlContent);
        renderer.layout();
        renderer.createPDF(pdfOutput);
        renderer.finishPDF();
        return pdfOutput.toByteArray();
    }

    private File saveToTempPdf(byte[] pdfBytes) throws IOException {
        File tempFile = File.createTempFile("tempInvoice", ".pdf");
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(pdfBytes);
        }
        return tempFile;
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
