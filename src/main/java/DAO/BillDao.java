package DAO;

import DBConnection.DBConnection;
import Model.Bill;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Le Tan Kim
 */
public class BillDao {

    private Connection conn;

    public BillDao() {
        try {
            conn = DBConnection.connect();
        } catch (Exception e) {
//            handle error here
            conn = null;
        }
    }
//  user

    public int addBill(Bill b) {
        int result = 0;
        String sql = "";
        if (b.getCustomerID() == -1) {
            sql = "insert into bill (email, customerName, phone, address, detailAddress,total, status, payment, dateOrder, transactionCode)"
                    + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        } else {
            sql = "insert into bill (customerID,email, customerName, phone, address, detailAddress,total, status, payment, dateOrder,transactionCode)"
                    + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        }
        try {
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int index = 1;
            if (b.getCustomerID() != -1) {
                st.setInt(index++, b.getCustomerID());
            }
            st.setString(index++, b.getEmail());
            st.setString(index++, b.getCustomerName());
            st.setString(index++, b.getPhone());
            st.setString(index++, b.getAddress());
            st.setString(index++, b.getDetailAddress());
            st.setFloat(index++, b.getTotal());
            st.setInt(index++, b.getStatus());
            st.setInt(index++, b.getPayment());
            st.setTimestamp(index++, b.getDateOrder());
            st.setString(index++, b.getTransactionCode());
            result = st.executeUpdate();
            if (result > 0) {
                try ( ResultSet generatedKeys = st.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);
                        return id;
                    }
                } catch (SQLException e) {

                }
            }
        } catch (SQLException e) {
            System.out.println("Add bill" + e);
        }
        return 0;
    }
//  admin

    public List<Bill> getAllBill() {
        List<Bill> bills = new ArrayList<>();
        String sql = "select * from bill order by id desc";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet result = st.executeQuery();
            while (result.next()) {
                bills.add(this.getBill(result));
            }
        } catch (SQLException e) {
            System.out.println("Select all bill: " + e);
        }
        return bills;
    }

    public List<Bill> getAllBillByDate(Date from, Date to) {
        List<Bill> bills = new ArrayList<>();
        String sql = "select * from bill where (dateOrder >= ? AND dateOrder <= ?) OR (dateOrder >= ? AND dateOrder <= ?)";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setDate(1, from);
            st.setDate(2, to);
            st.setDate(3, to);
            st.setDate(4, from);
            ResultSet result = st.executeQuery();
            while (result.next()) {
                bills.add(this.getBill(result));
            }
        } catch (SQLException e) {
            System.out.println("Select all bill: " + e);
        }
        return bills;
    }

    public List<Bill> getBillByStatus(int status) {
        List<Bill> bills = new ArrayList<>();
        String sql = "select * from bill where status=? order by id desc";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, status);
            ResultSet result = st.executeQuery();
            while (result.next()) {
                bills.add(this.getBill(result));
            }
        } catch (SQLException e) {
            System.out.println("Select bill by status: " + e);
        }
        return bills;
    }

    public List<Bill> getBillByCustomer(int id) {
        List<Bill> bills = new ArrayList<>();
        String sql = "select * from bill where customerID=? order by id desc";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet result = st.executeQuery();
            while (result.next()) {
                bills.add(this.getBill(result));
            }
        } catch (SQLException e) {
            System.out.println("Select bill by customer id: " + e);
        }
        return bills;
    }

    public Bill getBillByID(int id) {
        String sql = "select * from bill where id=?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet result = st.executeQuery();
            if (result.next()) {
                return this.getBill(result);
            }
        } catch (SQLException e) {
            System.out.println("Select all bill: " + e);
        }
        return null;
    }

    private Bill getBill(ResultSet result) {
        try {
            int id = result.getInt("id");
            int customerID = result.getInt("customerID");
            String email = result.getString("email");
            String customerName = result.getString("customerName");
            String phone = result.getString("phone");
            String address = result.getString("address");
            String detailAddress = result.getString("detailAddress");
            float total = result.getFloat("total");
            int status = result.getInt("status");
            int payment = result.getInt("payment");
            Timestamp dateOrder = result.getTimestamp("dateOrder");
            Timestamp dateUpdate = result.getTimestamp("dateUpdate");
            String transactionCode = result.getString("transactionCode");
            Bill bill = new Bill(id, customerID, email, customerName, phone, address, detailAddress, total, status, payment, dateOrder, dateUpdate, transactionCode);
            return bill;
        } catch (SQLException e) {
            System.out.println("Get bill: " + e);
        }
        return null;
    }

    public int updateStatus(int id, int status, Timestamp dateUpdate) {
        int result = 0;
        String sql = "update bill set status=?, dateUpdate=? where id=?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, status);
            st.setTimestamp(2, dateUpdate);
            st.setInt(3, id);
            result = st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Update status: " + e);
        }
        return result;
    }

    public int deleteBill(int id) {
        int result = 0;
        String sql = "delete from bill where id=?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            result = st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Delete bill: " + e);
        }
        return result;
    }

    public void insert(Bill bill) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
