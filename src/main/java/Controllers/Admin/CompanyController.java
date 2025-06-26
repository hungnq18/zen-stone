package Controllers.Admin;

import DAO.CompanyDao;
import Model.Company;
import Util.Validation;
import java.io.IOException;
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
public class CompanyController extends HttpServlet {

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
        if (path.endsWith("/admin/company")) {
            this.getAllCompany(request, response);
            return;
        }
        String paths[] = path.split("/");
        int id = validate.getInt(paths[paths.length - 1]);
        if (path.startsWith("/admin/company/update/")) {
            this.getCurrentCompany(request, response, id);
            return;
        }
        if (path.startsWith("/admin/company/delete/")) {
            this.deleteCompany(request, response, id);
            return;
        }
    }
    
    private void getAllCompany(HttpServletRequest request, HttpServletResponse response) {
        CompanyDao companyDao = new CompanyDao();
        try {
            List<Company> company = companyDao.getAllCompany();
            request.setAttribute("company", company);
            request.getRequestDispatcher("/admin/view/company/company.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println("Get all company: " + e);
        }
    }
    
    private void getCurrentCompany(HttpServletRequest request, HttpServletResponse response, int id) {
        CompanyDao companyDao = new CompanyDao();
        try {
            Company company = companyDao.getCompanyById(id);
            if (company != null) {
                request.setAttribute("currentCompany", company);
                request.getRequestDispatcher("/admin/view/company/updateCompany.jsp").forward(request, response);
            } else {
                response.sendRedirect("/admin/404");
            }
        } catch (Exception e) {
            System.out.println("Get current company: " + e);
        }
    }
    
    private void deleteCompany(HttpServletRequest request, HttpServletResponse response, int id) {
        CompanyDao companyDao = new CompanyDao();
        try {
            int result = companyDao.delete(id);
            String url = "/admin/company?status=" + result;
            response.sendRedirect(url);
        } catch (Exception e) {
            System.out.println("Get current company: " + e);
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
        if (request.getParameter("btn-add-company") != null) {
            this.insertCompany(request, response);
        } else if (request.getParameter("btn-update-company") != null) {
            this.updateCompany(request, response);
        } else if (request.getParameter("btn-delete-companies") != null) {
            this.deleteCompanies(request, response);
        }
    }
    
    private void insertCompany(HttpServletRequest request, HttpServletResponse response) {
        Validation validate = new Validation();
        CompanyDao companyDao = new CompanyDao();
        try {
            String name = request.getParameter("name");
            String address = request.getParameter("address");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            String introduce = request.getParameter("introduction");
            LocalDateTime dateTime = LocalDateTime.now();
            Timestamp date = Timestamp.valueOf(dateTime);
            int status = validate.getInt(request.getParameter("status"));
            Company c = new Company(-1, name, address, phone, email, introduce, date, status);
            int result = companyDao.insert(c);
            String url = "/admin/company?status=" + result;
            response.sendRedirect(url);
        } catch (IOException e) {
            System.out.println("Insert company: " + e);
        }
    }
    
    private void updateCompany(HttpServletRequest request, HttpServletResponse response) {
        Validation validate = new Validation();
        CompanyDao companyDao = new CompanyDao();
        try {
            int id = validate.getInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String address = request.getParameter("address");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            String introduce = request.getParameter("introduction");
            LocalDateTime dateTime = LocalDateTime.now();
            Timestamp date = Timestamp.valueOf(dateTime);
            int status = validate.getInt(request.getParameter("status"));
            Company c = new Company(id, name, address, phone, email, introduce, date, status);
            int result = companyDao.update(c);
            String url = "/admin/company?status=" + result;
            response.sendRedirect(url);
        } catch (IOException e) {
            System.out.println("Insert company: " + e);
        }
    }
    
    private void deleteCompanies(HttpServletRequest request, HttpServletResponse response) {
        try {
            Validation validate = new Validation();
            CompanyDao companyDao = new CompanyDao();
            String ids[] = request.getParameterValues("delete-company-item");
            if(ids == null) {
                response.sendRedirect("/admin/company?status=0&message=Please choose company to delete");
                return;
            }
            boolean isDelete = false;
            for (String id : ids) {
                int result = companyDao.delete(validate.getInt(id));
                if (result >= 1) {
                    isDelete = true;
                }
            }
            String url = "/admin/company?status=";
            if (isDelete) {
                url += 1;
            } else {
                url += 0;
            }
            response.sendRedirect(url);
        } catch (Exception e) {
            System.out.println("Delete companies: " + e);
        }
    }
}
