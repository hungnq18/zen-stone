/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DBConnection.DBConnection;
import Model.Company;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Le Tan Kim
 */
public class CompanyDao {

    private Connection conn;

    public CompanyDao() {
        try {
            conn = DBConnection.connect();
        } catch (Exception e) {
//            handle error here
            conn = null;
        }
    }

//  user
    public Company getTop1CompanyActive() {
        String sql = "select top 1* from company where status=1 order by id desc";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet result = st.executeQuery();
            if (result.next()) {
                return this.getCompany(result);
            }
        } catch (SQLException e) {
            System.out.println("Get company by active: " + e);
        }
        return null;
    }
//  admin
    public List<Company> getAllCompany() {
        List<Company> company = new ArrayList<>();
        String sql = "select * from company order by id desc";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet result = st.executeQuery();
            while (result.next()) {
                company.add(this.getCompany(result));
            }
        } catch (SQLException e) {
            System.out.println("Get all company: " + e);
        }
        return company;
    }

    public List<Company> getCompanyByStatus(int status) {
        List<Company> company = new ArrayList<>();
        String sql = "select * from company where status = ?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, status);
            ResultSet result = st.executeQuery();
            while (result.next()) {
                company.add(this.getCompany(result));
            }
        } catch (SQLException e) {
            System.out.println("Get company by status: " + e);
        }
        return company;
    }

    public Company getCompanyById(int id) {
        String sql = "select * from company where id=?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet result = st.executeQuery();
            if (result.next()) {
                return this.getCompany(result);
            }
        } catch (SQLException e) {
            System.out.println("Get company by id: " + e);
        }
        return null;
    }

    private Company getCompany(ResultSet result) {
        try {
            int id = result.getInt("id");
            String name = result.getString("name");
            String address = result.getString("address");
            String phone = result.getString("phone");
            String email = result.getString("email");
            String introduce = result.getString("introduce");
            Timestamp date = result.getTimestamp("date");
            int status = result.getInt("status");
            Company c = new Company(id, name, address, phone, email, introduce, date, status);
            return c;
        } catch (SQLException e) {
            System.out.println("Get company: " + e);
        }
        return null;
    }

    public int insert(Company c) {
        int result = 0;
        String sql = "insert into company values (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, c.getName());
            st.setString(2, c.getAddress());
            st.setString(3, c.getPhone());
            st.setString(4, c.getEmail());
            st.setString(5, c.getIntroduce());
            st.setTimestamp(6, c.getDate());
            st.setInt(7, c.getStatus());
            result = st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Insert company: " + e);
        }
        return result;
    }

    public int update(Company c) {
        int result = 0;
        String sql = "update company set name=?, address=?, phone=?, email=?, introduce=?, date=?, status=? where id=?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, c.getName());
            st.setString(2, c.getAddress());
            st.setString(3, c.getPhone());
            st.setString(4, c.getEmail());
            st.setString(5, c.getIntroduce());
            st.setTimestamp(6, c.getDate());
            st.setInt(7, c.getStatus());
            st.setInt(8, c.getId());
            result = st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Update company: " + e);
        }
        return result;
    }

    public int delete(int id) {
        int result = 0;
        String sql = "delete from company where id=?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            result = st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Delete company: " + e);
        }
        return result;
    }
}
