package DAO;

import DBConnection.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import Model.Product;
import java.sql.Timestamp;

/**
 *
 * @author Le Tan Kim
 */
public class ProductDao {

    private static Connection conn;

    public ProductDao() {
        try {
            conn = DBConnection.connect();
            if (conn == null) {
                System.out.println("[ERROR] ProductDao: DBConnection.connect() trả về null. Không thể kết nối database!");
            } else {
                System.out.println("[INFO] ProductDao: Kết nối database thành công.");
            }
        } catch (Exception e) {
            conn = null;
            System.out.println("[ERROR] ProductDao: Lỗi khi kết nối database!");
            e.printStackTrace();
        }
    }
//  user

    public List<Product> filterProduct(int[] idCategory, float from, float to, int time) {
        List<Product> products = new ArrayList<>();
        String sql = "select pro.* from product as pro join category as c on c.id = pro.categoryID "
                + "join producer as p on p.id = pro.producerID where pro.status = 1 and "
                + "p.status=1 and c.status=1 ";
        int i = 0;
        for (int id : idCategory) {
            if (idCategory.length - 1 == 0) {
                sql += " and pro.categoryId = ? ";
                break;
            } else if (i == 0) {
                sql += "and (pro.categoryId = ? ";
            } else if (i == idCategory.length - 1) {
                sql += "or pro.categoryId = ? ) ";
            } else {
                sql += "or pro.categoryId = ? ";
            }
            i++;
        }
        sql += "and ((pro.newPrice >= ?  and pro.newPrice <= ?) or (pro.oldPrice >= ?  and pro.oldPrice <= ?)) ";
        System.out.println("sql: " + sql);
        if (time == 0) {
            sql += "order by id asc";
        } else {
            sql += "order by id desc";
        }
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            int index = 1;
            for (int id : idCategory) {
                st.setInt(index++, id);
            }
            st.setFloat(index++, from);
            st.setFloat(index++, to);
            st.setFloat(index++, from);
            st.setFloat(index++, to);
            ResultSet result = st.executeQuery();
            while (result.next()) {
                products.add(this.getProduct(result));
            }
        } catch (SQLException e) {
            System.out.println("Get priority product: " + e);
        }
        return products;
    }

    public Product statusIsActive(String slug) {
        String sql = "select p.* from Product as p join Producer "
                + "as pr on p.producerID = pr.ID join "
                + "Category as Ca on p.categoryID =Ca.ID "
                + "where p.slug = ? "
                + "and p.status =1 and pr.status=1 and Ca.status =1";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, slug);
            ResultSet result = st.executeQuery();
            if (result.next()) {
                Product product = this.getProduct(result);
                return product;
            }
        } catch (SQLException e) {
            System.out.println("Product item: " + e);
        }
        return null;
    }

    public List<Product> seachProduct(String keyword) {
        List<Product> products = new ArrayList<>();
        String sql = "select pro.* from product as pro join category as c on c.id = pro.categoryID "
                + "join producer as p on p.id = pro.producerID where pro.status = 1 and "
                + "p.status=1 and c.status=1 and pro.name like ? order by id desc ";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, "%" + keyword + "%");
            ResultSet result = st.executeQuery();
            while (result.next()) {
                products.add(this.getProduct(result));
            }
        } catch (SQLException e) {
            System.out.println("Get priority product: " + e);
        }
        return products;
    }

    public List<Product> seachProduct(String keyword, int page, int pageSize) {
        List<Product> products = new ArrayList<>();
        String sql = "select pro.* from product as pro join category as c on c.id = pro.categoryID "
                + "join producer as p on p.id = pro.producerID where pro.status = 1 and "
                + "p.status=1 and c.status=1 and pro.name like ? order by id desc "
                + "OFFSET ? ROWS "
                + "FETCH FIRST ? ROWS ONLY";
        int offset = (page - 1) * pageSize;
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, "%" + keyword + "%");
            st.setInt(2, offset);
            st.setInt(3, pageSize);
            ResultSet result = st.executeQuery();
            while (result.next()) {
                products.add(this.getProduct(result));
            }
        } catch (SQLException e) {
            System.out.println("Get search product: " + e);
        }
        return products;
    }

    public List<Product> getProductByPriority(int status) {
        List<Product> products = new ArrayList<>();
        String sql = "select pro.* from product as pro join category as c on c.id = pro.categoryID"
                + " join producer as p on p.id = pro.producerID where pro.status = 1 and "
                + "pro.priority=? and p.status=1 and c.status=1 order by id desc";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, status);
            ResultSet result = st.executeQuery();
            while (result.next()) {
                products.add(this.getProduct(result));
            }
        } catch (SQLException e) {
            System.out.println("Get priority product: " + e);
        }
        return products;
    }

    public List<Product> getProductsCategoryByPage(String slugCategory, int page, int pageSize) {
        List<Product> products = new ArrayList<>();
        String sql = "select pro.* from product as pro join category as c on c.id = pro.categoryID "
                + "join producer as p on p.id = pro.producerID where c.slug=? and pro.status = 1 and "
                + "p.status=1 and c.status=1 order by id desc "
                + "OFFSET ? ROWS "
                + "FETCH FIRST ? ROWS ONLY";
        int offset = (page - 1) * pageSize;
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, slugCategory);
            st.setInt(2, offset);
            st.setInt(3, pageSize);
            ResultSet result = st.executeQuery();
            while (result.next()) {
                products.add(this.getProduct(result));
            }
        } catch (SQLException e) {
            System.out.println("get product category page: " + e);
        }
        return products;
    }

    public List<Product> getProductsByPage(int page, int pageSize) {
        List<Product> products = new ArrayList<>();
        String sql = "select pro.* from product as pro join category as c on c.id = pro.categoryID "
                + "join producer as p on p.id = pro.producerID where pro.status = 1 and "
                + "p.status=1 and c.status=1 order by id desc "
                + "OFFSET ? ROWS "
                + "FETCH FIRST ? ROWS ONLY";
        int offset = (page - 1) * pageSize;
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, offset);
            st.setInt(2, pageSize);
            ResultSet result = st.executeQuery();
            while (result.next()) {
                products.add(this.getProduct(result));
            }
        } catch (SQLException e) {
            System.out.println("get product page: " + e);
        }
        return products;
    }

    public List<Product> getAllProductActive(String... category) {
        String slugCategory = String.join("", category);
        List<Product> products = new ArrayList<>();
        String sql = "select pro.* from product as pro join category as c on c.id = pro.categoryID"
                + " join producer as p on p.id = pro.producerID where pro.status = 1 and "
                + "p.status=1 and c.status=1";
        if (!slugCategory.equals("")) {
            sql += " and c.slug =?";
        }
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            if (!slugCategory.equals("")) {
                st.setString(1, slugCategory);
            }
            ResultSet result = st.executeQuery();
            while (result.next()) {
                products.add(this.getProduct(result));
            }
        } catch (SQLException e) {
        }
        return products;
    }
//  admin

    public List<Product> getAllProduct() {
        List<Product> products = new ArrayList<>();
        String sql = "select * from product order by id desc";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet result = st.executeQuery();
            while (result.next()) {
                products.add(this.getProduct(result));
            }
        } catch (SQLException e) {
        }
        return products;
    }

    public List<Product> getTopFiveProduct() {
        List<Product> products = new ArrayList<>();
        String sql = "select top 5 * from product order by sold desc";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet result = st.executeQuery();
            while (result.next()) {
                products.add(this.getProduct(result));
            }
        } catch (SQLException e) {
        }
        return products;
    }

    public List<Product> getProductByStatus(int status) {
        List<Product> products = new ArrayList<>();
        String sql = "select * from product where status=?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, status);
            ResultSet result = st.executeQuery();
            while (result.next()) {
                products.add(this.getProduct(result));
            }
        } catch (SQLException e) {
        }
        return products;
    }

    public List<Product> getProductByLikeName(String nameInput) {
        List<Product> products = new ArrayList<>();
        String sql = "select * from product where name like ?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, "%" + nameInput + "%");
            ResultSet result = st.executeQuery();
            while (result.next()) {
                products.add(this.getProduct(result));
            }
        } catch (SQLException e) {
        }
        return products;
    }

    public Product getProductByID(int id) {
        String sql = "select * from Product where id=?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet result = st.executeQuery();
            if (result.next()) {
                Product p = this.getProduct(result);
                return p;
            }
        } catch (SQLException e) {

        }
        return null;
    }

    public Product getProductBySlug(String slugProduct) {
        String sql = "select * from Product where slug=?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, slugProduct);
            ResultSet result = st.executeQuery();
            if (result.next()) {
                Product p = this.getProduct(result);
                return p;
            }
        } catch (SQLException e) {

        }
        return null;
    }

    public Product getProduct(ResultSet result) {
        try {
            int ID = result.getInt("ID");
            String name = result.getString("name");
            String description = result.getString("description");
            float oldPrice = result.getFloat("oldPrice");
            float newPrice = result.getFloat("newPrice");
            Timestamp datePost = result.getTimestamp("datePost");
            Timestamp dateUpdate = result.getTimestamp("dateUpdate");
            String mainImg = result.getString("mainImg");
            int status = result.getInt("status");
            String slug = result.getString("slug");
            int available = result.getInt("available");
            int sold = result.getInt("sold");
            String configProduct = result.getString("configProduct");
            String model = result.getString("model");
            int priority = result.getInt("priority");
            int categoryID = result.getInt("categoryID");
            int producerID = result.getInt("producerID");
            Product p = new Product(ID, name, description, oldPrice, newPrice, datePost, dateUpdate, mainImg, status, slug, available, sold, configProduct, model, priority, categoryID, producerID);
            return p;
        } catch (SQLException e) {
            System.out.println("Product: " + e);
        }
        return null;
    }

    public int insert(Product p) {
        int result = 0;
        String sql = "insert into Product (name, description, oldPrice, newPrice, datePost,"
                + "mainImg, status, slug, available, configProduct, model, priority, categoryID, producerID)"
                + "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
        try {
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, p.getName());
            st.setString(2, p.getDescription());
            st.setFloat(3, p.getOldPrice());
            st.setFloat(4, p.getNewPrice());
            st.setTimestamp(5, p.getDatePost());
            st.setString(6, p.getMainImg());
            st.setInt(7, p.getStatus());
            st.setString(8, p.getSlug());
            st.setInt(9, p.getAvailable());
            st.setString(10, p.getConfigProduct());
            st.setString(11, p.getModel());
            st.setInt(12, p.getPriority());
            st.setInt(13, p.getCategoryID());
            st.setInt(14, p.getProducerID());
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
            System.out.println("add product" + e);
        }
        return 0;
    }

    public int update(Product p, String slug) {
        int result = 0;
        String sql = "update product set name=?, model=?,description=?, oldPrice=?, newPrice=?,dateUpdate=?,"
                + "mainImg=?,status=?,available=?, configProduct=?, priority=?,categoryID=?, producerID=?, "
                + "slug=?"
                + " where slug=?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, p.getName());
            st.setString(2, p.getModel());
            st.setString(3, p.getDescription());
            st.setFloat(4, p.getOldPrice());
            st.setFloat(5, p.getNewPrice());
            st.setTimestamp(6, p.getDateUpdate());
            st.setString(7, p.getMainImg());
            st.setInt(8, p.getStatus());
            st.setInt(9, p.getAvailable());
            st.setString(10, p.getConfigProduct());
            st.setInt(11, p.getPriority());
            st.setInt(12, p.getCategoryID());
            st.setInt(13, p.getProducerID());
            st.setString(14, p.getSlug());
            st.setString(15, slug);
            result = st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Update product: " + e);
        }
        return result;
    }

    public int updateSold(int available, int sold, int id, String slug) {
        int result = 0;
        String sql = "update product set available=?, sold =?, slug=? "
                + " where id=?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, available);
            st.setInt(2, sold);
            st.setString(3, slug);
            st.setInt(4, id);
            result = st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Update product sold: " + e);
        }
        return result;
    }

    public int delete(String slug) {
        int result = 0;
        String sql = "delete from product where slug=?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, slug);
            result = st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Delete product: " + e);
        }
        return result;
    }

    public Product getById(int productId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
