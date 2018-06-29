package controllertypelevel;

import java.net.URL;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;

import database.DatabaseManager;
import database.QueriesNameCheck;
import helpercomponents.AlertDialogFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import organizationalunits.OrganizationalUnitType;
import organizationalunits.PositionType;

public class PositionTypeController implements Initializable, IOrgStructTypeController {

	private OrganizationalUnitType orgUnT;
	private Stage stage;
	private EntityManager em;
	private boolean onEdit;
	private String nameBeforeEdit;

	@FXML
	private CheckBox chkb_staff;

	@FXML
	private TextField tf_name;

	@FXML
	private TextArea ta_description;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		PositionType pt = (PositionType) orgUnT;

		checkFields(pt);

	}

	private void checkFields(PositionType pt) {

		if (orgUnT.getName() != null && orgUnT.getDescription() != null) {

			onEdit = true;
			nameBeforeEdit = orgUnT.getName();
			tf_name.setText(orgUnT.getName());
			ta_description.setText(orgUnT.getDescription());
			chkb_staff.setSelected(pt.isStaff());
		} else {

			pt.staffProperty().bind(chkb_staff.selectedProperty());
			pt.nameProperty().bind(tf_name.textProperty());
			pt.descriptionProperty().bind(ta_description.textProperty());
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
							"Position type with entered name already exists in database!");
				}
			} else {

				if (QueriesNameCheck.orgStructTypeEditNameCheck(nameBeforeEdit, tf_name.getText())) {
					
					PositionType pt = (PositionType) orgUnT;
					pt.setName(tf_name.getText());
					pt.setDescription(ta_description.getText());
					pt.setStaff(chkb_staff.isSelected());
					
					DatabaseManager.getInstance().merge(pt);
					em.refresh(pt);
					
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