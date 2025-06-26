/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers.Admin;

import DAO.CategoryDao;
import DAO.ColorDao;
import DAO.CommentDao;
import DAO.ImgDescriptionDao;
import DAO.ProducerDao;
import DAO.ProductDao;
import Model.Category;
import Model.Color;
import Model.Comment;
import Model.ImgDescription;
import Model.Producer;
import Model.Product;
import Util.Slug;
import Util.Upload;
import Util.Validation;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author HP
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 50)
public class ProductController extends HttpServlet {

    private static CategoryDao categoryDao;
    private static ProducerDao producerDao;
    private static ProductDao productDao;

    public ProductController() {
        this.categoryDao = new CategoryDao();
        this.producerDao = new ProducerDao();
        this.productDao = new ProductDao();
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        if (path.endsWith("/admin/product")) {
            this.showAllProduct(request, response);
        } else {
            String paths[] = path.split("/");
            String slug = paths[paths.length - 1];
            if (path.startsWith("/admin/product/update/")) {
                boolean isUpdate = this.getCurrentProductUpdate(request, response, slug);
                if (isUpdate) {
                    request.getRequestDispatcher("/admin/view/product/updateProduct.jsp").forward(request, response);
                } else {
                    response.sendRedirect("/admin/404");
                }
            } else if (path.startsWith("/admin/product/view/")) {
                boolean isView = this.getCurrentProductUpdate(request, response, slug);
                if (isView) {
                    request.getRequestDispatcher("/admin/view/product/detailProduct.jsp").forward(request, response);
                } else {
                    response.sendRedirect("/admin/404");
                }
            } else if (path.startsWith("/admin/product/comment/")) {
                boolean isView = this.getCurrentProductComment(request, response, slug);
                if (isView) {
                    request.getRequestDispatcher("/admin/view/product/commentProduct.jsp").forward(request, response);
                } else {
                    response.sendRedirect("/admin/404");
                }
            } else if (path.startsWith("/admin/product/delete/")) {
                this.deleteProduct(request, response, slug);
            }
        }
    }

    private void showAllProduct(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Category> categories = categoryDao.allCategory();
            List<Producer> producers = producerDao.allProducer();
            List<Product> products = productDao.getAllProduct();
            request.setAttribute("products", products);
            request.setAttribute("categories", categories);
            request.setAttribute("producers", producers);
            request.getRequestDispatcher("/admin/view/product/product.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println("Show product: " + e);
        }
    }

    private boolean getCurrentProductUpdate(HttpServletRequest request, HttpServletResponse response, String slug) {
        try {
            ProductDao pDao = new ProductDao();
            ImgDescriptionDao imgDao = new ImgDescriptionDao();
            ColorDao colorDao = new ColorDao();
            CategoryDao cDao = new CategoryDao();
            ProducerDao producerDao = new ProducerDao();
            Product currentProduct = pDao.getProductBySlug(slug);
            if (currentProduct == null) {
                return false;
            }
            List<ImgDescription> imgDesc = imgDao.getAllImgDescriptionByProduct(currentProduct.getID());
            List<Color> imgColor = colorDao.getAllColorByProduct(currentProduct.getID());
            Category category = cDao.getCategoryByID(currentProduct.getCategoryID());
            Producer producer = producerDao.getProducerByID(currentProduct.getProducerID());
            request.setAttribute("product", currentProduct);
            request.setAttribute("imgDesc", imgDesc);
            request.setAttribute("imgColor", imgColor);
            request.setAttribute("producer", producer);
            request.setAttribute("category", category);
            List<Category> categories = cDao.allCategory();
            List<Producer> producers = producerDao.allProducer();
            request.setAttribute("categories", categories);
            request.setAttribute("producers", producers);
            return true;
        } catch (Exception e) {
            System.out.println("Get current product update: " + e);
        }
        return false;
    }

    private boolean getCurrentProductComment(HttpServletRequest request, HttpServletResponse response, String slug) {
        try {
            ProductDao pDao = new ProductDao();
            CommentDao commentDao = new CommentDao();
            Product currentProduct = pDao.getProductBySlug(slug);
            if (currentProduct == null) {
                return false;
            }
            List<Comment> comments = commentDao.allCommentByProduct(currentProduct.getID());
            request.setAttribute("commentProduct", comments);
            request.setAttribute("product", currentProduct);
            return true;
        } catch (Exception e) {
            System.out.println("Get current product update: " + e);
        }
        return false;
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response, String slug) {
        try {
            String pathProductImg = "./uploads/product/";
            String pathUploadImg = getServletContext().getRealPath(pathProductImg);
            Validation validate = new Validation();
            Upload upload = new Upload();
            ProductDao productDao = new ProductDao();
            ColorDao colorDao = new ColorDao();
            ImgDescriptionDao imgDao = new ImgDescriptionDao();
            Product product = productDao.getProductBySlug(slug);
            List<Color> colorList = colorDao.getAllColorByProduct(product.getID());
            List<ImgDescription> imgDesc = imgDao.getAllImgDescriptionByProduct(product.getID());
            int resultDelete = productDao.delete(slug);
            if (resultDelete >= 1) {
                String fileName[] = product.getMainImg().split("/");
                upload.deleteImage(pathUploadImg, fileName[fileName.length - 1]);
                String pathUploadDesc = getServletContext().getRealPath("./uploads/descriptions/");
                String pathUploadColor = getServletContext().getRealPath("./uploads/colors/");
                imgDao.delete(product.getID());
                colorDao.delete(product.getID());
                upload.deleteImageDescription(pathUploadDesc, imgDesc);
                upload.deleteImageColor(pathUploadColor, colorList);
            }
            String url = "/admin/product?status=";
            if (resultDelete >= 1) {
                url += 1;
            } else {
                url += 0;
            }
            response.sendRedirect(url);
        } catch (Exception e) {
            System.out.println("Delete product: " + e);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("btn-add-product") != null) {
            this.addProduct(request, response);
        } else if (request.getParameter("btn-update-product") != null) {
            this.updateProduct(request, response);
        } else if (request.getParameter("btn-delete-products") != null) {
            this.deleteProducts(request, response);
        } else {
            CommentDao commentDao = new CommentDao();
            Validation validate = new Validation();
            HttpSession session = request.getSession();
            String[] allItemComment = request.getParameterValues("item-comment");
            String slug = request.getParameter("slug");
            int result = 0;
            if (request.getParameter("btn-active-comment") != null) {
                for (String item : allItemComment) {
                    result = commentDao.updateStatusComment(1, validate.getInt(item));
                }
            } else if (request.getParameter("btn-ban-comment") != null) {
                for (String item : allItemComment) {
                    result = commentDao.updateStatusComment(2, validate.getInt(item));
                }
            } else if (request.getParameter("btn-delete-comment") != null) {
                for (String item : allItemComment) {
                    result = commentDao.deleteComment(validate.getInt(item));
                }
            }
            String url = "/admin/product/comment/" + slug;
            if (result >= 1) {
                url += "?status=1";
            } else {
                url += "?status=0";
            }
            response.sendRedirect(url);
        }
    }

    private void addProduct(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            String pathProduct = "./uploads/product/";
            String uploadPath = getServletContext().getRealPath(pathProduct);
            Upload upload = new Upload();
            Validation validate = new Validation();
            String name = request.getParameter("name");
            String model = request.getParameter("model");
            float price = Float.parseFloat(request.getParameter("price"));
            float newPrice = Float.parseFloat(request.getParameter("priceSale"));
            int numberOfProduct = validate.getInt(request.getParameter("numberOfProduct"));
            String desc = request.getParameter("desc");
            String config = request.getParameter("config");
            int priority = validate.getInt(request.getParameter("priority"));
            int categoryID = validate.getInt(request.getParameter("categoryID"));
            int producerID = validate.getInt(request.getParameter("producerID"));
            Part mainImgParth = request.getPart("imgMain");
            String fileNameImg = pathProduct + upload.uploadImg(mainImgParth, uploadPath);
            LocalDateTime dateTime = LocalDateTime.now();
            Timestamp date = Timestamp.valueOf(dateTime);
            Slug createSlug = new Slug();
            String slug = createSlug.toSlug(name);
            int status = validate.getInt(request.getParameter("status"));
            Product p = new Product(-1, name, desc, price, newPrice, date,
                    null, fileNameImg, status, slug, numberOfProduct, 0, config, model,
                    priority, categoryID, producerID);
            int curentIdProduct = productDao.insert(p);
//      insert into color and desc product
            String pathDesc = "./uploads/descriptions/";
            String pathColor = "./uploads/colors/";
            String uploadPathDesc = getServletContext().getRealPath(pathDesc);
            String uploadPathDescColor = getServletContext().getRealPath(pathColor);
            ImgDescriptionDao imgDescDao = new ImgDescriptionDao();
            ColorDao colorDao = new ColorDao();
            int indexColor = 0;
            for (Part part : request.getParts()) {
                if (part.getName().equals("imgDesc")) {
                    String nameImgDescPath = pathDesc + upload.uploadImg(part, uploadPathDesc);
                    ImgDescription imgDesc = new ImgDescription(0, nameImgDescPath, curentIdProduct);
                    imgDescDao.insert(imgDesc);
                }
                if (part.getName().equals("imgColor")) {
                    indexColor++;
                    String nameImgDescPath = pathColor + upload.uploadImg(part, uploadPathDescColor);
                    String nameColor = request.getParameter("colorName" + indexColor);
                    int quantityColor = 0;
                    try {
                        quantityColor = Integer.parseInt(request.getParameter("quantityColor" + indexColor));
                        if (quantityColor < 0) {
                            quantityColor = 0;
                        }
                    } catch (Exception e) {
                        System.out.println("quantitycolor: " + e);
                    }
                    Color color = new Color(0, curentIdProduct, nameImgDescPath, nameColor);
                    color.setQuantity(quantityColor);
                    colorDao.insert(color);
                }
            }
            int statusResponse = 0;
            if (curentIdProduct > 0) {
                statusResponse = 1;
            }
            String url = "/admin/product?status=" + statusResponse;
            response.sendRedirect(url);
        } catch (Exception e) {
            System.out.println("Insert product: " + e);
        }
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            String pathProduct = "./uploads/product/";
            String uploadPath = getServletContext().getRealPath(pathProduct);
            Upload upload = new Upload();
            Validation validate = new Validation();
            int idProduct = validate.getInt(request.getParameter("id"));
            String oldSlug = request.getParameter("slug");
            String name = request.getParameter("name");
            String model = request.getParameter("model");
            float price = Float.parseFloat(request.getParameter("price"));
            float newPrice = Float.parseFloat(request.getParameter("priceSale"));
            int numberOfProduct = validate.getInt(request.getParameter("available"));
            int sold = validate.getInt(request.getParameter("sold"));
            String desc = request.getParameter("desc");
            String config = request.getParameter("config");
            int priority = validate.getInt(request.getParameter("priority"));
            int categoryID = validate.getInt(request.getParameter("categoryID"));
            int producerID = validate.getInt(request.getParameter("producerID"));
            String oldMainImg = request.getParameter("oldMainImg");
            Part mainImgParth = request.getPart("imgMain");
            String fileNameImg = upload.uploadImg(mainImgParth, uploadPath);
            String imgMainUpload = pathProduct;
            if (fileNameImg != null) {
                String[] path = oldMainImg.split("/");
                upload.deleteImage(uploadPath, path[path.length - 1]);
                imgMainUpload += fileNameImg;
            } else {
                imgMainUpload = oldMainImg;
            }
            LocalDateTime dateTime = LocalDateTime.now();
            Timestamp dateUpdate = Timestamp.valueOf(dateTime);
            Slug createSlug = new Slug();
            String slugNew = createSlug.toSlug(name);
            int status = validate.getInt(request.getParameter("status"));
            Product p = new Product(idProduct, name, desc, price, newPrice, null,
                    dateUpdate, imgMainUpload, status, slugNew, numberOfProduct, sold, config, model,
                    priority, categoryID, producerID);
            int statusUpdate = productDao.update(p, oldSlug);
//      insert into color and desc product
            updateDetailsProduct(request.getParts(), idProduct, request);
            int statusResponse = 0;
            if (statusUpdate > 0) {
                statusResponse = 1;
            }
            String url = "/admin/product?status=" + statusResponse;
            response.sendRedirect(url);
        } catch (Exception e) {
            System.out.println("Update product: " + e);
        }
    }

    private boolean updateDetailsProduct(Collection<Part> parts, int idProduct, HttpServletRequest request) {
        Upload upload = new Upload();
        String pathDesc = "./uploads/descriptions/";
        String pathColor = "./uploads/colors/";
        String uploadPathDesc = getServletContext().getRealPath(pathDesc);
        String uploadPathDescColor = getServletContext().getRealPath(pathColor);
        ImgDescriptionDao imgDescDao = new ImgDescriptionDao();
        ColorDao colorDao = new ColorDao();
        boolean isDescUpdate = false;
        boolean isColorUpdate = false;
        int indexColor = 0;
        for (Part part : parts) {
            if (part.getName().equals("imgDesc")) {
                String fileNameUploadImgDesc = upload.uploadImg(part, uploadPathDesc);
                if (fileNameUploadImgDesc != null && !isDescUpdate) {
                    List<ImgDescription> imgDesc = imgDescDao.getAllImgDescriptionByProduct(idProduct);
                    upload.deleteImageDescription(uploadPathDesc, imgDesc);
                    imgDescDao.delete(idProduct);
                    isDescUpdate = true;
                }
                if (isDescUpdate) {
                    String nameImgDescPath = pathDesc + fileNameUploadImgDesc;
                    ImgDescription imgDesc = new ImgDescription(0, nameImgDescPath, idProduct);
                    imgDescDao.insert(imgDesc);
                }
            }
            if (part.getName().equals("imgColor")) {
                String fileNameUploadImgColor = upload.uploadImg(part, uploadPathDescColor);
                if (fileNameUploadImgColor != null && !isColorUpdate) {
                    List<Color> imgColor = colorDao.getAllColorByProduct(idProduct);
                    upload.deleteImageColor(uploadPathDescColor, imgColor);
                    colorDao.delete(idProduct);
                    isColorUpdate = true;
                }
                if (isColorUpdate) {
                    indexColor++;
                    String nameImgColorPath = pathColor + fileNameUploadImgColor;
                    String nameColor = request.getParameter("colorName" + indexColor);
                    int quantityColor = 0;
                    try {
                        quantityColor = Integer.parseInt(request.getParameter("quantityColor" + indexColor));
                        if (quantityColor < 0) {
                            quantityColor = 0;
                        }
                    } catch (Exception e) {
                        System.out.println("quantitycolor: " + e);
                    }
                    Color color = new Color(0, idProduct, nameImgColorPath, nameColor);
                    color.setQuantity(quantityColor);
                    colorDao.insert(color);
                } else {
                    String[] quantityColor = request.getParameterValues("quantity_color-hidden");
                    for (String st : quantityColor) {
                        try {
                            int quantitynew = Integer.parseInt(request.getParameter("quantity_color-" + st));
                            colorDao.update(Integer.parseInt(st), quantitynew);
                        }catch(Exception e) {
                            System.out.println("Fail: " + e);
                        }
                    }
                }
            }
        }
        return true;
    }

    private void deleteProducts(HttpServletRequest request, HttpServletResponse response) {
        try {
            String pathProductImg = "./uploads/product/";
            String pathUploadImg = getServletContext().getRealPath(pathProductImg);
            ColorDao colorDao = new ColorDao();
            ImgDescriptionDao imgDao = new ImgDescriptionDao();
            Upload upload = new Upload();
            String[] listDeleteProduct = request.getParameterValues("delete-product-item");
            if (listDeleteProduct == null) {
                response.sendRedirect("/admin/product?status=0&message=Please choose product to delete");
                return;
            }
            boolean isDelete = false;
            for (String item : listDeleteProduct) {
                Product product = productDao.getProductBySlug(item);
                List<Color> colorList = colorDao.getAllColorByProduct(product.getID());
                List<ImgDescription> imgDesc = imgDao.getAllImgDescriptionByProduct(product.getID());
                int result = productDao.delete(item);
                if (result >= 1) {
                    isDelete = true;
                    String fileName[] = product.getMainImg().split("/");
                    upload.deleteImage(pathUploadImg, fileName[fileName.length - 1]);
                    String pathUploadDesc = getServletContext().getRealPath("./uploads/descriptions/");
                    String pathUploadColor = getServletContext().getRealPath("./uploads/colors/");
                    imgDao.delete(product.getID());
                    colorDao.delete(product.getID());
                    upload.deleteImageDescription(pathUploadDesc, imgDesc);
                    upload.deleteImageColor(pathUploadColor, colorList);
                }
            }
            String url = "/admin/product?status=";
            if (isDelete) {
                url += 1;
            } else {
                url += 0;
            }
            response.sendRedirect(url);
        } catch (Exception e) {
            System.out.println("Delete categories:  " + e);
        }
    }
}
