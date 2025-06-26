package DAO;

import DBConnection.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Model.BillDetails;


/**
 *
 * @author Le Tan Kim
 */
public class BillDetailsDao {

    private Connection conn;

    public BillDetailsDao() {
        try {
            conn = DBConnection.connect();
        } catch (Exception e) {
//            handle error here
            conn = null;
        }
    }

    public int addBillDetail(BillDetails b) {
        int result = 0;
        String sql = "insert into billDetail (billID, imgProduct, numberOfProduct, priceProduct, modelProduct, nameProduct, color)"
                + "values (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, b.getBillID());
            st.setString(2, b.getImgProduct());
            st.setInt(3, b.getNumberOfProduct());
            st.setFloat(4, b.getPriceProduct());
            st.setString(5, b.getModelProduct());
            st.setString(6, b.getNameProduct());
            st.setString(7, b.getColor());
            result = st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Add bill detail" + e);
        }
        return result;
    }

    public List<BillDetails> getBillDetailByID(int idBill) {
        List<BillDetails> billDetails = new ArrayList<>();
        String sql = "select * from billDetail where billID=?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, idBill);
            ResultSet result = st.executeQuery();
            while (result.next()) {
                int id = result.getInt("id");
                String imgProduct = result.getString("imgProduct");
                int numberOfProduct = result.getInt("numberOfProduct");
                float priceProduct = result.getFloat("priceProduct");
                String modelProduct = result.getString("modelProduct");
                String nameProduct = result.getString("nameProduct");
                String color = result.getString("color");
                BillDetails billDetail = new BillDetails(id, idBill, imgProduct, numberOfProduct, priceProduct, modelProduct, nameProduct, color);
                billDetails.add(billDetail);
            }
        } catch (SQLException e) {
            System.out.println("Get bill details: " + e);
        }
        return billDetails;
    }

}
