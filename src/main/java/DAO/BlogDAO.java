/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author HP
 */
import Model.Blog;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BlogDAO {

    private Connection conn;

    public BlogDAO() {
        try {
            conn = DBConnection.DBConnection.connect();
        } catch (Exception e) {
            System.out.println("Connection fail: " + e);
        }
    }

    public List<Blog> getAllBlogActive() {
        String sql = "select * from Blog as B join Category as C on C.ID = B.categoryID"
                + " where B.status = 1 and C.status=1 order by B.ID desc";
        List<Blog> blogs = new ArrayList<>();
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet result = st.executeQuery();
            while (result.next()) {
                blogs.add(getBlog(result));
            }
        } catch (SQLException e) {
            System.out.println("Get all blogs: " + e);
        }
        return blogs;
    }

    public Blog getBlogByIdActive(int id) {
        String sql = "select * from Blog as B join Category as C on C.ID = B.categoryID"
                + " where B.status = 1 and C.status=1 and B.ID = ?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet result = st.executeQuery();
            if (result.next()) {
                return getBlog(result);
            }
        } catch (SQLException e) {
            System.out.println("Get blog by ID: " + e);
        }
        return null;
    }

    public List<Blog> getBlogByCategoryActive(int id) {
        String sql = "select * from Blog as B join Category as C on C.ID = B.categoryID"
                + " where B.status = 1 and C.status=1 and C.ID = ?";
        List<Blog> blogs = new ArrayList<>();
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet result = st.executeQuery();
            while (result.next()) {
                blogs.add(this.getBlog(result));
            }
        } catch (SQLException e) {
            System.out.println("Get blog by category: " + e);
        }
        return blogs;
    }

    public List<Blog> getBlogByKeyActive(String key) {
        String sql = "select * from Blog as B join Category as C on C.ID = B.categoryID"
                + " where B.status = 1 and C.status=1 and B.title like ?";
        List<Blog> blogs = new ArrayList<>();
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, "%" + key + "%");
            ResultSet result = st.executeQuery();
            while (result.next()) {
                blogs.add(this.getBlog(result));
            }
        } catch (SQLException e) {
            System.out.println("Get blog by category: " + e);
        }
        return blogs;
    }

    public List<Blog> getAllBlog() {
        String sql = "select * from Blog order by ID desc";
        List<Blog> blogs = new ArrayList<>();
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet result = st.executeQuery();
            while (result.next()) {
                blogs.add(getBlog(result));
            }
        } catch (SQLException e) {
            System.out.println("Get all blogs: " + e);
        }
        return blogs;
    }

    private Blog getBlog(ResultSet result) throws SQLException {
        int ID = result.getInt("ID");
        String title = result.getString("title");
        String shortDesc = result.getString("shortDesc");
        String description = result.getString("description");
        String image = result.getString("image");
        Timestamp datePost = result.getTimestamp("datePost");
        Timestamp dateUpdate = result.getTimestamp("dateUpdate");
        int status = result.getInt("status");
        int view = result.getInt("view");
        int categoryID = result.getInt("categoryID");

        return new Blog(ID, title, shortDesc, description, image, datePost, dateUpdate, status, view, categoryID);
    }

    // Create
    public int insert(Blog blog) {
        String sql = "INSERT INTO Blog (title, shortDesc, description, image, datePost "
                + ", status, [view], categoryID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            int i = 1;
            st.setString(i++, blog.getTitle());
            st.setString(i++, blog.getShortDesc());
            st.setString(i++, blog.getDescription());
            st.setString(i++, blog.getImage());
            st.setTimestamp(i++, blog.getDatePost());
            st.setInt(i++, blog.getStatus());
            st.setInt(i++, blog.getView());
            st.setInt(i++, blog.getCategoryID());
            int rowsInserted = st.executeUpdate();
            return rowsInserted;
        } catch (SQLException e) {
            System.out.println("Insert blog: " + e);
            return 0;
        }
    }

    public Blog getBlogById(int id) {
        String sql = "SELECT * FROM Blog WHERE ID = ?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet result = st.executeQuery();
            if (result.next()) {
                return getBlog(result);
            }
        } catch (SQLException e) {
            System.out.println("Get blog by ID: " + e);
        }
        return null;
    }

    // Update
    public int update(Blog blog) {
        String sql = "UPDATE Blog SET title = ?, shortDesc=?, description = ?, image = ?, dateUpdate = ?, status = ?, categoryID = ? WHERE ID = ?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            int i = 1;
            st.setString(i++, blog.getTitle());
            st.setString(i++, blog.getShortDesc());
            st.setString(i++, blog.getDescription());
            st.setString(i++, blog.getImage());
            st.setTimestamp(i++, blog.getDateUpdate());
            st.setInt(i++, blog.getStatus());
            st.setInt(i++, blog.getCategoryID());
            st.setInt(i++, blog.getID());
            int rowsUpdated = st.executeUpdate();
            return rowsUpdated;
        } catch (SQLException e) {
            System.out.println("Update blog: " + e);
            return 0;
        }
    }

    // Delete
    public int deleteBlog(int id) {
        String sql = "DELETE FROM Blog WHERE ID = ?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            int rowsDeleted = st.executeUpdate();
            return rowsDeleted;
        } catch (SQLException e) {
            System.out.println("Delete blog: " + e);
            return 0;
        }
    }
}
