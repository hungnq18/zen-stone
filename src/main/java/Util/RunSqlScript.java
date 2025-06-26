package Util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;

import DBConnection.DBConnection;

public class RunSqlScript {
    public static void main(String[] args) {
        try {
            System.out.println("Connecting to database to run initial script...");
            Connection conn = DBConnection.connect();
            if (conn == null) {
                System.err.println("Failed to connect to the database. Aborting script execution.");
                return;
            }
            
            System.out.println("Connection successful. Reading SQL script file...");
            Statement stmt = conn.createStatement();
            
            // Path to your SQL file
            String sqlFilePath = "db/db.sql";
            
            BufferedReader reader = new BufferedReader(new FileReader(sqlFilePath));
            String line;
            StringBuilder sqlStatement = new StringBuilder();
            
            System.out.println("Executing SQL statements...");
            while ((line = reader.readLine()) != null) {
                // Ignore comments and empty lines
                if (line.trim().isEmpty() || line.trim().startsWith("--")) {
                    continue;
                }
                
                sqlStatement.append(line);
                
                // If the line ends with a semicolon, it's the end of a statement
                if (line.trim().endsWith(";")) {
                    try {
                        stmt.execute(sqlStatement.toString());
                    } catch (Exception e) {
                        System.err.println("Failed to execute: " + sqlStatement.toString());
                        System.err.println(e.getMessage());
                    }
                    sqlStatement.setLength(0); // Clear the buffer
                }
            }
            
            reader.close();
            stmt.close();
            conn.close();
            System.out.println("SQL script executed successfully.");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 