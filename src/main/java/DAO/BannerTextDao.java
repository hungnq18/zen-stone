/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DBConnection.DBConnection;
import Model.BannerText;
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
public class BannerTextDao {

    private static Connection conn;

    public BannerTextDao() {
        try {
            conn = DBConnection.connect();
        } catch (Exception e) {
            conn = null;
        }
    }

    public List<BannerText> allBannerText() {
        String sql = "select * from BannerText";
        List<BannerText> banners = new ArrayList<>();
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet result = st.executeQuery();
            while (result.next()) {
                banners.add(this.getBannerText(result));
            }
        } catch (SQLException e) {
            System.out.println("Get all banner text: " + e);
        }
        return banners;
    }

    public List<BannerText> getTop5BannerText() {
        String sql = "select top 5 * from BannerText where status=1 order by id desc";
        List<BannerText> banners = new ArrayList<>();
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet result = st.executeQuery();
            while (result.next()) {
                banners.add(this.getBannerText(result));
            }
        } catch (SQLException e) {
            System.out.println("Get top 5 banner: " + e);
        }
        return banners;
    }

    public List<BannerText> getBannerTextByStatus(int status) {
        String sql = "select * from BannerText where status=? order by id desc";
        List<BannerText> banners = new ArrayList<>();
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, status);
            ResultSet result = st.executeQuery();
            while (result.next()) {
                banners.add(this.getBannerText(result));
            }
        } catch (SQLException e) {
            System.out.println("Get banner by status: " + e);
        }
        return banners;
    }

    public BannerText currentBannerText(int id) {
        String sql = "select * from BannerText where ID=?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet result = st.executeQuery();
            if (result.next()) {
                return this.getBannerText(result);
            }
        } catch (SQLException er) {
            System.out.println("Get current banner: " + er);
        }
        return null;
    }

    private BannerText getBannerText(ResultSet result) {
        try {
            int ID = result.getInt("ID");
            String img = result.getString("img");
            int status = result.getInt("status");
            String title = result.getString("title");
            String description = result.getString("description");
            Timestamp datePost = result.getTimestamp("datePost");
            Timestamp dateUpdate = result.getTimestamp("dateUpdate");
            BannerText b = new BannerText(ID, img, title, description, status, datePost, dateUpdate);
            return b;
        } catch (SQLException e) {
            System.out.println("Get banner text: " + e);
        }
        return null;
    }

    public int insert(BannerText b) {
        int result = 0;
        String sql = "insert into BannerText (img, title, description, status, datePost) values (?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, b.getImg());
            st.setString(2, b.getTitle());
            st.setString(3, b.getDescription());
            st.setInt(4, b.getStatus());
            st.setTimestamp(5, b.getDatePost());
            result = st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Insert banner: " + e);
        }
        return result;
    }

    public int update(BannerText b) {
        int result = 0;
        String sql = "update BannerText set img=?, title=?, description=?,status=?, dateUpdate=? where ID=?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, b.getImg());
            st.setString(2, b.getTitle());
            st.setString(3, b.getDescription());
            st.setInt(4, b.getStatus());
            st.setTimestamp(5, b.getDatePost());
            st.setInt(6, b.getID());
            result = st.executeUpdate();
        } catch (SQLException er) {
            System.out.println("Update banner: " + er);
        }
        return result;
    }

    public int delete(int id) {
        int result = 0;
        String sql = "delete from BannerText where ID=?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            result = st.executeUpdate();
        } catch (SQLException er) {
            System.err.println("Delete banner: " + er);
        }
        return result;
    }
}
