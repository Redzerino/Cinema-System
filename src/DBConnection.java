// Name: Sarah Crotty
// Date: 11/02/2026
// Purpose: Handles database connection to MySQL

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection
{
    // Database connection details
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/novaplexdb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    // Establishes and returns a database connection
    public static Connection getConnection() throws SQLException 
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch(ClassNotFoundException e)
        {
            System.out.println("Driver not found: " + e.getMessage());
        }
        return DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
    }
}