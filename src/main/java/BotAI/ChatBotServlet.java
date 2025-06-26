
package BotAI;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ChatBotServlet extends HttpServlet {

    private static final String GEMINI_API_KEY = "AIzaSyCxbgUfKelaM7OndLTfvvaQYbgxQdMiNlk";
    private static final String GEMINI_URL =
        "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key="
        + GEMINI_API_KEY;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        try {
            // Cấu hình encoding
            request.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");

            // Lấy và validate message
            String userMessage = request.getParameter("message");
            if (userMessage == null || userMessage.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\":\"Message không được để trống\"}");
                return;
            }

            // Build request JSON
            JsonObject part = new JsonObject();
            part.addProperty("text", userMessage);

            JsonObject content = new JsonObject();
            JsonArray parts = new JsonArray();
            parts.add(part);
            content.add("parts", parts);

            JsonObject body = new JsonObject();
            JsonArray contents = new JsonArray();
            contents.add(content);
            body.add("contents", contents);

            // Mở kết nối
            HttpURLConnection con = (HttpURLConnection) new URL(GEMINI_URL).openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setDoOutput(true);
            con.setConnectTimeout(10000); // 10s timeout
            con.setReadTimeout(10000);    // 10s timeout

            // Gửi request
            try (OutputStream os = con.getOutputStream()) {
                os.write(body.toString().getBytes(StandardCharsets.UTF_8));
            }

            int status = con.getResponseCode();

            if (status == 200) {
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {

                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) sb.append(line);

                    JsonObject json = JsonParser.parseString(sb.toString()).getAsJsonObject();
                    String botReply = json
                        .getAsJsonArray("candidates").get(0)
                        .getAsJsonObject().getAsJsonObject("content")
                        .getAsJsonArray("parts").get(0)
                        .getAsJsonObject().get("text").getAsString().trim();

                    response.getWriter().write(
                        "{\"reply\":\"" + botReply.replace("\"","\\\"") + "\"}"
                    );
                }
            } else {
                try (BufferedReader err = new BufferedReader(
                        new InputStreamReader(
                            con.getErrorStream() != null ? con.getErrorStream() :
                            new ByteArrayInputStream(new byte[0]), StandardCharsets.UTF_8))) {

                    String errMsg = err.lines().reduce("", (a,b) -> a+b);
                    response.setStatus(status);
                    response.getWriter().write(
                        "{\"error\":\"Gemini returned HTTP " + status + ": " + errMsg.replace("\"","\\\"") + "\"}"
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace(); // log lỗi server
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(
                "{\"error\":\"Internal server error: " + e.getMessage().replace("\"","\\\"") + "\"}"
            );
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet ChatBot tích hợp Gemini";
    }
}
