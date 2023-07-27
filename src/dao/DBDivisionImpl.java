package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * This class provides the division implementation methods to the customer appointment application.
 * This class implements the DBCountry class
 * @uthor Crystal Lu
 */



public class DBDivisionImpl implements DBDivision{

    /**
     This is the all divisions observable list.
     */


    ObservableList<Division> allDivisions = FXCollections.observableArrayList();

    /**
     This is the divisions by country observable list.
     */

    ObservableList<Division> divisionsCountry = FXCollections.observableArrayList();

    /**
     * This observable list is to get all divisions.
     * This method accesses the database and returns all divisions where the country IDs matches then is added to the observable list.
     *
     * @return allDivisions returns all divisions where the country ID matches with the division.
     */

    @Override
    public ObservableList<Division> getAllDivision() {
        try {
            String sql = "SELECT * FROM first_level_divisions, countries WHERE first_level_division.Country_ID = countries.Country_ID";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Division division = new Division(divisionId, divisionName, countryId, countryName);
                allDivisions.add(division);
            }
        } catch (Exception e) {
            System.out.println("Division Get All Error: " + e.getMessage());
        }
        return allDivisions;
    }

    /**
     * This is the observable list to get a divisions with the matching country ID.
     * This method accesses the database and returns divisions that match with the country ID which is then added to the observable list.
     *
     * @param countryId gets division by country ID.
     * @return divisionCountry returns the division with the correlating country ID.
     */

    @Override
    public ObservableList<Division> getDivisionCountry(int countryId) {
        try{
            String sql = "SELECT * FROM first_level_divisions, countries WHERE first_level_divisions.Country_ID " +
                    "= countries.Country_ID AND first_level_divisions.Country_ID=?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, countryId);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Division division = new Division(divisionId, divisionName, countryId, countryName);
                divisionsCountry.add(division);
            }
        } catch (Exception e) {
            System.out.println("Division Get Country Error: " + e.getMessage());
        }
        return divisionsCountry;
    }

    /**
     * This method gets a division by division ID.
     * This method accesses the database and gets the divisions and country ID.
     *
     * @param divisionId gets division by division ID.
     * @return divisionResult returns the matching division ID.
     * @return null returns no results if division ID does not exist.
     */

    @Override
    public Division getDivision(int divisionId) {
        try{
            String sql = "SELECT * FROM  first_Level_divisions, countries, WHERE first_level_divisions.Country_ID = countries.Country_ID AND Division_ID=?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, divisionId);

            ResultSet rs = ps.executeQuery();
            Division divisionResult = null;
            if(rs.next()){
                divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryId = rs.getInt("Country_Id");
                String countryName = rs.getString("Country");
                divisionResult = new Division(divisionId, divisionName, countryId, countryName);
            }
            return  divisionResult;

        } catch (Exception e) {
            System.out.println("Division Get Error: " + e.getMessage());
        }
        return null;
    }

    /**
     * This method adds new division to the database.
     * This method accesses the database and add division ID and country ID.
     *
     * @param divisionName adds division by division  name.
     * @param countryId adds division by country ID.
     * @return rowsAffected which are the number of rows affected in the database.
     */

    @Override
    public int addDivision(String divisionName, int countryId) {
        int rowsAffected = 0;
        try{
            String sql = "INSERT INTO first_level_divisions (Division, Country_ID) VALUE(?, ?)";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, divisionName);
            ps.setInt(2, countryId);
            rowsAffected = ps.executeUpdate();

            if(rowsAffected > 0) {
                System.out.println("Division Insert Successful");
            } else {
                System.out.println("Division Insert Unsuccessful");
            }
        } catch (Exception e) {
            System.out.println("Division Add Error: " + e.getMessage());
        }
        return rowsAffected;
    }

    /**
     * This method updates division country from the database.
     * This method accesses the database and get the selected division ID, country ID and update it's name.
     *
     *
     * @param divisionName update division by division name.
     * @param oldCountryId updates division by old country ID.
     * @param newCountryId updates division by new country ID.
     * @return rowsAffected which are the number of rows affected in the database
     */

    @Override
    public int updateDivisionCountry(String divisionName, int oldCountryId, int newCountryId) {
        int rowsAffected = 0;
                try{
                    String sql = "UPDATE first_level_divisions SET Country_ID=? WHERE Division=? AND Country_ID=?";
                    PreparedStatement ps = JDBC.connection.prepareStatement(sql);
                    ps.setString(1, divisionName);
                    ps.setInt(2, oldCountryId);
                    ps.setInt(3, newCountryId);
                    rowsAffected = ps.executeUpdate();

                    if(rowsAffected > 0){
                        System.out.println("Division " + divisionName +  " Country was Updated Successfully");
                    }
                    else{
                        System.out.println("Division " + divisionName +  " Country was Updated Failed");
                    }

                } catch (Exception e) {
                    System.out.println("Division Update Country Error: " + e.getMessage());
                }
        return rowsAffected;
    }

    /**
     * This method updates division name from country ID from the database.
     * This method accesses the database and get the selected division name and country ID then updates the name.
     *
     * @param oldDivisionName update division by division name.
     * @param newDivisionName updates division by old country ID.
     * @param countryId updates division by new country ID.
     * @return rowsAffected which are the number of rows affected in the database
     */


    @Override
    public int updateDivisionName(String oldDivisionName, String newDivisionName, int countryId) {
       int rowsAffected = 0;
        try{
            String sql = "UPDATE first_level_divisions SET Division=? WHERE Division=? AND Country_ID=?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, oldDivisionName);
            ps.setString(2, newDivisionName);
            ps.setInt(3, countryId);
            rowsAffected = ps.executeUpdate();

            if(rowsAffected > 0){
                System.out.println("Division " + oldDivisionName + " was Updated Successfully");
                System.out.println("Division Updated to " + newDivisionName);
            }

        } catch (Exception e) {
            System.out.println("Division Update Country Error: " + e.getMessage());
        }
        return rowsAffected;
    }

    /**
     * This method deletes a customer from the database.
     * This method accesses the database and get the selected division then deletes the division .
     *
     * @param divisionId   delete customer id
     * @param divisionName adds customer by customer  name.
     * @return rowsAffected which are the number of rows affected in the database
     */

    @Override
    public int deleteDivision(int divisionId, String divisionName) {
        int rowsAffected = 0;
        try{
            String sql = "DELETE FROM first_level_divisions WHERE Division_ID=? AND Division=?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, divisionId);
            ps.setString(2, divisionName);
            rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Division " + divisionName + " [" + divisionId + "] was Deleted Successfully" );
            }
            else{
                System.out.println("Division " + divisionName + "[" + divisionId + "] was Deleted Unsuccessful");
            }

        } catch (Exception e) {
            System.out.println("Division Delete Error: " + e.getMessage());
        }
        return rowsAffected;
    }
}
