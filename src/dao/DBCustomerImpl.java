package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * This class provides the customer implementation methods to the customer appointment application.
 * This class implements the DBCustomer class
 * @uthor Crystal Lu
 */

public class DBCustomerImpl implements DBCustomer{

    /**
     This is the all customer observable list.
     */

    ObservableList<Customer> allCx = FXCollections.observableArrayList();

    /**
     This is the customer object used for the look up method.
     */

    public boolean cxFound;

    /**
     This is the get all countries method.
     This method accesses the database and returns all customers which is then added to the observable list.

     @return allCx
     */

    @Override
    public ObservableList<Customer> getAllCx() {

        try{
            String sql = "SELECT * FROM customers, first_level_divisions, countries WHERE " +
                    "customers.Division_ID = first_level_divisions.Division_ID AND " +
                    "first_level_divisions.Country_ID = countries.Country_ID";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int customerId = rs.getInt("Customer_ID");
                int countryId = rs.getInt("Country_ID");
                int divisionId = rs.getInt("Division_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                String countryName = rs.getString("Country");
                String divisionName = rs.getString("Division");

                Customer customer = new Customer(customerId, countryId, divisionId, customerName, address, postalCode, phone, countryName, divisionName);allCx.add(customer);

            }
        } catch (Exception e) {
            System.out.println("Get All Customer Error: " + e.getMessage());
            e.printStackTrace();
        }
        return allCx;
    }

    /**
     This is the observable list to get a customers by country id.
     This method accesses the database and returns customers by country which is then added to the observable list.

     @param countryId gets customers by country ID.
     @return cxByCountry returns the customer by country ID
     */

    @Override
    public ObservableList<Customer> getCxByCountry(int countryId) {
        ObservableList<Customer> cxByCountry = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM customers, first_level_divisions, countries WHERE " +
                    "customers.Division_ID = first_level_divisions.Division_ID AND " +
                    "first_level_divisions.Country_ID = countries.Country_ID AND countries.Country_ID=?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, countryId);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int customerId = rs.getInt("Customer_ID");
                countryId = rs.getInt("Customer_ID");
                int divisionId = rs.getInt("Division_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                String countryName = rs.getString("Country");
                String divisionName = rs.getString("Division");
                Customer customer = new Customer(customerId, countryId, divisionId, customerName, address, postalCode, phone, countryName, divisionName);
                cxByCountry.add(customer);
            }

        } catch (Exception e) {
            System.out.println("Get Customer by Country Error: " + e.getMessage());
            e.printStackTrace();
        }

        return cxByCountry;
    }

    /**
     This is the observable list to get a customers by customer name.
     This method accesses the database and returns customers by customer name which is then added to the observable list.

     @param customerName gets customers by customer name.
     @return allCx if empty then returns all customers.
     @return foundCx if true then returns found customer by name.
     */

    @Override
    public ObservableList<Customer> lookupCx(String customerName) {
        ObservableList<Customer> foundCx = FXCollections.observableArrayList();
        cxFound = false;

        for(Customer customer : allCx){
            if(customer.getCustomerName().toLowerCase().contains(customerName.toLowerCase())){
                foundCx.add(customer);
            }
        }
        if(foundCx.isEmpty()){
            return allCx;
        }
        cxFound = true;
        return foundCx;
    }

    /**
     This method gets a customer by customer id.
     This method accesses the database and returns customers by customer ID.

     @param customerId gets customer by customer ID.
     @return null returns no results if customer ID does not exist
     */

    @Override
    public Customer getCx(int customerId) {

        try{
            String sql = "SELECT * FROM customers, first_level_divisions, countries WHERE " +
                    "customers.Division_ID = first_level_divisions.Division_ID AND " +
                    "first_level_divisions.Country_ID = countries_Country_ID";

            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, customerId);

            ResultSet rs = ps.executeQuery();
            Customer cxResult = null;
                if(rs.next()){
                    customerId =rs.getInt("Customer_ID");
                    int countryId = rs.getInt("Country_ID");
                    int divisionId = rs.getInt("Division_ID");
                    String customerName = rs.getString("Customer_Name");
                    String address = rs.getString("Address");
                    String postalCode = rs.getString("Postal_Code");
                    String phone = rs.getString("Phone");
                    String countryName = rs.getString("Country");
                    String divisionName = rs.getString("Division");

                    cxResult = new Customer(customerId, countryId, divisionId, customerName, address, postalCode, phone, countryName, divisionName);
                }
                return cxResult;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     This method adds new customer to the database.
     This method accesses the database and insert customer.


     @param customerName adds customer by customer  name.
     @param address adds customer address.
     @param postalCode adds customer postal code.
     @param phone adds customer phone number.
     @param divisionId adds customer first level division.
     @return rowsAffected which are the number of rows affected in the database.
     */

    @Override
    public int addCx(String customerName, String address, String postalCode, String phone, int divisionId) {
        int rowsAffected = 0;
        try {
            String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) " +
                    "VALUE(?, ?, ?, ?, ?)";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, customerName);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setInt(5, divisionId);
            rowsAffected = ps.executeUpdate();

            if(rowsAffected > 0){
                System.out.println("Customer Insert Successful");
            }
            else{
                System.out.println("Customer Insert Failed");
            }

        } catch (Exception e) {
            System.out.println("Customer Insert Error:  " + e.getMessage());
        }
        return rowsAffected;
    }

    /**
     This method updates customer from the database.
     This method accesses the database and updates the customer.

     @param customerId updates customer id
     @param customerName updates customer by customer  name.
     @param address updates customer address.
     @param postalCode updates customer postal code.
     @param phone updates customer phone number.
     @param divisionId updates customer first level division.
     @return rowsAffected which are the number of rows affected in the database.
     */

    @Override
    public int updateCx(int customerId, String customerName, String address, String postalCode, String phone, int divisionId) {
        int rowsAffected = 0;
        try{
            String sql = "UPDATE customers SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Division_ID=? WHERE Customer_ID=?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, customerName);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setInt(5, divisionId);
            ps.setInt(6, customerId);
            rowsAffected = ps.executeUpdate();

            if(rowsAffected > 0){
                System.out.println("Customer Update Successful");
            }
            else{
                System.out.println("Customer Update Failed");
            }

        } catch (Exception e) {
            System.out.println("Customer Update Error:  " + e.getMessage());
        }
        return rowsAffected;
    }

    /**
     This method deletes a customer from the database.
     This method access the database and deleted the customer by customer ID.

     @param customerId updates customer id
     @param customerName adds customer by customer  name.
     @return rowsAffected which are the number of rows affected in the database.
     */

    @Override
    public int deleteCx(int customerId, String customerName) {
        int rowsAffected = 0;
        try{
            String sql = "DELETE FROM customers WHERE Customer_ID=? AND Customer_Name=?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, customerId);
            ps.setString(2, customerName);
            rowsAffected = ps.executeUpdate();

            if(rowsAffected > 0){
                System.out.println("Customer " + customerName + "[" + customerId + "] was Deleted Successfully");
            }
            else{
                System.out.println("Customer " + customerName + "[" + customerId + "] was Deleted Unsuccessful");
            }
        } catch (Exception e) {
            System.out.println("Customer Delete Error:  " + e.getMessage());
        }
        return rowsAffected;
    }

    /**
     This method looks up a customer ID from the database.

     @param customerId locates customer by customer ID.
     @return null returns no results if customer ID does not exist
     */

    @Override
    public Customer lookupCx(int customerId) {
        cxFound = false;
        for (Customer cx : allCx){
            if(cx.getCustomerId() == customerId){
                cxFound = true;
                return cx;
            }
        }
        return null;
    }
}
