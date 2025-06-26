/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DBConnection.DBConnection;
import Model.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class AccountDao {

    private Connection conn;

    public AccountDao() {
        try {
            conn = DBConnection.connect();
        } catch (Exception e) {
//            handle error here
            conn = null;
        }
    }
//    user

    public int insertAccountUser(Account a) {
        int result = 0;
        String sql = "INSERT INTO Account (email, phone, username, password, role, date, status)"
                + "VALUES(?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, a.getEmail());
            st.setString(2, a.getPhone());
            st.setString(3, a.getUsername());
            st.setString(4, a.getPassword());
            st.setInt(5, a.getRole());
            st.setTimestamp(6, a.getDate());
            st.setInt(7, a.getStatus());
            result = st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Insert account user: " + e);
        }
        return result;
    }

    public List<Account> allAccount() {
        String sql = "select * from Account order by role desc, id desc";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet result = st.executeQuery();
            List<Account> accounts = new ArrayList<>();
            while (result.next()) {
                accounts.add(this.getAccount(result));
            }
            return accounts;
        } catch (SQLException er) {

        }
        return null;
    }

    public List<Account> getAccountByStatus(int status) {
        String sql = "select * from Account where status=?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, status);
            ResultSet result = st.executeQuery();
            List<Account> accounts = new ArrayList<>();
            while (result.next()) {
                accounts.add(this.getAccount(result));
            }
            return accounts;
        } catch (SQLException er) {

        }
        return null;
    }

    public Account getAccountByID(int id) {
        String sql = "select * from Account where id = ?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet result = st.executeQuery();
            if (result.next()) {
                return this.getAccount(result);
            }
        } catch (SQLException er) {
        }
        return null;
    }

    public Account getAccountByUsername(String username) {
        String sql = "select * from Account where username = ?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, username);
            ResultSet result = st.executeQuery();
            if (result.next()) {
                return this.getAccount(result);
            }
        } catch (SQLException er) {
        }
        return null;
    }
    
    public Account getAccountByEmail(String email) {
        String sql = "select * from Account where email = ?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, email);
            ResultSet result = st.executeQuery();
            if (result.next()) {
                return this.getAccount(result);
            }
        } catch (SQLException er) {
        }
        return null;
    }

    private Account getAccount(ResultSet result) {
        try {
            int ID = result.getInt("ID");
            String fullname = result.getString("fullname");
            String username = result.getString("username");
            String email = result.getString("email");
            String phone = result.getString("phone");
            String password = result.getString("password");
            int role = result.getInt("role");
            Timestamp datePost = result.getTimestamp("date");
            int status = result.getInt("status");
            String avatar = result.getString("avatar");
            Account a = new Account(ID, fullname, email, phone, username, password, role, datePost, status, avatar);
            return a;
        } catch (SQLException e) {
            System.out.println("Get account: " + e);
        }
        return null;
    }

    public int insert(Account a) {
        int result = 0;
        String sql = "INSERT INTO Account (fullname, email, phone, username, password, role, date, status) "
                + "VALUES(?, ?, ?, ?, ?, ?, ? ,?)";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, a.getFullname());
            st.setString(2, a.getEmail());
            st.setString(3, a.getPhone());
            st.setString(4, a.getUsername());
            st.setString(5, a.getPassword());
            st.setInt(6, a.getRole());
            st.setTimestamp(7, a.getDate());
            st.setInt(8, a.getStatus());
            result = st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Insert account: " + e);
        }
        return result;
    }

    public int updatePassword(String password, int id) {
        int result = 0;
        String sql = "update Account set password=? where ID=?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, password);
            st.setInt(2, id);
            result = st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Update password account: " + e);
        }
        return result;
    }

    public int updatePersonalUser(Account a) {
        int result = 0;
        String sql = "update Account set fullname=?, email=?, phone=? where ID=?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, a.getFullname());
            st.setString(2, a.getEmail());
            st.setString(3, a.getPhone());
            st.setInt(4, a.getId());
            result = st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Update personal account: " + e);
        }
        return result;
    }

    public int update(Account a) {
        int result = 0;
        String sql = "update Account set fullname=?, email=?, phone=?, username=?,"
                + "password=?, role=?, status=? where ID=?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, a.getFullname());
            st.setString(2, a.getEmail());
            st.setString(3, a.getPhone());
            st.setString(4, a.getUsername());
            st.setString(5, a.getPassword());
            st.setInt(6, a.getRole());
            st.setInt(7, a.getStatus());
            st.setInt(8, a.getId());
            result = st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Update account: " + e);
        }
        return result;
    }

    public int updatePersonal(Account a) {
        int result = 0;
        String sql = "update Account set fullname=?, email=?, phone=?, username=?,"
                + "password=?, role=?, status=?, avatar=? where ID=?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, a.getFullname());
            st.setString(2, a.getEmail());
            st.setString(3, a.getPhone());
            st.setString(4, a.getUsername());
            st.setString(5, a.getPassword());
            st.setInt(6, a.getRole());
            st.setInt(7, a.getStatus());
            st.setString(8, a.getAvatar());
            st.setInt(9, a.getId());
            result = st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Update account: " + e);
        }
        return result;
    }

    public int delete(int id) {
        int result = 0;
        String sql = "delete from Account where id=?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            result = st.executeUpdate();
        } catch (SQLException er) {
            System.out.println("Delete account: " + er);
        }
        return result;
    }
}
