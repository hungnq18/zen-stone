package DAO;

import DBConnection.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Model.Category;
import java.sql.Timestamp;

/**
 *
 * @author Le Tan Kim
 */
public class CategoryDao {

    private Connection conn;

    public CategoryDao() {
        try {
            conn = DBConnection.connect();
        } catch (Exception e) {
//            handle error here
            conn = null;
        }
    }
//  user

    public List<Category> getCategoryNumberByStatus() {
        String sql = "select c.id, c.name, COALESCE(COUNT(p.id), 0) as [numberOfProduct] from Category as c left join Product as p "
                + "on p.categoryId = c.id and p.status = 1 where c.status = 1 group by c.id, c.name order by [numberOfProduct] desc";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet result = st.executeQuery();
            List<Category> categories = new ArrayList<>();
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                int numberOfProduct = result.getInt("numberOfProduct");
                Category c = new Category(id, name, null, null, 1, "");
                c.setNumberOfProduct(numberOfProduct);
                categories.add(c);
            }
            return categories;
        } catch (SQLException er) {

        }
        return null;
    }
//  admin

    public List<Category> allCategory() {
        String sql = "select * from Category order by id desc";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet result = st.executeQuery();
            List<Category> categories = new ArrayList<>();
            while (result.next()) {
                categories.add(this.getCategory(result));
            }
            return categories;
        } catch (SQLException er) {

        }
        return null;
    }

    public int getNumberProductByCategory(int id) {
        String sql = "select count(id) as numberProduct from Product where categoryID =?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet result = st.executeQuery();
            if (result.next()) {
                return result.getInt("numberProduct");
            }
            return 0;
        } catch (SQLException e) {
             System.out.println("Get number product by category: "  + e);
        }
        return 0;
    }

    public List<Category> getCategoryByStatus(int status) {
        String sql = "select * from Category  where status=?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, status);
            ResultSet result = st.executeQuery();
            List<Category> categories = new ArrayList<>();
            while (result.next()) {
                categories.add(this.getCategory(result));
            }
            return categories;
        } catch (SQLException er) {

        }
        return null;
    }

    public Category currentCategory(String slug) {
        String sql = "select * from Category where slug = ?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, slug);
            ResultSet result = st.executeQuery();
            if (result.next()) {
                return this.getCategory(result);
            }
        } catch (SQLException er) {
        }
        return null;
    }

    public Category getCategoryByID(int id) {
        String sql = "select * from Category where id = ?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet result = st.executeQuery();
            if (result.next()) {
                return this.getCategory(result);
            }
        } catch (SQLException er) {
        }
        return null;
    }

    public Category getCategoryBySlug(String slug) {
        String sql = "select * from Category where slug = ?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, slug);
            ResultSet result = st.executeQuery();
            if (result.next()) {
                return this.getCategory(result);
            }
        } catch (SQLException er) {
        }
        return null;
    }

    private Category getCategory(ResultSet result) {
        try {
            int ID = result.getInt("ID");
            String name = result.getString("name");
            Timestamp datePost = result.getTimestamp("datePost");
            Timestamp dateUpdate = result.getTimestamp("dateUpdate");
            String slugGet = result.getString("slug");
            int status = result.getInt("status");
            Category c = new Category(ID, name, datePost, dateUpdate, status, slugGet);
            return c;
        } catch (SQLException e) {
            System.out.println("Get category: " + e);
        }
        return null;
    }

    public int insert(Category c) {
        int result = 0;
        String sql = "INSERT INTO Category (name, datePost, status, slug) VALUES(?, ?, ?, ?)";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, c.getName());
            st.setTimestamp(2, c.getDatePost());
            st.setInt(3, c.getStatus());
            st.setString(4, c.getSlug());
            result = st.executeUpdate();
        } catch (SQLException e) {

        }
        return result;
    }

    public int update(Category c) {
        int result = 0;
        String sql = "update Category set  name=?, dateUpdate=?, slug=?, status=? where ID=?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, c.getName());
            st.setTimestamp(2, c.getDateUpdate());
            st.setString(3, c.getSlug());
            st.setInt(4, c.getStatus());
            st.setInt(5, c.getID());
            result = st.executeUpdate();
        } catch (SQLException e) {

        }
        return result;
    }

    public int delete(String slug) {
        int result = 0;
        String sql = "delete from Category where slug=?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, slug);
            result = st.executeUpdate();
        } catch (SQLException er) {

        }
        return result;
    }
}
