package Controllers.User;

import DAO.BannerDao;
import DAO.BannerTextDao;
import DAO.ProductDao;
import Model.Banner;
import Model.BannerText;
import Model.Product;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;


public class HomeUserController extends HttpServlet {

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
//      2  => deal, 0=> normal, 1 priority, 3 feature
        BannerDao bannerDao = new BannerDao();
        ProductDao productDao = new ProductDao();
        BannerTextDao bannerTextDao = new BannerTextDao();
        List<Product> productsDeal = productDao.getProductByPriority(2);
        List<Product> productsFeature = productDao.getProductByPriority(3);
        List<Product> productsNormal = productDao.getProductByPriority(1);
        List<Banner> banners = bannerDao.getTop5Banner();
        List<BannerText> bannerTexts = bannerTextDao.getTop5BannerText();
        request.setAttribute("banners", banners);
        request.setAttribute("bannerTexts", bannerTexts);
        request.setAttribute("productsDeal", productsDeal);
        request.setAttribute("productsFeature", productsFeature);
        request.setAttribute("productsNormal", productsNormal);
        request.getRequestDispatcher("home.jsp").forward(request, response);
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

    }
}
