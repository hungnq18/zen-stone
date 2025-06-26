package DAO;

import DBConnection.DBConnection;
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
public class ProvinceDao {

    private Connection conn;

    public ProvinceDao() {
        try {
            conn = DBConnection.connect();
        } catch (Exception e) {
//            handle error here
            conn = null;
        }
    }

    public List<Province> getProvinces() {
        String sql = "select * from province order by name asc";
        List<Province> provines = new ArrayList<>();
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet result = st.executeQuery();
            while (result.next()) {
                int id = result.getInt("province_id");
                String name = result.getString("name");
                Province p = new Province(id, name);
                provines.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Provinces: " + e);
        }
        return provines;
    }
    
    public Province getProvinceByID(int id) {
        String sql = "select * from province where province_id =?";
        List<Province> provines = new ArrayList<>();
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet result = st.executeQuery();
            if (result.next()) {
                String name = result.getString("name");
                Province p = new Province(id, name);
                return p;
            }
        } catch (SQLException e) {
            System.out.println("Provinces: " + e);
        }
        return null;
    }

    public Province getById(int provinceId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
