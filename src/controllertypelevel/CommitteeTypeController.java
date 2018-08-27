package controllertypelevel;

import java.net.URL;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;

import database.DatabaseManager;
import database.QueriesNameCheck;
import helpercomponents.AlertDialogFactory;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import main.MainScreenController;
import organizationalunits.CommiteeType;
import organizationalunits.OrganizationalUnitType;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CommitteeTypeController implements Initializable, IOrgStructTypeController {

	private OrganizationalUnitType orgUnT;
	private Stage stage;
	private EntityManager em;
	private boolean onEdit;
	private String nameBeforeEdit;

	@FXML
	private TextField tf_name;

	@FXML
	private TextField tf_meetPerYear;

	@FXML
	private TextArea ta_mission;

	@FXML
	private ChoiceBox<String> cb_corpRel;

	@FXML
	private TextArea ta_description;

	@FXML
	private CheckBox chkb_internal;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		CommiteeType ct = (CommiteeType) orgUnT;

		// Textfeld nimmt nur Zahlen entgegen
		tf_meetPerYear.textProperty().addListener((observable1, oldValue1, newValue1) -> {

			if (!newValue1.matches("[0-9]+")) {

				tf_meetPerYear.setText(newValue1.replaceAll("[^0-9]", ""));
				if (tf_meetPerYear.getText().equals("")) {

					ct.setMeetingsPerYear(0);
				}
			} else if (newValue1.length() < 2 && newValue1.matches("[0]")) {

				tf_meetPerYear.setText(newValue1.replaceAll("[0]", ""));
			} else {

				
			}
		});

		checkFields(ct);
	}

	private void bindProperties(CommiteeType ct) {

		ct.internalProperty().bind(chkb_internal.selectedProperty());
		ct.nameProperty().bind(tf_name.textProperty());
		ct.descriptionProperty().bind(ta_description.textProperty());
		ct.corporateRelevanceProperty().bind(cb_corpRel.getSelectionModel().selectedItemProperty());
		ct.missionProperty().bind(ta_mission.textProperty());
	}

	private void checkFields(CommiteeType ct) {

		if (ct.getName() != null && ct.getDescription() != null) {

			onEdit = true;
			nameBeforeEdit = ct.getName();
			tf_name.setText(ct.getName());
			ta_description.setText(ct.getDescription());
			ta_mission.setText(ct.getMission());
			tf_meetPerYear.setText(Integer.toString(ct.getMeetingsPerYear()));
			cb_corpRel.getSelectionModel().select(ct.getCorporateRelevance());
			chkb_internal.setSelected(ct.isInternal());
		} else {
			
			bindProperties(ct);
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

				if (QueriesNameCheck.orgStructTypeEditNameCheck(nameBeforeEdit, tf_name.getText())) {
					
					CommiteeType ct = (CommiteeType) orgUnT;
					if (!tf_meetPerYear.getText().equals("")) {
						
						ct.setMeetingsPerYear(Integer.parseInt(tf_meetPerYear.getText()));
					}
					ct.setName(tf_name.getText());
					ct.setDescription(ta_description.getText());
					ct.setMission(ta_mission.getText());
					ct.setInternal(chkb_internal.isSelected());
					ct.setCorporateRelevance(cb_corpRel.getSelectionModel().getSelectedItem());
					
					DatabaseManager.getInstance().merge(ct);
					em.refresh(ct);
					
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

	@Override
	public void setMsc(MainScreenController msc) {
		// TODO Auto-generated method stub
		
	}

}
