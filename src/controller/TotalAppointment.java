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
import model.Report;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class provides the total appointment control to the customer appointment application.
 * @uthor Crystal Lu
 */

public class TotalAppointment implements Initializable {
    Stage stage;
    Parent scene;

    /**
     The report table
     */

    @FXML
    private TableView<Report> ttlAppReport;

    /**
     The count column for the table
     */

    @FXML
    private TableColumn<?, ?> countCol;

    /**
     The month column for the table
     */

    @FXML
    private TableColumn<?, ?> monthCol;

    /**
     The type column for the table
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
        System.out.println("Total Appointment Logout Button Clicked");

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
     This action event loads the  customer report menu controller.

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
     This initializes the total appointment report menu controller
     This opens the database connection and gets all reports for the months, the type, and displays the total appointments.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Total Appointment Menu Initialized");

        JDBC.openConnection();
        DBReport dbReport = new DBReportImpl();
        ttlAppReport.setItems(dbReport.getAllReports());

        monthCol.setCellValueFactory(new PropertyValueFactory<>("month"));
        countCol.setCellValueFactory(new PropertyValueFactory<>("total"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("appType"));
    }
}