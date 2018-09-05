package controllertypelevel;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.metamodel.EntityType;

import database.DatabaseManager;
import database.QueriesNameCheck;
import decisionprocess.DecisionProcessInstance;
import decisionprocess.DecisionProcessType;
import decisionprocess.StimulusInstance;
import decisionprocess.StimulusType;
import helpercomponents.AlertDialogFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.MainScreenController;

public class StimulusInstanceController implements Initializable, IControllerTypeLevel {
	
	private StimulusType st;
	private StimulusInstance si;
	private DecisionProcessType dpt;
	private Stage stage;
	private EntityManager em;
	private boolean editRequest;
	private boolean editDpiRequest;
	private String nameBeforeEdit;
	
	private MainScreenController msc;
	
	@FXML
	private TextField siName;
	
	@FXML 
	private DatePicker recordedDate;
	
	@FXML
	private ChoiceBox<StimulusType> siStimulusType;
	
	@FXML
	private ChoiceBox<String> siPerceivedUrgency;
	
	@Override
	public void setMsc(MainScreenController msc) {
		this.msc = msc;
	}
	
	@FXML
	void btn_test(ActionEvent event){
	/*	System.out.println("LocalDate " + si.getRecorded());
		si.setRecordedInDate(si.getRecorded());
		System.out.println("Date "+si.getRecordedDate());
		System.out.println("LocalDate "+si.getRecorded());
		System.out.println("LocalDate Property " +si.recordedProperty());
		si.recordedProperty().unbind();
		si.setRecordedDateinLocalDate(si.getRecordedDate());
		si.recordedProperty().bind(recordedDate.valueProperty());
		System.out.println("LocalDate " +si.getRecorded());*/
		System.out.println(si.getInitiatedDpi().getInstanceName());
		
	}
	@FXML
	void btn_actnsaveStimulus(ActionEvent event) throws IOException{
		
			if(siStimulusType.getSelectionModel().getSelectedItem()!= null && !siName.getText().isEmpty() && recordedDate.getValue() != null) {
				//Save
				if (!editRequest) {
					
					if (QueriesNameCheck.stimulusInstanceNameCheck(si.getInstanceName())) {
						//reference
						if (!em.isOpen())
							em = DatabaseManager.getInstance().getEmf().createEntityManager();
						si.setInitiatedStimulusType(siStimulusType.getSelectionModel().getSelectedItem());
						si.setRecordedInDate(si.getRecorded());
						//DatabaseManager.getInstance().persistType(si, em);
						//tPane.getTabs().remove(tab);				
						/*AlertDialogFactory.createStandardInformationAlert("Information",
								"Database persist",
								"A stimulus instance is saved!");*/
						stage.close();
						if (!editDpiRequest) {
							if (si.getInitiatedDpi()==null)
								si.setInitiatedDpi(new DecisionProcessInstance());
							msc.newDpiTab(si,si.getInitiatedDpi());
						//msc.SetSiSaved(true);
						//(System.out.println("test");
						}
						else { //Cause of the new Reference - Persist DPI
							if (!em.isOpen())
								em = DatabaseManager.getInstance().getEmf().createEntityManager();
							DecisionProcessInstance ddd = em.find(DecisionProcessInstance.class, getDpi(em,si.getInitiatedDpi().getInstanceName()).getId());
							em.getTransaction().begin();
							ddd.setStimInstReference(si);
							em.getTransaction().commit();
							//DatabaseManager.getInstance().persistType(getDpi(em,si.getInitiatedDpi().getInstanceName()));
						}
						
					} else {

						AlertDialogFactory.createStandardInformationAlert("Information",
								"Database persist error (duplicate name)",
								"A stimulus instance with the same name" + ", already exists in database!");
						return;
					}

				} else {
					if (QueriesNameCheck.stimulusInstanceEditNameCheck(nameBeforeEdit, si.getInstanceName())) {
						if (!em.isOpen())
							em = DatabaseManager.getInstance().getEmf().createEntityManager();
						//removeRelations(); StimulusInstance hasn't got any Relations to remove
						DatabaseManager.getInstance().persistType(si, em);
						//tPane.getTabs().remove(tab);
						/*AlertDialogFactory.createStandardInformationAlert("Information",
								"Database persist",
								"A stimulus instance is changed!");*/
						stage.close();

					} else {

						AlertDialogFactory.createStandardInformationAlert("Information",
								"Database persist error (duplicate name)",
								"A stimulus instance with the same name" + ", already exists in database!");
						return;
					}
				}
			}
			else {
				//throw Info, that empty
				String error = "";
				if (siName.getText().isEmpty())
					error = "Please type a name before you continue.";
				else if(siStimulusType.getSelectionModel().getSelectedItem()== null)
					error = "Please select a Stimulus Type before you continue.";
				else if(recordedDate.getValue() == null)
					error = "Please select a date before you continue.";
				AlertDialogFactory.createStandardInformationAlert("Information",
						"Stimulus Type",
						error);
				return;
			}
			
	}
	
	private void removeStimulusInstance() {
		
	//TODO When deleting the SI the DPI has to be marked (No SI -> No StimulusType -> So no DPT Connection for DPI)
		//-> setNoType = True
		
	}
	
	/* private void removeRelations() { //not necessary for SI

		if (relationsToRemove.size() > 0) {

			for (Object o : relationsToRemove) {

				DatabaseManager.getInstance().remove(o);
			}
		}
	} */
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fillStimulusTypeList();
		bindProperties();
	}
	
	private void fillStimulusTypeList() {
		try {
			
			EntityType<StimulusInstance> type = em.getMetamodel().entity(StimulusInstance.class);
			if (type != null) {
				
				TypedQuery<StimulusType> q1 = em.createQuery("SELECT st FROM StimulusType st", StimulusType.class);
				if (q1.getResultList().size() > 0) {
					ObservableList<StimulusType> oListGetResult = FXCollections.observableList(q1.getResultList());
					siStimulusType.setItems(oListGetResult);
				}
				else
					System.out.println("DPT Liste leer");
			}
			
			//em.close();
		} catch (Exception e) {
			
			e.printStackTrace();
		}

			
	}
	private void bindProperties() {
		checkFields();
		si.instanceNameProperty().bind(siName.textProperty());
		si.recordedProperty().bind(recordedDate.valueProperty());
		si.perceivedUrgencyProperty().bind(siPerceivedUrgency.getSelectionModel().selectedItemProperty());
		
	}
	
	private void checkFields() {
		//TODO für EDIT StimulusInstance noch nicht fertig
		if(si.getInstanceName() != null ) {
			
			editRequest = true;
			nameBeforeEdit = si.getInstanceName();
			siName.setText(si.getInstanceName());
			si.setRecordedDateinLocalDate(si.getRecordedDate());
			recordedDate.setValue(si.getRecorded()); 
			//TODO StimulusType Set Value
			siPerceivedUrgency.setValue(si.getPerceivedUrgency());
		}
		else if(si.getInitiatedDpi() != null) {
			
			editDpiRequest = true;
		}
	}
	private DecisionProcessInstance getDpi(EntityManager em, String dpiName) {
		
		try {
			TypedQuery<DecisionProcessInstance> q1 = em
					.createQuery("SELECT dpi FROM DecisionProcessInstance dpi WHERE dpi.instanceName = '" + dpiName + "'"
					, DecisionProcessInstance.class);
			return q1.getResultList().get(0);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return null;
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
	
	public void setSi(StimulusInstance si) {
		this.si = si;
	}
	public StimulusInstance getSi() {
		return si;
	}
	public void setEditRequest(boolean editRequest) {

		this.editRequest = editRequest;
	}

	@Override
	public void setEntityManager(EntityManager em) {
		// TODO Auto-generated method stub
		
	}

}
