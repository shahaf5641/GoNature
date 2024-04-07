package logic;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class is responsible for managing the system's final constants.
 */
public final class GoNatureConstants {

    // Private constructor to prevent instantiation of the class
    private GoNatureConstants() {
    }
    
    // Constants for system properties

    /** Path to the application's icon file */
    public final static String APP_ICON_PATH = "/resources/images/gonatureicon.png";

    /** Full price constant */
    public final static int FULL_TICKET_PRICE = 100;

    /** GoNature support email address */
    public final static String SUPPORT_EMAIL = "G21GoNature@gmail.com";

    /** GoNature support email password */
    public final static String SUPPORT_EMAIL_PASSWORD = "Aa123456!";

    /** Available hours throughout the day */
    public final static ArrayList<String> AVAILABLE_HOURS_LIST = new ArrayList<>(Arrays.asList(
        "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00"
    ));

    /** Array of month names */
    public final static String[] MONTH_NAMES = { 
        "Month", "January", "February", "March", "April", "May", "June", "July",
        "August", "September", "October", "November", "December" 
    };
}