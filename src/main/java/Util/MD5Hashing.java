package Util;

import jakarta.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Le Tan Kim
 */
public class MD5Hashing {

    public String hashPassword(String input) {
        String myHash = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes());
            byte[] digest = md.digest();
            myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Hashing error: " + e);
        }
        return myHash;
    }
}
