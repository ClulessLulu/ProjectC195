package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * This class provides the country implementation methods to the customer appointment application.
 * This class implements the DBCountry class
 * @uthor Crystal Lu
 */


public class DBCountryImpl implements DBCountry{

    /**
     This is the all country observable list.
     */

    ObservableList<Country> allCountries = FXCollections.observableArrayList();

    /**
     This is the get all countries method.
     This method accesses the database and returns all countries which is then added to the observable list.

     @return allCountries
     */

    @Override
    public ObservableList<Country> getAllCountries() {
        try{
            String sql = "SELECT * FROM countries";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Country country = new Country(countryId, countryName);
                allCountries.add(country);
            }
        } catch (Exception e) {
            System.out.println("Country Get All Error: " + e.getMessage());

        }
        return allCountries;
    }

    /**
     This is the method to get a country by country id.
     This method accesses the database and returns country by the country ID.

     @param countryId gets country by country  ID.
     @return null returns no results if country ID does not exist.
     */

    @Override
    public Country getCountry(int countryId) {
        try{
            String sql = "SELECT * FROM countries WHERE Country_ID=?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, countryId);
            ResultSet rs = ps.executeQuery();
            Country countryResult = null;

            if(rs.next()){
                countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                countryResult = new Country(countryId, countryName);
            }
            return countryResult;

        } catch (Exception e) {
            System.out.println("Country Update Error: " + e.getMessage());
        }
        return null;
    }

    /**
     This method adds new country by country name.
     This method accesses the database and insert country by the country name.

     @param countryName adds country by country name.
     @return rowsAffected which are the number of rows affected in the database.
     */

    @Override
    public int addCountry(String countryName) {
        int rowsAffected = 0;
        try{
            String sql = "INSERT INTO countries (Country) VALUE(?)";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, countryName);
            rowsAffected = ps.executeUpdate();

            if(rowsAffected > 0) {
                System.out.println("Country Insert Successful");
            }
            else{
                System.out.println("Country Insert Failed");
            }

        } catch (Exception e) {
            System.out.println("Country Insert Error: " + e.getMessage());
        }
        return rowsAffected;
    }

    /**
     This is the update method that accesses the database and updates a country.

     @param countryId updates associated country ID.
     @param oldCountryName updates associated old country name.
     @param newCountryName updates the associated to new country name.
     @return rowsAffected which are the number of rows affected in the database.
     */

    @Override
    public int updateCountry(int countryId, String oldCountryName, String newCountryName) {
        int rowsAffected = 0;
        try {
            String sql = "UPDATE countries SET Country=? WHERE Country=? AND Country_ID=?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, countryId);
            ps.setString(2, oldCountryName);
            ps.setString(3, newCountryName);
            rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0){
                System.out.println("Country " + oldCountryName + " was Updated Successful");
                System.out.println("Contact Updated to " + newCountryName);
            }
            else {
                System.out.println("Country Update Failed");
            }

        } catch (Exception e) {
            System.out.println("Country Update Error: " + e.getMessage());
        }
        return rowsAffected;
    }

    /**
     This is the delete method that accesses the database and removes the country.

     @param countryId the selected country by unique country ID.
     @return rowsAffected which are the number of rows affected in the database.
     */


    @Override
    public int deleteCountry(int countryId, String countryName) {
        int rowsAffected = 0;
        DBDivision dbDivision = new DBDivisionImpl();

        try{
            if(dbDivision.getDivisionCountry(countryId).isEmpty()){
                try{
                    String sql = "DELETE FROM countries WHERE Country_ID=? AND COUNTRY=?";
                    PreparedStatement ps = JDBC.connection.prepareStatement(sql);
                    ps.setInt(1, countryId);
                    ps.setString(2, countryName);
                    rowsAffected = ps.executeUpdate();

                    if(rowsAffected > 0 ){
                        System.out.println("Country " + countryName + " [" + countryId + "] was Deleted Successfully");
                    }
                    else {
                        System.out.println("Country Delete Failed");
                    }

                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            if(!dbDivision.getDivisionCountry(countryId).isEmpty()){
                System.out.println("Country " + countryName + " Delete Failed");
                System.out.println("Country " + countryName + " has associated Divisions");
            }
            }
        catch (Exception e) {
            System.out.println("Country Delete Error: " + e.getMessage());
        }
        return rowsAffected;
    }
}
