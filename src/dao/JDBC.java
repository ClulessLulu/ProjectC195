package dao;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * This class provides the java database connection methods for the customer appointment application.
 * @uthor Crystal Lu
 */

public abstract class JDBC {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "sqlUser"; // Username
    private static String password = "Passw0rd!"; // Password
    public static Connection connection;  // Connection Interface

    /**
     * This is the open database connection method.
     * This method opens the connection between Java and mySQL database.
     */


    public static void openConnection()
    {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection Successful!");
        }
        catch(Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * This is the close database connection method.
     * This method closes the connection between Java and mySQL database.
     */

    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection Closed!");
        }
        catch(Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
