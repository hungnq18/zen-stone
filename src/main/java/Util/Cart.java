package Util;

import DAO.ColorDao;
import DAO.ProductDao;
import Model.CartModel;
import Model.Color;
import Model.Product;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Le Tan Kim
 */
public class Cart {

    public List<CartModel> createCart(String cartCookie, CartModel cartItem) {
        boolean isNewProduct = true;
        List<CartModel> carts = this.loadCart(cartCookie);
        for (CartModel c : carts) {
            if (c.getId() == cartItem.getId() && c.getColor().equals(cartItem.getColor())) {
                if (cartItem.getQuantityColor() < (c.getNumberOfProduct() + cartItem.getNumberOfProduct())) {
                    return carts;
                }
                c.setNumberOfProduct(c.getNumberOfProduct() + cartItem.getNumberOfProduct());
                isNewProduct = false;
            }
        }
        if (isNewProduct) {
            carts.add(cartItem);
        }
        return carts;
    }

    public String deleteItemCart(String cartCookie, int idCartItem) {
        List<CartModel> carts = this.loadCart(cartCookie);
        for (CartModel c : carts) {
            if (c.getId() == idCartItem) {
                carts.remove(c);
                break;
            }
        }
        return convertCart(carts);
    }

    public String minusItemCart(String cartCookie, int idCartItem) {
        List<CartModel> carts = this.loadCart(cartCookie);
        for (CartModel c : carts) {
            if (c.getId() == idCartItem) {
                if (c.getNumberOfProduct() == 1) {
                    carts.remove(c);
                } else {
                    c.setNumberOfProduct(c.getNumberOfProduct() - 1);
                }
                break;
            }
        }
        return convertCart(carts);
    }

    public String addItemCart(String cartCookie, int idCartItem, Color color) {
        List<CartModel> carts = this.loadCart(cartCookie);
        ProductDao productDao = new ProductDao();
        Product currentProduct = productDao.getProductByID(idCartItem);
        ColorDao colorDao = new ColorDao();
        for (CartModel c : carts) {
            if (c.getId() == idCartItem) {
                if (c.getNumberOfProduct() < color.getQuantity()) {
                    c.setNumberOfProduct(c.getNumberOfProduct() + 1);
                }
                break;
            }
        }
        return convertCart(carts);
    }

//  quy tat cart "_" ngan cach giua cac thuoc tinh (id, numberOfProducy)
//  || ngan cach giua cac cartItem
    public String convertCart(List<CartModel> cart) {
        String cartReturn = "";
        for (CartModel c : cart) {
            String id = c.getId() + "";
            String colorId = c.getColorId() + "";
            String numberOfProduct = c.getNumberOfProduct() + "";
            String color = c.getColor().replaceAll(" ", "00");
            String cartItem = id + "_" + numberOfProduct + "_" +colorId+ "_"+ color;
            cartReturn += (cartItem + "||");
        }
        return cartReturn.equals("") ? "" : cartReturn.substring(0, cartReturn.length() - 2);
    }

    public List<CartModel> loadCart(String cartCookie) {
        Validation validate = new Validation();
        List<CartModel> carts = new ArrayList<>();
        try {
            if (cartCookie.length() == 0) {
                return carts;
            }
            if (!cartCookie.contains("||")) {
                cartCookie += "||";
            }
            String[] cartsSplit = cartCookie.split("\\|\\|");
            for (String cart : cartsSplit) {
                String itemCart[] = cart.split("_");
                if (itemCart.length == 4) {
                    int id = validate.getInt(itemCart[0]);
                    int numberOfProduct = validate.getInt(itemCart[1]);
                    int colorId = validate.getInt(itemCart[2]);
                    String colors[] = itemCart[3].split("00");
                    String color = "";
                    for (String c : colors) {
                        color += c + " ";
                    }
                    CartModel cartModel = new CartModel(id, numberOfProduct, color.trim());
                    cartModel.setColorId(colorId);
                    carts.add(cartModel);
                }
            }

        } catch (Exception e) {
            System.out.println("Load cart: " + e);
        }
        return carts;
    }

    public int getNumberCart(HttpServletRequest request) {
        try {
            String cartCookie = "";
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cook : cookies) {
                    if (cook != null && cook.getName().equals("cart")) {
                        cartCookie = cook.getValue();
                        break;
                    }
                }
            }
            List<CartModel> carts = this.loadCart(cartCookie);
            int numberCart = 0;
            for (CartModel cart : carts) {
                numberCart += cart.getNumberOfProduct();
            }
            return numberCart;
        } catch (Exception e) {
            System.out.println("Get number cart: " + e);
        }
        return 0;
    }

    public int getMaxNumberProduct(HttpServletRequest request, int id, int number) {
        try {
            String cartCookie = "";
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cook : cookies) {
                    if (cook != null && cook.getName().equals("cart")) {
                        cartCookie = cook.getValue();
                        break;
                    }
                }
            }
            List<CartModel> carts = this.loadCart(cartCookie);
            int maxNumber = number;
            for (CartModel cart : carts) {
                if (cart.getId() == id) {
                    maxNumber = (number - cart.getNumberOfProduct());
                }
            }
            return maxNumber;
        } catch (Exception e) {
            System.out.println("Get max number cart: " + e);
        }
        return 0;
    }

    public String showCart(String cartCookie) {
        List<CartModel> carts = this.loadCart(cartCookie);
        if (carts.size() == 0) {
            return "<div class=\"cart-empty\">\n"
                    + "                  <img src=\"https://mir-s3-cdn-cf.behance.net/projects/404/95974e121862329.Y3JvcCw5MjIsNzIxLDAsMTM5.png\" alt=\"Empty cart\">\n"
                    + "                  <p class=\"text\">Không có sản phẩm trong giỏ hàng</p>\n"
                    + "              </div>";
        }
        String cartShow = "<div class=\"wrapper-cart\">\n"
                + "                  <table class=\"table\">\n"
                + "                      <thead>\n"
                + "                        <tr>\n"
                + "                          <th class=\"with-img\" scope=\"col with-img\">Ảnh</th>\n"
                + "                          <th class=\"width-20\" scope=\"col\">Sản phẩm</th>\n"
                + "                          <th class=\"width-15\" scope=\"col\">Giá sản phẩm</th>\n"
                + "                          <th scope=\"col\">Số lượng</th>\n"
                + "                          <th scope=\"col\">Tổng</th>\n"
                + "                          <th class=\"width-15\" scope=\"col\">Màu</th>\n"
                + "                          <th class=\"width-10\" scope=\"col\">Trạng thái</th>\n"
                + "                        </tr>\n"
                + "                      </thead>\n"
                + "                      <tbody>";
        ProductDao productDao = new ProductDao();
        CurrencyConverter currency = new CurrencyConverter();
        float total = 0;
        int totalNumberCart = 0;
        for (CartModel c : carts) {
            Product p = productDao.getProductByID(c.getId());
            float price = p.getNewPrice() > 0 ? p.getNewPrice() : p.getOldPrice();
            float totalItem = price * c.getNumberOfProduct();
            totalNumberCart += c.getNumberOfProduct();
            total += totalItem;
            cartShow += "<tr>\n"
                    + "                          <td>\n"
                    + "                              <img class=\"img-yourCard\" src=\"" + p.getMainImg() + "\" alt=\"" + p.getName() + "\" srcset=\"\">\n"
                    + "                          </td>      \n"
                    + "                          <td>\n"
                    + "                              " + p.getName() + "\n"
                    + "                          </td>\n"
                    + "                          <td>" + currency.currencyFormat(price, "VND") + "</td>\n"
                    + "<form action=\"cart\" method=\"post\">"
                    + " <td><div class=\"number-input\">\n"
                    + "                              <button\n name=\"btn-minus\""
                    + "                                onclick=\"this.parentNode.querySelector('input[type=number]').stepDown()\"\n"
                    + "                                class=\"minus\"\n"
                    + "                              ></button>\n"
                    + "<input type=\"hidden\" name=\"color\" value=\"" + c.getColorId() + "\"/>"
                    + "<input type=\"hidden\" name=\"id\" value=\"" + p.getID() + "\"/>"
                    + "                              <input\n"
                    + "                                class=\"quantity\"\n"
                    + "                                max=\"" + p.getAvailable() + "\"\n"
                    + "                                name=\"quantity\"\n"
                    + "                                value=\"" + c.getNumberOfProduct() + "\" readonly\n"
                    + "                                type=\"number\"\n"
                    + "                              />\n"
                    + "                              <button name=\"btn-add\"\n"
                    + "                                onclick=\"this.parentNode.querySelector('input[type=number]').stepUp()\"\n"
                    + "                                class=\"plus\"\n"
                    + "                              ></button>\n"
                    + "                            </div>"
                    + "</form>"
                    + "                          </td>"
                    + "                          <td>" + currency.currencyFormat(totalItem, "VND") + "</td>\n"
                    + "                          <td>" + c.getColor() + "</td>\n"
                    + "                          <td>\n"
                    + "                              <a onclick=\"return confirm('Bạn chắc chắn muốn loại bỏ sản phẩm trong giỏ hàng?')\" href=\"cart/delete/" + p.getID() + "\">\n"
                    + "                                  <i class='bx bx-x'></i>\n"
                    + "                              </a>\n"
                    + "                          </td>\n"
                    + "                        </tr>";
        }
        cartShow += "</tbody>\n"
                + "                    </table>\n"
                + "              </div>\n"
                + "              <div class=\"thanhToan-yourCart\">\n"
                + "                <div class=\"box-thanh-toan\">\n"
                + "                    <h2>\n"
                + "                        Cart Totals\n"
                + "                    </h2>\n"
                + "                    <ul>\n"
                + "                        <li><span>Sản phẩm trong giỏ hàng</span> \n"
                + "                            <span class=\"subtotal\">" + totalNumberCart + "</span>\n"
                + "                        </li>\n"
                + "                        <li><spab>Tổng tiền</spab>\n"
                + "                            <span class=\"total\">" + currency.currencyFormat(total, "VND") + "</span>\n"
                + "                        </li>\n"
                + "                    </ul>\n"
                + "                    <div class=\"box-button-order-buy\">\n"
                + "                      <a href=\"/product\" class=\"btn pay-cart-now no-border\">Tiếp tục mua</a>\n"
                + "                      <a href=\"/checkout/buy-cart\" class=\"btn pay-cart-now bg\">Đặt hàng</a>\n"
                + "                  </div>\n"
                + "                </div>\n"
                + "              </div>";
        return cartShow;
    }
    public String showMiniCart(HttpServletRequest request) {
    String cartCookie = "";
    Cookie[] cookies = request.getCookies();

    // Kiểm tra nếu cookie không null
    if (cookies != null) {
        for (Cookie cook : cookies) {
            if ("cart".equals(cook.getName())) {
                cartCookie = cook.getValue();
                break;
            }
        }
    }
        List<CartModel> carts = this.loadCart(cartCookie);
        if (carts.size() == 0) {
            return "<div class=\"box-empty-cart\">\n"
                    + "                                                            <img src=\"https://mir-s3-cdn-cf.behance.net/projects/404/95974e121862329.Y3JvcCw5MjIsNzIxLDAsMTM5.png\" alt=\"Empty cart\">\n"
                    + "                                                            <p class=\"text\">Empty cart</p>\n"
                    + "                                                        </div>";
        }
        ProductDao productDao = new ProductDao();
        CurrencyConverter currency = new CurrencyConverter();
        String cartShow = "<div class=\"header-cart-mini\">\n"
                + "                                <h2>Giỏ hàng</h2>\n"
                + "                                <span>(" + carts.size() + " sản phẩm)</span>\n"
                + "                            </div>"
                + "         <div class=\"main-mini-cart\">\n"
                + "                                <ul class=\"list-mini-cart\">";
        float total = 0;
        int totalNumberCart = 0;
        for (CartModel c : carts) {
            Product p = productDao.getProductByID(c.getId());
            float price = p.getNewPrice() > 0 ? p.getNewPrice() : p.getOldPrice();
            float totalItem = price * c.getNumberOfProduct();
            totalNumberCart += c.getNumberOfProduct();
            total += totalItem;
            cartShow += "<li class=\"item-mini-cart\">\n"
                    + "                                        <img src=\"" + p.getMainImg() + "\" alt=\"" + p.getName() + "\"/>\n"
                    + "                                        <div class=\"content-mini-cart\">\n"
                    + "                                            <p class=\"item-cart-name\">\n"
                    + "                                               " + p.getName() + "\n"
                    + "                                            </p>\n"
                    + "                                            <span class=\"item-cart-number\">x" + c.getNumberOfProduct() + "</span>\n"
                    + "                                            <span class=\"item-cart-number\">" + c.getColor() + "</span>\n"
                    + "                                            <span class=\"item-cart-price\">" + currency.currencyFormat(totalItem, "VND") + "</span>\n"
                    + "                                        </div>\n"
                    + "                                    </li>";
        }
        cartShow += "</ul>\n"
                + "                                <div class=\"total-mini-cart\">\n"
                + "                                    <span class=\"total-mini-cart-title\">Tổng</span>\n"
                + "                                    <span class=\"total-mini-cart-title active\">" + currency.currencyFormat(total, "VND") + "</span>\n"
                + "                                </div>\n"
                + "                                <div class=\".active\">\n"
                + "                                    <a href=\"/cart\"class=\"btn btn-mini-cart active\">\n"
                + "                                        Xem giỏ hàng\n"
                + "                                    </a>\n"
                + "                                    <a href=\"/checkout/buy-cart\" class=\"btn btn-mini-cart\">Thanh toán</a>\n"
                + "                                </div>\n"
                + "                            </div>";
        return cartShow;
    }
}
