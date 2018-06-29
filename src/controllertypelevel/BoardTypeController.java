package controllertypelevel;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;

import database.DatabaseManager;
import database.QueriesNameCheck;
import helpercomponents.AlertDialogFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import organizationalunits.BoardType;
import organizationalunits.OrganizationalUnitType;
import organizationalunits.ParticipationRelationType;
import javafx.fxml.Initializable;

public class BoardTypeController implements Initializable, IOrgStructTypeController {

	private OrganizationalUnitType orgUnT;
	private Stage stage;
	private EntityManager em;
	private boolean onEdit;
	private String nameBeforeEdit;

	@FXML
	private TextField tf_name;

	@FXML
	private TextArea ta_mission;

	@FXML
	private TextArea ta_description;

	@FXML
	private CheckBox chkb_internal;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		BoardType bt = (BoardType) orgUnT;
		checkFields(bt);
	}
	
	public BoardTypeController() {
		
	}
	
	public BoardTypeController(OrganizationalUnitType orgUnt) {
		
		this.orgUnT = orgUnt;
	}
	
	private void bindProperties() {
		
		BoardType bt = (BoardType) orgUnT;
		bt.internalProperty().bind(chkb_internal.selectedProperty());
		bt.nameProperty().bind(tf_name.textProperty());
		bt.descriptionProperty().bind(ta_description.textProperty());
		bt.missionProperty().bind(ta_mission.textProperty());
	}
	
	private void checkFields(BoardType bt) {

		if (bt.getName() != null && bt.getDescription() != null) {
			
			onEdit = true;
			nameBeforeEdit = bt.getName();
			tf_name.setText(bt.getName());
			ta_description.setText(bt.getDescription());
			ta_mission.setText(bt.getMission());
			chkb_internal.setSelected(bt.isInternal());
		} else {
			
			bindProperties();
		}
	}

	@FXML
	void btn_saveAction(ActionEvent event) {

		if (!orgUnT.getName().equals("") && !tf_name.getText().equals("")) {

			if (!onEdit) {

				if (QueriesNameCheck.orgStructTypeNameCheck(orgUnT.getName())) {
					
					DatabaseManager.getInstance().persistType(orgUnT);
					orgUnT.unbindProperties();
					stage.close();
				} else {

					AlertDialogFactory.createStandardInformationAlert("Information", "Save error",
							"Board type with entered name already exists in database!");
				}
			} else {
				
				if (QueriesNameCheck.orgStructTypeEditNameCheck(nameBeforeEdit, orgUnT.getName())) {
					
					BoardType bt = (BoardType) orgUnT;
					bt.setName(tf_name.getText());
					bt.setDescription(ta_description.getText());
					bt.setMission(ta_mission.getText());
					bt.setInternal(chkb_internal.isSelected());
					
					DatabaseManager.getInstance().merge(bt);
					em.refresh(bt);
					
					stage.close();
				}
			}
		} else {

			AlertDialogFactory.createStandardInformationAlert("Information", "Save error", "No name entered!");
		}
	}
	
	@Override
	public void setStage(Stage stage) {

		this.stage = stage;
	}

	@Override
	public void setEntityManager(EntityManager em) {

		this.em = em;
	}

	@Override
	public void setOrganizationalUnitType(OrganizationalUnitType type) {

		this.orgUnT = type;
	}

}
