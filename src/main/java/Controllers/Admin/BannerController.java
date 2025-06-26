/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers.Admin;

import DAO.BannerDao;
import Model.Banner;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author HP
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 50)   // 50 MB
public class BannerController extends HttpServlet {
    
    private static BannerDao bannerDao;
    
    public BannerController() {
        this.bannerDao = new BannerDao();
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
        Validation validate = new Validation();
        String path = request.getRequestURI();
        if (path.endsWith("/admin/banner")) {
            this.getAllBanner(request, response);
        } else {
            String paths[] = path.split("/");
            int id = validate.getInt(paths[paths.length - 1]);
            if (path.startsWith("/admin/banner/delete")) {
                this.deleteBanner(request, response, id);
            } else if (path.startsWith("/admin/banner/update")) {
                this.getCurrentBannerUpdate(request, response, id);
            }
        }
    }
    
    private void getAllBanner(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Banner> banners = bannerDao.allBanner();
            request.setAttribute("banners", banners);
            request.getRequestDispatcher("/admin/view/banner/banner.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println("Get all banner: " + e);
        }
    }
    
    private void getCurrentBannerUpdate(HttpServletRequest request, HttpServletResponse response, int id) {
        try {
            Banner banner = bannerDao.currentBanner(id);
            if (banner != null) {
                request.setAttribute("currentBanner", banner);
                request.getRequestDispatcher("/admin/view/banner/updateBanner.jsp").forward(request, response);
            } else {
                response.sendRedirect("/admin/404");
            }
        } catch (Exception e) {
            System.out.println("Get banner update: " + e);
        }
    }
    
    private void deleteBanner(HttpServletRequest request, HttpServletResponse response, int id) {
        try {
            String uploadPath = getServletContext().getRealPath("./uploads/banners/");
            Upload upload = new Upload();
            Banner deleteBanner = bannerDao.currentBanner(id);
            int result = bannerDao.delete(id);
            if (result == 1) {
                upload.deleteImage(uploadPath, deleteBanner.getImg());
            }
            String url = "/admin/banner?status=" + result;
            response.sendRedirect(url);
        } catch (Exception e) {
            System.out.println("Delete banner: " + e);
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
        if (request.getParameter("btn-add-banner") != null) {
            this.addBanner(request, response);
        } else if (request.getParameter("btn-update-banner") != null) {
            this.updateBanner(request, response);
        } else if (request.getParameter("btn-delete-banners") != null) {
            this.deleteBanners(request, response);
        }
    }
    
    private void addBanner(HttpServletRequest request, HttpServletResponse response) {
        try {
            Upload upload = new Upload();
            Validation validate = new Validation();
            Part filePath = request.getPart("img");
            String pathDir = "./uploads/banners/";
            String uploadPath = getServletContext().getRealPath(pathDir);
            String fileName = pathDir + upload.uploadImg(filePath, uploadPath);
            LocalDateTime dateTime = LocalDateTime.now();
            Timestamp datePost = Timestamp.valueOf(dateTime);
            int status = validate.getInt(request.getParameter("status"));
            Banner banner = new Banner(0, fileName, status, datePost, null);
            int result = bannerDao.insert(banner);
            String url = "/admin/banner?status=" + result;
            response.sendRedirect(url);
        } catch (Exception e) {
            System.out.println("Add banner: " + e);
        }
    }
    
    private void updateBanner(HttpServletRequest request, HttpServletResponse response) {
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
            int status = validate.getInt(request.getParameter("status"));
            LocalDateTime dateTime = LocalDateTime.now();
            Timestamp dateUpdate = Timestamp.valueOf(dateTime);
            Banner b = new Banner(id, fileName, status, null, dateUpdate);
            int result = bannerDao.update(b);
            String url = "/admin/banner?status=" + result;
            response.sendRedirect(url);
        } catch (Exception e) {
            System.out.println("Update banner: " + e);
        }
    }
    
    private void deleteBanners(HttpServletRequest request, HttpServletResponse response) {
        try {
            String uploadPath = getServletContext().getRealPath("./uploads/banners/");
            Validation validate = new Validation();
            Upload upload = new Upload();
            String[] idChecked = request.getParameterValues("deleteBanner");
            if(idChecked == null) {
                response.sendRedirect("/admin/banner?status=0&message=Please choose banner to delete");
                return;
            }
            int isDelete = 0;
            for (String idDelete : idChecked) {
                int id = validate.getInt(idDelete);
                Banner b = bannerDao.currentBanner(id);
                int result = bannerDao.delete(id);
                if (result == 1) {
                    isDelete = 1;
                    upload.deleteImage(uploadPath, b.getImg());
                }
            }
            String url = "/admin/banner?status=" + isDelete;
            response.sendRedirect(url);
        } catch (Exception e) {
            System.out.println("Delete banners: " + e);
        }
    }
}
