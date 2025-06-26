/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers.Admin;

import DAO.BillDao;
import Model.Bill;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.sql.Date;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author HP
 */
public class FilterStatisticController extends HttpServlet {

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
//        processRequest(request, response);
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
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        JSONArray arr = this.statistic();
        if (!from.equals("") && !to.equals("")) {
            arr = this.statistic(Date.valueOf(from), Date.valueOf(to));
        }
        try ( PrintWriter out = response.getWriter()) {
            response.setContentType("application/json");
            out.print(arr);
        }
    }

    private JSONArray statistic() {
        BillDao billDao = new BillDao();
        List<Bill> bills = billDao.getAllBill();
        JSONArray arr = new JSONArray();
        for (Bill b : bills) {
            JSONObject bill = new JSONObject();
            bill.put("date", b.getDateOrder().toString().split(" ")[0]);
            bill.put("total", b.getTotal());
            bill.put("status", b.getStatus());
            arr.add(bill);
        }
        return arr;
    }

    private JSONArray statistic(Date from, Date to) {
        BillDao billDao = new BillDao();
        List<Bill> bills = billDao.getAllBillByDate(from, to);
        JSONArray arr = new JSONArray();
        for (Bill b : bills) {
            JSONObject bill = new JSONObject();
            bill.put("date", b.getDateOrder().toString().split(" ")[0]);
            bill.put("total", b.getTotal());
            bill.put("status", b.getStatus());
            arr.add(bill);
        }
        return arr;
    }

}
