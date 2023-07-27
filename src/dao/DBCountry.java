package dao;

import javafx.collections.ObservableList;
import model.Country;

/**
 * This class provides the dbcountry methods to the customer appointment application.
 * @uthor Crystal Lu
 */

public interface DBCountry {

    /**
     This observable list of get all countries.
     */

    public ObservableList<Country> getAllCountries();

    /**
     This is the method to get a country by country id.

     @param countryId gets country by country  id.
     */

    public Country getCountry(int countryId);

    /**
     This method adds new country by country name.

     @param countryName adds country by country name.
     */

    public int addCountry(String countryName);

    /**
     This is the update method that accesses the database and updates a country.

     @param countryId updates associated country ID.
     @param oldCountryName updates associated old country name.
     @param newCountryName updates the associated to new country name.
     */

    public int updateCountry(int countryId, String oldCountryName, String newCountryName);

    /**
     This is the delete method that accesses the database and removes the country.

     @param countryId the selected country by unique country ID.
     */

    public int deleteCountry(int countryId, String countryName);



}
