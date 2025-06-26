/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    
    public static Connection connect() {
        try {
            System.out.println("[DBConnection] Loading JDBC driver...");
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("[DBConnection] JDBC driver loaded.");

            // Thông tin Azure SQL fix cứng
            String dbHost = "zenstone.database.windows.net";
            String dbName = "zen-stone";
            String dbUser = "adminuser"; // Thay bằng username thực tế
            String dbPassword = "Hung18102003";    // Thay bằng password thực tế

            String urlConnect = String.format(
                "jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;",
                dbHost, dbName, dbUser, dbPassword
            );
            System.out.println("[DBConnection] Connection string: " + urlConnect);
            System.out.println("[DBConnection] Attempting to connect to database...");
            Connection conn = DriverManager.getConnection(urlConnect);
            System.out.println("[DBConnection] Connection successful!");
            return conn;
        } catch(ClassNotFoundException ex) {
            System.out.println("[DBConnection] JDBC Driver not found: " + ex);
        } catch(SQLException ex) {
            System.out.println("[DBConnection] SQL Exception: " + ex);
        } catch(Exception ex) {
            System.out.println("[DBConnection] General Exception: " + ex);
        }
        System.out.println("[DBConnection] Connection failed!");
        return null;
    }
}
