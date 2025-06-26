package DAO;

import DBConnection.DBConnection;
import Model.Banner;
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
public class BannerDao {

    private static Connection conn;

    public BannerDao() {
        try {
            conn = DBConnection.connect();
        } catch (Exception e) {
            conn = null;
        }
    }

    public List<Banner> allBanner() {
        String sql = "select * from Banner order by id desc";
        List<Banner> banners = new ArrayList<>();
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet result = st.executeQuery();
            while (result.next()) {
                banners.add(this.getBanner(result));
            }
        } catch (SQLException e) {
            System.out.println("Get all banner: " + e);
        }
        return banners;
    }

    public List<Banner> getTop5Banner() {
        String sql = "select top 5 * from Banner where status=1 order by id desc";
        List<Banner> banners = new ArrayList<>();
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet result = st.executeQuery();
            while (result.next()) {
                banners.add(this.getBanner(result));
            }
        } catch (SQLException e) {
            System.out.println("Get top 5 banner: " + e);
        }
        return banners;
    }

    public List<Banner> getBannerByStatus(int status) {
        String sql = "select * from Banner where status=? order by id desc";
        List<Banner> banners = new ArrayList<>();
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, status);
            ResultSet result = st.executeQuery();
            while (result.next()) {
                banners.add(this.getBanner(result));
            }
        } catch (SQLException e) {
            System.out.println("Get banner by status: " + e);
        }
        return banners;
    }

    public Banner currentBanner(int id) {
        String sql = "select * from Banner where ID=?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet result = st.executeQuery();
            if (result.next()) {
                return this.getBanner(result);
            }
        } catch (SQLException er) {
            System.out.println("Get current banner: " + er);
        }
        return null;
    }

    private Banner getBanner(ResultSet result) {
        try {
            int ID = result.getInt("ID");
            String img = result.getString("img");
            int status = result.getInt("status");
            Timestamp datePost = result.getTimestamp("datePost");
            Timestamp dateUpdate = result.getTimestamp("dateUpdate");
            Banner b = new Banner(ID, img, status, datePost, dateUpdate);
            return b;
        } catch (SQLException e) {
            System.out.println("Get banner: " + e);
        }
        return null;
    }

    public int insert(Banner b) {
        int result = 0;
        String sql = "insert into Banner (img, status, datePost) values (?, ?, ?)";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, b.getImg());
            st.setInt(2, b.getStatus());
            st.setTimestamp(3, b.getDatePost());
            result = st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Insert banner: " + e);
        }
        return result;
    }

    public int update(Banner b) {
        int result = 0;
        String sql = "update Banner set img=?, status=?, dateUpdate=? where ID=?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, b.getImg());
            st.setInt(2, b.getStatus());
            st.setTimestamp(3, b.getDateUpdate());
            st.setInt(4, b.getID());
            result = st.executeUpdate();
        } catch (SQLException er) {
            System.out.println("Update banner: " + er);
        }
        return result;
    }

    public int delete(int id) {
        int result = 0;
        String sql = "delete from Banner where ID=?";
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
