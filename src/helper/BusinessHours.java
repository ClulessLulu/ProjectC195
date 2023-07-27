package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.*;

/**
 * This class provides the business hours methods for the customer appointment application.
 * @uthor Crystal Lu
 */


public class BusinessHours {

    /**
     This is the zone business hour observable list.
     This method is used to create a dynamic list of times that coverts depending on the local time of the operating system.

     @param zoneId operating system zone ID.
     @param businessZone specify the business hours by the zone ID.
     @param businessOpen the business hour starting at 8:00.
     @param busiHours the number of work hours.
     @return businessTime returns the list of business hours.

     */
    public static ObservableList<LocalTime> zoneBusinessHours(ZoneId zoneId, ZoneId businessZone, LocalTime businessOpen, int busiHours ){
        ObservableList<LocalTime> businessTime = FXCollections.observableArrayList();
        ZonedDateTime busiZDT = ZonedDateTime.of(LocalDate.now(), businessOpen, businessZone);
        ZonedDateTime localZDT = ZonedDateTime.ofInstant(busiZDT.toInstant(), zoneId);

        int startHour = localZDT.getHour();
        int ttlHours = startHour + busiHours;
        int pastMid = 0;

        for (int i = startHour; i<= ttlHours; i++){
            if(i < 24){
                businessTime.add(LocalTime.of(i,0));
            }
            if (i > 23){
                businessTime.add(LocalTime.of(pastMid, 0));
                pastMid += 1;
            }
        }
        return businessTime;
    }
}
