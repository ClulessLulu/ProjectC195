package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * This class provides the user implementation methods to the customer appointment application.
 * This class implements the DBUSer class
 * @uthor Crystal Lu
 */

public class DBUserImpl implements DBUser{

    /**
     This is the all users observable list.
     */


    public ObservableList<User> allUsers = FXCollections.observableArrayList();

    /**
     * This observable list of get all users.
     * This method accesses the database and gets the selected user.
     *
     * @return allUsers returns all the users.
     */

    @Override
    public ObservableList<User> getAllUsers() {
      try {
          String sql = "SELECT * FROM users";
          PreparedStatement ps = JDBC.connection.prepareStatement(sql);
          ResultSet rs = ps.executeQuery();

          while (rs.next()){
              int userId = rs.getInt("User_ID");
              String userName = rs.getString("User_Name");
              String password = rs.getString("Password");
              User user = new User(userId, userName, password);
              allUsers.add(user);
          }
      } catch (Exception e) {
          System.out.println("User Get All Error: " + e.getMessage());
      }
        return allUsers;
    }

    /**
     * This is the method gets users by user ID.
     * This method accesses the database and gets the selected user.
     *
     * @param userId gets division by country ID.
     * @return userResult returns the selected user.
     * @return  null returns no results if user ID does not exist
     */

    @Override
    public User getUser(int userId) {
        try{
            String sql = "SELECT * FROM users WHERE User_ID=?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();
            User userResult = null;
            if(rs.next()){
                userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");
                userResult = new User(userId, userName, password);
            }
            return userResult;

        } catch (Exception e) {
            System.out.println("User Get ID Error: " + e.getMessage());
        }
        return null;
    }

    /**
     * This is the method that adds username and password.
     *
     * @param userName gets username of the user.
     * @param password gets password of the user.
     * @return rowsAffected which are the number of rows affected in the database.
     */


    @Override
    public int addUser(String userName, String password) {
        int rowsAffected = 0;
        try{
            String sql = "INSERT INTO users (User_Name, Password) VALUE(?, ?)";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, userName);
            ps.setString(2, password);
            rowsAffected = ps.executeUpdate();

            if(rowsAffected > 0){
                System.out.println("User Insert Successful");
            }
            else{
                System.out.println("User Insert Failed");
            }
        } catch (Exception e) {
            System.out.println("User Insert Error: " + e.getMessage());
        }
        return rowsAffected;
    }

    /**
     * This is the method that updates the username for the user.
     * This method accesses the database and updates the old username to a new username.
     *
     * @param oldUserName get old username of the user.
     * @param newUserName updates new username of the user.
     * @param password gets password of the user.
     * @return rowsAffected which are the number of rows affected in the database.
     */


    @Override
    public int updateUserName(String oldUserName, String newUserName, String password) {
        int rowsAffected = 0;
        try{
            String sql = "UPDATE users SET User_Name=? WHERE User_Name=? AND Password=?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, oldUserName);
            ps.setString(2, newUserName);
            ps.setString(3, password);
            rowsAffected = ps.executeUpdate();

            if(rowsAffected > 0){
                System.out.println("Username " + oldUserName + " was Updated Successfully");
                System.out.println("Username Updated to " + newUserName);
            }
            else{
                System.out.println("Username " + oldUserName + " Update Failed");
            }

        } catch (Exception e) {
            System.out.println("Username Update Error: " + e.getMessage());
        }

        return rowsAffected;
    }

    /**
     * This is the method that updates the password for the user.
     * This method accesses the database and updates the old password to a new password.
     *
     * @param userName get username of the user.
     * @param oldPassword gets old password of the user.
     * @param newPassword updates new password of the user.
     * @return rowsAffected which are the number of rows affected in the database.
     */

    @Override
    public int updatePassword(String userName, String oldPassword, String newPassword) {
        int rowsAffected = 0;
        try{
            String sql = "UPDATE users SET User_Name=? WHERE User_Name=? AND Password=?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, userName);
            ps.setString(2, oldPassword);
            ps.setString(3, newPassword);
            rowsAffected = ps.executeUpdate();

            if(rowsAffected > 0){
                System.out.println("Password for " + userName + " was Updated Successfully");
            }
            else{
                System.out.println("Password  for" + userName + " Update Failed");
            }

        } catch (Exception e) {
            System.out.println("User Password Update Error: " + e.getMessage());
        }
        return rowsAffected;
    }

    /**
     * This is the method deletes the selected user by user ID.
     * This method accesses the database and deletes the selected user ID.
     *
     * @param userId gets user ID of the user.
     * @return rowsAffected which are the number of rows affected in the database.
     */

    @Override
    public int deleteUser(int userId) {
        int rowsAffected = 0;
        try{
            String sql = "DELETE FROM users WHERE User_ID=?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, userId);
            rowsAffected = ps.executeUpdate();

            if(rowsAffected > 0){
                System.out.println("User ID " + userId + " was Deleted Successfully" );
            }
            else{
                System.out.println("User ID " + userId + " Delete Failed");
            }

        } catch (Exception e) {
            System.out.println("User Delete Error: " + e.getMessage());
        }
        return rowsAffected;
    }
}
