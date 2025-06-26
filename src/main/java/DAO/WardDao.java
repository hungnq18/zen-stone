package DAO;

import DBConnection.DBConnection;
import Model.District;
import Model.Province;
import Model.Ward;
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
public class WardDao {

    private Connection conn;

    public WardDao() {
        try {
            conn = DBConnection.connect();
        } catch (Exception e) {
//            handle error here
            conn = null;
        }
    }

    public List<Ward> getWard(int idDistrict) {
        String sql = "select * from wards where district_id = ? order by name asc";
        List<Ward> wards = new ArrayList<>();
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, idDistrict);
            ResultSet result = st.executeQuery();
            while (result.next()) {
                int idWard = result.getInt("wards_id");
                String name = result.getString("name");
                Ward w = new Ward(idWard, idDistrict, name);
                wards.add(w);
            }
        } catch (SQLException e) {
            System.out.println("Ward: " + e);
        }
        return wards;
    }
    
    public Ward getWardByID(int idDistrict) {
        String sql = "select * from wards where wards_id = ?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, idDistrict);
            ResultSet result = st.executeQuery();
            if (result.next()) {
                int idWard = result.getInt("wards_id");
                String name = result.getString("name");
                Ward w = new Ward(idWard, idDistrict, name);
                return w;
            }
        } catch (SQLException e) {
            System.out.println("Ward: " + e);
        }
        return null;
    }

    public Ward getById(int wardId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
