package controller;

import dao.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Country;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class provides the customer report control to the customer appointment application.
 * @uthor Crystal Lu
 */

public class CustomerReport implements Initializable {
    Stage stage;
    Parent scene;

    /**
     The country combo box
     */

    @FXML
    private ComboBox<Country> countryCombo;

    /**
     The customer table
     */

    @FXML
    private TableView<Customer> cxTable;

    /**
     The address column for the table
     */

    @FXML
    private TableColumn<?, ?> cxAddressCol;

    /**
     The postal column for the table
     */

    @FXML
    private TableColumn<?, ?> cxCodeCol;

    /**
     The country column for the table
     */

    @FXML
    private TableColumn<?, ?> cxCountryCol;

    /**d
     The customer I column for the table
     */

    @FXML
    private TableColumn<?, ?> cxIdCol;

    /**
     The name column for the table
     */

    @FXML
    private TableColumn<?, ?> cxNameCol;

    /**
     The phone column for the table
     */

    @FXML
    private TableColumn<?, ?> cxPhoneCol;

    /**
     This action event loads the main appointment menu controller.

     @param event Loads main appointment menu action event.
     */

    @FXML
    void onActionAppMenu(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainAppointmentMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     This action event loads the contact report menu controller.

     @param event Loads contact report menu action event.
     */

    @FXML
    void onActionContact(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ContactReportMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     The action event is for the country combo box.
     This will pull the country that correlate with the customer id.

     @param event Combo box loads the customers by the selected country.
     */

    @FXML
    void onActionCountryCombo(ActionEvent event) {
        JDBC.openConnection();
        DBCustomer dbCustomer = new DBCustomerImpl();
        int countryId = countryCombo.getSelectionModel().getSelectedItem().getCountryId();
        cxTable.setItems(dbCustomer.getCxByCountry(countryId));
    }

    /**
     This action event loads the main customer menu controller.

     @param event Loads main customer menu action event.
     */

    @FXML
    void onActionCxMenu(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainCustomerMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     This action event loads the login menu controller.
     This provides an alert to validate if you want to logout of the application.

     @param event Loads login menu action event.
     */

    @FXML
    void onActionLogout(ActionEvent event) throws IOException {
        System.out.println("Customer Logout Button Clicked");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setContentText("Do you want to logout of the program?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/LoginScreenMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     This action event loads the total appointment report menu controller.

     @param event Loads total appointment report menu action event.
     */

    @FXML
    void onActionTotalApp(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/TotalAppointmentMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     This initializes the customer report menu controller
     This opens the database connection and gets all customers associated with the selected country then sets the table columns.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Customer Report Menu Initialized");

        JDBC.openConnection();
        DBCountry dbCountry = new DBCountryImpl();
        countryCombo.setItems(dbCountry.getAllCountries());

        cxIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        cxNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        cxAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        cxCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        cxPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        cxCountryCol.setCellValueFactory(new PropertyValueFactory<>("countryName"));

    }
}
