package controllertypelevel;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;

import controllerandstagefactory.ControllerFactory;
import controllerandstagefactory.StageFactory;
import database.DatabaseManager;
import database.QueriesNameCheck;
import decisionpremise.AbstractGoalType;
import decisionpremise.DecisionGoalType;
import decisionpremise.DecisionPremiseType;
import decisionpremise.EngagementGoalType;
import decisionpremise.RationaleSpec;
import helpercomponents.AlertDialogFactory;
import helpercomponents.Views;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EngagementGoalTypeController implements Initializable, IDecisionPremiseTypeController {

	private Stage stage;
	private DecisionPremiseType decPremT;
	private EntityManager em;
	private boolean onEdit;
	private String nameBeforeEdit;
	
	@FXML
	private Parent embeddedSuppReqView;
	
	@FXML
	private SuppReqHelperController embeddedSuppReqViewController;

	@FXML
	private TextField tf_name;

	@FXML
	private TextArea ta_description;

	@FXML
	private ListView<RationaleSpec> lv_justification;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		checkFields();
	}

	private void bindProperties() {

		EngagementGoalType egt = (EngagementGoalType) decPremT;
		egt.nameProperty().bind(tf_name.textProperty());
		egt.descriptionProperty().bind(ta_description.textProperty());
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
	void btn_saveAction(ActionEvent event) {
		
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

					EngagementGoalType dgt = (EngagementGoalType) decPremT;
					dgt.setName(tf_name.getText());
					dgt.setDescription(ta_description.getText());
					
					DatabaseManager.getInstance().merge(dgt);
					em.refresh(dgt);
					
					stage.close();
				}
			}
		} else {

			AlertDialogFactory.createStandardInformationAlert("Information", "Save error", "Name field empty");
		}
	}

	@FXML
	void btn_addJustiAction(ActionEvent event) throws IOException {
		
		StageFactory.createAndShowStage("Add justification"
				, false
				, Views.RRJUSTIFICATION
				, ControllerFactory.getJustificationController(decPremT));
		
		fillList();
	}

	@Override
	public void setEntityManager(EntityManager em) {

		this.em = em;
	}

	@Override
	public void setStage(Stage stage) {

		this.stage = stage;
	}

	public void setDecPremT(DecisionPremiseType decPremT) {

		this.decPremT = decPremT;
	}
}