package Util;

/**
 *
 * @author HP
 */
public class Sale {
    public String calculateSale(double  newPrice, double oldPrice) {
        long sale = Math.round(Math.ceil(((oldPrice - newPrice) / oldPrice) * 100));
        return sale + " %";
    }
}
