package controllers;

import controllers.sqlHandlers.DataQueries;
import server.GoNatureServer;

public class DataControl {

    // Access the TravelersQueries object for retrieving email addresses
    private static DataQueries dataQueries = new DataQueries(GoNatureServer.mysqlconnection);
    public static int ExportData()
    {
    	return(dataQueries.exportTableData());
    	
    }
}
