/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers.Admin;

import DAO.VoucherDao;
import Model.Voucher;
import Util.Validation;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author HP
 */
public class VoucherController extends HttpServlet {

    private static VoucherDao voucherDao;

    public VoucherController() {
        this.voucherDao = new VoucherDao();
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
        Validation validate = new Validation();
        String path = request.getRequestURI();
        if (path.endsWith("/admin/voucher")) {
            this.showAllVoucher(request, response);
        } else {
            String paths[] = path.split("/");
            int id = validate.getInt(paths[paths.length - 1]);
            if (path.startsWith("/admin/voucher/update/")) {
                this.getCurrentVoucherUpdate(request, response, id);
            } else if (path.startsWith("/admin/voucher/delete/")) {
                this.deleteVoucher(request, response, id);
            }
        }
    }

    private void showAllVoucher(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Voucher> vouchers = voucherDao.allVoucher();
            request.setAttribute("vouchers", vouchers);
            request.getRequestDispatcher("/admin/view/voucher/voucher.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println("Show voucher: " + e);
        }
    }

    private void getCurrentVoucherUpdate(HttpServletRequest request, HttpServletResponse response, int id) {
        try {
            Voucher c = voucherDao.currentVoucher(id);
            if (c != null) {
                request.setAttribute("currentVoucher", c);
                request.getRequestDispatcher("/admin/view/voucher/updateVoucher.jsp").forward(request, response);
            } else {
                response.sendRedirect("/admin/404");
            }
        } catch (Exception e) {
            System.out.println("Get current voucher update: " + e);
        }
    }

    private void deleteVoucher(HttpServletRequest request, HttpServletResponse response, int id) {
        try {
            int result = voucherDao.delete(id);
            String url = "/admin/voucher?status=" + result;
            response.sendRedirect(url);
        } catch (Exception e) {
            System.out.println("Delete voucher: " + e);
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
        if (request.getParameter("btn-add-voucher") != null) {
            this.addVoucher(request, response);
        } else if (request.getParameter("btn-update-voucher") != null) {
            this.updateVoucher(request, response);
        } else if (request.getParameter("btn-delete-vouchers") != null) {
            this.deleteVouchers(request, response);
        }
    }

    private void addVoucher(HttpServletRequest request, HttpServletResponse response) {
        try {
            Validation validate = new Validation();
            String name = request.getParameter("name");
            String code = request.getParameter("code");
            if (this.isExist(code)) {
                response.sendRedirect("/admin/voucher?status=0&message=Code voucher is exist");
                return;
            }
            int type = validate.getInt(request.getParameter("type"));
            float value = validate.getFloat(request.getParameter("value"));
            Date start = Date.valueOf(request.getParameter("start"));
            Date end = Date.valueOf(request.getParameter("end"));
            int status = validate.getInt(request.getParameter("status"));
            float limit = validate.getFloat(request.getParameter("limit"));
            Voucher v = new Voucher(-1, name, code, type, value, start, end, status, limit);
            int result = voucherDao.insert(v);
            String url = "/admin/voucher?status=" + result;
            response.sendRedirect(url);
        } catch (Exception e) {
            System.out.println("Insert voucher: " + e);
        }
    }

    private boolean isExist(String code) {
        Voucher v = voucherDao.getVoucherByCode(code);
        if (v == null) {
            return false;
        }
        return true;
    }

    private void updateVoucher(HttpServletRequest request, HttpServletResponse response) {
        try {
            Validation validate = new Validation();
            int id = validate.getInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String code = request.getParameter("code");
            Voucher vCheck = voucherDao.currentVoucher(id);
            if (this.isExist(code) && !vCheck.getCode().equals(code)) {
                response.sendRedirect("/admin/voucher/update/" + id + "?status=0&message=Code voucher is exist");
                return;
            }
            int type = validate.getInt(request.getParameter("type"));
            float value = validate.getFloat(request.getParameter("value"));
            Date start = Date.valueOf(request.getParameter("start"));
            Date end = Date.valueOf(request.getParameter("end"));
            int status = validate.getInt(request.getParameter("status"));
            float limit = validate.getFloat(request.getParameter("limit"));
            Voucher v = new Voucher(id, name, code, type, value, start, end, status, limit);
            int result = voucherDao.update(v);
            String url = "/admin/voucher?status=" + result;
            response.sendRedirect(url);
        } catch (Exception e) {
            System.out.println("Update voucher: " + e);
        }
    }

    private void deleteVouchers(HttpServletRequest request, HttpServletResponse response) {
        Validation validate = new Validation();
        try {
            String[] allSlugChecked = request.getParameterValues("delete-voucher-item");
            if (allSlugChecked == null) {
                response.sendRedirect("/admin/voucher?status=0&message=Please choose voucher to delete");
                return;
            }
            int status = 0;
            for (String id : allSlugChecked) {
                int result = voucherDao.delete(validate.getInt(id));
                if (result == 1) {
                    status = 1;
                }
            }
            String url = "/admin/voucher?status=" + status;
            response.sendRedirect(url);
        } catch (Exception e) {
            System.out.println("Delete vouchers:  " + e);
        }
    }

}
