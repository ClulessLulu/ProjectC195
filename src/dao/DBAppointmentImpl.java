package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Alert;
import model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.*;

/**
 * This class provides the appointment implementation methods to the customer appointment application.
 * This class implements the DBAppointment class
 * @uthor Crystal Lu
 */


public class DBAppointmentImpl implements DBAppointment {

    public static String getCxId;
    /**
     This is the all appointments list.
     */

    ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    /**
     This is the appointment object used for the look up method.
     */

    public boolean appFound;

    /**
     This is the get all appointments method.
     This method accesses the database and returns all appointments which is then added to the observable list.

     @return allAppointments list
     */

    @Override
    public ObservableList<Appointment> getAllAppointments() {
        try {
            String sql = "SELECT * FROM appointments";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int appId = rs.getInt("Appointment_ID");
                String appTitle = rs.getString("Title");
                String appDescription = rs.getString("Description");
                String appLocation = rs.getString("Location");
                String appType = rs.getString("Type");
                LocalDateTime appStart = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime appEnd = rs.getTimestamp("End").toLocalDateTime();
                LocalDate startDate = appStart.toLocalDate();
                LocalDate endDate = appEnd.toLocalDate();
                LocalTime startTime = appStart.toLocalTime();
                LocalTime endTime = appEnd.toLocalTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointment appointment = new Appointment(appId, appTitle, appDescription, appLocation, appType, appStart, appEnd,
                        startDate, endDate, startTime, endTime, customerId, userId, contactId);
                allAppointments.add(appointment);
            }

        } catch (Exception e) {
            System.out.println("Appointments Get All Error: " + e.getMessage());
        }
        return allAppointments;
    }

    /**
     This is the get appointments by customer ID method.
     This method accesses the database and returns appointments associated to the customer ID.
     Then adds it to the customer appointments observable list.

     @param customerId the appointments associated to the customer ID
     @return cxApp list
     */

    @Override
    public ObservableList<Appointment> getAppByCxId(int customerId) {
        ObservableList<Appointment> cxApp = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments WHERE Customer_ID=?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, customerId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int appId = rs.getInt("Appointment_ID");
                String appTitle = rs.getString("Title");
                String appDescription = rs.getString("Description");
                String appLocation = rs.getString("Location");
                String appType = rs.getString("Type");
                LocalDateTime appStart = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime appEnd = rs.getTimestamp("End").toLocalDateTime();
                LocalDate startDate = appStart.toLocalDate();
                LocalDate endDate = appEnd.toLocalDate();
                LocalTime startTime = appStart.toLocalTime();
                LocalTime endTime = appEnd.toLocalTime();
                customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointment appointment = new Appointment(appId, appTitle, appDescription, appLocation, appType, appStart, appEnd,
                        startDate, endDate, startTime, endTime, customerId, userId, contactId);
                cxApp.add(appointment);
            }
        } catch (Exception e) {
            System.out.println("Appointment Get Customer ID Error: " + e.getMessage());

        }
        return cxApp;
    }

    /**
     This is the get appointments by contact ID method.
     This method accesses the database and returns appointments associated to the contact ID.
     Then adds it to the contact appointments observable list.

     @param contactId the appointments associated to the contact ID
     @return appByContactId list
     */

    @Override
    public ObservableList<Appointment> getAppByContactId(int contactId) {
        ObservableList<Appointment> appByContactId = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM appointments WHERE Contact_ID=?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, contactId);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int appId = rs.getInt("Appointment_ID");
                String appTitle = rs.getString("Title");
                String appDescription = rs.getString("Description");
                String appLocation = rs.getString("Location");
                String appType = rs.getString("Type");
                LocalDateTime appStart = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime appEnd = rs.getTimestamp("End").toLocalDateTime();
                LocalDate startDate = appStart.toLocalDate();
                LocalDate endDate = appEnd.toLocalDate();
                LocalTime startTime = appStart.toLocalTime();
                LocalTime endTime = appEnd.toLocalTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                contactId = rs.getInt("Contact_ID");

                Appointment appointment = new Appointment(appId, appTitle, appDescription, appLocation, appType, appStart, appEnd,
                        startDate, endDate, startTime, endTime, customerId, userId, contactId);
                appByContactId.add(appointment);
            }

        } catch (Exception e) {
            System.out.println("Appointment Get Contact ID Error: " + e.getMessage());
        }
        return appByContactId;
    }

    /**
     This observable list of get all appointments by equal local date.

     @param date looks up appointment by the equal local date.
     @return lookupApp list
     */

    @Override
    public ObservableList<Appointment> lookupApp(LocalDate date) {
        ObservableList<Appointment> lookupApp = FXCollections.observableArrayList();
        appFound = false;
        for (Appointment appointment : allAppointments){
            if(appointment.getStartDate().equals(date)){
                lookupApp.add(appointment);
            }
        }
        if(lookupApp.isEmpty()){
            return allAppointments;
        }
        appFound = true;
        return lookupApp;
    }

    /**
     This observable list of get all appointments by local date for the week.

     @param loginLD looks up appointment by local date of the week.
     @return fitlerApp list
     */


    @Override
    public ObservableList<Appointment> appByWeek(LocalDate loginLD) {
        allAppointments = getAllAppointments();
        FilteredList<Appointment> filterApp = new FilteredList<>(allAppointments);

        filterApp.setPredicate(appointment -> {
            LocalDate appDate = appointment.getStartDate();

            return ((appDate.isEqual(loginLD) || appDate.isAfter(loginLD))
                    && appDate.isBefore(loginLD.plusDays(7)));
        });
        return filterApp;
    }
    /**
     This observable list of get all appointments by local date for the month.

     @param loginLD looks up appointment by local date of the month.
     @return fitlerApp list
     */

    @Override
    public ObservableList<Appointment> appByMonth(LocalDate loginLD) {
        allAppointments = getAllAppointments();
        FilteredList<Appointment> filterApp = new FilteredList<>(allAppointments);

        filterApp.setPredicate(appointment -> {
            LocalDate appDate = appointment.getStartDate();

            return (appDate.isEqual(loginLD) || appDate.isAfter(loginLD))
                    && appDate.getMonth().equals(loginLD.getMonth());
        });
        return filterApp;
    }

    /**
     This method gets appointments by appointment ID.

     @param appId gets appointment by appointment ID.
     @return appResult returns the appointment.
     @return null returns no results if the appointment ID does not exist
     */

    @Override
    public Appointment getAppointment(int appId) {
        try{
            String sql = "SELECT * FROM appointments WHERE Appointment_ID=?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, appId);

            ResultSet rs = ps.executeQuery();
            Appointment appResult = null;
            if(rs.next()){
                appId = rs.getInt("Appointment_ID");
                String appTitle = rs.getString("Title");
                String appDescription = rs.getString("Description");
                String appLocation = rs.getString("Location");
                String appType = rs.getString("Type");
                LocalDateTime appStart = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime appEnd = rs.getTimestamp("End").toLocalDateTime();
                LocalDate startDate = appStart.toLocalDate();
                LocalDate endDate = appEnd.toLocalDate();
                LocalTime startTime = appStart.toLocalTime();
                LocalTime endTime = appEnd.toLocalTime();
                int customerId = rs.getInt("Customer_ID");
                int userId =  rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                appResult = new Appointment(appId, appTitle, appDescription, appLocation, appType,
                        appStart, appEnd, startDate, endDate, startTime, endTime, customerId, userId, contactId );
            }
            return appResult;
        } catch (Exception e) {
            System.out.println("Get Appointment Error: " + e.getMessage());
        }
        return null;
    }

    /**
     This is the add method that accesses the database and adds the appointment.

     @param customerId adds associated customer ID.
     @param contactId adds associated contact ID.
     @param userId adds associated user ID.
     @param appTitle adds appointment title.
     @param appDescription adds appointment description.
     @param appLocation adds appointment location.
     @param appType adds appointment type.
     @param appStart adds appointment start date and time.
     @param appEnd adds appointment end date and time.
     @return rowsAffected which are the number of affected rows in the database.
     */

    @Override
    public int addApp(int customerId, int contactId, int userId, String appTitle, String appDescription, String appLocation, String appType, LocalDateTime appStart, LocalDateTime appEnd) {
        int rowsAffected = 0;
        try{
            String sql = "INSERT INTO appointments (Customer_ID, Contact_ID, User_ID, Title, Description, " +
                    "Location, Type, Start, End) VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, customerId);
            ps.setInt(2, contactId);
            ps.setInt(3, userId);
            ps.setString(4, appTitle);
            ps.setString(5, appDescription);
            ps.setString(6, appLocation);
            ps.setString(7, appType);
            ps.setTimestamp(8, Timestamp.valueOf(appStart));
            ps.setTimestamp(9, Timestamp.valueOf(appEnd));
            rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0 ){
                System.out.println("Appointment Insert Successful");
            }
            else{
                System.out.println("Appointment Insert Failed");
            }
        } catch (Exception e) {
            System.out.println("Appointment Add Error: " + e.getMessage());
        }
        return rowsAffected;
    }

    /**
     This is the modify method that accesses the database and modifies the selected the appointment.

     @param appId the selected appointment unique appointment ID.
     @param customerId the selected appointment customer ID.
     @param contactId the selected appointment contact ID.
     @param userId the selected appointment user ID.
     @param appTitle the selected appointment title.
     @param appDescription the selected appointment description.
     @param appLocation the selected appointment location.
     @param appType the selected appointment type.
     @param appStart the selected appointment start date and time.
     @param appEnd the selected appointment end date and time.
     @return rowsAffected which are the number of affected rows in the database.
     */

    @Override
    public int modApp(int appId, int customerId, int userId, int contactId, String appTitle, String appDescription,
                         String appLocation, String appType, LocalDateTime appStart, LocalDateTime appEnd) {
       int rowAffected = 0;
       try {
           String sql = "UPDATE appointments SET Customer_ID=?, User_ID=?, Contact_ID=?, Title=?, Description=?, " +
                   "Location=?, Type=?, Start=?, End=? WHERE Appointment_ID=?";
           PreparedStatement ps = JDBC.connection.prepareStatement(sql);
           ps.setInt(1, customerId);
           ps.setInt(2, contactId);
           ps.setInt(3, userId);
           ps.setString(4, appTitle);
           ps.setString(5, appDescription);
           ps.setString(6, appLocation);
           ps.setString(7, appType);
           ps.setTimestamp(8, Timestamp.valueOf(appStart));
           ps.setTimestamp(9, Timestamp.valueOf(appEnd));
           ps.setInt(10, appId);
           rowAffected = ps.executeUpdate();

           if(rowAffected > 0){
               System.out.println("Appointment Update Successful");
           }
           else {
               System.out.println("Appointment Update Failed");
           }
       } catch (Exception e) {
           System.out.println("Appointment Update Error: " + e.getMessage());
       }
        return rowAffected;
    }

    /**
     This is the delete method that accesses the database and removes the appointment.

     @param appId the selected appointment unique appointment ID.
     @param customerId the selected appointment customer ID.
     @param appType the selected appointment type.
     @return rowsAffected which are the number of affected rows in the database.
     */

    @Override
    public int deleteApp(int appId, int customerId, String appType) {
        int rowAffected = 0;
        try{
            String sql = "DELETE FROM appointments WHERE Appointment_ID=? AND Customer_ID=? AND Type=?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, appId);
            ps.setInt( 2, customerId);
            ps.setString(3, appType);
            rowAffected = ps.executeUpdate();

            if(rowAffected > 0 ){
                System.out.println("Appointment " + appId + " was Deleted Successfully");
            }
            else{
                System.out.println("Appointment " + appId + " Deletion Failed");
            }
        } catch (Exception e) {
            System.out.println("Appointment Delete Error: " + e.getMessage());
        }
        return rowAffected;
    }

    /**
     This is the delete method that accesses the database and removes the appointment by customer ID.

     @param customerId the selected appointment customer ID.
     @return rowsAffected which are the number of affected rows in the database.
     */


    public int deleteCxApp(int customerId) {
        int rowAffected = 0;
        try{
            String sql = "DELETE FROM appointments WHERE Customer_ID=?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt( 1, customerId);
            rowAffected = ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Appointment Delete Error: " + e.getMessage());
        }
        return rowAffected;
    }

    /**
     This is the upcoming appointment alert method that accesses the database.
     Provides an alert for any upcoming appointment that is scheduled in 15 minutes of logging in.
     If there are no upcoming appointments an alert will state so.

     @param loginTimeAlert gets the local time date to check for upcoming appointments.
     */

    @Override
    public void upcomingAppAlert(LocalDateTime loginTimeAlert) {
        try{
            ObservableList<Appointment> upcomingAlert = FXCollections.observableArrayList();
            allAppointments = getAllAppointments();

            ZoneId zoneId = ZoneId.systemDefault();
            ZonedDateTime loginZoneDate = loginTimeAlert.atZone(zoneId);
            LocalDateTime appStartTime = loginTimeAlert.plusMinutes(15);

            for(Appointment appointment : allAppointments){
                ZonedDateTime appZone = ZonedDateTime.from(appointment.getAppStart().atZone(zoneId));
                if(appZone.isAfter(loginZoneDate) && appZone.isBefore(appStartTime.atZone(zoneId))){
                    upcomingAlert.add(appointment);
                    System.out.println("Upcoming Appointment for " + appointment);
                }
            }
            if (upcomingAlert.size() == 0){
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.setTitle("No Upcoming Appointments");
              alert.setContentText("There are no appointments in the next 15 minutes");
              alert.showAndWait();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Upcoming Appointments");
                alert.setHeaderText("The upcoming appointments will begin in 15 minutes are ");
                String appAlert = "";

                for(Appointment upcomingApp : upcomingAlert){
                    appAlert = ("Appointment [ " + upcomingApp.getAppId() + " ] at " +  upcomingApp.getStartDate() +
                            " ( "+ upcomingApp.getStartTime() +" ) " ) + appAlert;
                }
                alert.setContentText(appAlert);
                alert.showAndWait();
            }
        }
        catch (Exception e) {
            System.out.println("Upcoming Appointment Alert Error: " + e.getMessage());
        }
    }

    /**
     This is the check appointment start time method.
     Checks the start time and see if it was within the business hours of 8:00 to 22:00 EST.
     This also converts the local time to EST time and it is within business hours.

     @param appStart gets the local date and time then checks start date and time.
     @return appStart the start date and time if the time criteria is met.
     */

    @Override
    public boolean checkAppStart(LocalDateTime appStart) {
        ZonedDateTime zoneApp = appStart.atZone(ZoneId.systemDefault());
        zoneApp = zoneApp.withZoneSameInstant(ZoneId.of("US/Eastern"));
        appStart = zoneApp.toLocalDateTime();

        LocalTime openBusinessHour = LocalTime.of(8,0);
        LocalTime closeBusinessHour = LocalTime.of(22, 0);

        return ((appStart.toLocalTime().isAfter(openBusinessHour) || appStart.toLocalTime().equals(openBusinessHour))
                && (appStart.toLocalTime().isBefore(closeBusinessHour)));
    }

    /**
     This is the check appointment end time method.
     Checks the end time and see if it was within the business hours of 8:00 to 22:00 EST.
     This also converts the local time to EST time and it is within business hours.

     @param appEnd gets the local date and time then checks start date and time.
     @return appEnd the end date and time if the time criteria is met.
     */

    @Override
    public boolean checkAppEnd(LocalDateTime appEnd) {
        ZonedDateTime zoneApp = appEnd.atZone(ZoneId.systemDefault());
        zoneApp = zoneApp.withZoneSameInstant(ZoneId.of("US/Eastern"));
        appEnd = zoneApp.toLocalDateTime();

        LocalTime openBusinessHour = LocalTime.of(8,0);
        LocalTime closeBusinessHour = LocalTime.of(22, 0);

        return ((appEnd.toLocalTime().isAfter(openBusinessHour)) &&
                (appEnd.toLocalTime().isBefore(closeBusinessHour) || appEnd.toLocalTime().equals(closeBusinessHour)));
    }

    /**
     This is the appointment overlap check method that accesses the database and checks if there are any overlapping appointments.

     @param overlapStartDate the appointment start date.
     @param overlapEndDate the appointment end date.
     @param overlapStartTime the appointment start time.
     @param overlapEndTime the appointment end time.
     @return appOverlap if true it will return any overlap appointments.
     @return appOverlap if false no overlap appointments will return.
     */

   @Override
    public boolean checkNewAppOverlap(LocalDate overlapStartDate, LocalDate overlapEndDate, LocalTime overlapStartTime, LocalTime overlapEndTime) {
    DBAppointment dbAppointment = new DBAppointmentImpl();
     ObservableList<Appointment> cxOverlapCheck = dbAppointment.getAllAppointments();
     boolean appOverlap = false;
     for (Appointment appointment : cxOverlapCheck){
         if ((appointment.getStartDate().isEqual(overlapStartDate)) || (appointment.getEndDate().isEqual(overlapEndDate))){
            if (appointment.getStartTime().equals(overlapStartTime)){
                 appOverlap = true;
                 break;
             }else if(appointment.getStartTime().isAfter(overlapStartTime) && appointment.getStartTime().isBefore(overlapEndTime)){
                 appOverlap = true;
                 break;
             }
             else if (overlapStartTime.isBefore(appointment.getStartTime()) && (overlapEndTime.isAfter(appointment.getStartTime()))){
                 appOverlap = true;
                 break;
             }
         }
     }
        return appOverlap;
    }

    /**
     This is the modified appointment overlap check method that accesses the database and checks if there are any overlapping appointments.


     @param overlapStartDate the selected appointment start date.
     @param overlapEndDate the selected appointment end date.
     @param overlapStartTime the selected appointment start time.
     @param overlapEndTime the selected appointment end time.
     @return modOverlap if true it will return any overlap appointments.
     @return modOverlap if false no overlap appointments will return.
     */

    @Override
    public boolean checkModifyAppOverlap(LocalDate overlapStartDate, LocalDate overlapEndDate, LocalTime overlapStartTime, LocalTime overlapEndTime) {
        DBAppointment dbAppointment = new DBAppointmentImpl();
        ObservableList<Appointment> modOverlapCheck = dbAppointment.getAllAppointments();
        boolean modOverlap = false;
        for (Appointment appointment : modOverlapCheck){

            if ((appointment.getStartDate().isEqual(overlapStartDate)) || (appointment.getEndDate().isEqual(overlapEndDate))){
                if (appointment.getStartTime().equals(overlapStartTime)){
                    modOverlap = true;
                    break;
                }else if(appointment.getStartTime().isAfter(overlapStartTime) && appointment.getStartTime().isBefore(overlapEndTime)){
                    modOverlap = true;
                    break;
                }
                else if (overlapStartTime.isBefore(appointment.getStartTime()) && (overlapEndTime.isAfter(appointment.getStartTime()))){
                    modOverlap = true;
                    break;
                }
            }
        }
        return modOverlap;
    }
}
