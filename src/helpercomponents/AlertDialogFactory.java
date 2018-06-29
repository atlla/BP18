package helpercomponents;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public final class AlertDialogFactory {
	
	private AlertDialogFactory() {
		
	}
	
	public static void createStandardInformationAlert(String title, String headerText, String contentText){
		
		Alert a = new Alert(AlertType.INFORMATION);
		a.setTitle(title);
		a.setHeaderText(headerText);
		a.setContentText(contentText);
		a.show();
	}
}
