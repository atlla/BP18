package modultest;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.metamodel.EntityType;

import controllertypelevel.BoardTypeController;
import database.DatabaseManager;
import helpercomponents.Views;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import organizationalunits.BoardType;

//Klasse zum Testen ob ein BoardType korrekt editiert werden kann
public class BoardTypeEditTest extends Application {

	public static void main(String[] args) {

		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		//Testobjekt definieren (wird aus DB geladen)
		String testObjectName = "BT Test";
		//neue Attributwerte definieren
		String newDescription = "BT Test description edited 3";
		String newMission = "BT Test mission edited 4";
		boolean newInternal = true;

		BoardType btToEdit = null;
		// Testobjekt aus der Datenbank laden
		EntityManager em = DatabaseManager.getInstance().getEmf().createEntityManager();
		TypedQuery<BoardType> q1 = em.createQuery("SELECT bt FROM BoardType bt", BoardType.class);
		if (!q1.getResultList().isEmpty()) {

			for (BoardType bt : q1.getResultList()) {

				if (bt.getName().equals(testObjectName)) {

					btToEdit = bt;
					break;
				}
			}
		}
		// neue Werte setzen
		if (btToEdit != null) {

			btToEdit.setDescription(newDescription);
			btToEdit.setMission(newMission);
			btToEdit.setInternal(newInternal);
		}

		// relevante Objekte erzeugen
		FXMLLoader loader = new FXMLLoader(new File(Views.BOARDTYPE).toURI().toURL());
		BoardTypeController con = new BoardTypeController();
		con.setEntityManager(em);
		con.setOrganizationalUnitType(btToEdit);
		con.setStage(new Stage());
		loader.setController(con);
		loader.load();

		// Reflection zum Aufruf der Save Methode im BoardTypeController (Der
		// Merge des veränderten BoardType-Objektes findet in dieser Methode
		// statt)
		java.lang.reflect.Method method;
		try {
			method = null;
			for (Method allMethods : con.getClass().getDeclaredMethods()) {

				if (allMethods.getName().equals("btn_saveAction")) {

					method = allMethods;
					method.setAccessible(true);
					break;
				}
			}
			try {
				if (method != null) {

					method.invoke(con, new ActionEvent());
				}
			} catch (IllegalArgumentException e) {

				e.printStackTrace();
			} catch (IllegalAccessException e) {

				e.printStackTrace();
			} catch (InvocationTargetException e) {

				e.printStackTrace();
			}
		} catch (SecurityException e) {

			e.printStackTrace();
		}

		em.close();

		// Objekt wieder aus Datenbank laden und überprüfen ob die neuen Werte
		// gespeichert wurden
		BoardType btAfterEdit = null;
		em = DatabaseManager.getInstance().getEmf().createEntityManager();
		
		//Sicherstellen, dass eine BoardType Tabelle(bzw. Entity) in der DB existiert
		EntityType<BoardType> type = em.getMetamodel().entity(BoardType.class);
		if (type != null) {
			
			TypedQuery<BoardType> q2 = em.createQuery("SELECT bt FROM BoardType bt", BoardType.class);
			if (!q2.getResultList().isEmpty()) {

				for (BoardType bt : q2.getResultList()) {

					if (bt.getName().equals(testObjectName)) {

						btAfterEdit = bt;
						break;
					}
				}
			}
		}
		
		if (btAfterEdit != null) {

			if (btAfterEdit.getDescription().equals(newDescription) && btAfterEdit.getMission().equals(newMission)
					&& btAfterEdit.isInternal()) {

				System.out.println("Test bestanden!");
			}
		}
		primaryStage.close();
		System.exit(0);
	}

}
