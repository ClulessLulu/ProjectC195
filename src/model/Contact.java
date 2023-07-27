package model;

/**
 This class for contact to the customer appointment application.

 @uthor Crystal Lu
 */

public class Contact {

    /**
     The ID for the Contact.
     */

    public int contactId;

    /**
     The name for the Contact.
     */

    public String contactName;

    /**
     This is the Contact constructor.

      @param contactId The contact ID for the Contact.
      @param contactName The name for the Contact.
      */

    public Contact(int contactId, String contactName) {
        this.contactId = contactId;
        this.contactName = contactName;
    }

    /**
     This is the getter for contact ID.
     @return Returns the contact ID.
     */

    public int getContactId() {
        return contactId;
    }

    /**
     This is the setter for contact ID.
     @param contactId The ID for contact.
     */

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     This is the getter for contact name.
     @return Returns the contact name.
     */

    public String getContactName() {
        return contactName;
    }

    /**
     This is the setter for contact name.
     @param contactName The name for contact.
     */

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     This is the Contact toString method.

     This method provides default syntax for contact information.
     */

    public String toString() {
        return ("[" + Integer.toString(contactId) + "] " + contactName);
    }
}
