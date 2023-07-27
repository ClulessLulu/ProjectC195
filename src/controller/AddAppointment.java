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
import model.Contact;
import model.Customer;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class provides the add appointment control to the customer appointment application.
 * @uthor Crystal Lu
 */

public class AddAppointment implements Initializable {
    Stage stage;
    Parent scene;

    /**
     The add appointment contact combo box
     */

    @FXML
    private ComboBox<Contact> contactCombo;

    /**
     The add appointment customer combo box
     */

    @FXML
    private ComboBox<Customer> cxCombo;

    /**
     The add appointment description text field
     */

    @FXML
    private TextField descriptionTxt;

    /**
     The add appointment end date picker
     */

    @FXML
    private DatePicker endDatePicker;

    /**
     The add appointment location text field
     */

    @FXML
    private TextField locationTxt;

    /**
     The add appointment start date picker
     */

    @FXML
    private DatePicker startDatePicker;

    /**
     The add appointment start time combo box
     */

    @FXML
    private ComboBox<LocalTime> startTimeCombo;

    /**
     The add appointment end time combo box
     */

    @FXML
    private ComboBox<LocalTime> endTimeCombo;

    /**
     The add appointment title text field
     */

    @FXML
    private TextField titleTxt;

    /**
     The add appointment type text field
     */

    @FXML
    private TextField typeTxt;

    /**
     The add appointment user combo box
     */

    @FXML
    private ComboBox<User> userCombo;

    /**
     The add appointment error label
     */

    @FXML
    private Label errorLbl;

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
     The action event is to add appointment to the database.
     Takes the information from the text field, combo boxes, and date picker then check is those fields are blank.
     If they are blank then an error message populates and if they are not blank then it checks for if the start and end date.
     If the start and end date are within business days and not a day prior then it checks the start and end time.
     If the start and end time are within business hours then check a for any overlap appointments by customer id is triggered.
     If there are any issues with the selected start and end date an error message will display.
     If there are any issues with the select start and end time an error message will display.
     If the selected customer id have no overlapping then the add appointment method is triggered which loads the main appointment menu.

     @param event Save appointment action event.
     */

    @FXML
    void onActionSaveApp(ActionEvent event) {
        System.out.println("Appointment Save Button Clicked");

        try{
            boolean appError = false;
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
                        if (!dbAppointment.checkNewAppOverlap(startDate, endDate, startTime, endTime)) {
                            dbAppointment.addApp(customerId, contactId, userId, appTitle, appDescription, appLocation, appType, appStart, appEnd);

                            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                            scene = FXMLLoader.load(getClass().getResource("/view/MainAppointmentMenu.fxml"));
                            stage.setScene(new Scene(scene));
                            stage.show();
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
     This initializes the add appointment controller.
     This opens the database connection and set the parameters for the business hours to correlate with the EST business zone id.
     Which then loads the business hours but are set to the system default zone id and displays 13 hours from the drop down.

     Then the combo box loads the contact, customer, and users for selection.
     The dates picker is set to the operating systems current local date.
     */


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Add Appointment Initialized");

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
            contactCombo.getSelectionModel().selectFirst();
            cxCombo.setItems(dbCustomer.getAllCx());
            cxCombo.getSelectionModel().selectFirst();
            userCombo.setItems(dbUser.getAllUsers());
            userCombo.getSelectionModel().selectFirst();
            startDatePicker.setValue(LocalDate.now());
            endDatePicker.setValue(LocalDate.now());
            startTimeCombo.setItems(BusinessHours.zoneBusinessHours(zoneId, businessZone, startTime, busiHours));
            startTimeCombo.getSelectionModel().selectFirst();
            endTimeCombo.setItems(BusinessHours.zoneBusinessHours(zoneId, businessZone, LocalTime.of(9,0), busiHours));
            endTimeCombo.getSelectionModel().selectFirst();

        } catch (Exception e) {
            System.out.println("Add Appointment Error: " + e.getMessage());
        }
    }
}

