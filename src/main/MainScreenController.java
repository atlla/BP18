package main;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Stack;

import javax.persistence.EntityManager;

import controllerandstagefactory.ControllerFactory;
import controllerandstagefactory.StageFactory;
import controllerandstagefactory.TabFactory;
import controllertypelevel.IControllerTypeLevel;
import controllertypelevel.IDptTabController;
import controllertypelevel.IDpiTabController;
import database.DatabaseManager;
import decisionpremise.ActionVariableType;
import decisionpremise.DecisionGoalType;
import decisionpremise.EngagementGoalType;
import decisionpremise.EnvironmentalFactorType;
import decisionpremise.SymbolicGoalType;
import decisionprocess.DecisionProcessType;
import decisionprocess.DecisionProcessInstance;
import helpercomponents.Views;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import organizationalunits.BoardType;
import organizationalunits.CommiteeType;
import organizationalunits.OrganizationalUnitType;
import organizationalunits.PositionType;
import organizationalunits.RoleType;
import supportrequirements.InformationSystemType;
import supportrequirements.InformationType;

public class MainScreenController {

	@FXML
	private TabPane tabPane;

	public MainScreenController() {

		System.out.println("Controller?");
	}

	@FXML
	void closeApp(ActionEvent event) {

		Platform.exit();
	}

	// neues Tab zur Eingabe f�r Decision Process Types
	@FXML
	void newDpt(ActionEvent event) throws IOException {

		try {

			IDptTabController con = ControllerFactory.getDptTabController(new DecisionProcessType(), tabPane,
					DatabaseManager.getInstance().getEmf().createEntityManager());
			TabFactory.createAndShowDptTab(Views.MSDECPROCTYPETAB, "New DPT", con);

		} catch (MalformedURLException e) {

			e.printStackTrace();
		}
	}

	@FXML
	void newDptEditTab(ActionEvent event) {

		try {

			IControllerTypeLevel con = ControllerFactory.getChooseDptEditController(tabPane,
					DatabaseManager.getInstance().getEmf().createEntityManager());
			StageFactory.createAndShowStage("Choose DPT type to edit", false, Views.CHOOSEDPTTOEDIT, con);

		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	// Tab schlie�en Listener
	public void setCloseTabListener(Tab tab, EntityManager em) {

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
					tabPane.getTabs().remove(tab);
				}
			}
		});
	}

	// neues Tab zur Eingabe f�r Decision Process Instances
	@FXML
	void newDpi(ActionEvent event) throws IOException {

		try {

			IDpiTabController con = ControllerFactory.getDpiTabController(new DecisionProcessInstance(), tabPane,
					DatabaseManager.getInstance().getEmf().createEntityManager());
			TabFactory.createAndShowDpiTab(Views.MSDECPROCINSTTAB, "New DPI", con);
			
		} catch (MalformedURLException e) {

			e.printStackTrace();
		}
	}

	// Methoden zur Eingabe von Typen (die unabh�ngig von einem DPT etc.
	// eingegeben werden k�nnen!)
	@FXML
	void newInfoSys(ActionEvent event) {

		try {
			
			EntityManager em = createEm();
			IControllerTypeLevel con = ControllerFactory.getInformationSystemTypeController(new InformationSystemType(), em);
			StageFactory.createAndShowStage("Add information system type", false, Views.INFORMATIONSYSTEMTYPE, con);

			if (em.isOpen()) {

				em.close();
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	@FXML
	void addInfoType(ActionEvent event) {
		
		try {
			
			EntityManager em = createEm();
			IControllerTypeLevel con = ControllerFactory.getNewInformationTypeController(new InformationType(), em);
			StageFactory.createAndShowStage("Add information type", false, Views.NEWINFORMATIONTYPE, con);

			if (em.isOpen()) {

				em.close();
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	@FXML
	void addBoardType(ActionEvent event) {

		try {
			EntityManager em = createEm();
			IControllerTypeLevel con = ControllerFactory.getOrganizationalUnitTypeController(new BoardType(), em);
			StageFactory.createAndShowStage("Add Board type", false, Views.BOARDTYPE, con);

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@FXML
	void addCommitteeType(ActionEvent event) {

		try {
			EntityManager em = createEm();
			IControllerTypeLevel con = ControllerFactory.getOrganizationalUnitTypeController(new CommiteeType(), em);
			StageFactory.createAndShowStage("Add committee type", false, Views.COMMITTEETYPE, con);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@FXML
	void addPositionType(ActionEvent event) {

		try {
			EntityManager em = createEm();
			IControllerTypeLevel con = ControllerFactory.getOrganizationalUnitTypeController(new PositionType(), em);
			StageFactory.createAndShowStage("Add position type", false, Views.POSITIONTYPE, con);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@FXML
	void addRoleType(ActionEvent event) {

		try {
			EntityManager em = createEm();
			IControllerTypeLevel con = ControllerFactory.getOrganizationalUnitTypeController(new RoleType(), em);
			StageFactory.createAndShowStage("Add role type", false, Views.ROLETYPE, con);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@FXML
	void addDecGoalType(ActionEvent event) {

		try {
			EntityManager em = createEm();
			IControllerTypeLevel con = ControllerFactory.getDecPremTypeController(new DecisionGoalType(), em);
			StageFactory.createAndShowStage("Add decision goal type", false, Views.DECISIONGOALTYPE, con);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@FXML
	void addEngGoalType(ActionEvent event) {

		try {
			EntityManager em = createEm();
			IControllerTypeLevel con = ControllerFactory.getDecPremTypeController(new EngagementGoalType(), em);
			StageFactory.createAndShowStage("Add engagement goal type", false, Views.ENGAGEMENTGOALTYPE, con);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@FXML
	void addSymbGoalType(ActionEvent event) {

		try {
			EntityManager em = createEm();
			IControllerTypeLevel con = ControllerFactory.getDecPremTypeController(new SymbolicGoalType(), em);
			StageFactory.createAndShowStage("Add symbolic goal type", true, Views.SYMBOLICGOALTYPE, con);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@FXML
	void addActionVarType(ActionEvent event) {

		try {
			EntityManager em = createEm();
			IControllerTypeLevel con = ControllerFactory.getDecPremTypeController(new ActionVariableType(), em);
			StageFactory.createAndShowStage("Add action variable type", false, Views.ACTIONVARIABLETYPE, con);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@FXML
	void addEnvFacType(ActionEvent event) {

		try {
			EntityManager em = createEm();
			IControllerTypeLevel con = ControllerFactory.getDecPremTypeController(new EnvironmentalFactorType(), em);
			StageFactory.createAndShowStage("Add environmental factor type", false, Views.ENVIRONMENTALFACTORTYPE, con);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	private EntityManager createEm() {

		return DatabaseManager.getInstance().getEmf().createEntityManager();
	}
	// Methoden zur Eingabe von Typen Ende
}