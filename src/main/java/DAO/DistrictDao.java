package DAO;

import DBConnection.DBConnection;
import Model.District;
import Model.Province;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Le Tan Kim
 */
public class DistrictDao {

    private Connection conn;

    public DistrictDao() {
        try {
            conn = DBConnection.connect();
        } catch (Exception e) {
//            handle error here
            conn = null;
        }
    }

    public List<District> getDistrict(int idProvince) {
        String sql = "select * from district where province_id = ? order by name asc";
        List<District> district = new ArrayList<>();
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, idProvince);
            ResultSet result = st.executeQuery();
            while (result.next()) {
                int idDistrict = result.getInt("district_id");
                String name = result.getString("name");
                District d = new District(idDistrict, idProvince, name);
                district.add(d);
            }
        } catch (SQLException e) {
            System.out.println("Provinces: " + e);
        }
        return district;
    }

    public District getDistrictByID(int idProvince) {
        String sql = "select * from district where district_id = ?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, idProvince);
            ResultSet result = st.executeQuery();
            if (result.next()) {
                int idDistrict = result.getInt("district_id");
                String name = result.getString("name");
                District d = new District(idDistrict, idProvince, name);
                return d;
            }
        } catch (SQLException e) {
            System.out.println("Provinces: " + e);
        }
        return null;
    }

    public District getById(int districtId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
