package main;

/**
 Javadoc located in file path ProjectC195\javadoc\
 @author Crystal Lu
 */

import dao.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** This class creates an customer appointment management application and connect to the sql database to pull data.
        */

public class Main extends Application {
    /** This start method initializes fxml file LoginScreenMenu.fxml

     @param primaryStage shows LoginScreenMenu.fxml.
     */

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/LoginScreenMenu.fxml"));
        primaryStage.setTitle("Customer Appointment Management System");
        primaryStage.setScene(new Scene(root, 1250, 445));
        primaryStage.show();
    }

    /** This is the main method that calls JDBC connection to open before application launch then close after it is closed.
     */

    public static void main(String[] args) {
        JDBC.openConnection();

        launch(args);

        JDBC.closeConnection();
    }
}
