package controllertypelevel;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import controllerandstagefactory.ControllerFactory;
import controllerandstagefactory.StageFactory;
import database.DatabaseManager;
import database.QueriesNameCheck;
import decisionpremise.RelevanceRelationType;
import decisionprocess.DecisionProcessInstance;
import decisionprocess.DecisionProcessType;
import decisionprocess.StimulusType;
import helpercomponents.AlertDialogFactory;
import helpercomponents.Views;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.MainScreenController;
import organizationalunits.ParticipationRelationType;

public class DpiTabController implements Initializable, IDpiTabController {
	private DecisionProcessInstance dpi;
	private Tab tab;
	private TabPane tPane;
	private EntityManager em;
	private Stage stage;
	private boolean onEdit;
	private String nameBeforeEdit;
	private List<Object> relationsToRemove = new ArrayList<>();
	
	private MainScreenController msc;
	
	@FXML
	private TextField dpiName;
	
	@FXML
	private DatePicker dpiDate;
	
	@FXML
	private DatePicker dpiEEDate;
	
	@FXML
	private DatePicker dpiEndDate;
	
	@FXML
	private ChoiceBox<String> dpiPersumedImpact;
	
	@FXML 
	private Label dpiDPT;
	
	@FXML
	private Label dpiStimulusType;
	
	@FXML
	private Label dpiStimulusDate;
	
	//TODO für ListViews die entsprechenden Klassen erstellen 
	
	//private static EntityManager generateEmTEST() {

	//	return DatabaseManager.getInstance().getEmf().createEntityManager();
	//}
	@FXML
	void testButton(ActionEvent event) {
	/*	AlertDialogFactory.createStandardInformationAlert("Information",
				"Database persist",
				"Test Solution: " + dpi.getInstanceName());
	*/
		//EntityManager em = generateEmTEST();
		TypedQuery<String> q1 = em.createQuery("SELECT dpi.instanceName FROM DecisionProcessInstance dpi", String.class);
		List<String> t = q1.getResultList();
		String ts = "";
		for (String s : t){
			ts += s + "\n";
			
		}
		
		AlertDialogFactory.createStandardInformationAlert("Test BUTTON",
				"Liste Aller gespeicherten DPIs", ts);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		//onEdit = false;
		bindProperties();
	}
	
	//ToDO bind Properties
	private void bindProperties() {
		
		checkFields();
		dpi.instanceNameProperty().bind(dpiName.textProperty());
	
	}
	private void checkFields() {

		if (dpi.getInstanceName()!= null /*&& dpt.getGeneralAim() != null */ ) {

			onEdit = true;
			nameBeforeEdit = dpi.getInstanceName();
			dpiName.setText(dpi.getInstanceName());
/*			tf_generalAim.setText(dpt.getGeneralAim());
			cb_activityFocus.getSelectionModel().select(dpt.getCommonActivityFocus());
			cb_presumedImpact.getSelectionModel().select(dpt.getPresumedImpact());
*/
			fillLists();
		}
		else { 
			if (dpi.getStimInstReference() == null) {
				dpiDPT.setText(">Decission Process Type<");
				dpiStimulusType.setText(">Stimulus Type");
				dpiStimulusDate.setText(">Stimulus Date");
			}
			else {
				if (dpi.getStimInstReference().getInitatedStimulusType() == null)
						System.out.println("ST existiert nicht");
				dpi.setDptReference(dpi.getStimInstReference().getInitatedStimulusType().getDpt());
				dpiDPT.setText("DPT: " + dpi.getDptReference().getName());
				dpiStimulusType.setText("ST: "+ dpi.getStimInstReference().getInitatedStimulusType().getName());
				dpiStimulusDate.setText("StimulusDate: " + dpi.getStimInstReference().getRecorded().toString());
			}
		}
			
	}
	private void fillLists() {
/* ToDo ListView fuellen!
		if (!dpt.getInitiatingStimulusType().isEmpty()) {

			lv_stimTypes.getItems().addAll(dpt.getInitiatingStimulusType());
		}

		if (!dpt.getPartRelations().isEmpty()) {

			lv_partTypes.getItems().addAll(dpt.getPartRelations());
		}

		if (!dpt.getRelRelations().isEmpty()) {

			lv_relDecPrem.getItems().addAll(dpt.getRelRelations());
		}
*/
	}
	@FXML
	void btn_saveInstance(ActionEvent event) {
		
		if (!(dpi.getInstanceName().equals(""))) {

			//if (!(dpt.getInitiatingStimulusType().size() == 0)) {

				if (!onEdit) {

					if (QueriesNameCheck.dpiNameCheck(dpi.getInstanceName())) {
						//
						DatabaseManager.getInstance().persistType(dpi, em);
						tPane.getTabs().remove(tab);
						/*AlertDialogFactory.createStandardInformationAlert("Information",
								"Database persist",
								"A decision process instance is saved!");*/
						msc.setVisibilityOfMainScreenElements(true);

					} else {

						AlertDialogFactory.createStandardInformationAlert("Information",
								"Database persist error (duplicate name)",
								"A decision process instance with the same name" + ", already exists in database!");
					}

				} else {
					
					if (QueriesNameCheck.dpiEditNameCheck(nameBeforeEdit, dpi.getInstanceName())) {
						
						removeRelations();
						DatabaseManager.getInstance().persistDpt(dpi, em);
						tPane.getTabs().remove(tab);
						
						/*AlertDialogFactory.createStandardInformationAlert("Information",
								"Database persist",
								"A decision process instance is saved!");*/
						msc.setVisibilityOfMainScreenElements(true);

					} else {

						AlertDialogFactory.createStandardInformationAlert("Information",
								"Database persist error (duplicate name)",
								"A decision process instance with the same name" + ", already exists in database!");
					}
				}
			/*} else {

				AlertDialogFactory.createStandardInformationAlert("Information", "Database persist error",
						"No stimulus type added! (At least one)");
			} */
		} else {
			//TODO Just Error-Dialog, if no name is typed? 
			AlertDialogFactory.createStandardInformationAlert("Information", "Database persist error",
					"No name entered for decision process instance!");
		} 

	}

	private void removeRelations() { //wird benötigt für das Löschen der Relationen von Instanzen

		if (relationsToRemove.size() > 0) {

			for (Object o : relationsToRemove) {

				DatabaseManager.getInstance().remove(o);
			}
		}
	}
	
	
	@Override
	public void setEntityManager(EntityManager em) {
		// TODO Auto-generated method stub
		this.em = em;
	}

	@Override
	public void setStage(Stage stage) {
		// TODO Auto-generated method stub
		this.stage = stage;
	}

	@Override
	public void setTabPane(TabPane tPane) {
		// TODO Auto-generated method stub
		this.tPane = tPane;
	}
	
	public DecisionProcessInstance getDpi() {
		return dpi;
	}
	
	@Override
	public void setDpi(DecisionProcessInstance dpi) {
		// TODO Auto-generated method stub
		this.dpi = dpi;
	}

	@Override
	public void setDpiTab(Tab tab) {
		// TODO Auto-generated method stub
		this.tab = tab;
	}

	@Override
	public EntityManager getEm() {
		// TODO Auto-generated method stub
		return em;
	}

	@Override
	public TabPane getTpane() {
		// TODO Auto-generated method stub
		return this.tPane;
	}

	@Override
	public void setMsc(MainScreenController msc) {
		// TODO Auto-generated method stub
		this.msc = msc;
	}


}
