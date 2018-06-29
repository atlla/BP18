package controllertypelevel;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.objectdb.o.CBD;

import controllerandstagefactory.ControllerFactory;
import controllerandstagefactory.StageFactory;
import database.DatabaseManager;
import database.QueriesNameCheck;
import decisionpremise.AbstractGoalType;
import decisionpremise.DecisionGoalType;
import decisionpremise.DecisionPremiseType;
import decisionpremise.EnvironmentalFactorType;
import decisionpremise.RationaleSpec;
import helpercomponents.AlertDialogFactory;
import helpercomponents.Views;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import supportrequirements.AnalysisNeedType;
import supportrequirements.SupportRequirementType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class DecisionGoalTypeController implements IDecisionPremiseTypeController, Initializable {

	private EntityManager em;
	private Stage stage;
	private DecisionPremiseType decPremT;
	private boolean onEdit;
	private String nameBeforeEdit;

	@FXML
	private TextField tf_name;

	@FXML
	private TextArea ta_description;

	@FXML
	private ListView<RationaleSpec> lv_justification;

	@FXML
	private Parent embeddedSuppReqView;
	
	@FXML
	private SuppReqHelperController embeddedSuppReqViewController;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		checkFields();
	}

	private void bindProperties() {

		DecisionGoalType dgt = (DecisionGoalType) decPremT;
		dgt.nameProperty().bind(tf_name.textProperty());
		dgt.descriptionProperty().bind(ta_description.textProperty());
	}

	private void checkFields() {
		
		embeddedSuppReqViewController.setDecPremT(decPremT);
		embeddedSuppReqViewController.setEm(em);
		if (decPremT.getName() != null && decPremT.getDescription() != null) {

			fillList();
			embeddedSuppReqViewController.fillAddedTable();
			embeddedSuppReqViewController.fillAvailTable();
			if (!decPremT.getName().equals("") || !decPremT.getDescription().equals("")) {

				onEdit = true;
				nameBeforeEdit = decPremT.getName();
				tf_name.setText(decPremT.getName());
				ta_description.setText(decPremT.getDescription());
			}
		} else {
			
			embeddedSuppReqViewController.fillAvailTable();
			bindProperties();
		}
	}

	private void fillList() {

		List<RationaleSpec> tmp = ((AbstractGoalType) decPremT).getJustification();
		if (tmp.size() > 0) {

			lv_justification.getItems().clear();
			lv_justification.getItems().setAll(tmp);
		}
	}

	@FXML
	void btn_addJustiAction(ActionEvent event) throws IOException {

		StageFactory.createAndShowStage("Add justification", false, Views.RRJUSTIFICATION,
				ControllerFactory.getJustificationController(decPremT));

		fillList();
	}

	@FXML
	void btn_editJustiAction(ActionEvent event) {

	}

	@FXML
	void btn_delJustiAction(ActionEvent event) {
		
		if (lv_justification.getSelectionModel().getSelectedItem() != null) {
			
			RationaleSpec tmp = lv_justification.getSelectionModel().getSelectedItem();
			lv_justification.getItems().remove(tmp);
			((DecisionGoalType) decPremT).getJustification().remove(tmp);
		}
	}

	@FXML
	void btn_saveAction(ActionEvent event) {
		
		if(tf_name.getText().equals("")) {
			
			System.out.println("JA");
		}
		
		if (!decPremT.getName().equals("") && !tf_name.getText().equals("")) {

			if (!onEdit) {

				if (QueriesNameCheck.DecPremTypeNameCheck(decPremT.getName())) {

					DatabaseManager.getInstance().persistType(decPremT);
					decPremT.unbindProperties();
					stage.close();
				} else {

					AlertDialogFactory.createStandardInformationAlert("Information", "Save error",
							"Decision premise type with same name already exists in database");
				}

			} else {

				if (QueriesNameCheck.DecPremTypeEditNameCheck(nameBeforeEdit, tf_name.getText())) {

					DecisionGoalType dgt = (DecisionGoalType) decPremT;
					dgt.setName(tf_name.getText());
					dgt.setDescription(ta_description.getText());

					DatabaseManager.getInstance().merge(dgt);
					em.refresh(dgt);

					stage.close();
				} else {

					AlertDialogFactory.createStandardInformationAlert("Information", "Save error",
							"Decision premise type with same name already exists in database");
				}
			}
		} else {

			AlertDialogFactory.createStandardInformationAlert("Information", "Save error", "Name field empty");
		}
	}

	/////////// Getter/Setter///////////
	@Override
	public void setEntityManager(EntityManager em) {

		this.em = em;
	}

	@Override
	public void setStage(Stage stage) {

		this.stage = stage;
	}

	@Override
	public void setDecPremT(DecisionPremiseType decPremT) {

		this.decPremT = decPremT;
	}

}
