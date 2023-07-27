package model;

/**
 This class for division to the customer appointment application.

 @uthor Crystal Lu
 */

public class Division {

    /**
     The ID for the Division.
     */

    private int divisionId;

    /**
     The name for the Division.
     */

    private String divisionName;

    /**
     The Country ID for the Division.
     */

    private int countryId;

    /**
     The country name for the Division.
     */


    private String countryName;

    /**
     This is the Division constructor.

     @param divisionId The ID for the Division.
     @param divisionName The name for the Division.
     @param countryId The Country ID for the Division.
     @param countryName The country name for the Division.
     */

    public Division(int divisionId, String divisionName, int countryId, String countryName) {
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.countryId = countryId;
        this.countryName = countryName;
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
     This is the Division toString method.

     This method provides default syntax for division information.
     */
    public String toString() {
        return (divisionName);
    }
}