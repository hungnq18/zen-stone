/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Le Tan Kim
 */
public class Color {
    private int ID;
    private int productID;
    private String imgColor;
    private String name;
    private int quantity;

    public Color() {
    }

    public Color(int ID, int productID, String imgColor, String name) {
        this.ID = ID;
        this.productID = productID;
        this.imgColor = imgColor;
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getImgColor() {
        return imgColor;
    }

    public void setImgColor(String imgColor) {
        this.imgColor = imgColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
