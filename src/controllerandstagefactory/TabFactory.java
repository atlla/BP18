package controllerandstagefactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.persistence.EntityManager;

import controllertypelevel.IDpiTabController;
import controllertypelevel.IDptTabController;
import helpercomponents.Views;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import main.MainScreenController;
import javafx.scene.control.Alert.AlertType;

public final class TabFactory {
	
	private TabFactory() {
		
	}
	
	public static void createAndShowDptTab(String viewName, String title, IDptTabController con, MainScreenController msc) throws IOException {
		
		try {
			Tab tab = new Tab(title);
			FXMLLoader loader = new FXMLLoader(new File(Views.MSDECPROCTYPETAB).toURI().toURL());
			con.setDptTab(tab);
			loader.setController(con);
			setCloseTabListener(con.getTpane(), tab, con.getEm(), msc);
			tab.setContent(loader.load());
			con.getTpane().getTabs().add(tab);
			con.getTpane().getSelectionModel().select(tab);
			
		} catch (MalformedURLException e) {

			e.printStackTrace();
		}
	}
	public static void createAndShowDpiTab(String viewName, String title, IDpiTabController con, MainScreenController msc) throws IOException {
		
		try {
			Tab tab = new Tab(title);
			FXMLLoader loader = new FXMLLoader(new File(Views.MSDECPROCINSTTAB).toURI().toURL());
			con.setDpiTab(tab);
			loader.setController(con);
			setCloseTabListener(con.getTpane(), tab, con.getEm(), msc);
			tab.setContent(loader.load());
			con.getTpane().getTabs().add(tab);
			con.getTpane().getSelectionModel().select(tab);
			
		} catch (MalformedURLException e) {

			e.printStackTrace();
		}
	}
	// Listener, wenn dass Tab geschlossen werden soll
	private static void setCloseTabListener(TabPane tPane, Tab tab, EntityManager em, MainScreenController msc) {

		tab.setOnCloseRequest(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {

				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Unsaved data");
				alert.setHeaderText("All data will be lost if you close this tab!");
				alert.setContentText("Discard changes and close tab?");

				alert.showAndWait();

				if (alert.getResult() == ButtonType.CANCEL) {

					arg0.consume();
				} else {

					if (em.isOpen()) {

						em.close();
					}
					tPane.getTabs().remove(tab);
					msc.setVisibilityOfMainScreenElements(true);
				}
			}
		});
	}
}