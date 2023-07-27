package controller;

import dao.DBAppointment;
import dao.DBAppointmentImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.User;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

import static dao.DBLogin.userLoginQuery;

/**
 * This class provides the login control to the customer appointment application.
 * @uthor Crystal Lu
 */

public class LoginScreen implements Initializable {
    Stage stage;
    Parent scene;

    /**
     The cancel button on the login screen.
     */

    @FXML
    private Button cancelBttn;

    /**
     The Welcome Customer Label on the login screen.
     */

    @FXML
    private Label cxLbl;

    /**
     The login button on the login screen.
     */

    @FXML
    private Button loginBttn;

    /**
     The password text field on the login screen.
     */

    @FXML
    private PasswordField passTxt;

    /**
     The password label on the login screen.
     */

    @FXML
    private Label passwordLbl;

    /**
     The time zone label on the login screen.
     */

    @FXML
    private Label timeZoneLbl;

    /**
     The username label on the login screen.
     */

    @FXML
    private Label userLbl;

    /**
     The current user's time zone label on the login screen.
     */

    @FXML
    private Label userTimeLbl;

    /**
     The username text field on the login screen.
     */

    @FXML
    private TextField userTxt;

    /**
     The action event for the cancel button to clear data in text field.
     @param event Cancel button action event.
     */

    @FXML
    void onActionCancel(ActionEvent event) {
        System.out.println("Cancel Button is Clicked");
        userTxt.setText("");
        passTxt.setText("");

    }

    /**
     * The action event for the login button to allow access into the customer appointment management application.
     * This action event includes methods to check the username and password in the text field and validates the fields by calling the userLoginQuery method.
     * While opening the MainAppointmentMenu.fxml, the loginAlert checks the current time and JDBC connection opens to call the upcoming appointment alert method.
     * This will then check any appointments that might occur in the next 15 minutes. If there are no appointments another alert will prompt stating that there are no upcoming appointments.
     * If login is unsuccessful an alert will state so and will be logged into the "login_activity.txt" file.
     * If the operating system default is set to french all labels and alert will be displayed in french.
     *
     @param event Login button action event.
     */

    @FXML
    void onActionLogin(ActionEvent event) throws IOException {
        System.out.println("Login Button Clicked");

        try{
            String userName = userTxt.getText();
            String password = String.valueOf(passTxt.getText());
            User userResult = userLoginQuery(userName, password);
            LocalDate dateLog = LocalDate.now();
            LocalTime timeLog = LocalTime.now();
            String fileName = "login_activity.txt";

            FileWriter fWriter = new FileWriter(fileName, true);
            PrintWriter outputFile = new PrintWriter(fWriter);

            if(userResult != null){
                outputFile.println("User: " + userName + " has logged in Successfully [ Date: " + dateLog + " Time: " + timeLog + " " + ZoneId.systemDefault() + "]");
                System.out.println("Activity Logged");
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainAppointmentMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();

                DBAppointment dbAppointment = new DBAppointmentImpl();
                LocalDateTime loginAlert = LocalDateTime.now(ZoneId.systemDefault());
                dbAppointment.upcomingAppAlert(loginAlert);
            }

            else if(Locale.getDefault().getLanguage().equals("fr")){
                ResourceBundle rb = ResourceBundle.getBundle("properties/Lang_fr", Locale.getDefault());

                outputFile.println("User: " + userName + " has logged in Successfully [ Date: " + dateLog + " Time: " + timeLog + " " + ZoneId.systemDefault() + "]");
                System.out.println("Activity Logged");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(rb.getString("Invalid") + " " + rb.getString("Username") +
                        " " + rb.getString("and") + "/" + rb.getString("or") +
                        " " + rb.getString("Password") + ". " + rb.getString("Please") +
                        " " + rb.getString("try") + rb.getString("again") + "!");
                alert.showAndWait();
            }

            else {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Invalid Username and/or Password. Please try again!");
                alert.showAndWait();
                outputFile.println("User: " + userName + " has logged in Unsuccessfully [ Date: " + dateLog + " Time: " + timeLog + " " + ZoneId.systemDefault() + "]");
                System.out.println("Activity logged");
            }

            outputFile.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * The initialize controller for the login screen.
     * States that the login is initialized and changes the userTimeLbl to the user's default zone id from their operating system.
     *
     * If the user's operating system is defaulted to french then the french language resource is called from the properties package.
     * The text labels are then translated and displayed in french but if it is not french then it will display in english.
     *
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dao.JDBC.openConnection();
        System.out.println("Login is Initialized");
        userTimeLbl.setText(String.valueOf(ZoneId.systemDefault()));

        try {
            if (Locale.getDefault().getLanguage().equals("fr")) {
                ResourceBundle rb = ResourceBundle.getBundle("properties/Lang_fr", Locale.getDefault());

                userLbl.setText(rb.getString("Username"));
                passwordLbl.setText(rb.getString("Password"));
                timeZoneLbl.setText(rb.getString("TimeZone"));
                loginBttn.setText(rb.getString("Login"));
                cancelBttn.setText(rb.getString("Cancel"));
                cxLbl.setText(rb.getString("CustomerSystem"));
            }
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
