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
            
            // Read from Java System Properties, with fallbacks for local development
            String dbHost = System.getProperty("db.host", "localhost");
            String dbName = System.getProperty("db.name", "swp_tech");
            String dbUser = System.getProperty("db.user", "hungnq18");
            String dbPassword = System.getProperty("db.password", "123");

            String urlConnect = String.format("jdbc:sqlserver://%s:1433;databasename=%s;user=%s;password=%s;characterEncoding=UTF-8;encrypt=true;trustServerCertificate=true;",
                                              dbHost, dbName, dbUser, dbPassword);

            // tao doi tuong connection 
            Connection conn = DriverManager.getConnection(urlConnect);
            return conn;
        } catch(ClassNotFoundException | SQLException ex) {
            System.out.println(ex);
        } 
        return null;
    }
}
