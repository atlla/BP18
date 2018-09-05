package controllertypelevel;

import java.net.URL;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.metamodel.EntityType;

import controllerandstagefactory.ControllerFactory;
import controllerandstagefactory.StageFactory;
import controllerandstagefactory.TabFactory;
import database.DatabaseManager;
import decisionprocess.DecisionProcessInstance;
import decisionprocess.StimulusInstance;
import helpercomponents.AlertDialogFactory;
import helpercomponents.Views;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.MainScreenController;

public class ChooseStimulusInstanceEditController implements Initializable, IControllerTypeLevel{

	private EntityManager em;
	private TabPane tPane;
	private Stage stage;
	private String selectedItem;
	private boolean delete;
	
	private MainScreenController msc;
	
	@FXML
	private ListView<String> StimulusInstances;
	
	@FXML
	private Label dpiName;
	
		@Override
	public void initialize(URL location, ResourceBundle resources) {
		StimulusInstances.setOnMouseClicked(new EventHandler<MouseEvent>(){
		    	  
			@Override
			public void handle(MouseEvent arg0) {
		             
				InstanceSelected();
		          }
		      });
		
		fillList();
	}
	
	private void fillList() {
		try {
			
			EntityType<StimulusInstance> type = em.getMetamodel().entity(StimulusInstance.class);
			if (type != null) {		
				TypedQuery<String> q1 = em.createQuery("SELECT si.instanceName FROM StimulusInstance si", 
						String.class);
				if (q1.getResultList().size() > 0) {
					//ObservableList<DecisionProcessInstance> oListGetResult = FXCollections.observableList(q1.getResultList());
					//dpiInstances.setItems(oListGetResult);
					StimulusInstances.getItems().addAll(q1.getResultList());
				}
				dpiName.setText("<No Selection>");
				dpiName.setTextFill(Color.web("#77b300")); //green
			}
			em.close();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	void InstanceSelected(/*MouseEvent event*/) {
		if (StimulusInstances.getSelectionModel().getSelectedItem() != null) {
			selectedItem = StimulusInstances.getSelectionModel().getSelectedItem();
			EntityManager emtmp = DatabaseManager.getInstance().getEmf().createEntityManager();
			//System.out.println(selectedItem);
			if(getSi(emtmp, selectedItem)!=null) {
				if (getSi(emtmp, selectedItem).getInitiatedDpi() != null) {
					dpiName.setText(getSi(emtmp, selectedItem).getInitiatedDpi().getInstanceName());
					dpiName.setTextFill(Color.web("#000000")); //black
					//noReference = false;
				}
				/*else {
					siName.setText("<No referenced Stimulus Instance>");
					siName.setTextFill(Color.web("#ff4d4d")); //red
					noReference = true;
					}*/
			}
			else {
				dpiName.setText("<ERROR>");
				dpiName.setTextFill(Color.web("#e68a00")); //orange
				StimulusInstances.getSelectionModel().clearSelection();
				}
			}
		else {
			dpiName.setText("<No Selection>");
			dpiName.setTextFill(Color.web("#77b300")); //green
		}
	}	
	
	private StimulusInstance getSi(EntityManager em, String siName) {
		
		try {
			TypedQuery<StimulusInstance> q1 = em
					.createQuery("SELECT si FROM StimulusInstance si WHERE si.instanceName = '" + siName + "'"
					, StimulusInstance.class);
			return q1.getResultList().get(0);
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		return null;
	}
	
	private DecisionProcessInstance getDpi(EntityManager em, String dpiName) {
		
		try {
			TypedQuery<DecisionProcessInstance> q1 = em
					.createQuery("SELECT dpi FROM DecisionProcessInstance dpi WHERE dpi.instanceName = '" + dpiName + "'"
					, DecisionProcessInstance.class);
			return q1.getResultList().get(0);
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		return null;
	}
	
	@FXML
	void btn_okAction(ActionEvent event) {
		try {
			if (StimulusInstances.getSelectionModel().getSelectedItem() != null) {
				/*if(!noReference) { 
					selectedItem = StimulusInstances.getSelectionModel().getSelectedItem();
					EntityManager em = DatabaseManager.getInstance().getEmf().createEntityManager();
					IDpiTabController con = ControllerFactory.getDpiTabController(getDpi(em, selectedItem)
							, tPane, em);
					TabFactory.createAndShowDpiTab(Views.MSDECPROCTYPETAB, "New DPI", con, msc);
					stage.close();
				}
				else {*/
				if (delete) {
					selectedItem = StimulusInstances.getSelectionModel().getSelectedItem();
					EntityManager tmpem = DatabaseManager.getInstance().getEmf().createEntityManager();
					TypedQuery<StimulusInstance> q1 = tmpem
							.createQuery("SELECT si FROM StimulusInstance si WHERE si.instanceName = '" + selectedItem + "'", StimulusInstance.class);
					if (q1.getResultList().size() > 0) {
						String s = q1.getResultList().get(0).getInstanceName();
						q1.getResultList().get(0).getInitiatedDpi().setStimInstReference(null);
						DecisionProcessInstance ddd = tmpem.find(DecisionProcessInstance.class, q1.getResultList().get(0).getInitiatedDpi().getId());
						//Delete SI
						DatabaseManager.getInstance().remove(q1.getResultList().get(0));
						//Set DPI Reference Null
						tmpem.getTransaction().begin();
						ddd.setStimInstReference(null);
						tmpem.getTransaction().commit(); 					
						AlertDialogFactory.createStandardInformationAlert("Information",
							"Database delete",
							"The Stimulus Instance with the name " + s + ", is deleted!");
					}
					else {
						AlertDialogFactory.createStandardInformationAlert("Information",
								"Database delete ERROR",
								"There is an unexpected ERROR.");
					}
					tmpem.close();
					stage.close();

				}
				else {
					selectedItem = StimulusInstances.getSelectionModel().getSelectedItem();
					EntityManager cem = DatabaseManager.getInstance().getEmf().createEntityManager();
					StimulusInstance si = getSi(cem,StimulusInstances.getSelectionModel().getSelectedItem());
					IControllerTypeLevel con = ControllerFactory.getStimulusInstanceController(si, cem);
							/*getStimulusTypeController(dpt,
							lv_stimTypes.getSelectionModel().getSelectedItem(), em);*/
					con.setMsc(msc);
					StageFactory.createAndShowStage("Edit Stimulustype", false, Views.STIMULUSINSTANCE, con);
					stage.close();
					
				}
				//}	
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
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

	@Override
	public void setMsc(MainScreenController msc) {
		this.msc = msc;
	}
	
	public void settPane(TabPane tPane) {
		this.tPane = tPane;
	}
	
	public void setDelete(boolean delete) {
		this.delete = delete;
	}
	public boolean getDelete() {
		return delete;
	}

}
