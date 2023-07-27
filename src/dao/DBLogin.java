package dao;

import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * This class provides the DBlogin methods to the customer appointment application.
 * @uthor Crystal Lu
 */

public class DBLogin {

    /**
     * This method is used to get the user from the database.
     * This method accesses the database the gets the username and password to check if they are in the user database.
     *
     * @param userName gets username from user
     * @param password gets password from user
     * @return null returns no results if user does not exist.
     */


    public static User userLoginQuery (String userName, String password){
        try {
            String sql = "SELECT * FROM users WHERE User_Name =? AND Password =?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, userName);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            User userResult = null;
            if (rs.next()){
                int userId = rs.getInt("User_ID");
                userName = rs.getString("User_Name");
                password = rs.getString("Password");
                userResult = new User(userId, userName, password);
            }
            return  userResult;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    /**
     This is a method that is used to log the current operating system time and zone ID.

     @return loginTime returns the current zone ID from the operating system.
     */

    public static LocalDateTime getLoginTime(){
        LocalDateTime loginTime = LocalDateTime.now(ZoneId.systemDefault());
        return loginTime;
    }
}
