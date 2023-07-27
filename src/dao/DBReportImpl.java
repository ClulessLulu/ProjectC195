package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Report;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * This class provides the report implementation methods to the customer appointment application.
 * This class implements the DBReport class
 * @uthor Crystal Lu
 */

public class DBReportImpl implements DBReport{

    /**
     This is the all reports observable list.
     */

    ObservableList<Report> allReports = FXCollections.observableArrayList();

    /**
     * This observable list of get all reports.
     * This method accesses the database and gets the month start, type, and count of the appointments.
     * @return allReports returns all reports
     *
     */

    @Override
    public ObservableList<Report> getAllReports() {
        try{
            String sql = "SELECT monthname(start), type, count(*) as cnt FROM appointments GROUP BY monthname(start), type";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                String month = rs.getString("monthname(start)");
                String appType = rs.getString("type");
                int total = rs.getInt("cnt");
                Report report = new Report(month, appType, total);
                allReports.add(report);
            }
        } catch (Exception e) {
            System.out.println("Report Get All Error: " + e.getMessage());
        }
        return allReports;
    }
}
