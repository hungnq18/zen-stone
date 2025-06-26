package Controllers.User;

import DAO.ProductDao;
import Model.Product;
import Util.CurrencyConverter;
import Util.Sale;
import Util.Validation;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

public class FilterUserController extends HttpServlet {

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
        try {
            Validation validate = new Validation();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonData = mapper.readTree(request.getReader());
            String[] idCategories = mapper.readValue(jsonData.get("idCategory").toString(), String[].class);
            float from = Float.parseFloat(jsonData.get("from").asText());
            float to = Float.parseFloat(jsonData.get("to").asText());
            int time = Integer.parseInt(jsonData.get("time").asText());
            int idCategoryInt[] = new int[idCategories.length];
            int index = 0;
            for (String id : idCategories) {
                idCategoryInt[index++] = validate.getInt(id);
            }
            ProductDao productDao = new ProductDao();
            List<Product> products = productDao.filterProduct(idCategoryInt, from, to, time);
            System.out.println(products.size());
            String htmlReturn = this.filterProduct(products);
            PrintWriter out = response.getWriter();
            out.print(htmlReturn);
        } catch (Exception e) {
            System.out.println("Filter product: " + e);
        }
    }

    private String filterProduct(List<Product> products) {
        CurrencyConverter currency = new CurrencyConverter();
        Sale sale = new Sale();
        String productReturn = "";
        if (products.size() == 0) {
            productReturn = "<div class=\"box-no-found\">\n"
                    + "                            <img src=\"./uploads/base/no-product-found.png\" alt=\"Not found\">\n"
                    + "                            <p class=\"text\">Xin lỗi, không tìm thấy sản phẩm nào</p>\n"
                    + "                          </div>";
            return productReturn;
        }
        for (Product product : products) {
            productReturn += "<div class=\"cart-filter card col-6 col-lg-3 cart-filter\"  onclick=\"changePage(this, event)\">\n"
                    + "                                            <div class=\"box-img\">\n"
                    + "                                                <img src=\"" + product.getMainImg() + "\" class=\"card-img-top\" alt=\"" + product.getName() + "\"/>\n"
                    + "                                            </div>\n"
                    + "                                            <a class=\"link-page\" href=\"/product/detail/" + product.getSlug() + "\"></a>\n"
                    + "                                            <div class=\"card-body\">\n"
                    + "                                                <h5 class=\"card-text name\">\n"
                    + "                                                    " + product.getName() + "\n"
                    + "                                                </h5>\n"
                    + "                                                <p class=\"card-text price\">\n";
            if (product.getNewPrice() > 0) {
                productReturn += "<span class=\"new-price\">" + currency.currencyFormat(product.getNewPrice(), "VND") + "</span>\n"
                        + "<span class=\"old-price\">" + currency.currencyFormat(product.getOldPrice(), "VND") + "</span>\n"
                        + "                                                </p>\n";
            } else {
                productReturn += "<span>" + currency.currencyFormat(product.getOldPrice(), "VND") + "</span>\n</p>\n";
            }
            productReturn += " <a href=\"/product/detail/" + product.getSlug() + "\"class=\"btn btn-mini-cart active\">\n"
                    + "                                                            Chi tiết\n"
                    + "                                                        </a> </div>\n";
            if (product.getNewPrice() > 0) {
                productReturn += "                                              <div class=\"sale\">\n"
                        + "                                                <span>\n"
                        + "                                                    " + sale.calculateSale(product.getNewPrice(), product.getOldPrice()) + "\n"
                        + "                                                </span>\n"
                        + "                                            </div>\n";

            }
            productReturn += "</div>";
        }
        return productReturn;
    }
}
