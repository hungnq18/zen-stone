/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers.Admin;

import DAO.AccountDao;
import DAO.BannerDao;
import DAO.BillDao;
import DAO.CategoryDao;
import DAO.CompanyDao;
import DAO.ProducerDao;
import DAO.ProductDao;
import DAO.VoucherDao;
import Model.Account;
import Model.Banner;
import Model.Bill;
import Model.Category;
import Model.Company;
import Model.Producer;
import Model.Product;
import Model.Voucher;
import Util.CurrencyConverter;
import Util.Validation;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author HP
 */
public class FilterStatusController extends HttpServlet {

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
        String htmlReturn = "";
        Validation validate = new Validation();
        String page = request.getParameter("page");
        String data = request.getParameter("data");
        int status = validate.getInt(data);
        switch (page) {
            case "account":
                AccountDao accountDao = new AccountDao();
                List<Account> accounts = accountDao.getAccountByStatus(status);
                if (status == -1) {
                    accounts = accountDao.allAccount();
                }
                htmlReturn = this.AccountFilterStatus(accounts);
                break;
            case "banner":
                BannerDao bannerDao = new BannerDao();
                List<Banner> banners = bannerDao.getBannerByStatus(status);
                if (status == -1) {
                    banners = bannerDao.allBanner();
                }
                htmlReturn = this.BannerFilterStatus(banners);
                break;
            case "category":
                CategoryDao categoryDao = new CategoryDao();
                List<Category> categories = categoryDao.getCategoryByStatus(status);
                if (status == -1) {
                    categories = categoryDao.allCategory();
                }
                htmlReturn = this.CategoryFilterStatus(categories);
                break;
            case "producer":
                ProducerDao producerDao = new ProducerDao();
                List<Producer> producers = producerDao.getProducerByStatus(status);
                if (status == -1) {
                    producers = producerDao.allProducer();
                }
                htmlReturn += this.ProducerFilterStatus(producers);
                break;
            case "product":
                ProductDao productDao = new ProductDao();
                List<Product> products = productDao.getProductByStatus(status);
                if (status == -1) {
                    products = productDao.getAllProduct();
                }
                htmlReturn = this.ProductFilterStatus(products);
                break;
            case "bill":
                BillDao billDao = new BillDao();
                List<Bill> bills = billDao.getBillByStatus(status);
                if (status == -1) {
                    bills = billDao.getAllBill();
                }
                htmlReturn = this.BillFilterStatus(bills);
                break;
            case "voucher":
                VoucherDao voucherDao = new VoucherDao();
                List<Voucher> voucher = voucherDao.getVoucherByStatus(status);
                if (status == -1) {
                    voucher = voucherDao.allVoucher();
                }
                htmlReturn = this.VoucherFilterStatus(voucher);
                break;
            case "company":
                CompanyDao companyDao = new CompanyDao();
                List<Company> companies = companyDao.getCompanyByStatus(status);
                if (status == -1) {
                    companies = companyDao.getAllCompany();
                }
                htmlReturn = this.CompanyFilterStatus(companies);
                break;
            default:
                throw new AssertionError();
        }
        if (htmlReturn.equals("")) {
            htmlReturn += "<tr><td colspan='20' class=\"dataTables_empty\">No result</td></tr>";
        }
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            out.print(htmlReturn);
        }
    }

    private String AccountFilterStatus(List<Account> accounts) {
        String htmlReturn = "";
        for (Account account : accounts) {
            htmlReturn += "<tr>\n"
                    + "                                        <td><input type=\"checkbox\" name=\"delete-account-item\" value=\"" + account.getId() + "\"></td>\n"
                    + "                                        <td>" + account.getFullname() + "</td>\n"
                    + "                                        <td>" + account.getEmail() + "</td>\n"
                    + "                                        <td>" + account.getPhone() + "</td>\n"
                    + "                                        <td>" + account.getUsername() + "</td>\n"
                    + "                                        <td><span class=\"text-ellipsis\">" + account.getDate() + "</span></td>\n"
                    + "                                        <td>\n";
            if (account.getRole() == 2) {
                htmlReturn += "<span class=\"label label-success\">High admin</span>\n";
            } else if (account.getRole() == 1) {
                htmlReturn += "<span class=\"label label-info\">Admin</span>\n";
            } else {
                htmlReturn += "<span class=\"label label-warning\">User</span>\n";
            }
            htmlReturn += " </td>\n<td>\n";
            if (account.getStatus() == 1) {
                htmlReturn += "<span class=\"label label-success\">Normal</span>\n";
            } else {
                htmlReturn += "<span class=\"label label-default\">Hidden</span>\n";
            }
            htmlReturn += "                                        </td>\n"
                    + "                                        <td class=\"box-action\">\n"
                    + "                                            <a href=\"/admin/account/update/" + account.getId() + "\" class=\"active btn-action green\">\n"
                    + "                                                <i class=\"bx bx-edit\"></i>\n"
                    + "                                            </a>\n"
                    + "                                            <a onclick=\"return confirm('Are you sure to delete?')\" \n"
                    + "                                               href=\"/admin/account/delete/" + account.getId() + "\" \n"
                    + "                                               class=\"active btn-action orange\">\n"
                    + "                                                <i class='bx bx-trash'></i>\n"
                    + "                                            </a>\n"
                    + "                                        </td>\n"
                    + "                                    </tr>";
        }
        return htmlReturn;
    }

    private String BannerFilterStatus(List<Banner> banners) {
        String htmlReturn = "";
        for (Banner banner : banners) {
            htmlReturn += "<tr>\n"
                    + "                                    <td><input type=\"checkbox\" name=\"deleteBanner\" value=\"" + banner.getID() + "\"></td>\n"
                    + "                                    <td>\n"
                    + "                                        <img src=\"" + banner.getImg() + "\" alt=\"alt\"/>\n"
                    + "                                    </td>\n"
                    + "                                    <td><span class=\"text-ellipsis\">" + banner.getDatePost() + "</span></td>\n"
                    + "                                    <td>\n"
                    + "                                        <span class=\"text-ellipsis\">\n"
                    + "                                            " + banner.getDateUpdate() + "\n"
                    + "                                        </span>\n"
                    + "                                    </td>\n"
                    + "                                    <td>\n";
            if (banner.getStatus() == 1) {
                htmlReturn += "<span class=\"label label-success\">Normal</span>\n";
            } else {
                htmlReturn += "<span class=\"label label-default\">Hidden</span>\n";
            }
            htmlReturn += "                                    </td>\n"
                    + "                                    <td class=\"box-action\">\n"
                    + "                                        <a href=\"/admin/banner/update/" + banner.getID() + "\" class=\"active btn-action green\">\n"
                    + "                                            <i class=\"bx bx-edit\"></i>\n"
                    + "                                        </a>\n"
                    + "                                        <a onclick=\"return confirm('Are you sure to delete?')\" \n"
                    + "                                           href=\"/admin/banner/delete/" + banner.getID() + "\" \n"
                    + "                                           class=\"active btn-action orange\">\n"
                    + "                                            <i class='bx bx-trash'></i>\n"
                    + "                                        </a>\n"
                    + "                                    </td>\n"
                    + "                                </tr>";
        }
        return htmlReturn;
    }

    private String CategoryFilterStatus(List<Category> categories) {
        String htmlReturn = "";
        for (Category category : categories) {
            htmlReturn += "<tr>\n"
                    + "                                        <td><input type=\"checkbox\" name=\"delete-category-item\" value=\"" + category.getSlug() + "\"></td>\n"
                    + "                                        <td>" + category.getName() + "</td>\n"
                    + "                                        <td><span class=\"text-ellipsis\">" + category.getDatePost() + "</span></td>\n"
                    + "                                        <td><span class=\"text-ellipsis\">" + category.getDateUpdate() + "</span></td>\n"
                    + "                                        <td><span class=\"text-ellipsis\">" + category.getSlug() + "</span></td>\n"
                    + "                                        <td>\n";
            if (category.getStatus() == 1) {
                htmlReturn += "<span class=\"label label-success\">Normal</span>\n";
            } else {
                htmlReturn += "<span class=\"label label-default\">Hidden</span>\n";
            }
            htmlReturn += "                                        </td>\n"
                    + "                                        <td class=\"box-action\">\n"
                    + "                                            <a href=\"/admin/category/update/" + category.getSlug() + "\" class=\"btn-action green\">\n"
                    + "                                                <i class=\"bx bx-edit\"></i>\n"
                    + "                                            </a>\n"
                    + "                                            <a onclick=\"return confirm('Are you sure to delete?')\" \n"
                    + "                                               href=\"/admin/category/delete/" + category.getSlug() + "\" \n"
                    + "                                               class=\"btn-action orange\">\n"
                    + "                                                <i class='bx bx-trash'></i>\n"
                    + "                                            </a>\n"
                    + "                                        </td>\n"
                    + "                                    </tr>";
        }
        return htmlReturn;
    }

    private String ProducerFilterStatus(List<Producer> producers) {
        String htmlReturn = "";
        for (Producer producer : producers) {
            htmlReturn += "<tr>\n"
                    + "                                        <td><input type=\"checkbox\" name=\"delete-category-item\" value=\"" + producer.getSlug() + "\"></td>\n"
                    + "                                        <td>" + producer.getName() + "</td>\n"
                    + "                                        <td><span class=\"text-ellipsis\">" + producer.getDatePost() + "</span></td>\n"
                    + "                                        <td><span class=\"text-ellipsis\">" + producer.getDateUpdate() + "</span></td>\n"
                    + "                                        <td><span class=\"text-ellipsis\">" + producer.getSlug() + "</span></td>\n"
                    + "                                        <td>\n";
            if (producer.getStatus() == 1) {
                htmlReturn += "<span class=\"label label-success\">Normal</span>\n";
            } else {
                htmlReturn += "<span class=\"label label-default\">Hidden</span>\n";
            }
            htmlReturn += "                                        </td>\n"
                    + "                                        <td class=\"box-action\">\n"
                    + "                                            <a href=\"/admin/category/update/" + producer.getSlug() + "\" class=\"btn-action green\">\n"
                    + "                                                <i class=\"bx bx-edit\"></i>\n"
                    + "                                            </a>\n"
                    + "                                            <a onclick=\"return confirm('Are you sure to delete?')\" \n"
                    + "                                               href=\"/admin/category/delete/" + producer.getSlug() + "\" \n"
                    + "                                               class=\"btn-action orange\">\n"
                    + "                                                <i class='bx bx-trash'></i>\n"
                    + "                                            </a>\n"
                    + "                                        </td>\n"
                    + "                                    </tr>";
        }
        return htmlReturn;
    }

    private String ProductFilterStatus(List<Product> products) {
        CurrencyConverter currency = new CurrencyConverter();
        String htmlReturn = "";
        for (Product product : products) {
            htmlReturn += "<tr>\n"
                    + "                                        <td><input type=\"checkbox\" name=\"delete-product-item\" value=\"" + product.getSlug() + "\"></td>\n"
                    + "                                        <td>" + product.getName() + "</td>\n"
                    + "                                        <td>\n"
                    + "                                            <img src=\"" + product.getMainImg() + "\" alt=\"alt\"/>\n"
                    + "                                        </td>\n"
                    + "                                        <td><span class=\"text-ellipsis\">" + currency.currencyFormat(product.getOldPrice(), "") + "</span></td>\n"
                    + "                                        <td><span class=\"text-ellipsis\">" + currency.currencyFormat(product.getNewPrice(), "") + "</span></td>\n"
                    + "                                        <td>\n";
            if (product.getAvailable() > 0) {
                htmlReturn += "<span>" + product.getAvailable() + "</span>\n";
            } else {
                htmlReturn += "<span class=\"label label-danger\">Sold out</span>\n";
            }
            htmlReturn += "                 </td>\n"
                    + "                                        <td><span class=\"text-ellipsis\">" + product.getDatePost() + "</span></td>\n"
                    + "                                        <td><span class=\"text-ellipsis\">" + product.getDateUpdate() + "</span></td>\n"
                    + "                                        <td><span class=\"text-ellipsis\">" + product.getSlug() + "</span></td>\n"
                    + "                                        <td>\n";
            if (product.getStatus() == 1) {
                htmlReturn += "<span class=\"label label-success\">Normal</span>\n";
            } else {
                htmlReturn += "<span class=\"label label-default\">Hidden</span>\n";
            }
            htmlReturn += "                                        </td>\n"
                    + "                                        <td class=\"box-action\">\n"
                    + "                                            <a href=\"/admin/product/view/" + product.getSlug() + "\" class=\"btn-action blue\">\n"
                    + "                                                <i class='bx bx-slider'></i>\n"
                    + "                                            </a> \n"
                    + "                                            <a href=\"/admin/product/update/" + product.getSlug() + "\" class=\"btn-action green\">\n"
                    + "                                                <i class=\"bx bx-edit\"></i>\n"
                    + "                                            </a>\n"
                    + "                                            <a onclick=\"return confirm('Are you sure to delete?')\" \n"
                    + "                                               href=\"/admin/product/delete/" + product.getSlug() + "\" \n"
                    + "                                               class=\"btn-action orange\">\n"
                    + "                                                <i class='bx bx-trash'></i>\n"
                    + "                                            </a>\n"
                    + "                                        </td>\n"
                    + "                                    </tr>";
        }
        return htmlReturn;
    }

    private String BillFilterStatus(List<Bill> bills) {
        CurrencyConverter currency = new CurrencyConverter();
        String htmlReturn = "";
        for (Bill bill : bills) {
            htmlReturn += "<tr>\n"
                    + "                                        <td><input type=\"checkbox\" name=\"deleteBill\" value=\"" + bill.getId() + "\"></td>\n"
                    + "                                        <td>" + bill.getEmail() + "</td>\n"
                    + "                                        <td>" + bill.getCustomerName() + "</td>\n"
                    + "                                        <td>" + bill.getPhone() + "</td>\n"
                    + "                                        <td>" + bill.getAddress() + "</td>\n"
                    + "                                        <td>" + currency.currencyFormat(bill.getTotal(), "") + "</td>\n"
                    + "                                        <td>\n";
            if (bill.getPayment() == 1) {
                htmlReturn += "<span class=\"label label-danger\">Banking</span>\n";
            } else {
                htmlReturn += "<span class=\"label label-info\">Cash</span>\n";
            }
            htmlReturn += "                                        </td>\n"
                    + "                                        <td>\n";
            switch (bill.getStatus()) {
                case 0:
                    htmlReturn += "<span class=\"label label-default\">\n"
                            + "                                                    <i class='bx bx-block' ></i>\n"
                            + "                                                    Cancel\n"
                            + "                                                </span>\n";
                    break;
                case 1:
                    htmlReturn += "<span class=\"label label-primary\">\n"
                            + "                                                    <i class='bx bxs-message-rounded-add'></i>\n"
                            + "                                                    New\n"
                            + "                                                </span>\n";
                    break;
                case 2:
                    htmlReturn += "<span class=\"label label-warning\">\n"
                            + "                                                    <i class='bx bx-show' ></i>\n"
                            + "                                                    Accept\n"
                            + "                                                </span>\n";
                    break;
                case 3:
                    htmlReturn += "<span class=\"label label-info\">\n"
                            + "                                                    <i class='bx bx-package' ></i>\n"
                            + "                                                    Preparing\n"
                            + "                                                </span>\n";
                    break;
                case 4:
                    htmlReturn += "<span class=\"label label-danger\">\n"
                            + "                                                    <i class='bx bxs-truck' ></i>\n"
                            + "                                                    Delivery\n"
                            + "                                                </span>\n";
                    break;
                case 5:
                    htmlReturn += "<span class=\"label label-success\">\n"
                            + "                                                    <i class='bx bxs-check-circle' ></i>\n"
                            + "                                                    Finish\n"
                            + "                                                </span>\n";
                    break;
                default:
                    throw new AssertionError();
            }

            htmlReturn += "                                        </td>\n"
                    + "                                        <td><span class=\"text-ellipsis\">" + bill.getDateOrder() + "</span></td>\n"
                    + "                                        <td><span class=\"text-ellipsis\">" + bill.getDateUpdate() + "</span></td>\n"
                    + "                                        <td class=\"box-action\">\n"
                    + "                                            <a href=\"/admin/bill/view/" + bill.getId() + "\" class=\"btn-action blue\">\n"
                    + "                                                <i class='bx bx-slider' ></i>\n"
                    + "                                            </a>   \n"
                    + "                                            <a onclick=\"return confirm('Are you sure to delete?')\" \n"
                    + "                                               href=\"/admin/bill/delete/" + bill.getId() + "\" \n"
                    + "                                               class=\"btn-action orange\">\n"
                    + "                                                <i class='bx bx-trash'></i>\n"
                    + "                                            </a>\n"
                    + "                                        </td>\n"
                    + "                                    </tr>";
        }
        return htmlReturn;
    }

    private String VoucherFilterStatus(List<Voucher> vouchers) {
        Date now = Date.valueOf(LocalDate.now());
        String htmlReturn = "";
        for (Voucher voucher : vouchers) {
            htmlReturn += "<tr>\n"
                    + "                                        <td><input type=\"checkbox\" name=\"delete-voucher-item\" value=\"" + voucher.getId() + "\"></td>\n"
                    + "                                        <td>" + voucher.getName() + "</td>\n"
                    + "                                        <td><span class=\"label label-primary\">" + voucher.getCode() + "</span></td>\n"
                    + "                                        <td>\n";
            if (voucher.getType() == 1) {
                htmlReturn += "<span class=\"label label-success\">By percent</span>\n";
            } else {
                htmlReturn += "<span class=\"label label-default\">By price of bill</span>\n";
            }
            htmlReturn += "                                        </td>\n"
                    + "                                      <td>" + voucher.getValue() + "</td>\n"
                    + "                                        <td><span class=\"text-ellipsis\">" + voucher.getStart() + "</span></td>\n"
                    + "                                        <td><span class=\"text-ellipsis\">" + voucher.getEnd() + "</span></td>\n"
                    + "                                        <td>\n";
            if (voucher.getEnd().after(now)) {
                htmlReturn += "<span class=\"label label-warning\">Activing</span>\n";
            } else {
                htmlReturn += "<span class=\"label label-danger\">Expired</span>\n";
            }
            htmlReturn += "                                        </td>\n"
                    + "                                        <td>\n";
            if (voucher.getStatus() == 1) {
                htmlReturn += "<span class=\"label label-success\">Normal</span>\n";
            } else {
                htmlReturn += "<span class=\"label label-default\">Hidden</span>\n";
            }
            htmlReturn += "                                        </td>\n"
                    + "                                        <td class=\"box-action\">\n"
                    + "                                            <a href=\"/admin/voucher/update/" + voucher.getId() + "\" class=\"btn-action green\">\n"
                    + "                                                <i class=\"bx bx-edit\"></i>\n"
                    + "                                            </a>\n"
                    + "                                            <a onclick=\"return confirm('Are you sure to delete?')\" \n"
                    + "                                               href=\"/admin/voucher/delete/" + voucher.getId() + "\" \n"
                    + "                                               class=\"btn-action orange\">\n"
                    + "                                               <i class='bx bx-trash'></i>\n"
                    + "                                            </a>\n"
                    + "                                        </td>\n"
                    + "                                    </tr>";
        }
        return htmlReturn;
    }

    private String CompanyFilterStatus(List<Company> companies) {
        Date now = Date.valueOf(LocalDate.now());
        String htmlReturn = "";
        for (Company company : companies) {
            htmlReturn += "<tr>\n"
                    + "                                        <td><input type=\"checkbox\" name=\"delete-company-item\" value=\"" + company.getId() + "\"></td>\n"
                    + "                                        <td>" + company.getName() + "</td>\n"
                    + "                                        <td><span class=\"text-ellipsis\">" + company.getAddress() + "</span></td>\n"
                    + "                                        <td><span class=\"text-ellipsis\">" + company.getEmail() + "</span></td>\n"
                    + "                                        <td><span class=\"text-ellipsis\">" + company.getPhone() + "</span></td>\n"
                    + "                                        <td><span class=\"text-ellipsis\">" + company.getIntroduce() + "</span></td>\n"
                    + "                                        <td><span class=\"text-ellipsis\">" + company.getDate() + "</span></td>\n"
                    + "                                        <td>\n";
            if (company.getStatus() == 1) {
                htmlReturn += "<span class=\"label label-success\">Active</span>\n";
            } else {
                htmlReturn += "<span class=\"label label-default\">Hidden</span>\n";
            }
            htmlReturn += "                                        </td>\n"
                    + "                                        <td class=\"box-action\">\n"
                    + "                                            <a href=\"/admin/company/update/" + company.getId() + "\" class=\"active btn-action green\">\n"
                    + "                                                <i class=\"bx bx-edit\"></i>\n"
                    + "                                            </a>\n"
                    + "                                            <a onclick=\"return confirm('Are you sure to delete?')\" \n"
                    + "                                               href=\"/admin/company/delete/" + company.getId() + "\" \n"
                    + "                                               class=\"active btn-action orange\">\n"
                    + "                                                <i class='bx bx-trash'></i>\n"
                    + "                                            </a>\n"
                    + "                                        </td>\n"
                    + "                                    </tr>";
        }
        return htmlReturn;
    }
}
