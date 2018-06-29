package controllertypelevel;

import java.net.URL;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import database.DatabaseManager;
import database.QueriesNameCheck;
import helpercomponents.AlertDialogFactory;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import supportrequirements.InformationType;

public class NewInformationTypeController implements Initializable, IControllerTypeLevel {

	private InformationType it;
	private EntityManager em;
	private Stage stage;
	private boolean onEdit;
	String nameBeforEdit;

	@FXML
	private TextField tf_name;

	@FXML
	private TextArea ta_description;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		checkFields();
	}

	private void checkFields() {

		if (it.getName() != null && it.getDescription() != null) {
			
			nameBeforEdit = it.getName();
			onEdit = true;
			tf_name.setText(it.getName());
			ta_description.setText(it.getDescription());
		} else {
			
			bindProperties();
		}
	}

	private void bindProperties() {

		it.nameProperty().bind(tf_name.textProperty());
		it.descriptionProperty().bind(ta_description.textProperty());
	}

	private void unbindProperties() {

		it.nameProperty().unbind();
		it.descriptionProperty().unbind();
	}

	@FXML
	void btn_saveAction(ActionEvent event) {
		
		if (!tf_name.getText().equals("")) {
			
			if (!onEdit) {
				
				if (QueriesNameCheck.InformationTypeNameCheck(tf_name.getText())) {
					
					DatabaseManager.getInstance().persistType(it);
					unbindProperties();
					stage.close();
				}
			} else {
				
				if (QueriesNameCheck.informationTypeEditNameCheck(nameBeforEdit, tf_name.getText())) {
					
					it.setName(tf_name.getText());
					it.setDescription(ta_description.getText());
					DatabaseManager.getInstance().merge(it);
					stage.close();
				}
			}
		}
	}

	public void setIt(InformationType it) {

		this.it = it;
	}

	public void setEm(EntityManager em) {

		this.em = em;
	}

	public void setStage(Stage stage) {

		this.stage = stage;
	}

	@Override
	public void setEntityManager(EntityManager em) {

		this.em = em;
	}

}
