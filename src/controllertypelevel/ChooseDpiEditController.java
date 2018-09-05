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
import decisionprocess.DecisionProcessType;
import decisionprocess.StimulusInstance;
import decisionprocess.StimulusType;
import helpercomponents.AlertDialogFactory;
import helpercomponents.Views;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.MainScreenController;

public class ChooseDpiEditController implements Initializable, IControllerTypeLevel {
	private EntityManager em;
	private TabPane tPane;
	private Stage stage;
	private String selectedItem;
	private boolean noReference;
	private boolean delete;
	
	private MainScreenController msc;
	
	@FXML
	private ListView<String> dpiInstances;
	@FXML
	private Label siName;
	
	@FXML
	void SiRead(ActionEvent event) {
		EntityManager tmpem = DatabaseManager.getInstance().getEmf().createEntityManager();
		System.out.println(" "+ getDpi(tmpem, dpiInstances.getSelectionModel().getSelectedItem()).getStimInstReference());
		tmpem.close();
	}
	@FXML
	void DeleteAllSI(ActionEvent event) {
		try {
			EntityManager tmpem = DatabaseManager.getInstance().getEmf().createEntityManager();
			TypedQuery<StimulusInstance> q1 = tmpem.createQuery("SELECT si FROM StimulusInstance si", StimulusInstance.class);
			if (q1.getResultList().size() > 0) {

				for (StimulusInstance tmpsi : q1.getResultList()) {
					tmpsi.getInitiatedDpi().setDptReference(null);
					tmpsi.getInitiatedDpi().setStimInstReference(null);
					DatabaseManager.getInstance().remove(tmpsi);
					System.out.println("SI " + tmpsi.getInstanceName() + " is deleted.");
				}
			}
			else 
				System.out.println("No SI to delete");
			tmpem.close();
			//em.close();
			tmpem = DatabaseManager.getInstance().getEmf().createEntityManager();
			TypedQuery<DecisionProcessInstance> q2 = tmpem.createQuery("SELECT dpi FROM DecisionProcessInstance dpi", DecisionProcessInstance.class);
			if (q2.getResultList().size() > 0) {
				for (DecisionProcessInstance tmpsi : q2.getResultList()) {
					if(tmpsi.getDptReference() == null) 
						System.out.println( tmpsi.getInstanceName() + " has no DPT Reference to delete.");
					else {
						tmpsi.setDptReference(null);
						System.out.println( tmpsi.getInstanceName() + "  DPI Reference deleted.");
					}	
					if(tmpsi.getStimInstReference() == null) 
						System.out.println( tmpsi.getInstanceName() + " has no SI Reference to delete.");
					else {
						tmpsi.setStimInstReference(null);
						System.out.println( tmpsi.getInstanceName() + "  SI Reference deleted.");
					}
					DatabaseManager.getInstance().persistType(tmpsi);
				}
			}
			else 
				System.out.println("No DPI to dereference");
			tmpem.close();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		/*lv_types.getSelectionModel().selectedItemProperty().addListener((obs, oldI, newI) -> {
			
			if (newI != null) {
				
				selectedItem = newI;
			}
		});    
		dpiInstances.setOnMouseClicked(new EventHandler<MouseEvent>() {
			  @Override
		        public void handle(MouseEvent event) {
		            System.out.println("clicked on " + dpiInstances.getSelectionModel().getSelectedItem());
		        }
		   });*/
		dpiInstances.setOnMouseClicked(new EventHandler<MouseEvent>(){
	    	  
	          @Override
	          public void handle(MouseEvent arg0) {
	             
	        	  InstanceSelected();
	          }
	 
	      });
		
		fillList();
	}

	private void fillList() {
		try {
			
			EntityType<DecisionProcessInstance> type = em.getMetamodel().entity(DecisionProcessInstance.class);
			if (type != null) {		
				TypedQuery<String> q1 = em.createQuery("SELECT dpi.instanceName FROM DecisionProcessInstance dpi", 
						String.class);
				if (q1.getResultList().size() > 0) {
					//ObservableList<DecisionProcessInstance> oListGetResult = FXCollections.observableList(q1.getResultList());
					//dpiInstances.setItems(oListGetResult);
					dpiInstances.getItems().addAll(q1.getResultList());
				}
				siName.setText("<No Selection>");
				siName.setTextFill(Color.web("#77b300")); //green
			}
			em.close();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
		
	//@FXML
	void InstanceSelected(/*MouseEvent event*/) {
		if (dpiInstances.getSelectionModel().getSelectedItem() != null) {
			selectedItem = dpiInstances.getSelectionModel().getSelectedItem();
			EntityManager emtmp = DatabaseManager.getInstance().getEmf().createEntityManager();
			//System.out.println(selectedItem);
			if(getDpi(emtmp, selectedItem)!=null) {
				/* if (!emtmp.isOpen())
					emtmp = DatabaseManager.getInstance().getEmf().createEntityManager();
				DecisionProcessInstance ddd = emtmp.find(DecisionProcessInstance.class, getDpi(emtmp, selectedItem).getId());
				emtmp.getTransaction().begin();
				ddd.setStimInstReference(null);
				emtmp.getTransaction().commit(); */ //Set SI Reference of DPI to null
				
				if (getDpi(emtmp, selectedItem).getStimInstReference()!= null) {
					siName.setText(getDpi(emtmp, selectedItem).getStimInstReference().getInstanceName());
					siName.setTextFill(Color.web("#000000")); //black
					noReference = false;
				}
				else {
					siName.setText("<No referenced Stimulus Instance>");
					siName.setTextFill(Color.web("#ff4d4d")); //red
					noReference = true;
					}
			}
			else {
				siName.setText("<ERROR>");
				siName.setTextFill(Color.web("#e68a00")); //orange
				dpiInstances.getSelectionModel().clearSelection();
				}
			}
		else {
			siName.setText("<No Selection>");
			siName.setTextFill(Color.web("#77b300")); //green
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
	
	private void deleteDPI(boolean noRfrnc, String selection) {
		EntityManager tmp = DatabaseManager.getInstance().getEmf().createEntityManager();
		DecisionProcessInstance d = getDpi(tmp, selection);
		if (noRfrnc) {
			
		}
		else {
			//Delete referenced SI, too 
			DatabaseManager.getInstance().remove(tmp.find(StimulusInstance.class, d.getStimInstReference().getID()));
		}
			
		DatabaseManager.getInstance().remove(d);
		tmp.close();
	}
	
	@FXML
	void btn_okAction(ActionEvent event){
		//TODO
		try {
			if (dpiInstances.getSelectionModel().getSelectedItem() != null) {
				selectedItem = dpiInstances.getSelectionModel().getSelectedItem();
				if(delete) {
					deleteDPI(noReference, selectedItem);
					AlertDialogFactory.createStandardInformationAlert("Information",
							"Database delete",
							"The Decision Process Instance " + selectedItem + " and his referenced Stimulus Instance, is deleted!");
					stage.close();
				}
				else {
					if(!noReference) { 
						EntityManager em = DatabaseManager.getInstance().getEmf().createEntityManager();
						IDpiTabController con = ControllerFactory.getDpiTabController(getDpi(em, selectedItem)
								, tPane, em);
						TabFactory.createAndShowDpiTab(Views.MSDECPROCTYPETAB, "Edit DPI", con, msc);
						msc.setVisibilityOfMainScreenElements(false);
						stage.close();
					}
					else {
						StimulusInstance si = new StimulusInstance();
						EntityManager cem = DatabaseManager.getInstance().getEmf().createEntityManager();
						IControllerTypeLevel con = ControllerFactory.getStimulusInstanceController(si, cem);
						/*getStimulusTypeController(dpt,
							lv_stimTypes.getSelectionModel().getSelectedItem(), em);*/
						con.setMsc(msc);
						si.setInitiatedDpi(getDpi(cem, selectedItem));
						StageFactory.createAndShowStage("Edit Stimulustype", false, Views.STIMULUSINSTANCE, con);
						stage.close();
					}	
				}
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
