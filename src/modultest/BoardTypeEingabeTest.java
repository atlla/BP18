package modultest;

import java.io.File;
import java.lang.reflect.Field;
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

//Klasse zum Testen der Eingabe und Speicherung (in die Datenbank) eines BoardType Objektes
public class BoardTypeEingabeTest extends Application {

	public static void main(String[] args) {

		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		//Neues BoardType Objekt
		BoardType bt = new BoardType();
		bt.setName("BT Test 5");
		bt.setDescription("BT Test Description");
		bt.setInternal(true);
		bt.setMission("BT Test Mission");
		
		try {

			FXMLLoader loader = new FXMLLoader(new File(Views.BOARDTYPE).toURI().toURL());
			BoardTypeController con = new BoardTypeController();
			EntityManager em = DatabaseManager.getInstance().getEmf().createEntityManager();
			con.setEntityManager(em);
			con.setOrganizationalUnitType(bt);
			con.setStage(new Stage());
			loader.setController(con);
			loader.load();
			
			// Reflection nutzen um save Methode des
			// BoardtypeController-Objektes zu callen
			Field field = con.getClass().getDeclaredField("onEdit");
			field.setAccessible(true);
			field.setBoolean(con, false);
			
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

						System.out.println("NOT NULL??");
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

			EntityType<BoardType> type = em.getMetamodel().entity(BoardType.class);
			if (type != null) {

				TypedQuery<String> q1 = em.createQuery("SELECT bt.name FROM BoardType bt", String.class);
				if (q1.getResultList().size() > 0) {

					int i = 0;
					for (String s : q1.getResultList()) {

						if (s.equals(bt.getName())) {

							System.out.println("Test bestanden!");
							break;
						} else {

							i++;
						}
					}
					if (i == q1.getResultList().size()) {

						System.out.println("Test nicht bestanden!");
					}
				}
			} else {

				System.out.println("Test nicht bestanden");
			}
			em.close();
			primaryStage.close();
			System.exit(0);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}
