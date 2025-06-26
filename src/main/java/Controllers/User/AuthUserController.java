package Controllers.User;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GooglePublicKeysManager;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

import DAO.AccountDao;
import Model.Account;
import Util.Email;
import Util.MD5Hashing;
import com.fasterxml.jackson.core.JsonFactory;
import com.google.api.client.http.HttpTransport;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "AuthUserController", urlPatterns = {"/auth/login", "/auth/register"})
public class AuthUserController extends HttpServlet {

    private static final String CLIENT_ID = "671924345403-ueq7k824a4nnspeeqlkpvi23p4l84523.apps.googleusercontent.com";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        if (path.endsWith("/auth/login")) {
            request.getRequestDispatcher("/user/login.jsp").forward(request, response);
        } else if (path.endsWith("/auth/register")) {
            request.getRequestDispatcher("/user/register.jsp").forward(request, response);
        } else {
            response.sendRedirect("/404");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        AccountDao adao = new AccountDao();
        MD5Hashing md5 = new MD5Hashing();

        String idTokenString = request.getParameter("id_token");

        if (idTokenString != null && !idTokenString.isEmpty()) {
            try {
                HttpTransport transport = new NetHttpTransport();
                GsonFactory jsonFactory = GsonFactory.getDefaultInstance();

                GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                        .setAudience(Collections.singletonList(CLIENT_ID))
                        .build();

                GoogleIdToken idToken = verifier.verify(idTokenString);

                if (idToken != null) {
                    GoogleIdToken.Payload payload = idToken.getPayload();
                    String email = payload.getEmail();
                    String emailLower = email.toLowerCase(); // đảm bảo không phân biệt hoa thường

                    System.out.println("Google email: " + emailLower); // debug

                    // Lấy account theo email (nên dùng lowercase trong cả DB lẫn khi tìm)
                    Account account = adao.getAccountByEmail(emailLower);

                    if (account == null) {
                        // Người dùng chưa có tài khoản → tạo mới
                        String username = emailLower.split("@")[0];
                        String defaultPassword = "12345678";
                        String hashedPassword = md5.hashPassword(defaultPassword);
                        Timestamp dateCreate = Timestamp.valueOf(LocalDateTime.now());

                        Account newAccount = new Account(
                                0,
                                null,
                                emailLower,
                                "", // phone
                                username,
                                hashedPassword,
                                0, // role user
                                dateCreate,
                                1 // status active
                        );
                        adao.insertAccountUser(newAccount);

                        // Gửi mail thông báo mật khẩu (chỉ lần đầu)
                        try {
                            String subject = "Your new account password";
                            String body = "Hello " + username + ",\n\nYour account has been created.\n"
                                    + "Your temporary password is: " + defaultPassword + "\n"
                                    + "Please login and change your password as soon as possible.";
                            Email.sendEmailConfirm(emailLower, subject, body);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        // Set session cho tài khoản mới
                        session.setAttribute("usernameUser", username);
                        session.setAttribute("usernameRole", "0");

                    } else {
                        // Người dùng đã có tài khoản → Đăng nhập (KHÔNG gửi mail lại)
                        System.out.println("Account đã tồn tại, không gửi mail."); // debug

                        session.setAttribute("usernameUser", account.getUsername());
                        session.setAttribute("usernameEmail", account.getEmail());
                        session.setAttribute("usernameRole", String.valueOf(account.getRole()));
                    }

                    response.sendRedirect("/");
                    return;

                } else {
                    request.setAttribute("messageUserAuth", "Invalid Google login.");
                    request.getRequestDispatcher("/user/login.jsp").forward(request, response);
                }

            } catch (GeneralSecurityException | IOException e) {
                throw new ServletException("Google login failed: " + e.getMessage(), e);
            }
        }




        // ✅ Handle traditional login
        if (request.getParameter(
                "submitLogin") != null) {
            String username = request.getParameter("loginName");
            String password = request.getParameter("loginPassword");
            Account a = adao.getAccountByUsername(username);
            String messageUserAuth = "";
            String passwordMD5 = md5.hashPassword(password);
            boolean isError = false;

            if (a == null) {
                messageUserAuth = "Your account does not exist";
                isError = true;
            } else if (a.getRole() != 0) {
                messageUserAuth = "Your account cannot login here";
                isError = true;
            } else if (a.getStatus() == 0) {
                messageUserAuth = "Your account is blocked";
                isError = true;
            } else if (!passwordMD5.equals(a.getPassword())) {
                messageUserAuth = "Password is not valid";
                isError = true;
            }

            if (isError) {
                request.setAttribute("messageUserAuth", messageUserAuth);
                request.getRequestDispatcher("/user/login.jsp").forward(request, response);
                return;
            }

            if (request.getParameter("remember") != null) {
                int cookieMaxAge = 3 * 24 * 60 * 60;
                Cookie usernameCookie = new Cookie("usernameUser", a.getUsername());
                Cookie roleUserCookie = new Cookie("usernameRole", a.getRole() + "");
                usernameCookie.setMaxAge(cookieMaxAge);
                roleUserCookie.setMaxAge(cookieMaxAge);
                usernameCookie.setPath("/");
                roleUserCookie.setPath("/");
                response.addCookie(usernameCookie);
                response.addCookie(roleUserCookie);
            } else {
                session.setAttribute("usernameUser", a.getUsername());
                session.setAttribute("usernameRole", a.getRole() + "");
            }
            response.sendRedirect("/");
            return;
        }

        // ✅ Handle traditional register
        if (request.getParameter(
                "register") != null) {
            String username = request.getParameter("username");
            if (adao.getAccountByUsername(username) != null) {
                request.setAttribute("messageFailRegister", "Username already exists");
                request.getRequestDispatcher("/user/register.jsp").forward(request, response);
                return;
            }
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String password = request.getParameter("password");
            String passwordHash = md5.hashPassword(password);
            Timestamp dateCreate = Timestamp.valueOf(LocalDateTime.now());
            Account account = new Account(0, null, email, phone, username, passwordHash, 0, dateCreate, 1);
            if (adao.insertAccountUser(account) > 0) {
                session.setAttribute("messageSuccessRegister", "Register successfully.");
                session.setMaxInactiveInterval(3);
                response.sendRedirect("/auth/login");
            } else {
                request.setAttribute("messageFailRegister", "Register failed. Try again");
                request.getRequestDispatcher("/user/register.jsp").forward(request, response);
            }
        }
    }
}
