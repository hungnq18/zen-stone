/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Timestamp;

/**
 *
 * @author Le Tan Kim
 */
public class Product {

    private int ID;
    private String name;
    private String description;
    private float oldPrice;
    private float newPrice;
    private Timestamp datePost;
    private Timestamp dateUpdate;
    private String mainImg;
    private int status;
    private String slug;
    private int available;
    private int sold;
    private String configProduct;
    private String model;
    private int priority;
    private int categoryID;
    private int producerID;

    public Product() {
    }

    public Product(int ID, String name, String description, float oldPrice, float newPrice, Timestamp datePost, Timestamp dateUpdate, String mainImg, int status, String slug,int available, int sold, String configProduct, String model, int priority, int categoryID, int producerID) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
        this.datePost = datePost;
        this.dateUpdate = dateUpdate;
        this.mainImg = mainImg;
        this.status = status;
        this.slug = slug;
        this.available = available;
        this.sold = sold;
        this.configProduct = configProduct;
        this.model = model;
        this.priority = priority;
        this.categoryID = categoryID;
        this.producerID = producerID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(float oldPrice) {
        this.oldPrice = oldPrice;
    }

    public float getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(float newPrice) {
        this.newPrice = newPrice;
    }

    public Timestamp getDatePost() {
        return datePost;
    }

    public void setDatePost(Timestamp datePost) {
        this.datePost = datePost;
    }

    public Timestamp getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Timestamp dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public String getMainImg() {
        return mainImg;
    }

    public void setMainImg(String mainImg) {
        this.mainImg = mainImg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public String getConfigProduct() {
        return configProduct;
    }

    public void setConfigProduct(String configProduct) {
        this.configProduct = configProduct;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getProducerID() {
        return producerID;
    }

    public void setProducerID(int producerID) {
        this.producerID = producerID;
    }

    public int getStock() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int getPrice() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setStock(int i) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
