package dao;

import javafx.collections.ObservableList;
import model.Division;

/**
 * This class provides the dbdivision methods to the customer appointment application.
 * @uthor Crystal Lu
 */

public interface DBDivision {

    /**
     * This observable list of get all divisions.
     */

    public ObservableList<Division> getAllDivision();

    /**
     * This is the observable list to get a divisions by country ID.
     *
     * @param countryId gets division by country ID.
     */

    public ObservableList<Division> getDivisionCountry(int countryId);

    /**
     * This method gets a division by division ID.
     *
     * @param divisionId gets division by division ID.
     */

    public Division getDivision(int divisionId);

    /**
     * This method adds new division to the database.
     *
     * @param divisionName adds division by division  name.
     * @param countryId    adds division by country ID.
     */

    public int addDivision(String divisionName, int countryId);

    /**
     * This method updates division country from the database.
     *
     * @param divisionName update division by division name.
     * @param oldCountryId updates division by old country ID.
     * @param newCountryId updates division by new country ID.
     */

    public int updateDivisionCountry(String divisionName, int oldCountryId, int newCountryId);

    /**
     * This method updates division country from the database.
     *
     * @param oldDivisionName update division by division name.
     * @param newDivisionName updates division by old country ID.
     * @param countryId       updates division by new country ID.
     */

    public int updateDivisionName(String oldDivisionName, String newDivisionName, int countryId);

    /**
     * This method deletes a customer from the database.
     *
     * @param divisionId   delete customer id
     * @param divisionName adds customer by customer  name.
     */

    public int deleteDivision(int divisionId, String divisionName);

}

