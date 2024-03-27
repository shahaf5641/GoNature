package gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import Controllers.ReportsControl;
import alerts.CustomAlerts;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logic.GoNatureFinals;

/**
 * counts pending orders with passed date and cancelled orders for each park.
 * presents it in report and shows in total how many orders were cancelled
 */
public class CancelsReportController implements Initializable{
	
	@FXML
	private TextField startDatetext;
	
	@FXML
	private TextField endDatetext;
	
    @FXML
    private AnchorPane rootPane;
    
    @FXML
    private Label headerLabel;

    @FXML
    private Label MosesCancelLabel;

    @FXML
    private Label PheonixCancelsLabel;

    @FXML
    private Label YosemiteCancelsLabel;

    @FXML
    private Label CancelsTotalLabel;
    
    @FXML
    private Label MosesNotArrivedLabel;
    
    @FXML
    private Label PheonixNotArrivedLabel;
    
    @FXML
    private Label YosemiteNotArrivedLabel;
    
    @FXML
    private Label NotArrivedTotalLabel;
    
	
    private int monthNumber; // the month number
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initLabels();
	}
	
	private void initLabels() {
		MosesCancelLabel.setText("0");
		PheonixCancelsLabel.setText("0");
		YosemiteCancelsLabel.setText("0");
		CancelsTotalLabel.setText("0");
		MosesNotArrivedLabel.setText("0");
		PheonixNotArrivedLabel.setText("0");
		YosemiteNotArrivedLabel.setText("0");
		NotArrivedTotalLabel.setText("0");
		//To be continued
	}
	
	@FXML
	private void viewDetaillsButton(){
        String startdate = startDatetext.getText();
        String enddate = endDatetext.getText();
        int cancels=0,cancelsPark1=0,cancelsPark2=0,cancelsPark3=0, notarrived= 0, notarrivedPark1=0, notarrivedPark2=0, notarrivedPark3=0;
        if (startdate.isEmpty() || enddate.isEmpty())
            new CustomAlerts(AlertType.ERROR, "Input Error", "Input Error", "Please fill all the fields").showAndWait();
        else {

    		/*Moses*/
    		cancelsPark1=ReportsControl.getParkCancels("1",startdate,enddate).get(0);			//get cancels for Moses
    		notarrivedPark1=ReportsControl.getParkNotArrived("1",startdate,enddate).get(0);//get not arrived for Moses
    		MosesCancelLabel.setText(String.valueOf(cancelsPark1));				
    		MosesNotArrivedLabel.setText(String.valueOf(notarrivedPark1));	
    		/*Pheonix*/
    		cancelsPark2=ReportsControl.getParkCancels("2",startdate,enddate).get(0);			//get cancels for Pheonix
    		notarrivedPark2=ReportsControl.getParkNotArrived("2",startdate,enddate).get(0);//get not arrived for for Pheonix
    		PheonixCancelsLabel.setText(String.valueOf(String.valueOf(cancelsPark2)));	
    		PheonixNotArrivedLabel.setText(String.valueOf(String.valueOf(notarrivedPark2)));
    		/*Yosemite*/
    		cancelsPark3=ReportsControl.getParkCancels("3",startdate,enddate).get(0);			//get cancels for Yosemite
    		notarrivedPark3=ReportsControl.getParkNotArrived("3",startdate,enddate).get(0);//get not arrived for for Yosemite
    		YosemiteCancelsLabel.setText(String.valueOf(String.valueOf(cancelsPark3)));
    		YosemiteNotArrivedLabel.setText(String.valueOf(String.valueOf(notarrivedPark3)));
    		cancels=cancelsPark1+cancelsPark2+cancelsPark3;
    		notarrived = notarrivedPark1+notarrivedPark2+notarrivedPark3;
    		CancelsTotalLabel.setText(String.valueOf(String.valueOf(cancels)));
    		NotArrivedTotalLabel.setText(String.valueOf(String.valueOf(notarrived)));
        }
	}
	
	@FXML
	private void saveReportAsPdf() {
		File directory = new File(System.getProperty("user.home") + "/Desktop/reports/");
	    if (! directory.exists()){
	        directory.mkdir();
	    }
	    
		WritableImage nodeshot = rootPane.snapshot(new SnapshotParameters(), null);
		String fileName = "Cancels Report - month number " + monthNumber + ".pdf";
		File file = new File("test.png");

		try {
			ImageIO.write(SwingFXUtils.fromFXImage(nodeshot, null), "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		PDDocument doc = new PDDocument();
		PDPage page = new PDPage();
		PDImageXObject pdimage;
		PDPageContentStream content;
		try {
			pdimage = PDImageXObject.createFromFile("test.png", doc);
			content = new PDPageContentStream(doc, page);
			content.drawImage(pdimage, 50, 100, 500, 600);
			content.close();
			doc.addPage(page);
			doc.save(System.getProperty("user.home") + "/Desktop/reports/" +fileName);
			doc.close();
			file.delete();
			new CustomAlerts(AlertType.INFORMATION, "Success", "Success",
					"The report was saved in your desktop under reports folder").showAndWait();
		} catch (IOException ex) {
			System.out.println("faild to create pdf");
			ex.printStackTrace();
		}
		
		Stage stage = (Stage) rootPane.getScene().getWindow();
		stage.close();

	}
	
	/**
	 * Setter for class variable monthNumber
	 * 
	 * @param month The mounth number
	 */
	public void setMonthNumber(int month){
		this.monthNumber = month;	
	}

}