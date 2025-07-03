package BotAI;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

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

            // ==== BẮT ĐẦU: Trả lời theo mẫu nếu khớp ====
            Map<String, String> patterns = new HashMap<>();
            patterns.put("xin chào",  "<b>Xin chào bạn đến với ZenStone – Cầu Đá Phong Thủy!</b>");
            patterns.put("bạn là ai", "<b>Mình là Trợ lý phong thủy AI</b>, sẵn sàng giúp bạn chọn quả cầu đá phù hợp để hút tài lộc, tình duyên, sức khỏe hay bình an.<br>Bạn đang muốn cải thiện điều gì trong cuộc sống?<br><ul><li>1. Tài lộc & công việc</li><li>2. Tình duyên & gia đạo</li><li>3. Sức khỏe & an yên</li><li>4. Thiền định & tâm linh</li><li>5. Trấn trạch, bảo vệ</li></ul>");
            patterns.put("tài lộc", "<b>Bạn muốn thu hút tài lộc, may mắn trong kinh doanh hoặc công việc ổn định?</b><br>ZenStone gợi ý bạn nên chọn:<ul><li>🔸 <b>Cầu đá Thạch Anh Vàng</b> – tượng trưng cho tiền tài, sự thịnh vượng.</li><li>🔸 <b>Cầu Citrine</b> – hấp thụ năng lượng mặt trời, rất tốt cho doanh nhân.</li></ul>Những quả cầu này còn giúp tăng tự tin, sáng tạo và năng lượng tích cực trong công việc mỗi ngày.<br>Bạn muốn xem danh sách sản phẩm tương ứng không?");
            patterns.put("tình duyên", "<b>Bạn muốn cải thiện tình cảm hoặc thu hút một mối quan hệ mới?</b><br>Gợi ý từ mình là:<ul><li><b>Cầu Thạch Anh Hồng</b> – đá của tình yêu, giúp tăng cảm xúc và chữa lành vết thương lòng.</li><li><b>Cầu Moonstone</b> – giúp tăng sự thấu cảm, ổn định cảm xúc nữ giới.</li></ul>Đặt quả cầu tại góc Tây Nam (cung tình duyên) trong nhà là rất phù hợp!");
            patterns.put("trấn trạch / bảo vệ", "<b>Nếu bạn cảm thấy bất an, muốn bảo vệ không gian khỏi năng lượng xấu…</b><br>Gợi ý từ ZenStone là:<ul><li><b>Cầu đá Obsidian đen</b> – hấp thu tà khí, bảo vệ chủ nhân.</li><li><b>Tourmaline đen</b> – trấn trạch mạnh, giữ vững năng lượng trong không gian sống.</li></ul>Thường được đặt tại cửa ra vào hoặc bàn làm việc để tăng hiệu quả.");
            patterns.put("thiền định & phát triển tâm linh", "<b>Nếu bạn đang tìm kiếm sự tĩnh tâm, phát triển trực giác hay thiền định sâu hơn…</b><br>ZenStone gợi ý:<ul><li><b>Thạch Anh Tím (Amethyst)</b> – đá tâm linh, giúp an thần, hỗ trợ thiền định.</li><li><b>Moonstone / Selenite</b> – làm sạch năng lượng xấu, hỗ trợ kết nối tâm hồn.</li></ul>Những loại đá này thường được đặt gần nơi ngủ hoặc phòng thiền.");
            patterns.put("tư vấn – gợi ý mua hàng", "<b>Nếu bạn đã chọn được loại đá phù hợp, mình có thể gợi ý một số mẫu cầu đá đang có sẵn tại ZenStone.</b><br>Bạn muốn xem mẫu đá nào? (Vàng, Hồng, Đen, Tím, Xanh...)<br>Mỗi sản phẩm có thể khắc tên hoặc mã QR theo yêu cầu – bạn muốn mình hỗ trợ luôn không?");
            patterns.put("thạch anh trắng (Clear Quartz)", "<b>• Ý nghĩa:</b> Là loại đá phong thủy mạnh mẽ nhất về khả năng thanh lọc năng lượng. Giúp loại bỏ tà khí, làm sạch môi trường sống và nâng cao sự minh mẫn. Đặc biệt phù hợp với người làm việc trí óc, thiền định hoặc học hành.<br><b>• Khuyên dùng cho:</b> Người học tập, nghiên cứu, làm việc cần sự sáng suốt.");
            patterns.put("thạch anh hồng (Rose Quartz)", "<b>• Ý nghĩa:</b> Tượng trưng cho tình yêu, sự dịu dàng và hòa hợp. Tăng cường tình cảm đôi lứa, cải thiện các mối quan hệ xung quanh, giảm căng thẳng và chữa lành cảm xúc.<br><b>• Khuyên dùng cho:</b> Người độc thân muốn thu hút tình duyên hoặc người đã kết hôn muốn hâm nóng tình cảm.");
            patterns.put("thạch anh tím (Amethyst)", "<b>• Ý nghĩa:</b> Mang lại sự bình an, giúp thư giãn và tăng cường trực giác. Đây là loại đá rất được ưa chuộng trong thiền định và hỗ trợ giấc ngủ sâu.<br><b>• Khuyên dùng cho:</b> Người hay căng thẳng, khó ngủ, muốn phát triển tâm linh.");
            patterns.put("thạch anh vàng (Citrine)", "<b>• Ý nghĩa:</b> Tượng trưng cho tiền tài, sự thịnh vượng. Giúp tăng tự tin, sáng tạo và năng lượng tích cực trong công việc mỗi ngày.<br><b>• Khuyên dùng cho:</b> Doanh nhân, nhà đầu tư, người làm việc cần sự tự tin.");
            patterns.put("thạch anh đen (Obsidian)/ tourmaline đen", "<b>• Ý nghĩa:</b> Hấp thu tà khí, bảo vệ chủ nhân. Trấn trạch mạnh, giữ vững năng lượng trong không gian sống.<br><b>• Khuyên dùng cho:</b> Người hay căng thẳng, khó ngủ, muốn phát triển tâm linh.");
            patterns.put("quả cầu đá aventurine", "<b>• Ý nghĩa:</b> Biểu tượng của sức khỏe, sự phát triển và chữa lành. Thường đặt trong phòng trẻ nhỏ, người bệnh hoặc góc gia đình.<br><b>• Khuyên dùng cho:</b> Người mong muốn bình an, gia đạo yên ổn.");
            patterns.put("thạch anh xanh lá", "<b>• Ý nghĩa:</b> Biểu tượng của sức khỏe, sự phát triển và chữa lành. Thường đặt trong phòng trẻ nhỏ, người bệnh hoặc góc gia đình.<br><b>• Khuyên dùng cho:</b> Người mong muốn bình an, gia đạo yên ổn.");
            patterns.put("quả cầu thạch anh đỏ", "<b>• Ý nghĩa:</b> Tăng cường sự nhiệt huyết, can đảm và sức sống. Kích hoạt cung danh vọng, thúc đẩy quyết đoán và tạo động lực mạnh mẽ.<br><b>• Khuyên dùng cho:</b> Người làm lãnh đạo, ngành nghề cần năng lượng và sự quyết đoán.");
            patterns.put("đá mã não đỏ", "<b>• Ý nghĩa:</b> Tăng cường sự nhiệt huyết, can đảm và sức sống. Kích hoạt cung danh vọng, thúc đẩy quyết đoán và tạo động lực mạnh mẽ.<br><b>• Khuyên dùng cho:</b> Người làm lãnh đạo, ngành nghề cần năng lượng và sự quyết đoán.");
            patterns.put("quả cầu đá xanh lam", "<b>• Ý nghĩa:</b> Tăng khả năng giao tiếp, thúc đẩy sự trung thực và minh bạch. Giúp cải thiện các mối quan hệ nơi công sở, gia đình.<br><b>• Khuyên dùng cho:</b> Người làm giáo dục, diễn thuyết, thương lượng.");
            patterns.put("blue quartz", "<b>• Ý nghĩa:</b> Tăng khả năng giao tiếp, thúc đẩy sự trung thực và minh bạch. Giúp cải thiện các mối quan hệ nơi công sở, gia đình.<br><b>• Khuyên dùng cho:</b> Người làm giáo dục, diễn thuyết, thương lượng.");
            patterns.put("lapis lazuli", "<b>• Ý nghĩa:</b> Tăng khả năng giao tiếp, thúc đẩy sự trung thực và minh bạch. Giúp cải thiện các mối quan hệ nơi công sở, gia đình.<br><b>• Khuyên dùng cho:</b> Người làm giáo dục, diễn thuyết, thương lượng.");
            patterns.put("quả cầu đá opalite", "<b>• Ý nghĩa:</b> Là loại đá nhẹ nhàng, hỗ trợ năng lượng tinh thần, tạo cảm giác dễ chịu, bình tĩnh trong khởi đầu mới.<br><b>• Khuyên dùng cho:</b> Người vừa thay đổi công việc, chuyển nhà, bước sang giai đoạn mới.");
            patterns.put("opalite", "<b>• Ý nghĩa:</b> Là loại đá nhẹ nhàng, hỗ trợ năng lượng tinh thần, tạo cảm giác dễ chịu, bình tĩnh trong khởi đầu mới.<br><b>• Khuyên dùng cho:</b> Người vừa thay đổi công việc, chuyển nhà, bước sang giai đoạn mới.");
            patterns.put("quả cầu đá mặt trăng", "<b>• Ý nghĩa:</b> Cân bằng cảm xúc, thúc đẩy sự thấu cảm và kết nối nữ tính. Đặc biệt phù hợp với phụ nữ.<br><b>• Khuyên dùng cho:</b> Phụ nữ mang thai, người cần ổn định tâm trạng, tìm lại sự dịu dàng trong cuộc sống.");
            patterns.put("moonstone", "<b>• Ý nghĩa:</b> Cân bằng cảm xúc, thúc đẩy sự thấu cảm và kết nối nữ tính. Đặc biệt phù hợp với phụ nữ.<br><b>• Khuyên dùng cho:</b> Phụ nữ mang thai, người cần ổn định tâm trạng, tìm lại sự dịu dàng trong cuộc sống.");

            String normalizedMsg = userMessage.trim().toLowerCase();
            for (Map.Entry<String, String> entry : patterns.entrySet()) {
                if (normalizedMsg.contains(entry.getKey())) {
                    // Trả về câu trả lời mẫu
                    response.getWriter().write("{\"reply\":\"" + entry.getValue().replace("\"","\\\"") + "\"}");
                    return;
                }
            }
            // ==== KẾT THÚC: Trả lời theo mẫu nếu khớp ====

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

                    JsonObject replyJson = new JsonObject();
                    replyJson.addProperty("reply", botReply);
                    response.getWriter().write(replyJson.toString());
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
