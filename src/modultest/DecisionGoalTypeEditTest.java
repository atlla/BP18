package modultest;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.metamodel.EntityType;

import controllertypelevel.BoardTypeController;
import controllertypelevel.DecisionGoalTypeController;
import database.DatabaseManager;
import decisionpremise.CoherenceRationaleSpec;
import decisionpremise.DecisionGoalType;
import helpercomponents.Views;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import organizationalunits.BoardType;

public class DecisionGoalTypeEditTest extends Application {
	
	public static void main(String[] args) {
		
		launch();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		// Testobjekt definieren (wird aus DB geladen)
		String testObjectName = "DGT Testobjekt";
		// neue Attributwerte definieren
		String newDescription = "DGT Test description edited 3";
		
		//Neues RationaleSpec-objekt hinzufügen (Justification)
		CoherenceRationaleSpec crs = new CoherenceRationaleSpec();
		crs.setDescription("descr Edit Test");
		crs.setDiscussion("disc Edit Test");
		crs.setSource("src Edit Test");

		DecisionGoalType dgtToEdit = null;
		// Testobjekt aus der Datenbank laden
		EntityManager em = DatabaseManager.getInstance().getEmf().createEntityManager();
		TypedQuery<DecisionGoalType> q1 = em.createQuery("SELECT dgt FROM DecisionGoalType dgt",
				DecisionGoalType.class);
		if (!q1.getResultList().isEmpty()) {

			for (DecisionGoalType dgt : q1.getResultList()) {

				if (dgt.getName().equals(testObjectName)) {

					dgtToEdit = dgt;
					break;
				}
			}
		}
		// neue Werte setzen
		if (dgtToEdit != null) {

			dgtToEdit.setDescription(newDescription);
			dgtToEdit.getJustification().add(crs);
		}

		// relevante Objekte erzeugen
		FXMLLoader loader = new FXMLLoader(new File(Views.DECISIONGOALTYPE).toURI().toURL());
		DecisionGoalTypeController con = new DecisionGoalTypeController();
		con.setEntityManager(em);
		con.setDecPremT(dgtToEdit);
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
		DecisionGoalType dgtAfterEdit = null;
		em = DatabaseManager.getInstance().getEmf().createEntityManager();

		// Sicherstellen, dass eine DecisionGoalType Tabelle(bzw. Entity) in der
		// DB existiert
		EntityType<DecisionGoalType> type = em.getMetamodel().entity(DecisionGoalType.class);
		if (type != null) {

			TypedQuery<DecisionGoalType> q2 = em.createQuery("SELECT dgt FROM DecisionGoalType dgt",
					DecisionGoalType.class);
			if (!q2.getResultList().isEmpty()) {

				for (DecisionGoalType dgt : q2.getResultList()) {

					if (dgt.getName().equals(testObjectName)) {

						dgtAfterEdit = dgt;
						break;
					}
				}
			}
		}

		if (dgtAfterEdit != null) {
			
			System.out.println(dgtAfterEdit.getName());
			if (dgtAfterEdit.getDescription().equals(newDescription)) {

				System.out.println("Test bestanden!");
			}
		}
		primaryStage.close();
		System.exit(0);
	}
}
