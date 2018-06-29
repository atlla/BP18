package controllertypelevel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import controllerandstagefactory.ControllerFactory;
import controllerandstagefactory.StageFactory;
import database.DatabaseManager;
import decisionpremise.ActionVariableType;
import decisionpremise.DecisionGoalType;
import decisionpremise.DecisionPremiseType;
import decisionpremise.EngagementGoalType;
import decisionpremise.EnvironmentalFactorType;
import decisionpremise.RelevanceRelationType;
import decisionpremise.SymbolicGoalType;
import decisionprocess.DecisionProcessType;
import helpercomponents.AlertDialogFactory;
import helpercomponents.Views;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import supportrequirements.SupportRequirementType;

public class RelevanceRelationTypeController implements Initializable, IControllerTypeLevel {

	private RelevanceRelationType rrt;
	private DecisionProcessType dpt;
	private Stage stage;
	private EntityManager em;
	private DecisionPremiseType selectedItem;
	private boolean onEdit;

	@FXML
	private TableView<SupportRequirementType> tv_corrSuppReq;

	@FXML
	private TableColumn<SupportRequirementType, String> tc_corrSuppReqName;

	@FXML
	private TableColumn<SupportRequirementType, String> tc_corrSuppReqType;

	@FXML
	private TableView<DecisionPremiseType> tv_DecPremTypes;

	@FXML
	private TableColumn<DecisionPremiseType, String> tc_decPremType;

	@FXML
	private TableColumn<DecisionPremiseType, String> tc_decPremName;

	@FXML
	private ChoiceBox<String> cb_ChooseTypeToAdd;

	@FXML
	private ChoiceBox<String> cb_chooseSrToAdd;

	@FXML
	private ChoiceBox<String> cb_consReq;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// Listener
		tv_DecPremTypes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

			if (newValue != null) {

				if (tv_corrSuppReq.getItems().size() > 0) {

					tv_corrSuppReq.getItems().clear();
					if (newValue.getSrt().size() > 0) {

						tv_corrSuppReq.getItems().setAll(newValue.getSrt());
					}
				}

				selectedItem = newValue;
				cb_consReq.disableProperty().setValue(false);
			}
		});

		tc_decPremName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tc_decPremType.setCellValueFactory(new PropertyValueFactory<>("type"));

		tc_corrSuppReqName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tc_corrSuppReqType.setCellValueFactory(new PropertyValueFactory<>("type"));

		checkFields();
		fillTable();

	}

	private void bindProperties() {

		rrt.considerationRequiredProperty().bind(cb_consReq.getSelectionModel().selectedItemProperty());
	}

	private void checkFields() {

		if (rrt.getConsiderationRequired() != null) {

			System.out.println("EDIT????");
			System.out.println(rrt.getConsiderationRequired());
			onEdit = true;
			cb_consReq.getSelectionModel().select(rrt.getConsiderationRequired());
			tv_DecPremTypes.getSelectionModel().select(rrt.getDecPremType());
			tv_DecPremTypes.scrollTo(rrt.getDecPremType());
			selectedItem = rrt.getDecPremType();

		} else {

			bindProperties();
		}
	}

	private void unbindProperties() {

		rrt.considerationRequiredProperty().unbind();
	}

	private void fillTable() {

		System.out.println("fill table");
		try {
			if (tv_DecPremTypes.getItems().size() > 0) {

				tv_DecPremTypes.getItems().clear();
			}
			TypedQuery<DecisionPremiseType> q1 = em.createQuery("SELECT decPrem FROM DecisionPremiseType decPrem",
					DecisionPremiseType.class);
			System.out.println("LIST SIZE " + q1.getResultList().size());
			tv_DecPremTypes.getItems().addAll(q1.getResultList());
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@FXML
	void btn_addDecPremTypeAction(ActionEvent event) throws IOException {

		if (cb_ChooseTypeToAdd.getSelectionModel().getSelectedItem() != null) {

			String s = cb_ChooseTypeToAdd.getSelectionModel().getSelectedItem();
			EntityManager em = DatabaseManager.getInstance().getEmf().createEntityManager();
			if (s.equals("Decision goal")) {

				String view = Views.DECISIONGOALTYPE;
				IControllerTypeLevel con = ControllerFactory.getDecPremTypeController(new DecisionGoalType(), em);
				StageFactory.createAndShowStage("Add decision goal type", false, view, con);

			} else if (s.equals("Engagement goal")) {

				String view = Views.ENGAGEMENTGOALTYPE;
				IControllerTypeLevel con = ControllerFactory.getDecPremTypeController(new EngagementGoalType(), em);
				StageFactory.createAndShowStage("Add engagement goal type", false, view, con);

			} else if (s.equals("Symbolic goal")) {

				String view = Views.SYMBOLICGOALTYPE;
				IControllerTypeLevel con = ControllerFactory.getDecPremTypeController(new SymbolicGoalType(), em);
				StageFactory.createAndShowStage("Add symbolic goal type", true, view, con);

			} else if (s.equals("Action variable")) {

				String view = Views.ACTIONVARIABLETYPE;
				IControllerTypeLevel con = ControllerFactory.getDecPremTypeController(new ActionVariableType(), em);
				StageFactory.createAndShowStage("Add action ", false, view, con);

			} else if (s.equals("Environmental factor")) {

				String view = Views.ENVIRONMENTALFACTORTYPE;
				IControllerTypeLevel con = ControllerFactory.getDecPremTypeController(new EnvironmentalFactorType(), em);
				StageFactory.createAndShowStage("Add environmental factor type", false, view, con);
			}
			selectedItem = null;
			fillTable();
		}
	}

	@FXML
	void btn_decPremTeditAction(ActionEvent event) throws IOException {

		if (tv_DecPremTypes.getSelectionModel().getSelectedItem() != null) {

			DecisionPremiseType decPrem = tv_DecPremTypes.getSelectionModel().getSelectedItem();
			EntityManager em = DatabaseManager.getInstance().getEmf().createEntityManager();

			if (decPrem instanceof DecisionGoalType) {

				String view = Views.DECISIONGOALTYPE;
				IControllerTypeLevel con = ControllerFactory.getDecPremTypeController(decPrem, em);
				StageFactory.createAndShowStage("Edit decision goal type", false, view, con);

			} else if (decPrem instanceof EngagementGoalType) {

				String view = Views.ENGAGEMENTGOALTYPE;
				IControllerTypeLevel con = ControllerFactory.getDecPremTypeController(decPrem, em);
				StageFactory.createAndShowStage("Edit engagement goal type", false, view, con);

			} else if (decPrem instanceof SymbolicGoalType) {

				String view = Views.SYMBOLICGOALTYPE;
				IControllerTypeLevel con = ControllerFactory.getDecPremTypeController(decPrem, em);
				StageFactory.createAndShowStage("Edit symbolic goal type", true, view, con);

			} else if (decPrem instanceof ActionVariableType) {

				String view = Views.ACTIONVARIABLETYPE;
				IControllerTypeLevel con = ControllerFactory.getDecPremTypeController(decPrem, em);
				StageFactory.createAndShowStage("Edit action variable type", false, view, con);

			} else if (decPrem instanceof EnvironmentalFactorType) {

				String view = Views.ENVIRONMENTALFACTORTYPE;
				IControllerTypeLevel con = ControllerFactory.getDecPremTypeController(decPrem, em);
				StageFactory.createAndShowStage("Edit environmental factor type", false, view, con);
			}

			// this.em.refresh(decPrem);
			fillTable();
			for (DecisionPremiseType decPremT : tv_DecPremTypes.getItems()) {

				if (decPremT.getName().equals(selectedItem.getName())) {

					tv_DecPremTypes.getSelectionModel().select(decPremT);
					selectedItem = decPremT;
					break;
				}
			}
		}
	}

	@FXML
	void btn_save(ActionEvent event) {

		if (selectedItem != null) {

			if (!onEdit) {

				if (checkLv()) {

					unbindProperties();
					dpt.getRelRelations().add(rrt);
					rrt.setDecProcType(dpt);
					rrt.setDecPremType(selectedItem);
					rrt.getDecPremType().getRelRelation().add(rrt);
					stage.close();
				}
			} else {

				if (selectedItem.getName().equals(rrt.getDecPremType().getName())) {

					unbindProperties();
					stage.close();
					System.out.println("TEST");

				} else {

					if (checkLv()) {

						unbindProperties();
						rrt.getDecPremType().getRelRelation().remove(rrt);
						rrt.setDecPremType(selectedItem);
						selectedItem.getRelRelation().add(rrt);
						stage.close();
					}
				}
			}
		} else {

			AlertDialogFactory.createStandardInformationAlert("Information", "Save error",
					"No decision premise selected!");
		}
	}

	private boolean checkLv() {

		for (RelevanceRelationType rrt : dpt.getRelRelations()) {

			if (rrt.getDecPremType().getName().equals(selectedItem.getName())) {

				AlertDialogFactory.createStandardInformationAlert("information", "", "Type already added");
				return false;
			}
		}

		return true;
	}

	public void setDpt(DecisionProcessType dpt) {

		this.dpt = dpt;
	}

	public void setStage(Stage stage) {

		this.stage = stage;
	}

	public void setEm(EntityManager em) {

		this.em = em;
	}

	public void setRrt(RelevanceRelationType rrt) {

		this.rrt = rrt;
	}

	@Override
	public void setEntityManager(EntityManager em) {

		this.em = em;
	}

}
