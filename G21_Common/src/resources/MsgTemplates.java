package resources;

/**
 * This class holds message templates.
 */
public class MsgTemplates {
	
	public static String[] orderConfirmation = {
		"Thank you for your order!", 
		"Thank you for your order!\n"
		+ "We appreciate your booking and look forward to seeing you.\n"
		+ "Please remember to confirm your order when you receive the reminder.\n\n"
		+ "Order details:\n"
		+ "Order ID: %s.\n"
		+ "Park: %s.\n"
		+ "Date: %s.\n"
		+ "Time: %s.\n"
		+ "Type: %s.\n"
		+ "Number of visitors: %s.\n"
		+ "Total price: %s.\n\n"
		+ "See you at the park!\n"
		+ "GoNature21 Team.\n\n"
	};

	public static String[] enterToWaitingList = { 
		"Join Waiting List", 
		"You have been added to the waiting list.\n"
		+ "If a spot becomes available, we'll notify you by email.\n" 
		+ "Order details:\n"
		+ "Park: %s\n"
		+ "Visit date: %s\n"
		+ "Visit time: %s\n"
		+ "\n"
		+ "Thank you,\n"
		+ "GoNature21 Team" 
	};
	
	public static String[] errorWaitingList = { 
		"Waiting List Error", 
		"We encountered an error while adding you to the waiting list.\n" 
		+ "Please try again later."
	};
	
	public static String[] passwordRecovery = { 
		"GoNature Password Recovery", 
		"Hello,\n"
		+ "Here is your login information:\n"
		+ "ID: %s\n"
		+ "Password: %s\n\n"
		+ "Regards,\n"
		+ "GoNature21 Team."
	};
	
	public static String[] ConfirmOrder24hoursBeforeVisit = { 
		"Reminder: Confirm Your Order",
		"Hello,\n"
		+ "You have an upcoming visit booked at %s in %s at %s.\n"
		+ "Please confirm your order within the next two hours.\n"
		+ "NOTE: If you fail to confirm, your order will be automatically cancelled.\n\n"
		+ "Best Regards,\n"
		+ "GoNature21 Team."
	};
	
	public static String[] orderCancel = { 
		"Order Cancelled",
		"Hello,\n"
		+ "We regret to inform you that your visit order to %s in %s at %s has been cancelled.\n\n"
		+ "Best Regards,\n"
		+ "GoNature21 Team."
	};
	
	public static String[] waitingListPlaceInPark = { 
		"Spot Available in the Park!", 
		"Hello,\n"
		+ "Good news! A spot has opened up while you were on the waiting list.\n"
		+ "You're invited to visit %s park on %s at %s.\n"
		+ "To confirm your visit, please respond within the next two hours.\n"
		+ "Best Regards,\n"
		+ "GoNature21 Team."		
	};
}