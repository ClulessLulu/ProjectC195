package model;

/**
 This class for contact appointments to the customer appointment application.

 @uthor Crystal Lu
 */

public class Country {

    /**
     The ID for the Country.
     */

    private int countryId;

    /**
     The name for the Country.
     */

    private String countryName;

    /**
     This is the Country constructor.

     @param countryId The country ID for the Country.
     @param countryName The name for the Counrty.
     */

    public Country(int countryId, String countryName) {
        this.countryId = countryId;
        this.countryName = countryName;
    }

    /**
     This is the getter for country ID.
     @return Returns the country ID.
     */

    public int getCountryId() { return countryId; }

    /**
     This is the setter for country ID.
     @param countryId The ID for country.
     */

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     This is the getter for country name.
     @return Returns the country name.
     */

    public String getCountryName() {
        return countryName;
    }

    /**
     This is the setter for country name.
     @param countryName The name for country.
     */

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }


    /**
     This is the Country toString method.

     This method provides default syntax for contact information.
     */

    public String toString() {
        return (countryName);
    }

}
