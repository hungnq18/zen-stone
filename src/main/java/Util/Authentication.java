package Util;

import DAO.AccountDao;
import Model.Account;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.security.SecureRandom;

/**
 *
 * @author Le Tan Kim
 */
public class Authentication {

    private String username;
    private String password;
    private int role;
    private int status;

    public Authentication() {
    }

    public Authentication(String username, String password, int role, int status) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Account isExist(String username) {
        AccountDao accountDao = new AccountDao();
        Account a = accountDao.getAccountByUsername(username);
        return a;
    }

    public boolean isLoginAdmin(HttpServletRequest request) {
        String username = "";
        boolean isLogin = true;
        HttpSession session = request.getSession();
        String usernameAdmin = (String) session.getAttribute("usernameAdmin");
        String role = (String) (session.getAttribute("role") + "");
        if ((usernameAdmin == null || usernameAdmin.equals(""))) {
            isLogin = false;
        }
        if ((role == null || (!role.equals("1") && !role.equals("2")))) {
            isLogin = false;
        }
        return isLogin;
    }

    public boolean isLogigAdminWithHighPermission(HttpServletRequest request) {
        String username = "";
        boolean isLogin = true;
        HttpSession session = request.getSession();
        String usernameAdmin = (String) session.getAttribute("usernameAdmin");
        String role = (String) (session.getAttribute("role") + "");
        if ((usernameAdmin == null || usernameAdmin.equals(""))) {
            isLogin = false;
        }
        if ((role == null || !role.equals("2"))) {
            isLogin = false;
        }
        return isLogin;
    }

    public String isLogigUser(HttpServletRequest request) {
        try {
            String userFinal = "";
            HttpSession session = request.getSession();
            String usernameSession = (String) session.getAttribute("usernameUser");
            String usernameRoleSession = (String) session.getAttribute("usernameRole");
            if (usernameSession != null && usernameRoleSession != null) {
                userFinal = usernameSession;
            } else {
                String usernameUserCookie = "";
                String roleUserCookie = "";
                Cookie cookies[] = request.getCookies();
                if (cookies != null) {
                    for (Cookie c : cookies) {
                        if (c != null && c.getName().equals("usernameUser")) {
                            usernameUserCookie = c.getValue();
                        }
                        if (c != null && c.getName().equals("usernameRole")) {
                            roleUserCookie = c.getValue();
                        }
                    }
                }
                if (!usernameUserCookie.equals("") && roleUserCookie.equals("0")) {
                    userFinal = usernameUserCookie;
                }
            }
            if (!userFinal.equals("")) {
                return userFinal;
            }
        } catch (Exception e) {
            System.out.println("Error login: " + e);
        }
        return null;
    }

    public boolean Logout(HttpSession session) {
        session.invalidate();
        return true;
    }

    public boolean LogoutUser(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        Cookie cookieUsername = new Cookie("usernameUser", null);
        Cookie cookieRole = new Cookie("usernameRole", null);
        cookieUsername.setMaxAge(0);
        cookieRole.setMaxAge(0);
        cookieUsername.setPath("/");
        cookieRole.setPath("/");
        response.addCookie(cookieRole);
        response.addCookie(cookieUsername);
        session.invalidate();
        return true;
    }

    public String generatePassword(int lengthOfPassword) {
        String letterUpper = "ABCDEFGHIJKLMNOQPRSTUYWVZX";
        String letterLower = "abcdefghijklmnoqprstuvwyzx";
        String number = "123456789";
        String special = "!@#&^?$*";
        SecureRandom random = new SecureRandom();
        String newPassword = "";
        for (int i = 0; i < lengthOfPassword; i++) {
            int ratio = random.nextInt(4);
            if (ratio == 0) {
                newPassword += letterUpper.charAt(random.nextInt(letterUpper.length()));
            } else if (ratio == 1) {
                newPassword += letterLower.charAt(random.nextInt(letterLower.length()));
            } else if (ratio == 2) {
                newPassword += number.charAt(random.nextInt(number.length()));
            } else {
                newPassword += special.charAt(random.nextInt(special.length()));
            }
        }
        return newPassword;
    }
}
