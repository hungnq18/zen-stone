package Controllers.Admin;

import DAO.AccountDao;
import Model.Account;
import Util.Authentication;
import Util.MD5Hashing;
import Util.Upload;
import Util.Validation;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author HP
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 50)   // 50 MB
public class AccountController extends HttpServlet {

    private static AccountDao accountDao;

    public AccountController() {
        this.accountDao = new AccountDao();
    }

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
        Authentication auth = new Authentication();
        String path = request.getRequestURI();
        if (auth.isLogigAdminWithHighPermission(request)) {
            Validation validate = new Validation();
            if (path.endsWith("/admin/account")) {
                this.showAllAccount(request, response);
            } else {
                String paths[] = path.split("/");
                int id = validate.getInt(paths[paths.length - 1]);
                if (path.startsWith("/admin/account/update/")) {
                    this.getCurrentAccountUpdate(request, response, id);
                } else if (path.startsWith("/admin/account/delete/")) {
                    this.deleteAccount(request, response, id);
                } else if (path.startsWith("/admin/account/personal/")) {
                    this.getCurrentAccountUpdatePersonal(request, response, paths[paths.length - 1]);
                }
            }
        } else if (auth.isLoginAdmin(request) && path.startsWith("/admin/account/personal/")) {
            String paths[] = path.split("/");
            this.getCurrentAccountUpdatePersonal(request, response, paths[paths.length - 1]);
        } else {
            response.sendRedirect("/admin");
        }
    }

    private void showAllAccount(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Account> accounts = accountDao.allAccount();
            request.setAttribute("accounts", accounts);
            request.getRequestDispatcher("/admin/view/account/account.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println("Show account: " + e);
        }
    }

    private void getCurrentAccountUpdatePersonal(HttpServletRequest request, HttpServletResponse response, String username) {
        try {
            Validation validate = new Validation();
            HttpSession session = request.getSession();
            if (session.getAttribute("usernameAdmin").equals(username)) {
                Account a = accountDao.getAccountByUsername(username);
                request.setAttribute("account", a);
                request.getRequestDispatcher("/admin/view/account/updateAccountPersonal.jsp").forward(request, response);
            } else {
                response.sendRedirect("/admin/404");
            }
        } catch (Exception e) {
            System.out.println("Get current account update: " + e);
        }
    }

    private void getCurrentAccountUpdate(HttpServletRequest request, HttpServletResponse response, int id) {
        try {
            Validation validate = new Validation();
            Account a = accountDao.getAccountByID(id);
            if (a != null) {
                request.setAttribute("account", a);
                request.getRequestDispatcher("/admin/view/account/updateAccount.jsp").forward(request, response);
            } else {
                response.sendRedirect("/admin/404");
            }
        } catch (Exception e) {
            System.out.println("Get current account update: " + e);
        }
    }

    private void deleteAccount(HttpServletRequest request, HttpServletResponse response, int id) {
        try {
            int result = accountDao.delete(id);
            String url = "/admin/account?status=" + result;
            response.sendRedirect(url);
        } catch (Exception e) {
            System.out.println("Delete category: " + e);
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
        if (request.getParameter("btn-add-account") != null) {
            this.addAccount(request, response);
        } else if (request.getParameter("btn-update-account") != null) {
            this.updateAccount(request, response);
        } else if (request.getParameter("btn-delete-accounts") != null) {
            this.deleteAccounts(request, response);
        } else if (request.getParameter("btn-update-account-personal") != null) {
            this.updateAccountPersonal(request, response);
        }
    }

    private void addAccount(HttpServletRequest request, HttpServletResponse response) {
        try {
            Validation validate = new Validation();
            MD5Hashing md5 = new MD5Hashing();
//     get information from form
            String fullname = request.getParameter("fullname");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String username = request.getParameter("username");
            Account accountCheckUsername = accountDao.getAccountByUsername(username);
            int result = 0;
            String url = "/admin/account?status=";
            if (accountCheckUsername == null) {
                String password = md5.hashPassword(request.getParameter("password"));
                int role = validate.getInt(request.getParameter("role"));
                LocalDateTime dateTime = LocalDateTime.now();
                Timestamp dateCreate = Timestamp.valueOf(dateTime);
                int status = validate.getInt(request.getParameter("status"));
                Account a = new Account(0, fullname, email, phone, username, password, role, dateCreate, status);
                result = accountDao.insert(a);
                url += result;
            } else {
                result = 0;
                url += (result + "&message=Username was exist. Try other username");
            }
            response.sendRedirect(url);
        } catch (Exception e) {
            System.out.println("Insert Account: " + e);
        }
    }

    private void updateAccountPersonal(HttpServletRequest request, HttpServletResponse response) {
        try {
            Upload upload = new Upload();
            Validation validate = new Validation();
            MD5Hashing md5 = new MD5Hashing();
//     get information from form
            int id = validate.getInt(request.getParameter("id"));
            String fullname = request.getParameter("fullname");
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            Part filePath = request.getPart("avatar");
            String pathDir = "./uploads/account/";
            String uploadPath = getServletContext().getRealPath(pathDir);
            String fileName = pathDir;
            String fileNameUpload = upload.uploadImg(filePath, uploadPath);
            if (fileNameUpload == null) {
                fileName = request.getParameter("avatarOld");
            } else {
                Account a = accountDao.getAccountByUsername(username);
                fileName += fileNameUpload;
            }
            String phone = request.getParameter("phone");
            String newPassword = request.getParameter("password");
            String passwordHashing = md5.hashPassword(newPassword);
            if (newPassword.equals("")) {
                passwordHashing = request.getParameter("oldPassword");
            }
            int role = validate.getInt(request.getParameter("role"));
            int status = validate.getInt(request.getParameter("status"));
            Account a = new Account(id, fullname, email, phone, username, passwordHashing, role, null, status, fileName);
            int result = accountDao.updatePersonal(a);
            String url = "/admin?status=" + result;
            HttpSession session = request.getSession();
            if (result == 1) {
                session.setAttribute("avatar", fileName);
            }
            response.sendRedirect(url);
        } catch (Exception e) {
            System.out.println("Update account personal: " + e);
        }
    }

    private void updateAccount(HttpServletRequest request, HttpServletResponse response) {
        try {
            Validation validate = new Validation();
            MD5Hashing md5 = new MD5Hashing();
//     get information from form
            int id = validate.getInt(request.getParameter("id"));
            String fullname = request.getParameter("fullname");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String username = request.getParameter("username");
            String newPassword = request.getParameter("password");
            String passwordHashing = md5.hashPassword(newPassword);
            if (newPassword.equals("")) {
                passwordHashing = request.getParameter("oldPassword");
            }
            int role = validate.getInt(request.getParameter("role"));
            int status = validate.getInt(request.getParameter("status"));
            Account a = new Account(id, fullname, email, phone, username, passwordHashing, role, null, status);
            int result = accountDao.update(a);
            String url = "/admin/account?status=" + result;
            response.sendRedirect(url);
        } catch (Exception e) {
            System.out.println("Update account: " + e);
        }
    }

    private void deleteAccounts(HttpServletRequest request, HttpServletResponse response) {
        try {
            Validation validate = new Validation();
            String ids[] = request.getParameterValues("delete-account-item");
            boolean isDelete = false;
            if (ids == null) {
                response.sendRedirect("/admin/account?status=0&message=Please choose account to delete");
                return;
            }
            for (String id : ids) {
                int result = accountDao.delete(validate.getInt(id));
                if (result >= 1 && !isDelete) {
                    isDelete = true;
                }
            }
            String url = "/admin/account?status=";
            if (isDelete) {
                url += 1;
            } else {
                url += 0;
            }
            response.sendRedirect(url);
        } catch (Exception e) {
            System.out.println("Delete categories:  " + e);
        }
    }

}
