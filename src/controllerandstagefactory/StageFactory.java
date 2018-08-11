package controllerandstagefactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.persistence.EntityManager;

import controllertypelevel.IControllerTypeLevel;
import helpercomponents.Views;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class StageFactory {

	private StageFactory() {

	}

	public static void createAndShowStage(String title, boolean resizable, String viewName, IControllerTypeLevel con) throws IOException {

		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader(new File(viewName).toURI().toURL());
			con.setStage(stage);
			loader.setController(con);
			stage.setScene(new Scene(loader.load()));
			// Wenn die neue Stage gestartet wird, werden die Eingaben auf
			// anderen Stages blockiert
			// --> Also ausschlie�lich Eingaben in neuem Fenster m�glich, bis
			// dieses
			// geschlossen wird
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(resizable);
			stage.setTitle(title);
			stage.showAndWait();
		} catch (MalformedURLException e) {

			e.printStackTrace();
		}
	}
}
