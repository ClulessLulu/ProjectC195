package dao;

import javafx.collections.ObservableList;
import model.Appointment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * This class provides the dbappointment methods to the customer appointment application.
 * @uthor Crystal Lu
 */

public interface DBAppointment {

    /**
     This observable list of get all appointments.
     * @return
     */

    public ObservableList<Appointment> getAllAppointments();

    /**
     This observable list of get all appointments by customer id.

     @param customerId gets appointment by customer id.
     */

    public ObservableList<Appointment> getAppByCxId(int customerId);

    /**
     This observable list of get all appointments by contact id.

     @param contactId gets appointment by contact id.
     */

    public ObservableList<Appointment> getAppByContactId(int contactId);

    /**
     This observable list of get all appointments by local date.

     @param date looks up appointment by local date.
     */

    public ObservableList<Appointment> lookupApp (LocalDate date);

    /**
     This observable list of get all appointments by local date for the week.

     @param loginLD looks up appointment by local date of the week.
     */

    public ObservableList<Appointment> appByWeek (LocalDate loginLD);

    /**
     This observable list of get all appointments by local date for the month.

     @param loginLD looks up appointment by local date of the month.
     */

    public ObservableList<Appointment> appByMonth(LocalDate loginLD);

    /**
     This method gets appointments by appointment ID.

     @param appId gets appointment by appointment ID.
     */

    public Appointment getAppointment(int appId);

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
     */

    public int addApp(int customerId, int contactId, int userId, String appTitle, String appDescription,
                      String appLocation, String appType, LocalDateTime appStart, LocalDateTime appEnd);

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
     */

    public int modApp(int appId, int customerId, int userId, int contactId, String appTitle, String appDescription,
                      String appLocation, String appType, LocalDateTime appStart, LocalDateTime appEnd);

    /**
     This is the delete method that accesses the database and removes the appointment.

     @param appId the selected appointment unique appointment ID.
     @param customerId the selected appointment customer ID.
     @param appType the selected appointment type.
     */

    public int deleteApp(int appId, int customerId, String appType);


    /**
     This is the upcoming appointment alert method that accesses the database.
     Provides an alert for any upcoming appointment that is scheduled in 15 minutes of logging in.
     If there are no upcoming appointments an alert will state so.

     @param loginTimeAlert gets the local time date to check for upcoming appointments.
     */

    public void upcomingAppAlert (LocalDateTime loginTimeAlert);

    /**
     This is the check appointment start time method.
     Checks the start time and see if it was within the business hours of 8:00 to 22:00 EST.
     This also converts the local time to EST time and it is within business hours.

     @param appStart gets the local date and time then checks start date and time.
     */


    public boolean checkAppStart(LocalDateTime appStart);

    /**
     This is the check appointment end time method.
     Checks the end time and see if it was within the business hours of 8:00 to 22:00 EST.
     This also converts the local time to EST time and it is within business hours.

     @param appEnd gets the local date and time then checks start date and time.
     */

    public boolean checkAppEnd(LocalDateTime appEnd);

    /**
     This is the appointment overlap check method that accesses the database and checks if there are any overlapping appointments.


     @param overlapStartDate the appointment start date.
     @param overlapEndDate the appointment end date.
     @param overlapStartTime the appointment start time.
     @param overlapEndTime the appointment end time.
     */



    public boolean checkNewAppOverlap (LocalDate overlapStartDate, LocalDate overlapEndDate, LocalTime overlapStartTime, LocalTime overlapEndTime);

    /**
     This is the modified appointment overlap check method that accesses the database and checks if there are any overlapping appointments.


     @param overlapStartDate the selected appointment start date.
     @param overlapEndDate the selected appointment end date.
     @param overlapStartTime the selected appointment start time.
     @param overlapEndTime the selected appointment end time.
     */

    public boolean checkModifyAppOverlap(LocalDate overlapStartDate, LocalDate overlapEndDate, LocalTime overlapStartTime, LocalTime overlapEndTime);

}
