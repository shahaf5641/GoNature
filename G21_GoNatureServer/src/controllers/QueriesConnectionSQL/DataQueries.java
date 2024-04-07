 
package controllers.QueriesConnectionSQL;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 
/**
 * Handles database queries related to exporting data.
 */
public class DataQueries {
    private Connection conn;
 
    public DataQueries(Connection conn) {
        this.conn = conn;
    }
 
    /**
     * Exports table data from the database.
     * 
     * @return An integer indicating the result of the data export operation.
     *         - 0: Data export successful.
     *         - 1: Error occurred during data export.
     */
    public int exportTableData() {
        String sql = "SELECT * FROM g21gonature.system_users";
        try {
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(sql);
            while (res.next()) {
                // Guide
                if (res.getString(2) == null) {
                    sql = "INSERT INTO g21gonature.guide (travelerId, firstName, lastName, email, phoneNumber)"
                            + " VALUES (?, ?, ?, ?, ?);";
                } else { // Employee
                    sql = "INSERT INTO g21gonature.employees (employeeId, job, parkId, FirstName, LastName, email, username, password)"
                            + " VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
                }
                try (PreparedStatement query = conn.prepareStatement(sql)) {
                    if (res.getString(2) == null) { // Guide
                        query.setString(1, res.getString(1));
                        query.setString(2, res.getString(4));
                        query.setString(3, res.getString(5));
                        query.setString(4, res.getString(6));
                        query.setString(5, res.getString(7));
                    } else { // Employee
                        query.setString(1, res.getString(1));
                        query.setString(2, res.getString(2));
                        query.setString(3, res.getString(3));
                        query.setString(4, res.getString(4));
                        query.setString(5, res.getString(5));
                        query.setString(6, res.getString(6));
                        query.setString(7, res.getString(8));
                        query.setString(8, res.getString(9));
                    }
                    query.executeUpdate();
                }
            }
            return 0; // Data export successful
        } catch (SQLException e) {
            return 1; // Error occurred during data export
        }
    }
}