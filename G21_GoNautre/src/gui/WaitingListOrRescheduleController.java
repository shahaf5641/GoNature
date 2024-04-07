package gui;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logic.Order;
import logic.OrderStatusName;
import logic.Traveler;
import Controllers.CommunicationControl;
import Controllers.OrderControl;
import Controllers.ParkControl;
import alerts.Alerts;
import resources.MsgTemplates;

public class WaitingListOrRescheduleController implements Initializable {

    @FXML
    private Label headerLabel;
    @FXML
    private AnchorPane mainPane;

    @FXML
    private TextArea rescheduleTextArea;

    @FXML
    private ListView<String> datesListView;

    @FXML
    private Button waitingListButton;
    @FXML
    private Button newTimeButton;
    @FXML
    private ProgressIndicator pi;
    private Order order;
    private Order recentOrder;
    private Traveler traveler;
    private Stage rescheduleStage;
    private Stage orderStage;
	boolean checkOrderFromMain = false;
    boolean checkWaitingList = false;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        init();
        Task<Boolean> task = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                getAlternativeDates();
                return true;
            }
        };

        pi.setVisible(true);
        mainPane.setDisable(true);
        new Thread(task).start();

        task.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                pi.setVisible(false);
                mainPane.setDisable(false);
            }
        });

    }

    private void init() {
        datesListView.getItems().clear();
    }
    private String getEmailContent() {
        String parkName = ParkControl.findParkName(String.valueOf(recentOrder.getParkId()));
        return String.format(MsgTemplates.orderConfirmation[1].toString(), String.valueOf(recentOrder.getOrderId()),
                parkName, recentOrder.getOrderDate(), recentOrder.getOrderTime(), recentOrder.getOrderType(),
                String.valueOf(recentOrder.getNumberOfParticipants()), String.valueOf(recentOrder.getTotalPrice()));
    }

    private void getAlternativeDates() {

        ArrayList<String> availableDatesList = GetOptionalDays();
        datesListView.getItems().addAll(availableDatesList);

    }

    @FXML
    private void placeOrder() {
        String newDateAndTime = datesListView.getSelectionModel().getSelectedItem();
        String newDate = newDateAndTime.split(",")[0];
        String newTime = newDateAndTime.split(",")[1].trim();
        Order newOrder = order;
        newOrder.setOrderDate(newDate);
        newOrder.setOrderTime(newTime);

        if (OrderControl.addNewOrder(order, traveler)) {
            System.out.println("Order added successfully ");
            recentOrder = OrderControl.findTravelerRecentOrder(traveler.getTravelerId());

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String dateAndTime = dtf.format(now);
            String date = dateAndTime.split(" ")[0];
            String time = dateAndTime.split(" ")[1];
            if (recentOrder != null) {

                String emailContent = getEmailContent();
                CommunicationControl.sendMessageToTraveler(traveler.getTravelerId(), date, time,
                        MsgTemplates.orderConfirmation[0], emailContent, String.valueOf(recentOrder.getOrderId()));

                if (checkOrderFromMain)
                    orderStage.close();

                rescheduleStage.close();
                loadOrderReciept();
            }
        }

    }

 

	private ArrayList<String> GetOptionalDays() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        Order tempOrder = new Order(order.getOrderId(), order.getTravelerId(), order.getParkId(), order.getOrderDate(),
                order.getOrderTime(), order.getOrderType(), order.getNumberOfParticipants(), order.getTravelerEmail(),
                order.getTotalPrice(), order.getOrderStatus());
        String originalDate = order.getOrderDate();
        try {
            c.setTime(sdf.parse(originalDate));
        } catch (Exception e) {
            e.printStackTrace();
        }

        c.add(Calendar.DAY_OF_MONTH, 1);
        originalDate = sdf.format(c.getTime());
        String currentTime = "8:00";
        String currentDate = originalDate;
        int counter = 0;
        ArrayList<String> availableDatesList = new ArrayList<String>();
        while (counter != 6) {
            tempOrder.setOrderDate(currentDate);
            tempOrder.setOrderTime(currentTime);
            if (OrderControl.checkDateAvailability(tempOrder)) {
                availableDatesList.add(tempOrder.getOrderDate() + ", " + tempOrder.getOrderTime());
                counter++;
            }

            int hour = Integer.parseInt((currentTime.split(":")[0]));

            hour += 1;
            if (hour > 17) {
                currentTime = "8:00";
                try {
                    c.setTime(sdf.parse(currentDate));
                } catch (Exception e) {
                }
                c.add(Calendar.DAY_OF_MONTH, 1);
                currentDate = sdf.format(c.getTime());

            } else {
                currentTime = String.valueOf(hour) + ":00";
            }

        }

        return availableDatesList;
    }


    @FXML
	private void enterToWaitingList() {
        this.order.setOrderStatus(OrderStatusName.IN_WAITING_LIST.toString());
        System.out.println(order.getOrderDate());
        if (OrderControl.addNewOrder(order, traveler)) {
            System.out.println("Order added successfully - waiting list");

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String dateAndTime = dtf.format(now);
            String date = dateAndTime.split(" ")[0];
            String time = dateAndTime.split(" ")[1];
            recentOrder = OrderControl.findTravelerRecentOrder(traveler.getTravelerId());

            if (recentOrder != null) {
                String parkName = ParkControl.findParkName(String.valueOf(recentOrder.getParkId()));

                String msgContent = String.format(MsgTemplates.enterToWaitingList[1].toString(), parkName,
                        order.getOrderDate(), order.getOrderTime());
                CommunicationControl.sendMessageToTraveler(traveler.getTravelerId(), date, time,
                        MsgTemplates.enterToWaitingList[0], msgContent, String.valueOf(recentOrder.getOrderId()));

                checkWaitingList = true;
                loadOrderReciept();
            }

        } else {
            new Alerts(AlertType.ERROR, MsgTemplates.enterToWaitingList[0], MsgTemplates.enterToWaitingList[0],
                    MsgTemplates.enterToWaitingList[1]).showAndWait();
        }

        if (checkOrderFromMain) {
            orderStage.close();
        }
        rescheduleStage.close();
    }

    private void loadOrderReciept() {
        try {
            Stage thisStage = this.rescheduleStage;
            Stage newStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/OrderReciept.fxml"));
            OrderRecieptController controller = new OrderRecieptController();
            controller.setOrder(recentOrder);
            controller.setTraveler(traveler);
            controller.setWaitingList(checkWaitingList);
            controller.setOrderFromWeb(true);
            loader.setController(controller);
            loader.load();
            AnchorPane p = loader.getRoot();
            newStage.setTitle("Order Reciept");
            newStage.setScene(new Scene(p));
            newStage.setResizable(false);
            if (checkOrderFromMain)
                thisStage.close();
            newStage.show();
        } catch (IOException e) {
            System.out.println("Failed to load form");
            e.printStackTrace();
        }

    }

   

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setOrderStage(Stage stage) {
        this.orderStage = stage;
    }

    public void setTraveler(Traveler traveler) {
        this.traveler = traveler;
    }

    public void setRescheduleStage(Stage stage) {
        this.rescheduleStage = stage;
    }


    public void setOrderFromMain(boolean checkOrderFromMain) {
        this.checkOrderFromMain = checkOrderFromMain;
    }

}
