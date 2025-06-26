package Model;

/**
 *
 * @author Le Tan Kim
 */
public class BillDetails {

    private int id;
    private int billID;
    private String imgProduct;
    private int numberOfProduct;
    private float priceProduct;
    private String modelProduct;
    private String nameProduct;
    private String color;

    public BillDetails() {
    }

    public BillDetails(int id, int billID, String imgProduct, int numberOfProduct, float priceProduct, String modelProduct, String nameProduct, String color) {
        this.id = id;
        this.billID = billID;
        this.imgProduct = imgProduct;
        this.numberOfProduct = numberOfProduct;
        this.priceProduct = priceProduct;
        this.modelProduct = modelProduct;
        this.nameProduct = nameProduct;
        this.color = color;
    }
   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBillID() {
        return billID;
    }

    public void setBillID(int billID) {
        this.billID = billID;
    }

    public String getImgProduct() {
        return imgProduct;
    }

    public void setImgProduct(String imgProduct) {
        this.imgProduct = imgProduct;
    }

    public int getNumberOfProduct() {
        return numberOfProduct;
    }

    public void setNumberOfProduct(int numberOfProduct) {
        this.numberOfProduct = numberOfProduct;
    }

    public float getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(float priceProduct) {
        this.priceProduct = priceProduct;
    }

    public String getModelProduct() {
        return modelProduct;
    }

    public void setModelProduct(String modelProduct) {
        this.modelProduct = modelProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
