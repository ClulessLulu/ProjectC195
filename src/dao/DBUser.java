package dao;

import javafx.collections.ObservableList;
import model.User;

/**
 * This class provides the dbuser methods to the customer appointment application.
 * @uthor Crystal Lu
 */

public interface DBUser {

  /**
   * This observable list of get all users.
   */

  public ObservableList<User> getAllUsers();

  /**
   * This is the method gets users by user ID.
   *
   * @param userId gets division by country ID.
   */

  public User getUser(int userId);

  /**
   * This is the method that adds username and password.
   *
   * @param userName gets username of the user.
   * @param password gets password of the user.
   */

  public int addUser(String userName, String password);

  /**
   * This is the method that updates the username for the user.
   *
   * @param oldUserName get old username of the user.
   * @param newUserName updates new username of the user.
   * @param password gets password of the user.
   */

  public int updateUserName(String oldUserName, String newUserName, String password);

  /**
   * This is the method that updates the password for the user.
   *
   * @param userName get username of the user.
   * @param oldPassword gets old password of the user.
   * @param newPassword updates new password of the user.
   */

  public int updatePassword(String userName, String oldPassword, String newPassword);

  /**
   * This is the method deletes the selected user by user ID.
   *
   * @param userId gets user ID of the user.
   */

  public int deleteUser(int userId);



}
