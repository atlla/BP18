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
import decisionpremise.SymbolicGoalType;
import helpercomponents.AlertDialogFactory;
import helpercomponents.Views;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SymbolicGoalTypeController implements IDecisionPremiseTypeController, Initializable {

	private EntityManager em;
	private Stage stage;
	private DecisionPremiseType decPremT;
	private SuppReqHelperController helCon;
	private boolean onEdit;
	private String nameBeforeEdit;

	@FXML
	private TextField tf_name;

	@FXML
	private TextField tf_typeOfAnn;

	@FXML
	private TextArea ta_description;

	@FXML
	private TextField tf_targGrp;

	@FXML
	private ListView<RationaleSpec> lv_justification;

	@FXML
	private TextArea ta_targGrpDescr;

	@FXML
	private Parent embeddedSuppReqView;

	@FXML
	private SuppReqHelperController embeddedSuppReqViewController;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		checkFields();
	}

	private void bindProperties(SymbolicGoalType sgt) {

		sgt.nameProperty().bind(tf_name.textProperty());
		sgt.descriptionProperty().bind(ta_description.textProperty());
		sgt.targetGroupProperty().bind(tf_targGrp.textProperty());
		sgt.targetGroupDescriptionProperty().bind(ta_targGrpDescr.textProperty());
		sgt.typeOfAnnouncementProperty().bind(tf_typeOfAnn.textProperty());

	}

	private void checkFields() {

		SymbolicGoalType sgt = (SymbolicGoalType) decPremT;
		embeddedSuppReqViewController.setDecPremT(decPremT);
		embeddedSuppReqViewController.setEm(em);
		if (sgt.getName() != null && sgt.getDescription() != null) {

			fillList();
			embeddedSuppReqViewController.fillAddedTable();
			embeddedSuppReqViewController.fillAvailTable();
			if (!sgt.getName().equals("") || !sgt.getDescription().equals("")) {

				onEdit = true;
				nameBeforeEdit = sgt.getName();
				tf_name.setText(sgt.getName());
				ta_description.setText(sgt.getDescription());
				tf_targGrp.setText(sgt.getTargetGroup());
				tf_typeOfAnn.setText(sgt.getTypeOfAnnouncement());
				ta_targGrpDescr.setText(sgt.getTargetGroupDescription());
			}
		} else {

			embeddedSuppReqViewController.fillAvailTable();
			bindProperties(sgt);
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
	void btn_addJustifAction(ActionEvent event) throws IOException {

		StageFactory.createAndShowStage("Add justification", false, Views.RRJUSTIFICATION,
				ControllerFactory.getJustificationController(decPremT));

		fillList();
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

					SymbolicGoalType dgt = (SymbolicGoalType) decPremT;
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
