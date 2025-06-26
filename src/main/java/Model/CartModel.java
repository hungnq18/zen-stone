package Model;

/**
 *
 * @author Le Tan Kim
 */
public class CartModel {

    private int id;
    private int numberOfProduct;
    private String color;
    private int quantityColor;
    private int colorId;

    public CartModel() {
    }

    public CartModel(int id, int numberOfProduct) {
        this.id = id;
        this.numberOfProduct = numberOfProduct;
    }
    public CartModel(int id, int numberOfProduct, String color) {
        this.id = id;
        this.numberOfProduct = numberOfProduct;
        this.color = color;
    }

    public int getQuantityColor() {
        return quantityColor;
    }

    public void setQuantityColor(int quantityColor) {
        this.quantityColor = quantityColor;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberOfProduct() {
        return numberOfProduct;
    }

    public void setNumberOfProduct(int numberOfProduct) {
        this.numberOfProduct = numberOfProduct;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
}
