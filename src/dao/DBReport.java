package dao;

import javafx.collections.ObservableList;
import model.Report;

/**
 * This class provides the dbreport methods to the customer appointment application.
 * @uthor Crystal Lu
 */

public interface DBReport {

    /**
     * This observable list of get all reports.
     */

    ObservableList<Report> getAllReports();

}
