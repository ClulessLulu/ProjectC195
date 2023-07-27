package model;

/**
 This class for customer to the customer appointment application.

 @uthor Crystal Lu
 */

public class Customer {

    /**
     The ID for the Customer.
     */

    private int customerId;

    /**
     The ID for the Country.
     */

    private int countryId;

    /**
     The ID for the Division.
     */

    private int divisionId;

    /**
     The name for the Customer.
     */

    private String customerName;

    /**
     The address for the Customer.
     */

    private String address;

    /**
     The postal code for the Customer.
     */

    private String postalCode;

    /**
     The phone number for the Customer.
     */

    private String phone;

    /**
     The country name for the Customer.
     */

    private String countryName;

    /**
     The division name for the Customer.
     */


    private String divisionName;

    /**
     This is the Contact constructor.

     @param customerId The customer ID for the Customer.
     @param countryId The country ID for the Customer.
     @param divisionId The division ID for the Customer.
     @param customerName The name for the Customer.
     @param address The address for the Customer.
     @param postalCode The postal code for the Customer.
     @param phone The phone number for the Customer.
     @param countryName The country name for the Customer.
     @param divisionName The division name for the Customer.
     */

    public Customer(int customerId, int countryId, int divisionId, String customerName, String address, String postalCode, String phone, String countryName, String divisionName) {
        this.customerId = customerId;
        this.countryId = countryId;
        this.divisionId = divisionId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.countryName = countryName;
        this.divisionName = divisionName;
    }

    /**
     This is the getter for customer ID.
     @return Returns the customer ID.
     */


    public int getCustomerId() {
        return customerId;
    }

    /**
     This is the setter for customer ID.
     @param customerId The ID for customer.
     */

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     This is the getter for country ID.
     @return Returns the country ID.
     */


    public int getCountryId() {
        return countryId;
    }

    /**
     This is the setter for country ID.
     @param countryId The ID for country.
     */

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     This is the getter for division ID.
     @return Returns the division ID.
     */


    public int getDivisionId() {
        return divisionId;
    }

    /**
     This is the setter for division ID.
     @param divisionId The ID for division.
     */

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     This is the getter for customer name.
     @return Returns the customer name.
     */

    public String getCustomerName() {
        return customerName;
    }

    /**
     This is the setter for customer name.
     @param customerName The name for customer.
     */

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     This is the getter for address.
     @return Returns the address.
     */

    public String getAddress() {
        return address;
    }

    /**
     This is the setter for address.
     @param address The name for address.
     */

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     This is the getter for postal code.
     @return Returns the postal code.
     */

    public String getPostalCode() {
        return postalCode;
    }

    /**
     This is the setter for postal code.
     @param postalCode The name for postal code.
     */

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     This is the getter for phone.
     @return Returns the phone.
     */

    public String getPhone() {
        return phone;
    }

    /**
     This is the setter for phone.
     @param phone The name for phone.
     */

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     This is the getter for country name.
     @return Returns the country name.
     */

    public String getCountryName() {
        return countryName;
    }

    /**
     This is the setter for county name.
     @param countryName The name for country.
     */


    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     This is the getter for division name.
     @return Returns the division name.
     */

    public String getDivisionName() {
        return divisionName;
    }

    /**
     This is the setter for division name.
     @param divisionName The name for division.
     */

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     This is the Customer toString method.

     This method provides default syntax for customer information.
     */

    public String toString() {
        return ("[" + Integer.toString(customerId) + "] " + customerName);
    }
}
