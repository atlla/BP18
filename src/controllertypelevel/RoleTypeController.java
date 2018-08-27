package controllertypelevel;

import java.net.URL;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;

import database.DatabaseManager;
import database.QueriesNameCheck;
import helpercomponents.AlertDialogFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.MainScreenController;
import organizationalunits.OrganizationalUnitType;
import organizationalunits.RoleType;

public class RoleTypeController implements Initializable, IOrgStructTypeController {

	private OrganizationalUnitType orgUnT;
	private Stage stage;
	private EntityManager em;
	private boolean onEdit;
	private String nameBeforeEdit;

	@FXML
	private TextArea ta_procedure;

	@FXML
	private TextField tf_period;

	@FXML
	private TextField tf_name;

	@FXML
	private TextArea ta_description;

	@FXML
	private CheckBox chkb_internal;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		RoleType rt = (RoleType) orgUnT;

		// Textfeld das nur Zahlen entgegennimmt
		tf_period.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				if (!newValue.matches("[0-9]+")) {

					tf_period.setText(newValue.replaceAll("[^0-9]", ""));
					if (tf_period.getText().equals("")) {

						rt.setPeriod(0);
					}
				} else if (newValue.length() < 2 && newValue.matches("[0]")) {

					tf_period.setText(newValue.replaceAll("[0]", ""));
				} else {

					rt.setPeriod(Integer.parseInt(newValue));
					System.out.println(rt.getPeriod() + " Period");
				}
			}
		});

		checkFields(rt);
	}

	private void checkFields(RoleType rt) {

		if (rt.getName() != null && rt.getDescription() != null) {

			nameBeforeEdit = rt.getName();
			onEdit = true;
			tf_name.setText(rt.getName());
			ta_description.setText(rt.getDescription());
			ta_procedure.setText(rt.getProcedure());
			tf_period.setText(Integer.toString(rt.getPeriod()));
			chkb_internal.setSelected(rt.isInternal());
		} else {

			rt.internalProperty().bind(chkb_internal.selectedProperty());
			rt.nameProperty().bind(tf_name.textProperty());
			rt.descriptionProperty().bind(ta_description.textProperty());
			rt.procedureProperty().bind(ta_procedure.textProperty());
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

					RoleType rt = (RoleType) orgUnT;
					rt.setName(tf_name.getText());
					rt.setDescription(ta_description.getText());
					rt.setInternal(chkb_internal.isSelected());
					if (!tf_period.getText().equals("")) {
						
						rt.setPeriod(Integer.parseInt(tf_period.getText()));
					}
					rt.setProcedure(ta_procedure.getText());
					
					DatabaseManager.getInstance().merge(rt);
					em.refresh(rt);
					
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