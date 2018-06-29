package controllertypelevel;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.management.Query;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import controllerandstagefactory.ControllerFactory;
import controllerandstagefactory.StageFactory;
import database.DatabaseManager;
import decisionprocess.DecisionProcessType;
import helpercomponents.AlertDialogFactory;
import helpercomponents.Views;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.stage.Modality;
import javafx.stage.Stage;
import organizationalunits.BoardType;
import organizationalunits.CommiteeType;
import organizationalunits.OrganizationalUnitType;
import organizationalunits.ParticipationRelationType;
import organizationalunits.PositionType;
import organizationalunits.RoleType;

public class ParticipationRelationTypeController implements Initializable, IControllerTypeLevel {

	private DecisionProcessType dpt;
	private ParticipationRelationType prt;
	private OrganizationalUnitType selectedItem;
	private EntityManager em;
	private Stage stage;
	private boolean elementsDisabled = true;
	private boolean editRequest;

	@FXML
	private ChoiceBox<String> cb_role;

	@FXML
	private ChoiceBox<String> cb_suggDet;

	@FXML
	private ChoiceBox<String> cb_partReq;

	@FXML
	private ChoiceBox<String> cb_chooseTypeToAdd;

	@FXML
	private CheckBox chkB_EntitledToAuthDec;

	@FXML
	private Button btn_editOrgStruct;

	@FXML
	private TextArea ta_partDet;

	@FXML
	private TableView<OrganizationalUnitType> tv_OrgStruct;

	@FXML
	private TableColumn<OrganizationalUnitType, String> tc_type;

	@FXML
	private TableColumn<OrganizationalUnitType, String> tc_name;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		tc_name.setCellValueFactory(new PropertyValueFactory<>("name"));
		tc_type.setCellValueFactory(new PropertyValueFactory<>("orgType"));

		fillTable();
		initializeSelectListener();
		bindProperties();
	}

	private void fillTable() {

		try {
			if (tv_OrgStruct.getItems().size() > 0) {

				tv_OrgStruct.getItems().clear();
			}
			TypedQuery<OrganizationalUnitType> orgList = em.createQuery("SELECT o FROM OrganizationalUnitType o",
					OrganizationalUnitType.class);
			if (orgList.getResultList().size() > 0) {

				tv_OrgStruct.getItems().addAll(orgList.getResultList());

			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private void initializeSelectListener() {

		tv_OrgStruct.getSelectionModel().selectedItemProperty().addListener((obvserv, oldStruct, newStruct) -> {

			if (newStruct != null) {

				selectedItem = newStruct;
				if (elementsDisabled) {

					enableDisableElements(false);
				}
			}
		});
	}

	private void bindProperties() {

		checkFields();
		prt.entitledToAuthorizeFinalDecisionProperty().bind(chkB_EntitledToAuthDec.selectedProperty());
		prt.roleProperty().bind(cb_role.valueProperty());
		prt.participationRequiredProperty().bind(cb_partReq.valueProperty());
		prt.suggestedAttendanceLevelProperty().bind(cb_suggDet.valueProperty());
		prt.participationDetailsProperty().bind(ta_partDet.textProperty());
	}

	private void checkFields() {

		if (prt.getParticipationDetails() != null) {

			editRequest = true;
			enableDisableElements(false);
			ta_partDet.setText(prt.getParticipationDetails());
			cb_role.getSelectionModel().select(prt.getRole());
			cb_partReq.getSelectionModel().select(prt.getParticipationRequired());
			cb_suggDet.getSelectionModel().select(prt.getSuggestedAttendanceLevel());
			chkB_EntitledToAuthDec.selectedProperty().set(prt.isEntitledToAuthorizeFinalDecision());
			tv_OrgStruct.getSelectionModel().select(prt.getOut());
			tv_OrgStruct.scrollTo(prt.getOut());
		}
	}

	private void unbindProperties() {

		prt.entitledToAuthorizeFinalDecisionProperty().unbind();
		prt.roleProperty().unbind();
		prt.participationDetailsProperty().unbind();
		prt.participationRequiredProperty().unbind();
		prt.suggestedAttendanceLevelProperty().unbind();
	}

	private void clearElements() {

		ta_partDet.clear();
		cb_role.getSelectionModel().selectFirst();
		cb_partReq.getSelectionModel().selectFirst();
		cb_suggDet.getSelectionModel().selectFirst();
		chkB_EntitledToAuthDec.selectedProperty().set(false);
	}

	private void enableDisableElements(boolean b) {

		elementsDisabled = b;
		chkB_EntitledToAuthDec.setDisable(b);
		cb_role.setDisable(b);
		cb_partReq.setDisable(b);
		cb_suggDet.setDisable(b);
		ta_partDet.setDisable(b);
	}

	@FXML
	void btn_addOrgStruct(ActionEvent event) {

		if (cb_chooseTypeToAdd.getSelectionModel().getSelectedItem() != null) {
			
			OrganizationalUnitType org;
			try {

				String s = cb_chooseTypeToAdd.getSelectionModel().getSelectedItem();
				IControllerTypeLevel con;
				EntityManager em = DatabaseManager.getInstance().getEmf().createEntityManager();
				if (s.equals("Board")) {

					con = ControllerFactory.getOrganizationalUnitTypeController(org = new BoardType(), em);
					StageFactory.createAndShowStage("Add board type", false, Views.BOARDTYPE, con);

				} else if (s.equals("Committee")) {

					con = ControllerFactory.getOrganizationalUnitTypeController(org = new CommiteeType(), em);
					StageFactory.createAndShowStage("Add committee type", false, Views.COMMITTEETYPE, con);

				} else if (s.equals("Position")) {

					con = ControllerFactory.getOrganizationalUnitTypeController(org = new PositionType(), em);
					StageFactory.createAndShowStage("Add position type", false, Views.POSITIONTYPE, con);

				} else {

					con = ControllerFactory.getOrganizationalUnitTypeController(org = new RoleType(), em);
					StageFactory.createAndShowStage("Add role type", false, Views.ROLETYPE, con);
				}
				
				fillTable();
				selectAddedOrEditedItem(org);
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
	}

	@FXML
	void btn_editOrgStructAction(ActionEvent event) {

		try {

			if (tv_OrgStruct.getSelectionModel().getSelectedItem() != null) {

				OrganizationalUnitType tmp = tv_OrgStruct.getSelectionModel().getSelectedItem();
				IControllerTypeLevel con = ControllerFactory.getOrganizationalUnitTypeController(tmp, em);
				StageFactory.createAndShowStage("Edit type", false, Views.getOrgStructTypeView(tmp), con);
				// em.refresh(tmp);

				printTmp(tmp);
				selectAddedOrEditedItem(tmp);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	private void selectAddedOrEditedItem(OrganizationalUnitType org) {
		
		for (OrganizationalUnitType orgTmp : tv_OrgStruct.getItems()) {
			
			if (org.getName() != null && orgTmp.getName().equals(org.getName())) {
				
				tv_OrgStruct.getSelectionModel().select(orgTmp);
				tv_OrgStruct.scrollTo(orgTmp);
				break;
			}
		}
	}
	
	private void printTmp(OrganizationalUnitType tmp) {

		System.out.println(tmp.getName());
		System.out.println(tmp.getDescription());
		System.out.println(tmp.getPartRelations().size());
	}

	@FXML
	void btn_saveAction(ActionEvent event) {

		if (selectedItem != null) {

			if (!editRequest) {

				if (checkLv()) {

					prt.setDpt(dpt);
					prt.setOut(selectedItem);
					selectedItem.getPartRelations().add(prt);
					dpt.getPartRelations().add(prt);
					unbindProperties();
					stage.close();
				}
			} else {

				if (selectedItem.getName().equals(prt.getOut().getName())) {

					if (em.contains(prt.getOut())) {

						System.out.println("JA ER CONTAINED  ES");
					}
					unbindProperties();
					stage.close();
				} else {

					if (checkLv()) {

						unbindProperties();
						prt.getOut().getPartRelations().remove(prt);
						prt.setOut(selectedItem);
						selectedItem.getPartRelations().add(prt);
						stage.close();
					}
				}
			}
		}
	}

	private boolean checkLv() {

		for (ParticipationRelationType prt : dpt.getPartRelations()) {

			if (prt.getOut().getName().equals(selectedItem.getName())) {

				AlertDialogFactory.createStandardInformationAlert("Information", "Save error",
						"Organizational structure already added to decision process type");
				return false;
			}
		}
		return true;
	}

	public void setPrt(ParticipationRelationType prt) {

		this.prt = prt;
	}

	public void setDpt(DecisionProcessType dpt) {

		this.dpt = dpt;
	}

	public EntityManager getEm() {

		return em;
	}

	public Stage getStage() {

		return stage;
	}

	@Override
	public void setStage(Stage stage) {

		this.stage = stage;
	}

	public void setEditRequest(boolean editRequest) {

		this.editRequest = editRequest;
	}

	@Override
	public void setEntityManager(EntityManager em) {

		this.em = em;
	}

}
