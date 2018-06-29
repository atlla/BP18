package modultest;

import java.io.File;
import java.lang.reflect.Field;
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
import decisionpremise.RationaleSpec;
import helpercomponents.Views;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import organizationalunits.BoardType;

public class DecisionGoalTypeEingabeTest extends Application {

	public static void main(String[] args) {

		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		// Neues DecisionGoalType Testobjekt
		DecisionGoalType dgt = new DecisionGoalType();
		dgt.setName("DGT Testobjekt 4");
		dgt.setDescription("DGT Test Description");
		
		CoherenceRationaleSpec justification = new CoherenceRationaleSpec();
		justification.setDescription("Test descr");
		justification.setDiscussion("disc");
		justification.setSource("src");
		
		dgt.getJustification().add(justification);

		try {

			FXMLLoader loader = new FXMLLoader(new File(Views.DECISIONGOALTYPE).toURI().toURL());
			DecisionGoalTypeController con = new DecisionGoalTypeController();
			EntityManager em = DatabaseManager.getInstance().getEmf().createEntityManager();
			con.setEntityManager(em);
			con.setDecPremT(dgt);
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

			EntityType<DecisionGoalType> type = em.getMetamodel().entity(DecisionGoalType.class);
			if (type != null) {

				TypedQuery<String> q1 = em.createQuery("SELECT dgt.name FROM DecisionGoalType dgt", String.class);
				if (q1.getResultList().size() > 0) {

					int i = 0;
					for (String s : q1.getResultList()) {

						if (s.equals(dgt.getName())) {

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
