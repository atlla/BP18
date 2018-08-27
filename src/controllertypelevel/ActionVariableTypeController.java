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
import decisionpremise.EngagementGoalType;
import decisionpremise.FactualDecisionPremiseType;
import decisionpremise.PresumedInfluenceRelationType;
import helpercomponents.AlertDialogFactory;
import helpercomponents.Views;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import main.MainScreenController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ActionVariableTypeController implements IDecisionPremiseTypeController, Initializable {

	private Stage stage;
	private EntityManager em;
	private DecisionPremiseType decPremT;
	private SuppReqHelperController helpCon;
	private boolean onEdit;
	private String nameBeforeEdit;
	private DecisionPremiseType selectedItem;
	private MainScreenController msc;
	
	@FXML
	private ChoiceBox<String> cb_quality;

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
		ActionVariableType avt = (ActionVariableType) decPremT;
		checkFields(avt);
	}

	private void bindProperties(ActionVariableType avt) {

		avt.nameProperty().bind(tf_name.textProperty());
		avt.descriptionProperty().bind(ta_description.textProperty());
		avt.qualityProperty().bind(cb_quality.getSelectionModel().selectedItemProperty());
	}

	private void checkFields(ActionVariableType avt) {
		
		embeddedSuppReqViewController.setDecPremT(decPremT);
		embeddedSuppReqViewController.setEm(em);
		if (avt.getName() != null && avt.getDescription() != null) {
			
			fillTable(avt);
			if (!decPremT.getName().equals("") || !decPremT.getDescription().equals("")) {
				
				embeddedSuppReqViewController.fillAddedTable();
				embeddedSuppReqViewController.fillAvailTable();
				onEdit = true;
				nameBeforeEdit = decPremT.getName();
				tf_name.setText(decPremT.getName());
				ta_description.setText(decPremT.getDescription());
				cb_quality.getSelectionModel().select(avt.getQuality());
			}
		} else {
			
			embeddedSuppReqViewController.fillAvailTable();
			bindProperties(avt);
		}
	}

	private void fillTable(ActionVariableType avt) {

		if (avt.getPresInfluRelations().size() > 0) {

			lv_presInfluRel.getItems().clear();
			for (PresumedInfluenceRelationType pirt : avt.getPresInfluRelations()) {

				lv_presInfluRel.getItems().add(pirt.getInfluenceOnVdp());
			}
		}
	}

	@FXML
	void btn_addPresInfluRelAction(ActionEvent event) throws IOException {

		StageFactory.createAndShowStage("Add presumed influence relation", false, Views.PRESUMEDINFLUENCERELATIONTYPE,
				ControllerFactory.getPresInfluRelTypeController(decPremT, new PresumedInfluenceRelationType()));
		fillTable((ActionVariableType) decPremT);
	}

	@FXML
	void btn_delAction(ActionEvent event) {

		if (selectedItem != null) {

			PresumedInfluenceRelationType toRemove = null;
			ActionVariableType avt = (ActionVariableType) decPremT;
			for (PresumedInfluenceRelationType pirt : avt.getPresInfluRel()) {

				if (pirt.getInfluenceOnVdp().getName().equals(selectedItem.getName())) {

					toRemove = pirt;
					break;
				}
			}

			if (toRemove != null) {

				avt.getPresInfluRel().remove(toRemove);
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

					ActionVariableType avt = (ActionVariableType) decPremT;
					avt.setName(tf_name.getText());
					avt.setDescription(ta_description.getText());
					avt.setQuality(cb_quality.getSelectionModel().getSelectedItem());

					DatabaseManager.getInstance().merge(avt);
					em.refresh(avt);

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
	
	@Override
	public void setMsc(MainScreenController msc) {
		this.msc = msc;
	}
	

}
