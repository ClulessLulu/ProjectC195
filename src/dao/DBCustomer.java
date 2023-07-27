package dao;

import javafx.collections.ObservableList;
import model.Customer;

/**
 * This class provides the dbcustomer methods to the customer appointment application.
 * @uthor Crystal Lu
 */

public  interface DBCustomer {

    /**
     This observable list of get all customers.
     */

    public  ObservableList<Customer> getAllCx();

    /**
     This is the observable list to get a customers by country id.

     @param countryId gets customers by country ID.
     */


    public ObservableList<Customer> getCxByCountry(int countryId);

    /**
     This is the observable list to get a customers by customer name.

     @param customerName gets customers by customer name.
     */

    public ObservableList<Customer> lookupCx (String customerName);

    /**
     This method gets a customer by customer ID.

     @param customerId gets customer by customer ID.
     */

    public Customer getCx(int customerId);

    /**
     This method adds new customer to the database.

     @param customerName adds customer by customer  name.
     @param address adds customer address.
     @param postalCode adds customer postal code.
     @param phone adds customer phone number.
     @param divisionId adds customer first level division.
     */

    public int addCx(String customerName, String address, String postalCode, String phone, int divisionId);

    /**
     This method updates customer from the database.

     @param customerId updates customer id
     @param customerName updates customer by customer  name.
     @param address updates customer address.
     @param postalCode updates customer postal code.
     @param phone updates customer phone number.
     @param divisionId updates customer first level division.
     */


    public int updateCx(int customerId, String customerName, String address, String postalCode, String phone, int divisionId);

    /**
     This method deletes a customer from the database.

     @param customerId updates customer id
     @param customerName adds customer by customer  name.
     */

    public  int deleteCx(int customerId, String customerName);

    /**
     This method looks up a customer from the database.

     @param customerId locates customer by customer ID.
     */

    public Customer lookupCx(int customerId);


}
