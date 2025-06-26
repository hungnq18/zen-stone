/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers.Admin;

import DAO.BannerTextDao;
import Model.BannerText;
import Util.Upload;
import Util.Validation;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Le Tan Kim
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 50)   // 50 MB
public class BannerWithText extends HttpServlet {
    
    private BannerTextDao bannerTextDao;
    
    public BannerWithText() {
        this.bannerTextDao = new BannerTextDao();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        Validation validate = new Validation();
        String path = request.getRequestURI();
        if (path.endsWith("/admin/banner-text")) {
            this.getAllBannerText(request, response);
        } else {
            String paths[] = path.split("/");
            int id = validate.getInt(paths[paths.length - 1]);
            if (path.startsWith("/admin/banner-text/delete")) {
                this.deleteBannerText(request, response, id);
            } else if (path.startsWith("/admin/banner-text/update")) {
                this.getCurrentBannerTextUpdate(request, response, id);
            }
        }
    }
    
    private void getAllBannerText(HttpServletRequest request, HttpServletResponse response) {
        try {
            
            List<BannerText> banners = bannerTextDao.allBannerText();
            request.setAttribute("bannerTexts", banners);
            request.getRequestDispatcher("/admin/view/banner/bannerText.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println("Get all banner text: " + e);
        }
    }
    
    private void getCurrentBannerTextUpdate(HttpServletRequest request, HttpServletResponse response, int id) {
        try {
            BannerText banner = bannerTextDao.currentBannerText(id);
            if (banner != null) {
                request.setAttribute("currentBannerText", banner);
                request.getRequestDispatcher("/admin/view/banner/updateBannerText.jsp").forward(request, response);
            } else {
                response.sendRedirect("/admin/404");
            }
        } catch (Exception e) {
            System.out.println("Get banner update: " + e);
        }
    }
    
    private void deleteBannerText(HttpServletRequest request, HttpServletResponse response, int id) {
        try {
            String uploadPath = getServletContext().getRealPath("./uploads/banners/");
            Upload upload = new Upload();
            BannerText deleteBanner = bannerTextDao.currentBannerText(id);
            int result = bannerTextDao.delete(id);
            if (result == 1) {
                upload.deleteImage(uploadPath, deleteBanner.getImg());
            }
            String url = "/admin/banner-text?status=" + result;
            response.sendRedirect(url);
        } catch (Exception e) {
            System.out.println("Delete banner text: " + e);
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
        if (request.getParameter("btn-add-banner-text") != null) {
            this.addBannerText(request, response);
        } else if (request.getParameter("btn-update-banner-text") != null) {
            this.updateBannerText(request, response);
        } else if (request.getParameter("btn-delete-banners-text") != null) {
            this.deleteBannersText(request, response);
        }
    }
    
    private void addBannerText(HttpServletRequest request, HttpServletResponse response) {
        try {
            Upload upload = new Upload();
            Validation validate = new Validation();
            Part filePath = request.getPart("img");
            String pathDir = "./uploads/banners/";
            String uploadPath = getServletContext().getRealPath(pathDir);
            String fileName = pathDir + upload.uploadImg(filePath, uploadPath);
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            LocalDateTime dateTime = LocalDateTime.now();
            Timestamp datePost = Timestamp.valueOf(dateTime);
            int status = validate.getInt(request.getParameter("status"));
            BannerText banner = new BannerText(0, fileName, title, description, status, datePost, null);
            int result = bannerTextDao.insert(banner);
            String url = "/admin/banner-text?status=" + result;
            response.sendRedirect(url);
        } catch (Exception e) {
            System.out.println("Add banner text: " + e);
        }
    }
    
    private void updateBannerText(HttpServletRequest request, HttpServletResponse response) {
        try {
            Validation validate = new Validation();
            Upload upload = new Upload();
            Part filePath = request.getPart("img");
            String pathDir = "./uploads/banners/";
            String uploadPath = getServletContext().getRealPath(pathDir);
            String fileName = pathDir;
            String fileNameUpload = upload.uploadImg(filePath, uploadPath);
            String oldImgName = request.getParameter("oldImg");
            if (fileNameUpload == null) {
                fileName = oldImgName;
            } else {
                fileName += fileNameUpload;
                upload.deleteImage(uploadPath, oldImgName);
            }
            int id = validate.getInt(request.getParameter("id"));
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            int status = validate.getInt(request.getParameter("status"));
            LocalDateTime dateTime = LocalDateTime.now();
            Timestamp dateUpdate = Timestamp.valueOf(dateTime);
            BannerText b = new BannerText(id, fileName, title, description, status, dateUpdate, dateUpdate);
            int result = bannerTextDao.update(b);
            String url = "/admin/banner-text?status=" + result;
            response.sendRedirect(url);
        } catch (Exception e) {
            System.out.println("Update banner text: " + e);
        }
    }
    
    private void deleteBannersText(HttpServletRequest request, HttpServletResponse response) {
        try {
            String uploadPath = getServletContext().getRealPath("./uploads/banners/");
            Validation validate = new Validation();
            Upload upload = new Upload();
            String[] idChecked = request.getParameterValues("deleteBannerText");
            if(idChecked == null) {
                response.sendRedirect("/admin/banner-text?status=0&message=Please choose banner to delete");
                return;
            }
            int isDelete = 0;
            for (String idDelete : idChecked) {
                int id = validate.getInt(idDelete);
                BannerText b = bannerTextDao.currentBannerText(id);
                int result = bannerTextDao.delete(id);
                if (result == 1) {
                    isDelete = 1;
                    upload.deleteImage(uploadPath, b.getImg());
                }
            }
            String url = "/admin/banner-text?status=" + isDelete;
            response.sendRedirect(url);
        } catch (Exception e) {
            System.out.println("Delete banners: " + e);
        }
    }
    
}
