package controllertypelevel;

import java.net.URL;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;

import database.QueriesNameCheck;
import decisionprocess.DecisionProcessType;
import decisionprocess.StimulusType;
import helpercomponents.AlertDialogFactory;
import helpercomponents.TooltipFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class StimulusTypeController implements Initializable, IControllerTypeLevel {

	private StimulusType st;
	private DecisionProcessType dpt;
	private Stage stage;
	private EntityManager em;
	private boolean editRequest;
	private String nameBeforeEdit;

	@FXML
	private TextField tf_name;

	@FXML
	private TextArea ta_Description;

	@FXML
	private ChoiceBox<String> cb_CommonFreq;

	@FXML
	private ChoiceBox<String> cb_Valency;

	@FXML
	private ChoiceBox<String> cb_CommonUrg;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		bindProperties();
	}

	private void bindProperties() {
		
		checkFields();
		st.nameProperty().bind(tf_name.textProperty());
		st.descriptionProperty().bind(ta_Description.textProperty());
		st.valencyProperty().bind(cb_Valency.valueProperty());
		st.commonFrequencyProperty().bind(cb_CommonFreq.valueProperty());
		st.commonUrgencyProperty().bind(cb_CommonUrg.valueProperty());
	}

	private void checkFields() {

		if(st.getName() != null && st.getDescription() != null) {
			
			editRequest = true;
			nameBeforeEdit = st.getName();
			tf_name.setText(st.getName());
			ta_Description.setText(st.getDescription());
			cb_CommonFreq.getSelectionModel().select(st.getCommonFrequency());
			cb_CommonUrg.getSelectionModel().select(st.getCommonUrgency());
			cb_Valency.getSelectionModel().select(st.getValency());
		}
	}

	private void unbindProperties() {

		st.nameProperty().unbind();
		st.descriptionProperty().unbind();
		st.commonFrequencyProperty().unbind();
		st.commonUrgencyProperty().unbind();
		st.valencyProperty().unbind();
	}

	@FXML
	void actionSaveBtn(ActionEvent event) {
		
		if (!editRequest) {

			if (!st.getName().equals("")) {

				if (checkName(st.getName())) {
					
					unbindProperties();
					st.setDpt(dpt);
					dpt.getInitiatingStimulusType().add(st);
					stage.close();
				}
			} else {

				AlertDialogFactory.createStandardInformationAlert("Information", "Save error", "No name entered!");
			}
		} else {

			if (!tf_name.getText().equals("")) {
				
				if(QueriesNameCheck.stimulusTypeEditNameCheck( nameBeforeEdit, tf_name.getText())){
					
					unbindProperties();
					st.setName(tf_name.getText());
					st.setDescription(ta_Description.getText());
					st.setCommonFrequency(cb_CommonFreq.getValue());
					st.setCommonUrgency(cb_CommonUrg.getValue());
					st.setValency(cb_Valency.getValue());
					stage.close();
				}
			} else {
			
				AlertDialogFactory.createStandardInformationAlert("Information", "Save error", "No name entered!");
			}
		}

	}

	// Wird aufgerufen um zu überprüfen, ob Felder nicht ausgefüllt wurden vom
	// Nutzer(bspw. name/description etc.)
	public boolean checkName(String name) {
		
		if (!QueriesNameCheck.stimulusTypeNameCheck(name)) {

			AlertDialogFactory.createStandardInformationAlert("Information", "Save error",
					"Stimulus type with entered name already exists in database!" + "\n"
							+ "Note: A stimulus type only can initiate exactly one decision process type");
			return false;
		}
		return true;
	}

	public void setDpt(DecisionProcessType dpt) {

		this.dpt = dpt;
	}

	public void setStage(Stage stage) {

		this.stage = stage;
	}

	public void setEm(EntityManager em) {

		this.em = em;
	}

	public void setSt(StimulusType st) {

		this.st = st;
	}

	public void setEditRequest(boolean editRequest) {

		this.editRequest = editRequest;
	}

	@Override
	public void setEntityManager(EntityManager em) {
		// TODO Auto-generated method stub
		
	}

}
