package dao;

import javafx.collections.ObservableList;
import model.Contact;

/**
 * This class provides the dbcontact methods to the customer appointment application.
 * @uthor Crystal Lu
 */

public interface DBContact {

    /**
     This observable list of get all contacts.
     */

    public ObservableList<Contact> getAllContacts();

    /**
     This is the method to get a contact by contact id.

     @param contactId gets contacts by contact id.
     */

    public Contact getContact(int contactId);

    /**
     This method adds new contact by contact name.

     @param contactName adds contact by contact name.
     */

    public int addContact(String contactName);

    /**
     This is the update method that accesses the database and updates a contact.

     @param contactId updates associated contact ID.
     @param oldContactName updates associated old contact name.
     @param newContactName updates the associated to new contact name.
     */


    public int updateContact(int contactId, String oldContactName, String newContactName);

    /**
     This is the delete method that accesses the database and removes the contact.

     @param contactId the selected contact by unique contact ID.
     */

    public int deleteContact(int contactId);


}
