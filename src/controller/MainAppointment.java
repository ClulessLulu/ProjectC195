package controller;


import dao.DBAppointment;
import dao.DBAppointmentImpl;
import dao.JDBC;
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
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class provides the main appointment menu control to the customer appointment application.
 * @uthor Crystal Lu
 */

public class MainAppointment implements Initializable {
    Stage stage;
    Parent scene;

    /**
     The contact ID column for the appointment table.
     */

    @FXML
    private TableColumn<?, ?> appContactCol;

    /**
     The customer ID column for the appointment table.
     */

    @FXML
    private TableColumn<?, ?> appCxIdCol;

    /**
     The description column for the appointment table.
     */

    @FXML
    private TableColumn<?, ?> appDescriptionCol;

    /**
     The end date column for the appointment table.
     */

    @FXML
    private TableColumn<?, ?> appEndDateCol;

    /**
     The end time column for the appointment table.
     */

    @FXML
    private TableColumn<?, ?> appEndTimeCol;

    /**
     The appointment ID column for the appointment table.
     */

    @FXML
    private TableColumn<?, ?> appIdCol;

    /**
     The location column for the appointment table.
     */

    @FXML
    private TableColumn<?, ?> appLocationCol;

    /**
     The start date column for the appointment table.
     */

    @FXML
    private TableColumn<?, ?> appStartDateCol;

    /**
     The start time column for the appointment table.
     */

    @FXML
    private TableColumn<?, ?> appStartTimeCol;

    /**
     The appointment table.
     */

    @FXML
    private TableView<Appointment> appTableView;

    /**
     The title column for the appointment table.
     */

    @FXML
    private TableColumn<?, ?> appTitleCol;

    /**
     The type column for the appointment table.
     */

    @FXML
    private TableColumn<?, ?> appTypeCol;

    /**
     The user ID column for the appointment table.
     */

    @FXML
    private TableColumn<?, ?> appUserIdCol;

    /**
     The date picker for the appointment table.
     */

    @FXML
    private DatePicker datePicker;

    /**
     The filter toggle group for the appointment table.
     */

    @FXML
    private ToggleGroup viewTG;

    /**
     The main appointment error label
     */

    @FXML
    private Label errorLbl;

    /**
     This action event loads the add appointment controller.

     @param event Loads add appointment action event.
     */

    @FXML
    void onActionAddApp(ActionEvent event) throws IOException {
        System.out.println("Add Appointment Button Clicked");
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddAppointmentMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     This action event loads the main customer menu controller.

     @param event Loads customer main menu action event.
     */

    @FXML
    void onActionCx(ActionEvent event) throws IOException {
        System.out.println("Customer Button Clicked");
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainCustomerMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     This action event deletes the selected appointment.

     The database connection opens then the get appointment ID, customer id, and appointment type is called then assigned a variable.

     This delete method will check if there is a selected appointment and if not will provide an alert to have an appointment selected.
     This pulls the selected appointment from the database then provides an confirmation alert validating about deletion.

     If confirmed the selected appointment will be deleted and the appointment table will repopulate the update information.

     @param event Delete button for appointment table action event.
     */

    @FXML
    void onActionDelApp(ActionEvent event) {

        System.out.println("Delete Appointment Button Clicked");
        try{
            DBAppointment dbAppointment =new DBAppointmentImpl();
            Appointment delApp = appTableView.getSelectionModel().getSelectedItem();
            int appointmentId = delApp.getAppId();
            int customerId = delApp.getCustomerId();
            String appType = delApp.getAppType();

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Deletion");
            alert.setContentText("The selected Appointment will be delete. Do you want to continue?");

            Optional<ButtonType> result = alert.showAndWait();

            if((result.isPresent() && result.get() == ButtonType.OK)) {
                dbAppointment.deleteApp(appointmentId, customerId, appType);
                errorLbl.setText("Appointment ID: " + appointmentId + "  ( " + appType + " )" + " was Deleted");
                System.out.println("Appointment ID " + appointmentId + "  ( " + appType + " )" + " was Deleted");

                appTableView.setItems(dbAppointment.getAllAppointments());
            }

        } catch (NullPointerException  e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Select a Appointment to delete");
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
        System.out.println("Appointment Logout Button Clicked");

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
     This action event loads the modify appointment menu controller.

     This must have an appointment selected from the table and if not will provide an alert to select.
     The selected appointment will load the modify appointment menu


     @param event Loads modify appointment menu action event.
     */

    @FXML
    void onActionModApp(ActionEvent event) {
        System.out.println("Modify Appointment Button Clicked");
       try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyAppointmentMenu.fxml"));
            loader.load();

            ModifyAppointment modifyAppointment = loader.getController();

            Appointment selectedApp = appTableView.getSelectionModel().getSelectedItem();

            modifyAppointment.modApp(selectedApp);

           stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
           Parent scene = loader.getRoot();
           stage.setScene(new Scene(scene));
           stage.show();

        } catch (NullPointerException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Select a Appointment to Modify");
            alert.show();
        }
    }

    /**
     This action event loads the contact report controller.

     @param event Loads contact report menu action event.
     */

    @FXML
    void onActionReports(ActionEvent event) throws IOException {
        System.out.println("Appointment Report Button Clicked");
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ContactReportMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     This action event searches appointments by the start date.

     The database is open and gets all appointments then the set start date is selected for the look up method to check matching parameters.
     The table then will populate the results of the selected start date.
     This provides an alert if the dates selected does not have any appointments.

     @param event Loads the selected start date search action event.
     */

    @FXML
    void onActionSearchDate(ActionEvent event) {
        JDBC.openConnection();
        DBAppointment dbAppointment = new DBAppointmentImpl();
        appTableView.setItems(dbAppointment.getAllAppointments());
        LocalDate searchDate = datePicker.getValue();

        try{
            ObservableList<Appointment> appointments = dbAppointment.lookupApp(searchDate);
            appTableView.setItems(appointments);

            if(!((DBAppointmentImpl)dbAppointment).appFound){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Search Error");
                alert.setContentText("No Appointments for the Dates Picked");
                alert.showAndWait();

            }
        } catch (Exception e) {
            System.out.println("Appointment Search Error: " + e.getMessage());
        }
    }

    /**
     This action event loads view all radio button.
     This opens the database connection and gets all appointments.

     @param event Loads all appointments action event.
     */

    @FXML
    void onActionViewAll(ActionEvent event) {
        System.out.println("View All Appointments Radio Button Clicked");

        JDBC.openConnection();
        DBAppointment dbAppointment = new DBAppointmentImpl();
        appTableView.setItems(dbAppointment.getAllAppointments());
    }

    /**
     This action event loads view week radio button.
     This opens the database connection and gets all appointments by the week.

     @param event Loads all appointments for the current week action event.
     */

    @FXML
    void onActionViewWeek(ActionEvent event) {
        System.out.println("View Appointments by Week Radio Button Clicked");

        JDBC.openConnection();
        DBAppointment dbAppointment = new DBAppointmentImpl();
        appTableView.setItems(dbAppointment.appByWeek(LocalDate.from(dao.DBLogin.getLoginTime())));
    }

    /**
     This action event loads view month radio button.
     This opens the database connection and gets all appointments by the month.

     @param event Loads all appointments for the month action event.
     */

    @FXML
    void OnActionViewMonth(ActionEvent event) {
        System.out.println("View Appointments by Month Radio Button Clicked");

        JDBC.openConnection();
        DBAppointment dbAppointment = new DBAppointmentImpl();
        appTableView.setItems(dbAppointment.appByMonth(LocalDate.from(dao.DBLogin.getLoginTime())));
    }

    /**
     This initializes the main appointment controller
     This opens the database connection and gets all appointments then sets the table columns.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Main Appointment Menu Initialized");

        JDBC.openConnection();
        DBAppointment appDB = new DBAppointmentImpl();
        appTableView.setItems(appDB.getAllAppointments());

        appIdCol.setCellValueFactory(new PropertyValueFactory<>("appId"));
        appTitleCol.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
        appDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("appDescription"));
        appLocationCol.setCellValueFactory(new PropertyValueFactory<>("appLocation"));
        appContactCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        appTypeCol.setCellValueFactory(new PropertyValueFactory<>("appType"));
        appStartDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        appEndDateCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        appStartTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        appEndTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        appCxIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        appUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

    }
}
