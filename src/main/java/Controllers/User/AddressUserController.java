package Controllers.User;

import DAO.DistrictDao;
import DAO.WardDao;
import Model.District;
import Model.Ward;
import Util.Validation;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author Le Tan Kim
 */
public class AddressUserController extends HttpServlet {

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonData = mapper.readTree(request.getReader());
            String province = jsonData.get("province").asText();
            String ward = jsonData.get("district").asText();
            Validation validate = new Validation();
            if (!province.equals("")) {
                int idProvince = validate.getInt(province);
                DistrictDao districtDao = new DistrictDao();
                List<District> districts = districtDao.getDistrict(idProvince);
                PrintWriter out = response.getWriter();
                out.print(this.district(districts));
            } else if (!ward.equals("")) {
                int idWard = validate.getInt(ward);
                WardDao wardDao = new WardDao();
                List<Ward> wards = wardDao.getWard(idWard);
                System.out.println(idWard);
                PrintWriter out = response.getWriter();
                out.print(this.ward(wards));
            }
        } catch (IOException e) {
            System.out.println("Eror: " + e);
        }

    }

    private String district(List<District> districts) {
        String districtHtml = "<option value=\"\">Choose  a district</option>";
        for (District district : districts) {
            districtHtml += "<option value=\"" + district.getDistrict_id() + "\">" + district.getName() + "</option>";
        }
        return districtHtml;
    }

    private String ward(List<Ward> wards) {
        String wardHtml = "";
        for (Ward ward : wards) {
            wardHtml += "<option value=\"" + ward.getWard_id() + "\">" + ward.getName() + "</option>";
        }
        return wardHtml;
    }

}
