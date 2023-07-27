package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 This class for customer appointments to the customer appointment application.

 @uthor Crystal Lu
 */

public class Appointment {

    /**
     The ID for the Appointment.
     */

    private int appId;

    /**
     The title for the Appointment.
     */
    private String appTitle;

    /**
     The description for the Appointment.
     */

    private String appDescription;

    /**
     The location for the Appointment.
     */

    private String appLocation;

    /**
     The type for the Appointment.
     */

    private String appType;

    /**
     The local start date/time for the Appointment.
     */

    private LocalDateTime appStart;

    /**
     The local end date/time for the Appointment.
     */

    private LocalDateTime appEnd;

    /**
     The local start date for the Appointment.
     */

    private LocalDate startDate;

    /**
     The local end date for the Appointment.
     */

    private LocalDate endDate;

    /**
     The local start time for the Appointment.
     */

    private LocalTime startTime;

    /**
     The local end time for the Appointment.
     */

    private LocalTime endTime;

    /**
     The customer ID for the Appointment.
     */

    private int customerId;

    /**
     The user ID for the Appointment.
     */

    private int userId;

    /**
     The contact ID for the Appointment.
     */

    private int contactId;

    /**
     * This is the Appointment constructor.

      @param appId The ID for the Appointment.
      @param appTitle The title for the Appointment.
      @param appDescription The description for the Appointment.
      @param appLocation The location for the Appointment.
      @param appType The type for the Appointment.
      @param startDate The local start date/time for the Appointment.
      @param endDate The local end date/time for the Appointment.
      @param startDate The local start date for the Appointment.
      @param endDate The local end date for the Appointment.
      @param startTime The local start time for the Appointment.
      @param endTime The local end time for the Appointment.
      @param customerId The customer ID for the Appointment.
      @param userId The contact ID for the Appointment.
      @param contactId The contact ID for the Appointment.
     */

    public Appointment(int appId, String appTitle, String appDescription, String appLocation, String appType, LocalDateTime appStart, LocalDateTime appEnd, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, int customerId, int userId, int contactId) {
        this.appId = appId;
        this.appTitle = appTitle;
        this.appDescription = appDescription;
        this.appLocation = appLocation;
        this.appType = appType;
        this.appStart = appStart;
        this.appEnd = appEnd;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /**
     This is the getter for appointment ID.
     @return Returns the appointment ID.
     */

    public int getAppId() {
        return appId;
    }

    /**
     This is the setter for appointment ID.
     @param appId The ID for appointment.
     */


    public void setAppId(int appId) {
        this.appId = appId;
    }

    /**
     This is the getter for appointment title.
     @return Returns the appointment title.
     */


    public String getAppTitle() {
        return appTitle;
    }

    /**
     This is the setter for appointment title.
     @param appTitle The title for appointment.
     */


    public void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
    }

    /**
     This is the getter for appointment description.
     @return Returns the appointment description.
     */

    public String getAppDescription() {
        return appDescription;
    }

    /**
     This is the setter for appointment description.
     @param appDescription The description for appointment.
     */

    public void setAppDescription(String appDescription) {
        this.appDescription = appDescription;
    }

    /**
     This is the getter for appointment location.
     @return Returns the appointment location.
     */

    public String getAppLocation() {
        return appLocation;
    }

    /**
     This is the setter for appointment location.
     @param appLocation the location for appointment.
     */

    public void setAppLocation(String appLocation) {
        this.appLocation = appLocation;
    }

    /**
     This is the getter for appointment type.
     @return Returns the appointment type.
     */

    public String getAppType() {
        return appType;
    }

    /**
     This is the setter for appointment type.
     @param appType the type for appointment.
     */

    public void setAppType(String appType) {
        this.appType = appType;
    }

    /**
     This is the getter for appointment local start date/time.
     @return Returns the appointment local start date/time.
     */

    public LocalDateTime getAppStart() {
        return appStart;
    }

    /**
     This is the setter for appointment local start date/time.
     @param appStart the local start date/time for appointment.
     */

    public void setAppStart(LocalDateTime appStart) {
        this.appStart = appStart;
    }

    /**
     This is the getter for appointment local end date/time.
     @return Returns the appointment local end date/time.
     */


    public LocalDateTime getAppEnd() {
        return appEnd;
    }

    /**
     This is the setter for appointment local end date/time.
     @param appEnd the local end date/time for appointment.
     */

    public void setAppEnd(LocalDateTime appEnd) {
        this.appEnd = appEnd;
    }

    /**
     This is the getter for appointment local start date.
     @return Returns the appointment local start date.
     */

    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     This is the setter for appointment local start date.
     @param startDate the local start date for appointment.
     */

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     This is the getter for appointment local end date.
     @return Returns the appointment local end date.
     */

    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     This is the setter for appointment local end date.
     @param endDate the local end date for appointment.
     */

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     This is the getter for appointment local start time.
     @return Returns the appointment local start time.
     */

    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     This is the setter for appointment local start time.
     @param startTime the local start time for appointment.
     */

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    /**
     This is the getter for appointment local end time.
     @return Returns the appointment local end time.
     */

    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     This is the setter for appointment local end time.
     @param endTime the local end time for appointment.
     */

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    /**
     This is the getter for appointment customer ID.
     @return Returns the appointment customer ID.
     */

    public int getCustomerId() {
        return customerId;
    }

    /**
     This is the setter for appointment customer ID.
     @param customerId the appointment customer ID.
     */

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     This is the getter for appointment user ID.
     @return Returns the appointment user ID.
     */

    public int getUserId() {
        return userId;
    }

    /**
     This is the setter for appointment customer ID.
     @param userId the appointment customer ID.
     */

    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     This is the getter for appointment contact ID.
     @return Returns the appointment contact ID.
     */

    public int getContactId() {
        return contactId;
    }

    /**
     This is the setter for appointment contact ID.
     @param contactId the appointment contact ID.
     */

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     This is the Appointment toString method.

     This method provides default syntax for appointment information.
     */

    public String toString() {
        return ("Appointment: " + Integer.toString(appId) + "  Customer: [" + Integer.toString(customerId) + "] " +
                "| Contact: [" + Integer.toString(contactId) + "] | Type: " + appType + "| Start: " + appStart
                + " | End: " + appEnd );
    }
}
