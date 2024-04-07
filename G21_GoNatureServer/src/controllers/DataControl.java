 
package controllers;
 
import controllers.QueriesConnectionSQL.DataQueries;
import server.GoNatureServer;
 
/**
 * Manages data export functionality.
 */
public class DataControl {
 
    private static DataQueries dataQueries = new DataQueries(GoNatureServer.mysqlconnection);
 
    /**
     * Exports data from the database.
     * 
     * @return The result of the data export operation.
     */
    public static int exportData() {
        return dataQueries.exportTableData();
    }
}