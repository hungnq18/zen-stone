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
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            
            // Thông tin Azure SQL fix cứng
            String dbHost = "zen-stone.database.windows.net";
            String dbName = "zen-stone";
            String dbUser = "adminuser@zenstone"; // Thay bằng username thực tế
            String dbPassword = "Hung18102003";    // Thay bằng password thực tế

            String urlConnect = String.format(
                "jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;",
                dbHost, dbName, dbUser, dbPassword
            );

            // tao doi tuong connection 
            Connection conn = DriverManager.getConnection(urlConnect);
            return conn;
        } catch(ClassNotFoundException | SQLException ex) {
            System.out.println(ex);
        } 
        return null;
    }
}
