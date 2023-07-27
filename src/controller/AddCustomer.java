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
import model.Division;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class provides the add customer control to the customer appointment application.
 * @uthor Crystal Lu
 */

public class AddCustomer implements Initializable {
    Stage stage;
    Parent scene;

    /**
     The add customer address text field
     */

    @FXML
    private TextField addressTxt;

    /**
     The add customer country combo box
     */

    @FXML
    private ComboBox<Country> countryComboBx;

    /**
     The add customer division combo box
     */

    @FXML
    private ComboBox<Division> diviComboBx;

    /**
     The add customer name text field
     */

    @FXML
    private TextField nameTxt;

    /**
     The add customer phone text field
     */

    @FXML
    private TextField phoneTxt;

    /**
     The add customer postal code text field
     */

    @FXML
    private TextField postalTxt;

    /**
    The add customer error label
     */

    @FXML
    private Label errorLbl;

    /**
     The action event is for canceling and provides a confirmation dialog to confirm cancellation.
     This will loads the customer main menu.

     @param event Cancel button to load main customer menu.
     */

    @FXML
    void onActionCancelCx(ActionEvent event) throws IOException {
        System.out.println("Cancel Button Clicked");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Add Customer Cancel");
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
     The action event is for the country combo box.
     This will pull the first level division that correlate with the country id.

     @param event Combo box loads the country then provides the division combo box the first level divisions.
     */

    @FXML
    void onActionCountry(ActionEvent event) {
        DBDivision dbDivision = new DBDivisionImpl();
        int countryId = countryComboBx.getValue().getCountryId();
        diviComboBx.setItems(dbDivision.getDivisionCountry(countryId));
    }

    /**
     The action event is add customer to the database.
     This takes the information from the text field and combo boxes then check if the text field are blank.
     If they are blank and error message will display.

     @param event Save customer action event.
     */

    @FXML
    void onActionSaveCx(ActionEvent event) {
        System.out.println("Customer Save Button Clicked");

        try{
            boolean cxError = false;
            String  cxName = nameTxt.getText();
            String  address = addressTxt.getText();
            String   postal = postalTxt.getText();
            String  phone = phoneTxt.getText();
            int divisionId = diviComboBx.getSelectionModel().getSelectedItem().getDivisionId();

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
                dbCustomer.addCx(cxName,address, postal, phone, divisionId);

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
     This initializes the add customer controller.
     This opens the database and loads all countries into the country combo box.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Add Customer Initialized");

        try {
            JDBC.openConnection();
            DBCountry dbCountry = new DBCountryImpl();

            countryComboBx.setItems(dbCountry.getAllCountries());

        } catch (Exception e) {
            System.out.println("Add Customer Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

