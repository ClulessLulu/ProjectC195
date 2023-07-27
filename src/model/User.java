package model;

/**
 This class for user to the customer appointment application.

 @uthor Crystal Lu
 */

public class User {

    /**
     The ID for the User.
     */

    private int userId;

    /**
     The username for the User.
     */

    private String userName;

    /**
     The password for the User.
     */

    private String password;

    /**
     This is the User constructor.

     @param userId The ID for the User.
     @param userName The username for the User.
     @param password The password for the User.
     */

    public User(int userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    /**
     This is the getter for user ID.
     @return Returns the user ID.
     */

    public int getUserId() {
        return userId;
    }

    /**
     This is the setter for user ID.
     @param userId The ID for user.
     */

    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     This is the getter for username.
     @return Returns the username.
     */

    public String getUserName() {
        return userName;
    }

    /**
     This is the setter for username.
     @param userName The username for user.
     */

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     This is the getter for user password.
     @return Returns the user password.
     */

    public String getPassword() {
        return password;
    }

    /**
     This is the setter for password.
     @param password The password for user.
     */

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     This is the User toString method.

     This method provides default syntax for user information.
     */

    public String toString() {
        return ("[" + Integer.toString(userId) + "] " + userName);}

}