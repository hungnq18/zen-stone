package Model;

import java.sql.Timestamp;

/**
 *
 * @author Le Tan Kim
 */
public class Bill {

    private int id;
    private int customerID;
    private String email;
    private String customerName;
    private String phone;
    private String address;
    private String detailAddress;
    private float total;
    private int status;
    private int payment;
    private Timestamp dateOrder;
    private Timestamp dateUpdate;
    private String transactionCode;

    public Bill() {
    }

    public Bill(int id, int customerID, String email, String customerName, String phone, String address, String detailAddress, float total, int status, int payment, Timestamp dateOrder, Timestamp dateUpdate) {
        this.id = id;
        this.customerID = customerID;
        this.email = email;
        this.customerName = customerName;
        this.phone = phone;
        this.address = address;
        this.detailAddress = detailAddress;
        this.total = total;
        this.status = status;
        this.payment = payment;
        this.dateOrder = dateOrder;
        this.dateUpdate = dateUpdate;
    }

    public Bill(int id, int customerID, String email, String customerName, String phone, String address, String detailAddress, float total, int status, int payment, Timestamp dateOrder, Timestamp dateUpdate, String transactionCode) {
        this.id = id;
        this.customerID = customerID;
        this.email = email;
        this.customerName = customerName;
        this.phone = phone;
        this.address = address;
        this.detailAddress = detailAddress;
        this.total = total;
        this.status = status;
        this.payment = payment;
        this.dateOrder = dateOrder;
        this.dateUpdate = dateUpdate;
        this.transactionCode = transactionCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public Timestamp getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(Timestamp dateOrder) {
        this.dateOrder = dateOrder;
    }

    public Timestamp getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Timestamp dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public void setAccount(Account account) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setProvince(String name) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setDistrict(String name) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
