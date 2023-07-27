package controller;


import dao.*;
import helper.BusinessHours;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class provides the modify appointment control to the customer appointment application.
 * @uthor Crystal Lu
 */


public class ModifyAppointment implements Initializable {
    Stage stage;
    Parent scene;

    /**
     The modify contact combo box.
     */

    @FXML
    private ComboBox<Contact> contactCombo;

    /**
     The modify customer combo box.
     */

    @FXML
    private ComboBox<Customer> cxCombo;

    /**
     The modify description text field.
     */

    @FXML
    private TextField descriptionTxt;

    /**
     The modify end date picker.
     */

    @FXML
    private DatePicker endDatePicker;

    /**
     The modify end time combo box.
     */

    @FXML
    private ComboBox<LocalTime> endTimeCombo;

    /**
     The modify location text field.
     */

    @FXML
    private TextField locationTxt;

    /**
     The modify start date picker.
     */

    @FXML
    private DatePicker startDatePicker;

    /**
     The modify start time combo box.
     */

    @FXML
    private ComboBox<LocalTime> startTimeCombo;

    /**
     The modify title text field.
     */

    @FXML
    private TextField titleTxt;

    /**
     The modify type text field.
     */

    @FXML
    private TextField typeTxt;

    /**
     The modify user combo box.
     */

    @FXML
    private ComboBox<User> userCombo;

    /**
     The modify error label.
     */

    @FXML
    private Label errorLbl;

    /**
     The modify selected appointment.
     */

    Appointment modApp = null;

    /**
     The is the modify appointment method.
     This will populate the selected appointment from the main appointment menu.
     This fills the data into the fields to be modified

     @param selectedApp The selected appointment to be modified.
     */

    public void modApp(Appointment selectedApp){
        JDBC.openConnection();
        DBContact dbContact = new DBContactImpl();
        DBCustomer dbCustomer = new DBCustomerImpl();
        DBUser dbUser = new DBUserImpl();

        modApp = selectedApp;
        titleTxt.setText(String.valueOf(modApp.getAppTitle()));
        descriptionTxt.setText(String.valueOf(modApp.getAppDescription()));
        locationTxt.setText(String.valueOf(modApp.getAppLocation()));
        typeTxt.setText(String.valueOf(modApp.getAppType()));

        Contact modContact = null;
        for(Contact contact : dbContact.getAllContacts()){
            if(contact.getContactId() == modApp.getContactId()){
                modContact = contact;
                break;
            }
        }
        contactCombo.getSelectionModel().select(modContact);

        Customer modCx = null;
        for(Customer customer : dbCustomer.getAllCx()){
            if(customer.getCustomerId() == modApp.getCustomerId()){
                modCx = customer;
                break;
            }
        }
        cxCombo.getSelectionModel().select(modCx);

        User modUser = null;
        for(User user : dbUser.getAllUsers()){
            if(user.getUserId() == modApp.getUserId()){
                modUser = user;
                break;
            }
        }
        userCombo.getSelectionModel().select(modUser);

        startDatePicker.setValue(modApp.getStartDate());
        endDatePicker.setValue(modApp.getEndDate());
        startTimeCombo.getSelectionModel().select(modApp.getStartTime());
        endTimeCombo.getSelectionModel().select(modApp.getEndTime());
    }

    /**
     The action event is for canceling and provides a confirmation dialog to confirm cancellation.
     This will loads the appointment main menu.

     @param event Cancel button to load main appointment menu.
     */

    @FXML
    void onActionCancelApp(ActionEvent event) throws IOException {
        System.out.println("Cancel Button Clicked");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Add Appointment Cancel");
        alert.setContentText("Values will be cleared, do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainAppointmentMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     The action event is to save the modified appointment to the database.
     Takes the information from the text field, combo boxes, and date picker then check is those fields are blank.
     If they are blank then an error message populates and if they are not blank then it checks for if the start and end date.
     If the start and end date are within business days and not a day prior then it checks the start and end time.
     If the start and end time are within business hours then check a for any overlap appointments by customer id is triggered.
     If there are any issues with the selected start and end date an error message will display.
     If there are any issues with the select start and end time an error message will display.
     If the selected customer id have no overlapping then the add appointment method is triggered which loads the main appointment menu.

     @param event Save modified appointment action event.
     */

    @FXML
    void onActionSaveApp(ActionEvent event) {
        System.out.println("Appointment Save Button Clicked");
        try{
            boolean appError = false;
            int appId = modApp.getAppId();
            String appTitle = titleTxt.getText();
            String appDescription = descriptionTxt.getText();
            String appLocation = locationTxt.getText();
            String appType = typeTxt.getText();
            int contactId = contactCombo.getSelectionModel().getSelectedItem().getContactId();
            int customerId = cxCombo.getSelectionModel().getSelectedItem().getCustomerId();
            int userId = userCombo.getSelectionModel().getSelectedItem().getUserId();
            LocalDate startDate = startDatePicker.getValue();
            LocalDate endDate = endDatePicker.getValue();
            LocalTime startTime = startTimeCombo.getSelectionModel().getSelectedItem();
            LocalTime endTime = endTimeCombo.getSelectionModel().getSelectedItem();
            LocalDateTime appStart = LocalDateTime.of(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth(), startTime.getHour(), startTime.getMinute());
            LocalDateTime appEnd = LocalDateTime.of(endDate.getYear(), endDate.getMonth(), endDate.getDayOfMonth(), endTime.getHour(), endTime.getMinute());

            if(appTitle.isBlank()){
                errorMessage(1);
                appError = true;
            }
            else if(appDescription.isBlank()){
                errorMessage(2);
                appError = true;
            }
            else if(appLocation.isBlank()){
                errorMessage(3);
                appError = true;
            }
            else if(appType.isBlank()){
                errorMessage(4);
                appError = true;
            }

            else if (startDate.isBefore(LocalDate.now()) && startTime.isBefore(LocalTime.now())){
                errorMessage(8);
                appError = true;
            }

            else if(appStart.getDayOfWeek().equals(DayOfWeek.SATURDAY) && appEnd.getDayOfWeek().equals(DayOfWeek.SATURDAY) ||
                    appStart.getDayOfWeek().equals(DayOfWeek.SUNDAY) && appEnd.getDayOfWeek().equals(DayOfWeek.SUNDAY) ||
                    appStart.getDayOfWeek().equals(DayOfWeek.SATURDAY) && appEnd.getDayOfWeek().equals(DayOfWeek.SUNDAY) )  {
                errorMessage(9);
                appError = true;
            }

            else if(appStart.isBefore(LocalDateTime.now()) && appEnd.isBefore(LocalDateTime.now())){
                errorMessage(10);
                appError = true;
            }

            if(!appError){
                DBAppointment dbAppointment = new DBAppointmentImpl();
                if(dbAppointment.checkAppStart(appStart) && dbAppointment.checkAppEnd(appEnd)){
                    if (appStart.toLocalTime().isBefore(appEnd.toLocalTime())) {
                        if (!dbAppointment.checkModifyAppOverlap(startDate, endDate, startTime, endTime)) {
                            dbAppointment.modApp(appId, customerId, contactId, userId, appTitle, appDescription, appLocation, appType, appStart, appEnd);

                            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                            scene = FXMLLoader.load(getClass().getResource("/view/MainAppointmentMenu.fxml"));
                            stage.setScene(new Scene(scene));
                            stage.show();
                            JDBC.closeConnection();
                        }
                        else {

                           errorMessage(7);
                        }
                    }
                    else  {
                        errorMessage(6);
                    }
                }
                else  {
                    errorMessage(5);
                }
            }
        } catch (Exception e) {
            System.out.println("Appointment Add Error: " + e.getMessage());
        }
    }

    /**
     The is the lambda expression error message method.
     This method is used to generate an error on the error label and displays the error message.
     It also display an error alert if there is an issue with the selected start or end date and time.

     The reason why i used the lambda expression is to reduce the amount of coding needed to create error messages.
     The error is associated by case number which is the displayed.
     */

    public void errorMessage(int errorCode){
        switch (errorCode){
            case 1 -> {
                errorLbl.setText("Title field cannot be blank");
            }
            case 2 -> {
                errorLbl.setText("Description field cannot be blank");
            }
            case 3 -> {
                errorLbl.setText("Location field cannot be blank");
            }
            case 4 -> {
                errorLbl.setText("Type field  cannot be blank");
            }
            case 5 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error with Start or End Time");
                alert.setContentText("Appointment Selected out of Business Hours. Please choose a time between 08:00 AM and 22:00 PM EST");
                alert.showAndWait();
            }
            case 6 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error with Start or End Time");
                alert.setContentText("The Start Date or Time needs to be Before the End Date or Time. Please select a different time slot");
                alert.showAndWait();
            }
            case 7 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Appointment is Overlapping");
                alert.setContentText("Customer " + cxCombo.getSelectionModel().getSelectedItem() + " has an overlapping appointment. Please select a different time slot");
                alert.showAndWait();
            }
            case 8 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error with Start or End Time");
                alert.setContentText("The Start Date or Time needs to be After Current Date or Time. Please select a different time slot");
                alert.showAndWait();
            }
            case 9 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error with Start or End Date");
                alert.setContentText("Appointment Selected on a Weekend. Please choose a Day between Monday to Friday");
                alert.showAndWait();
            }
            case 10 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error with Start or End Time");
                alert.setContentText("The Time Selected has passed. Please choose a time between 08:00 AM and 22:00 PM EST for the Next Business Day");
                alert.showAndWait();
            }
        }
    }

    /**
     This initializes the modify appointment controller.
     This opens the database connection and set the parameters for the business hours to correlate with the EST business zone id.
     Which then loads the business hours but are set to the system default zone id and displays 13 hours from the drop down.

     Then the combo box loads the contact, customer, and users for selection.
     The dates picker is set to the operating systems current local date.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Update Appointment Initialized!");
        try{
            ZoneId zoneId = ZoneId.systemDefault();
            ZoneId businessZone = ZoneId.of("America/New_York");
            LocalTime startTime = LocalTime.of(8,0);

            int busiHours = 13;

            JDBC.openConnection();
            DBContact dbContact = new DBContactImpl();
            DBCustomer dbCustomer = new DBCustomerImpl();
            DBUser dbUser = new DBUserImpl();

            contactCombo.setItems(dbContact.getAllContacts());
            cxCombo.setItems(dbCustomer.getAllCx());
            userCombo.setItems(dbUser.getAllUsers());
            startTimeCombo.setItems(BusinessHours.zoneBusinessHours(zoneId, businessZone, startTime, busiHours));
            endTimeCombo.setItems(BusinessHours.zoneBusinessHours(zoneId, businessZone, LocalTime.of(9,0), busiHours));

        } catch (Exception e) {
            System.out.println("Modify Appointment Error: " + e.getMessage());
        }
    }
}
