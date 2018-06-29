package controllertypelevel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;

import controllerandstagefactory.ControllerFactory;
import controllerandstagefactory.StageFactory;
import database.DatabaseManager;
import database.QueriesNameCheck;
import decisionpremise.ActionVariableType;
import decisionpremise.DecisionPremiseType;
import decisionpremise.EnvironmentalFactorType;
import decisionpremise.PresumedInfluenceRelationType;
import helpercomponents.AlertDialogFactory;
import helpercomponents.Views;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class EnvironmentalFactorTypeController implements IDecisionPremiseTypeController, Initializable {
	
	private Stage stage;
	private EntityManager em;
	private DecisionPremiseType decPremT;
	private SuppReqHelperController helCon;
	private boolean onEdit;
	private String nameBeforeEdit;
	private DecisionPremiseType selectedItem;
	
	@FXML
	private ChoiceBox<String> cb_presPred;

	@FXML
	private TextField tf_name;

	@FXML
	private ListView<DecisionPremiseType> lv_presInfluRel;

	@FXML
	private TextArea ta_description;
	
	@FXML
	private Parent embeddedSuppReqView;
	
	@FXML
	private SuppReqHelperController embeddedSuppReqViewController;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		lv_presInfluRel.getSelectionModel().selectedItemProperty().addListener((obs, oldI, newI) -> {

			if (newI != null) {

				selectedItem = newI;
			}
		});
		EnvironmentalFactorType eft = (EnvironmentalFactorType) decPremT;
		checkFields(eft);
		
	}

	private void bindProperties(EnvironmentalFactorType eft) {

		eft.nameProperty().bind(tf_name.textProperty());
		eft.descriptionProperty().bind(ta_description.textProperty());
		eft.presumedPredictabilityProperty().bind(cb_presPred.getSelectionModel().selectedItemProperty());
	}

	private void checkFields(EnvironmentalFactorType eft) {
		
		embeddedSuppReqViewController.setDecPremT(decPremT);
		embeddedSuppReqViewController.setEm(em);
		if (eft.getName() != null && eft.getDescription() != null) {
			
			fillTable(eft);
			embeddedSuppReqViewController.fillAddedTable();
			embeddedSuppReqViewController.fillAvailTable();
			if (!decPremT.getName().equals("") || !decPremT.getDescription().equals("")) {

				onEdit = true;
				nameBeforeEdit = decPremT.getName();
				tf_name.setText(decPremT.getName());
				ta_description.setText(decPremT.getDescription());
				cb_presPred.getSelectionModel().select(eft.getPresumedPredictability());
			}
		} else {
			
			embeddedSuppReqViewController.fillAvailTable();
			bindProperties(eft);
		}
	}

	private void fillTable(EnvironmentalFactorType eft) {

		if (eft.getPresInfluRelations().size() > 0) {

			lv_presInfluRel.getItems().clear();
			for (PresumedInfluenceRelationType pirt : eft.getPresInfluRelations()) {

				lv_presInfluRel.getItems().add(pirt.getInfluenceOnVdp());
			}
		}
	}

	@FXML
	void btn_addAction(ActionEvent event) throws IOException {

		StageFactory.createAndShowStage("Add presumed influence relation", false, Views.PRESUMEDINFLUENCERELATIONTYPE,
				ControllerFactory.getPresInfluRelTypeController(decPremT, new PresumedInfluenceRelationType()));
		fillTable((EnvironmentalFactorType) decPremT);
	}

	@FXML
	void btn_delAction(ActionEvent event) {

		if (selectedItem != null) {

			PresumedInfluenceRelationType toRemove = null;
			EnvironmentalFactorType eft = (EnvironmentalFactorType) decPremT;
			for (PresumedInfluenceRelationType pirt : eft.getPresInfluRel()) {

				if (pirt.getInfluenceOnVdp().getName().equals(selectedItem.getName())) {

					toRemove = pirt;
					break;
				}
			}

			if (toRemove != null) {

				eft.getPresInfluRel().remove(toRemove);
				lv_presInfluRel.getItems().remove(selectedItem);
				lv_presInfluRel.getSelectionModel().clearSelection();
				DatabaseManager.getInstance().remove(toRemove);
				selectedItem = null;
			}
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

					EnvironmentalFactorType eft = (EnvironmentalFactorType) decPremT;
					eft.setName(tf_name.getText());
					eft.setDescription(ta_description.getText());
					eft.setPresumedPredictability(cb_presPred.getSelectionModel().getSelectedItem());
					
					DatabaseManager.getInstance().merge(eft);
					em.refresh(eft);
					
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
