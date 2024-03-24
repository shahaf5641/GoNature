package controllers.sqlHandlers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataQueries {
    private Connection conn;

    public DataQueries(Connection conn) {
        this.conn = conn;
    }

    public void exportTableData() {
        String sql = "SELECT * FROM g21gonature.system_users";
        try {
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(sql);
            while (res.next()) {
            	//Subscriber
                if (res.getString(2) == null) {
                    sql = "INSERT INTO g21gonature.subscriber (travelerId, firstName, lastName, email, phoneNumber)"
                            + " VALUES (?, ?, ?, ?, ?);";
                //Employee
                } else {
                    sql = "INSERT INTO g21gonature.employees (employeeId, role, parkId, firstName, lastName, email, username, password)"
                            + " VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
                }
                //query.setInt(1, Integer.parseInt((String) parameters.get(0)));
                try (PreparedStatement query = conn.prepareStatement(sql)) {
                    if (res.getString(2) == null) { 
                    query.setString(1, res.getString(1));
                    query.setString(2, res.getString(4));
                    query.setString(3, res.getString(5));
                    query.setString(4, res.getString(6));
                    query.setString(5, res.getString(7));
                    }
                    else {
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
            System.out.println("Data export completed successfully.");
        } catch (SQLException e) {
            System.out.println("Please empty tables and try again");
            e.printStackTrace();
        }
    }
}
