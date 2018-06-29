package controllertypelevel;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import decisionpremise.AbstractGoalType;
import decisionpremise.DecisionGoalType;
import decisionpremise.DecisionPremiseType;
import decisionpremise.EngagementGoalType;
import decisionpremise.RelevanceRelationType;
import decisionpremise.SymbolicGoalType;
import helpercomponents.Views;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ValueDecisionPremiseTypeController implements Initializable {

	private DecisionPremiseType decPremType;
	private RelevanceRelationType rrt;
	private Stage stage;
	private ListView<DecisionPremiseType> lvFromRelRelationController;
	private boolean symbolicGoalSelected = false;
	private boolean editRequest;

	@FXML
	private TextField tf_typeOfAnnouncement;

	@FXML
	private ChoiceBox<String> cb_typeOfGoal;

	@FXML
	private TextField tf_name;

	@FXML
	private TextArea ta_targetGroupDescr;

	@FXML
	private ListView<String> lv_justifications;

	@FXML
	private TextArea ta_description;

	@FXML
	private TextField tf_absolutePriority;

	@FXML
	private TextField tf_targetGroup;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// Listener Auswahl Type of goal
		if (!editRequest) {
			
			cb_typeOfGoal.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

				if (newValue.equals("Engagement goal")) {

					EngagementGoalType egt = new EngagementGoalType();
					clearControls();
					disableSymbolicGoalControls();
					egt.nameProperty().bind(tf_name.textProperty());
					egt.descriptionProperty().bind(ta_description.textProperty());
					egt.getAbsolutePriority().valueProperty().bind(tf_absolutePriority.textProperty());
					decPremType = egt;
				} else if (newValue.equals("Symbolic goal")) {

					SymbolicGoalType sgt = new SymbolicGoalType();
					clearControls();
					enableSymbolicGoalControls();
					sgt.nameProperty().bind(tf_name.textProperty());
					sgt.descriptionProperty().bind(ta_description.textProperty());
					sgt.getAbsolutePriority().valueProperty().bind(tf_absolutePriority.textProperty());
					sgt.targetGroupProperty().bind(tf_targetGroup.textProperty());
					sgt.targetGroupDescriptionProperty().bind(ta_targetGroupDescr.textProperty());
					sgt.typeOfAnnouncementProperty().bind(tf_typeOfAnnouncement.textProperty());
					decPremType = sgt;
				} else if (newValue.equals("Decision goal")) {

					DecisionGoalType dgt = new DecisionGoalType();
					clearControls();
					disableSymbolicGoalControls();
					dgt.nameProperty().bind(tf_name.textProperty());
					dgt.descriptionProperty().bind(ta_description.textProperty());
					dgt.getAbsolutePriority().valueProperty().bind(tf_absolutePriority.textProperty());
					decPremType = dgt;
				}
			});
		} else {
			
			
		}
	}

	public void clearControls() {

		tf_name.clear();
		ta_description.clear();
		lv_justifications.getItems().clear();
		tf_absolutePriority.clear();
		tf_targetGroup.clear();
		ta_targetGroupDescr.clear();
		tf_typeOfAnnouncement.clear();
	}

	public void disableSymbolicGoalControls() {

		if (symbolicGoalSelected) {

			tf_targetGroup.setDisable(true);
			ta_targetGroupDescr.setDisable(true);
			tf_typeOfAnnouncement.setDisable(true);
		}
	}

	public void enableSymbolicGoalControls() {

		symbolicGoalSelected = true;
		tf_targetGroup.setDisable(false);
		ta_targetGroupDescr.setDisable(false);
		tf_typeOfAnnouncement.setDisable(false);
	}

	@FXML
	void btn_addJustification(ActionEvent event) {
		
		Stage stage;
		try {
			stage = new Stage();
			FXMLLoader loader = new FXMLLoader(new File(Views.RRJUSTIFICATION).toURI().toURL());
			stage.setScene(new Scene(loader.load()));
			JustificationRelevRelTypeController con = loader.getController();
			con.setAgt((AbstractGoalType) decPremType);
			stage.showAndWait();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@FXML
	void btn_editJustification(ActionEvent event) {

	}

	@FXML
	void btn_delJustification(ActionEvent event) {

	}

	@FXML
	void btn_save(ActionEvent event) {

		if (decPremType != null && decPremType.getName().length() > 0 && decPremType.getDescription().length() > 0) {

			lvFromRelRelationController.getItems().add(decPremType);
			lvFromRelRelationController.getSelectionModel().select(decPremType);
			stage.close();
		}
	}

	public void setRrt(RelevanceRelationType rrt) {

		this.rrt = rrt;
	}

	public void setStage(Stage stage) {

		this.stage = stage;
	}

	public void setLvFromRelRelationController(ListView<DecisionPremiseType> lvFromRelRelationController) {

		this.lvFromRelRelationController = lvFromRelRelationController;
	}
}
