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
import model.Appointment;
import model.Contact;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class provides the contact report control to the customer appointment application.
 * @uthor Crystal Lu
 */


public class ContactReport implements Initializable {
    Stage stage;
    Parent scene;

    /**
     The appointment table.
     */

    @FXML
    private TableView<Appointment> contactTable;

    /**
     The appointment ID column for the table.
     */

    @FXML
    private TableColumn<?, ?> appIdCol;

    /**
     The contact ID column for the table.
     */

    @FXML
    private TableColumn<?, ?> contactCol;

    /**
     The contact id for the combo box.
     */

    @FXML
    private ComboBox<Contact> contactCombo;

    /**
     The customer ID column for the table.
     */

    @FXML
    private TableColumn<?, ?> cxIdCol;

    /**
     The user ID column for the table.
     */

    @FXML
    private TableColumn<?, ?> userCol;

    /**
     The description column for the table.
     */

    @FXML
    private TableColumn<?, ?> descriptionCol;

    /**
     The end date column for the table.
     */

    @FXML
    private TableColumn<?, ?> endDateCol;

    /**
     The end time column for the table.
     */

    @FXML
    private TableColumn<?, ?> endTimeCol;

    /**
     The stast date column for the table.
     */

    @FXML
    private TableColumn<?, ?> startDateCol;

    /**
     The start time column for the table.
     */

    @FXML
    private TableColumn<?, ?> startTimeCol;

    /**
     The title column for the table.
     */

    @FXML
    private TableColumn<?, ?> titleCol;

    /**
     The type column for the table.
     */

    @FXML
    private TableColumn<?, ?> typeCol;

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
     This action event loads the customer report menu controller.

     @param event Loads customer report menu action event.
     */

    @FXML
    void onActionCXCountry(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerReportMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     The action event is for the contact combo box.
     This will pull the contact that correlate with the appointment id.

     @param event Combo box loads the appointment by the selected contact id.
     */

    @FXML
    void onActionContactCombo(ActionEvent event) {
        DBAppointment dbAppointment = new DBAppointmentImpl();
        int contactId = contactCombo.getSelectionModel().getSelectedItem().getContactId();
        contactTable.setItems(dbAppointment.getAppByContactId(contactId));

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
        System.out.println("Contact Report Logout Button Clicked");

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
     This action event loads the total appointment menu controller.

     @param event Loads total appointment menu.
     */

    @FXML
    void onActionTotalApp(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/TotalAppointmentMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     This initializes the contact report controller
     This opens the database connection and gets all appointments associated with the selected contact then sets the table columns.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Contact Report Menu Initialized");

        JDBC.openConnection();
        DBContact dbContact = new DBContactImpl();
        contactCombo.setItems(dbContact.getAllContacts());

        appIdCol.setCellValueFactory(new PropertyValueFactory<>("appId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("appDescription"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("appType"));
        startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        cxIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

    }
}


