/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers.Admin;

import DAO.BillDao;
import DAO.BillDetailsDao;
import Model.Bill;
import Model.BillDetails;
import Util.Validation;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author HP
 */
public class BillController extends HttpServlet {
    
    private static BillDao billDao;
    
    public BillController() {
        this.billDao = new BillDao();
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
        String path = request.getRequestURI();
        Validation validate = new Validation();
        if (path.endsWith("/admin/bill")) {
            this.showAllBill(request, response);
        } else {
            String paths[] = path.split("/");
            int idBill = validate.getInt(paths[paths.length - 1]);
            if (path.startsWith("/admin/bill/view")) {
                this.viewBill(request, response, idBill);
            } else if (path.startsWith("/admin/bill/delete")) {
                this.deleteBill(request, response, idBill);
            }
        }
    }
    
    private void showAllBill(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Bill> bills = billDao.getAllBill();
            request.setAttribute("bills", bills);
            request.getRequestDispatcher("/admin/view/bill/bill.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println("Show bill: " + e);
        }
    }
    
    private void viewBill(HttpServletRequest request, HttpServletResponse response, int idBill) {
        try {
            BillDetailsDao billDetailsDao = new BillDetailsDao();
            Bill bill = billDao.getBillByID(idBill);
            if (bill != null) {
                List<BillDetails> billDetails = billDetailsDao.getBillDetailByID(idBill);
                request.setAttribute("bill", bill);
                request.setAttribute("billDetails", billDetails);
                request.getRequestDispatcher("/admin/view/bill/billDetails.jsp").forward(request, response);
            } else {
                response.sendRedirect("/admin/404");
            }
        } catch (Exception e) {
            System.out.println("View bill: " + e);
        }
    }
    
    private void deleteBill(HttpServletRequest request, HttpServletResponse response, int idBill) {
        try {
            int result = billDao.deleteBill(idBill);
            String url = "/admin/bill?status=" + result;
            if (result >= 1) {
                url += "&message=Delete bill success";
            } else {
                url += "&message=Delete bill fail";
            }
            response.sendRedirect(url);
        } catch (IOException e) {
            System.out.println("Delete bill: " + e);
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
        if (request.getParameter("btn-update-bill-status") != null) {
            updateBillStatus(request, response);
        } else if (request.getParameter("btn-delete-bills") != null) {
            deleteBills(request, response);
        }
    }
    
    private void updateBillStatus(HttpServletRequest request, HttpServletResponse response) {
        try {
            Validation validate = new Validation();
            int id = validate.getInt(request.getParameter("idBill"));
            int status = validate.getInt(request.getParameter("status"));
            LocalDateTime dateTime = LocalDateTime.now();
            Timestamp dateUpdate = Timestamp.valueOf(dateTime);
            int result = billDao.updateStatus(id, status, dateUpdate);
            String url = "/admin/bill?status=";
            if (result >= 1) {
                url += result + "&message=Update status this bill success";
            } else {
                url += result + "&message=Update status this bill fail. Try again";
            }
            response.sendRedirect(url);
        } catch (Exception e) {
            System.out.println("Update status: " + e);
        }
    }
    
    private void deleteBills(HttpServletRequest request, HttpServletResponse response) {
        try {
            Validation validate = new Validation();
            String[] idUrl = request.getParameterValues("delete-item-bill");
            if(idUrl == null) {
                response.sendRedirect("/admin/bill?status=0&message=Please choose bill to delete");
                return;
            }
            boolean isDelete = false;
            idUrl = (idUrl == null) ? new String[0] : idUrl;
            for (String id : idUrl) {
                int result = billDao.deleteBill(validate.getInt(id));
                if (result >= 1) {
                    isDelete = true;
                }
            }
            String url = "/admin/bill?status=";
            if (isDelete) {
                url += "1&message=Delete bill success";
            } else {
                url += "0&message=Delete bill fail";
            }
            response.sendRedirect(url);
        } catch (Exception e) {
            System.out.println("Delete bills: " + e);
        }
    }
}
