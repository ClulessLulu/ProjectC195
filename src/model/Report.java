package model;

/**
 This class for report to the customer appointment application.

 @uthor Crystal Lu
 */


public class Report {

    /**
     The month for the Report.
     */

    private String month;

    /**
     The type for the Report.
     */

    private String appType;

    /**
     The total for the Report.
     */

    private int total;

    /**
     This is the Report constructor.

     @param month The month for the Report.
     @param appType The appointment type for the Report.
     */

    public Report(String month, String appType, int total) {
        this.month = month;
        this.appType = appType;
        this.total = total;
    }

    /**
     This is the getter for report month.
     @return Returns the report month.
     */

    public String getMonth() {
        return month;
    }

    /**
     This is the setter for report month.
     @param month The month for report.
     */

    public void setMonth(String month) {
        this.month = month;
    }

    /**
     This is the getter for report appointment type.
     @return Returns the report appointment type.
     */

    public String getAppType() {
        return appType;
    }

    /**
     This is the setter for report appointment type.
     @param appType The appointment type for report.
     */

    public void setAppType(String appType) {
        this.appType = appType;
    }

    /**
     This is the getter for report total.
     @return Returns the report total.
     */

    public int getTotal() {
        return total;
    }

    /**
     This is the setter for report total.
     @param total The total for report.
     */

    public void setTotal(int total) {
        this.total = total;
    }

}
