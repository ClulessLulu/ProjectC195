package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * This class provides the contact implementation methods to the customer appointment application.
 * This class implements the DBContact class
 * @uthor Crystal Lu
 */

public class DBContactImpl implements DBContact{

    /**
     This is the all contacts observable list.
     */

    ObservableList<Contact> allContacts = FXCollections.observableArrayList();


    /**
     This is the get all contacts method.
     This method accesses the database and returns all contacts which is then added to the observable list.

     @return allContacts
     */

    @Override
    public ObservableList<Contact> getAllContacts() {
        try{
            String sql = "SELECT * FROM contacts";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                Contact contact = new Contact(contactId, contactName);
                allContacts.add(contact);
            }
        } catch (Exception e) {
            System.out.println("Contact Get All Error: " + e.getMessage());
        }
        return allContacts;
    }

    /**
     This is the method to get a contact by contactID.
     This method accesses the database and returns contact by the contact ID.

     @param contactId gets contacts by contact ID.
     @return null returns no results if contact ID does not exist
     */

    @Override
    public Contact getContact(int contactId) {
        try{
            String sql = "SELECT * FROM contacts WHERE Contact_ID=?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, contactId);
            ResultSet rs = ps.executeQuery();
            Contact contactResult = null;

            if(rs.next()){
                contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                contactResult = new Contact(contactId, contactName);
            }
            return contactResult;
        } catch (Exception e) {
            System.out.println("Contact Get Error: " + e.getMessage());
        }
        return null;
    }

    /**
     This method adds new contact by contact name.
     This method accesses the database and insert contact by the contact name.

     @param contactName adds contact by contact name.
     @return rowsAffected which are the number of rows affected in the database.
     */

    @Override
    public int addContact(String contactName) {
        int rowsAffected = 0;
        try {
            String sql = "INSERT INTO contacts (Contact_Name) VALUE(?)";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, contactName);
            rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0){
                System.out.println("Contact Insert Successful");
            }
            else{
                System.out.println("Contact Insert Failed");
            }

        } catch (Exception e) {
            System.out.println("Contact Insert Error: " + e.getMessage());
        }

        return rowsAffected;
    }

    /**
     This is the update method that accesses the database and updates a contact.

     @param contactId updates associated contact ID.
     @param oldContactName updates associated old contact name.
     @param newContactName updates the associated to new contact name.
     @return rowsAffected which are the number of rows affected in the database.
     */

    @Override
    public int updateContact(int contactId, String oldContactName, String newContactName) {
        int rowsAffected = 0;
        try{
            String sql = "UPDATE contacts SET Contact_Name=? WHERE Contact_Name=? AND Contact_ID=?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, contactId);
            ps.setString(2, oldContactName);
            ps.setString(3, newContactName);
            rowsAffected = ps.executeUpdate();

            if(rowsAffected > 0){
                System.out.println("Contact " + oldContactName + " was Updated Successful");
                System.out.println("Contact Updated to " + newContactName);
            }
            else {
                System.out.println("Contact Update Failed");
            }
        } catch (Exception e) {
            System.out.println("Contact Update Error: " + e.getMessage());
        }
        return rowsAffected;
    }

    /**
     This is the delete method that accesses the database and removes the contact.

     @param contactId the selected contact by unique contact ID.
     @return rowsAffected which are the number of rows affected in the database.
     */

    @Override
    public int deleteContact(int contactId) {
        int rowsAffected = 0;
        try{
            String sql = "DELETE FROM contacts WHERE Contact_ID=?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, contactId);
            rowsAffected = ps.executeUpdate();

            if(rowsAffected > 0){
                System.out.println("Contact ID " + contactId + " was Deleted Successful");
            }
            else {
                System.out.println("Contact Delete Failed");
            }
        } catch (Exception e) {
            System.out.println("Contact Delete Error: " + e.getMessage());
        }
        return rowsAffected;
    }
}
