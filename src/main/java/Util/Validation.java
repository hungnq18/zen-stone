/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

/**
 *
 * @author Le Tan Kim
 */
public class Validation {

    public int getInt(String input) {
        int result = -1;
        try {
            result = Integer.parseInt(input);
        } catch (NumberFormatException er) {

        }
        return result;
    }

    public double getDouble(String input) {
        double result = -1;
        try {
            result = Double.parseDouble(input);
        } catch (NumberFormatException er) {

        }
        return result;
    }
    
    public float getFloat(String input) {
        float result = -1;
        try {
            result = Float.parseFloat(input);
        } catch (NumberFormatException er) {

        }
        return result;
    }
}
