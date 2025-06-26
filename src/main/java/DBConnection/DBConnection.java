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
            
            String dbHost = System.getenv("DB_HOST");
            String dbName = System.getenv("DB_NAME");
            String dbUser = System.getenv("DB_USER");
            String dbPassword = System.getenv("DB_PASSWORD");

            if (dbHost == null) dbHost = "localhost";
            if (dbName == null) dbName = "swp_tech";
            if (dbUser == null) dbUser = "hungnq18";
            if (dbPassword == null) dbPassword = "123";

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
