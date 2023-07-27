package controller;


import dao.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class provides the main customer control to the customer appointment application.
 * @uthor Crystal Lu
 */

public class MainCustomer implements Initializable {
    Stage stage;
    Parent scene;

    /**
     The customer table
     */

    @FXML
    private TableView<Customer> cxTable;

    /**
     The postal code column for the customer table
     */

    @FXML
    private TableColumn<?, ?> cxPostCol;

    /**
     The address column for the customer table
     */

    @FXML
    private TableColumn<?, ?> cxAddCol;

    /**
     The customer id column for the customer table
     */

    @FXML
    private TableColumn<?, ?> cxIdCol;

    /**
     The name column for the customer table
     */

    @FXML
    private TableColumn<?, ?> cxNameCol;

    /**
     The phone column for the customer table.
     */


    @FXML
    private TableColumn<?, ?> cxPhoneCol;

    /**
     The first level division column for the customer table.
     */

    @FXML
    private TableColumn<?, ?> cxStCol;

    /**
     The search text field.
     */

    @FXML
    private TextField searchField;

    /**
     This action event loads the add customer controller.

     @param event Loads add customer action event.
     */

    @FXML
    void onActionAddCx(ActionEvent event) throws IOException {
        System.out.println("Customer Add Button Clicked");
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddCustomerMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     This action event loads the main appointment menu controller.

     @param event Loads appointment main menu action event.
     */

    @FXML
    void onActionApps(ActionEvent event) throws IOException {
        System.out.println("Customer Appointment Button Clicked");
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainAppointmentMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     This action event deletes the selected customer.

     The database connection is open then gets the customer id and customer name then assigned a variable.

     The delete method will check if the selected customer has appointment and then provides an confirmation alert.
     Selecting confirm will remove the customer and their associated appointments then repopulates the table.


     @param event Delete button for customer table action event.
     */

    @FXML
    void onActionDelCx(ActionEvent event) {
        System.out.println("Customer Delete Button Clicked");
        try{
            JDBC.openConnection();
            DBCustomer dbCustomer = new DBCustomerImpl();
            Customer selectedCx = cxTable.getSelectionModel().getSelectedItem();
            int customerId = selectedCx.getCustomerId();
            String customerName = selectedCx.getCustomerName();

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Deletion");
            alert.setHeaderText("WARNING DELETION: Are you sure you want to continue?");
            alert.setContentText("The selected Customer and their Appointment(s) will be deleted. Do you want to delete?");
            Optional<ButtonType> result = alert.showAndWait();
            if ((result.isPresent() && result.get() == ButtonType.OK)) {
                DBAppointmentImpl dbAppointment = new DBAppointmentImpl();
                dbAppointment.deleteCxApp(customerId);
                System.out.println(dbCustomer.deleteCx(customerId, customerName));
                cxTable.setItems(dbCustomer.getAllCx());}

        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Select a Customer to Delete");
            alert.show();
        }
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

        if(result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/LoginScreenMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     This action event loads the modify customer menu controller.

     This must have an customer selected from the table and if not will provide an alert to select.
     The selected customer will load the modify appointment menu

     @param event Loads modify appointment menu action event.
     */

    @FXML
    void onActionModCx(ActionEvent event) throws IOException {
        System.out.println("Customer Modify Button Clicked");
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyCustomerMenu.fxml"));
            loader.load();

            ModifyCustomer modifyCustomer  = loader.getController();

            Customer selectedCx = cxTable.getSelectionModel().getSelectedItem();

            modifyCustomer.modifyCx(selectedCx);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Select a Customer to Modify");
            alert.show();
        }

    }

    /**
     This action event loads the contact report controller.

     @param event Loads contact report menu action event.
     */

    @FXML
    void onActionReports(ActionEvent event) throws IOException {
        System.out.println("Customer Report Button Clicked");
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ContactReportMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     This action event loads the customer typed in the search text field.
     This opens the database connection and gets all customers then grabs what is entered in the text field.

     The text will be taken the allocate the name of customer or locate the customer's id.

     @param event Loads searches for customers in the table action event.
     */


    @FXML
    void onActionSearchCx(ActionEvent event) {
        JDBC.openConnection();
        DBCustomer dbCustomer = new DBCustomerImpl();
        cxTable.setItems(dbCustomer.getAllCx());

        try{
           int cxId = Integer.parseInt(searchField.getText());
            Customer customer = dbCustomer.lookupCx(cxId);
            cxTable.getSelectionModel().select(customer);
            cxTable.scrollTo(customer);
            cxTable.requestFocus();

        } catch (NumberFormatException e) {
           String cxName = searchField.getText();
          ObservableList<Customer> customers = dbCustomer.lookupCx(cxName);
          cxTable.setItems(customers);
        }

      if(!((DBCustomerImpl) dbCustomer).cxFound){
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("Error Message");
          alert.setContentText("Customer does not Exist");
          alert.showAndWait();
      }
    }

    /**
     This initializes the main customer controller
     This opens the database connection and gets all customers then sets the table columns.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        System.out.println("Main Customer Menu Initialized");

        JDBC.openConnection();
        DBCustomer dbCustomer = new DBCustomerImpl();

        cxTable.setItems(dbCustomer.getAllCx());
        cxIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        cxNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        cxAddCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        cxPostCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        cxStCol.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
        cxPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
    }

}

