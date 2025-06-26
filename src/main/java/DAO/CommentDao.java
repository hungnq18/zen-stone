package DAO;

import DBConnection.DBConnection;
import Model.Comment;
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
public class CommentDao {

    private Connection conn;

    public CommentDao() {
        try {
            conn = DBConnection.connect();
        } catch (Exception e) {
//            handle error here
            conn = null;
        }
    }

    public List<Comment> allAccountByProduct(int idProduct) {
        String sql = "select * from comment where status = 1 and productId=? order by id desc";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, idProduct);
            ResultSet result = st.executeQuery();
            List<Comment> comments = new ArrayList<>();
            while (result.next()) {
                int id = result.getInt("id");
                int userId = result.getInt("userId");
                int productId = result.getInt("productId");
                String comment = result.getString("comment");
                Timestamp datePost = result.getTimestamp("datePost");
                Timestamp dateUpdate = result.getTimestamp("dateUpdate");
                Comment c = new Comment(id, userId, productId, comment, userId, datePost, dateUpdate);
                comments.add(c);
            }
            return comments;
        } catch (SQLException er) {
            System.out.println("Get all comment: " + er);
        }
        return null;
    }

    public List<Comment> allCommentByProduct(int idProduct) {
        String sql = "select * from comment where productId=? order by id desc";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, idProduct);
            ResultSet result = st.executeQuery();
            List<Comment> comments = new ArrayList<>();
            while (result.next()) {
                int id = result.getInt("id");
                int userId = result.getInt("userId");
                int productId = result.getInt("productId");
                String comment = result.getString("comment");
                int status = result.getInt("status");
                Timestamp datePost = result.getTimestamp("datePost");
                Timestamp dateUpdate = result.getTimestamp("dateUpdate");
                Comment c = new Comment(id, userId, productId, comment, status, datePost, dateUpdate);
                comments.add(c);
            }
            return comments;
        } catch (SQLException er) {
            System.out.println("Get all comment: " + er);
        }
        return null;
    }

    public List<Comment> allCommentActive(int status) {
        String sql = "select * from comment where status = ? order by id desc";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, status);
            ResultSet result = st.executeQuery();
            List<Comment> comments = new ArrayList<>();
            while (result.next()) {
                int id = result.getInt("id");
                int userId = result.getInt("userId");
                int productId = result.getInt("productId");
                String comment = result.getString("comment");
                Timestamp datePost = result.getTimestamp("datePost");
                Timestamp dateUpdate = result.getTimestamp("dateUpdate");
                Comment c = new Comment(id, userId, productId, comment, userId, datePost, dateUpdate);
                comments.add(c);
            }
            return comments;
        } catch (SQLException er) {
            System.out.println("Get all comment by active: " + er);
        }
        return null;
    }

    public int insertComment(Comment c) {
        String sql = "insert into comment (userId, productId, comment, status, datePost)"
                + "values(?, ?, ?, ? ,?)";
        int result = 0;
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, c.getUserID());
            st.setInt(2, c.getProductID());
            st.setString(3, c.getComment());
            st.setInt(4, c.getStatus());
            st.setTimestamp(5, c.getDatePost());
            result = st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Insert comment: " + e);
        }
        return result;
    }

    public int updateStatusComment(int status, int id) {
        String sql = "update comment set status = ? where id=?";
        int result = 0;
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, status);
            st.setInt(2, id);
            result = st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Update status comment: " + e);
        }
        return result;
    }

    public int updateComment(Comment c) {
        String sql = "update comment set comment = ?, dateUpdate=?, status=? where id=?";
        int result = 0;
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, c.getComment());
            st.setTimestamp(2, c.getDateUpdate());
            st.setInt(3, c.getStatus());
            st.setInt(4, c.getId());
            result = st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Update comment: " + e);
        }
        return result;
    }

    public int deleteComment(int id) {
        String sql = "delete comment where id=?";
        int result = 0;
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            result = st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Delete comment: " + e);
        }
        return result;
    }
}
