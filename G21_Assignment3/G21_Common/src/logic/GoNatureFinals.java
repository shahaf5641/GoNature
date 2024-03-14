package logic;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class is responsible for storing the system's final constants.
 */
public final class GoNatureFinals {

    // Private constructor to prevent instantiation of the class
    private GoNatureFinals() {
    }
    
    // Constants for system properties

    /** Application icon file path */
    public final static String APP_ICON = "/resources/images/tree.png";

    /** Full price constant */
    public final static int FULL_PRICE = 100;

    /** GoNature email address */
    public final static String GO_NATURE_EMAIL = "G21GoNature@gmail.com";

    /** GoNature email password */
    public final static String GO_NATURE_EMAIL_PASSWORD = "Aa123456!";

    /** Available hours throughout the day */
    public final static ArrayList<String> AVAILABLE_HOURS = new ArrayList<>(Arrays.asList(
        "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00"
    ));

    /** Array of months */
    public final static String[] MONTHS = { 
        "Month", "January", "February", "March", "April", "May", "June", "July",
        "August", "September", "October", "November", "December" 
    };
}
