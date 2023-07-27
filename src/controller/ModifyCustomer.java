package controller;

import dao.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.Division;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class provides the modify customer control to the customer appointment application.
 * @uthor Crystal Lu
 */

public class ModifyCustomer implements Initializable {
    Stage stage;
    Parent scene;

    /**
     The modify address text field
     */

    @FXML
    private TextField addressTxt;

    /**
     The modify country combo box
     */

    @FXML
    private ComboBox<Country> countryComboBx;

    /**
     The modify division combo box
     */

    @FXML
    private ComboBox<Division> diviComboBx;

    /**
     The modify name text field
     */

    @FXML
    private TextField nameTxt;

    /**
     The modify phone text field
     */

    @FXML
    private TextField phoneTxt;

    /**
     The modify postal code text field
     */

    @FXML
    private TextField postalTxt;

    /**
     The modify error label
     */

    @FXML
    private Label errorLbl;

    /**
     The modify selected customer.
     */

    Customer modCx = null;

    /**
     The is the modify customer method.
     This will populate the selected customer from the main customer menu.
     This fills the data into the fields to be modified

     @param selectedCx The selected customer to be modified.
     */

    public void modifyCx(Customer selectedCx){
        JDBC.openConnection();
        DBCountry dbCountry = new DBCountryImpl();
        DBDivision dbDivision = new DBDivisionImpl();

        modCx = selectedCx;
        nameTxt.setText(String.valueOf(modCx.getCustomerName()));
        addressTxt.setText(String.valueOf(modCx.getAddress()));
        postalTxt.setText(String.valueOf(modCx.getPostalCode()));
        phoneTxt.setText(String.valueOf(modCx.getPhone()));

        Country modCountry = null;
        for(Country country : dbCountry.getAllCountries()){
            if(country.getCountryId()== modCx.getCountryId()){
                modCountry = country;
                break;
            }
        }
        countryComboBx.getSelectionModel().select(modCountry);
       int countryId = modCountry.getCountryId();

       diviComboBx.setItems(dbDivision.getDivisionCountry(countryId));
       Division modDivision = null;
       for(Division division : dbDivision.getDivisionCountry(countryId)){
           if(division.getDivisionId() == modCx.getDivisionId()){
               modDivision = division;
               break;
           }
       }
       diviComboBx.getSelectionModel().select(modDivision);
      int divisionId = modDivision.getDivisionId();
    }

    /**
     The action event is for canceling and provides a confirmation dialog to confirm cancellation.
     This will loads the customer main menu.

     @param event Cancel button to load main customer menu.
     */
    @FXML
    void onActionCancelCx(ActionEvent event) throws IOException {
        System.out.println("Cancel Button Clicked");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Modify Customer Cancel");
        alert.setContentText("Values will be cleared, do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainCustomerMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     The action event is for the modify country combo box.
     This will pull the selected customer's first level division that correlate with the country id.

     @param event Combo box loads the country then provides the division combo box the first level divisions.
     */

    @FXML
    void onActionCountry(ActionEvent event) {
        DBDivision dbDivision = new DBDivisionImpl();
        int countryId = countryComboBx.getValue().getCountryId();
        diviComboBx.setItems(dbDivision.getDivisionCountry(countryId));
    }

    /**
     The action event is add the modified customer to the database.
     This takes the information from the text field and combo boxes then check if the text field are blank.
     If they are blank and error message will display.

     @param event Save customer action event.
     */

    @FXML
    void onActionSaveCx(ActionEvent event) {
        System.out.println("Modify Customer Save Button Clicked");

        try{
            boolean cxError = false;
            int customerId = modCx.getCustomerId();
            String  cxName = nameTxt.getText();
            String  address = addressTxt.getText();
            String   postal = postalTxt.getText();
            String  phone = phoneTxt.getText();
            int divisionId = diviComboBx.getValue().getDivisionId();

            if(cxName.isBlank()){
                errorMessage(1);
                cxError = true;
            }
            if(address.isBlank()){
                errorMessage(2);
                cxError = true;
            }
            if(postal.isBlank()){
                errorMessage(3);
                cxError = true;
            }
            if(phone.isBlank()){
                errorMessage(4);
                cxError = true;
            }
            if(!cxError){
                DBCustomer dbCustomer = new DBCustomerImpl();
                dbCustomer.updateCx(customerId, cxName,address, postal, phone, divisionId);

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainCustomerMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        } catch (Exception e) {
            System.out.println("Customer Save Error: " + e.getMessage());
        }
    }

    /**
     The is the lambda expression error message method.
     This method is used to generate an error on the error label.

     The reason why i used the lambda expression is to reduce the amount of coding needed to create error messages.
     The error is associated by case number which is the displayed.
     */

    public void errorMessage(int errorCode){
        switch (errorCode){
            case 1 -> {
                errorLbl.setText("Name field cannot be blank");
            }
            case 2 -> {
                errorLbl.setText("Address field cannot be blank");
            }
            case 3 -> {
                errorLbl.setText("Postal Code cannot be blank");
            }
            case 4 -> {
                errorLbl.setText("Phone Number cannot be blank");
            }
        }
    }

    /**
     This initializes the modify customer controller.
     This opens the database and loads all countries into the country combo box.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Modify Customer Initialized");

        JDBC.openConnection();
        DBCountry dbCountry = new DBCountryImpl();

        countryComboBx.setItems(dbCountry.getAllCountries());
    }
}
