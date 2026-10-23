// Name: Sarah Crotty
// Date: 11/02/2026
// Purpose: Handles database connection to MySQL.
//          Connection details are read from db.properties in the project folder,
//          so they are not hard-coded. If the file is missing, local defaults are used.

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection
{
    // Config file and fallback values for a local MySQL install
    private static final String CONFIG_FILE = "db.properties";
    private static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/novaplexdb";
    private static final String DEFAULT_USERNAME = "root";
    private static final String DEFAULT_PASSWORD = "root";

    private static final Properties config = loadConfig();

    // Reads the connection details from db.properties, if it exists
    private static Properties loadConfig()
    {
        Properties props = new Properties();
        try(FileInputStream in = new FileInputStream(CONFIG_FILE))
        {
            props.load(in);
        }
        catch(IOException e)
        {
            System.out.println("No " + CONFIG_FILE + " found, using default local database settings.");
        }
        return props;
    }

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
        String url = config.getProperty("db.url", DEFAULT_URL);
        String username = config.getProperty("db.username", DEFAULT_USERNAME);
        String password = config.getProperty("db.password", DEFAULT_PASSWORD);
        return DriverManager.getConnection(url, username, password);
    }
}
