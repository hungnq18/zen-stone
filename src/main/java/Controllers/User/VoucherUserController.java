package Controllers.User;

import DAO.VoucherDao;
import Model.Voucher;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 *
 * @author Le Tan Kim
 */
public class VoucherUserController extends HttpServlet {

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
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonData = mapper.readTree(request.getReader());
        String code = jsonData.get("code").asText();
        String billPriceText = jsonData.get("billPrice").asText();
        float priceBill = Float.parseFloat(billPriceText);
        int userID = Integer.parseInt(jsonData.get("userId").asText());
        VoucherDao voucherDao = new VoucherDao();
        Voucher voucherActive = voucherDao.getVoucherByCode(code, priceBill);
        String used = "";
        if (voucherActive != null && voucherActive.getUsed().equals("")) {
            used += -1 + ",";
        }
        String voucherUsers[] = voucherActive == null ? new String[0] : voucherActive.getUsed().split(",");
        String isValid = "";
        if (voucherActive == null) {
            isValid = "inactive";
        } else if (this.isExistID(voucherUsers, userID + "")) {
            isValid = "used";
        } else {
            isValid += voucherActive.getValue();
        }
        PrintWriter out = response.getWriter();
        out.print(isValid);
    }

    private boolean isExistID(String[] array, String target) {
        for (String str : array) {
            if (str.equals(target)) {
                return true;
            }
        }
        return false;
    }

}
