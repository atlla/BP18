package controllertypelevel;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import controllerandstagefactory.ControllerFactory;
import controllerandstagefactory.StageFactory;
import database.DatabaseManager;
import decisionpremise.DecisionPremiseType;
import helpercomponents.TooltipFactory;
import helpercomponents.Views;
import javafx.event.ActionEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import supportrequirements.AnalysisNeedType;
import supportrequirements.SupportRequirementType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

//Diese Instanzen dieser Klasse werden von den DecisionPremise Controllern benutzt
//Hier werden einige Funktionalitäten (Eingabe SupportRequirements) in einem Objekt gekapselt um Redundanzen im Code zu vermeiden
//(Bei 5 DecPrem Controller Klassen wäre 5 mal dieser Code hier zu implementieren!)
public class SuppReqHelperController implements Initializable {

	private DecisionPremiseType decPremT;
	private EntityManager em;
	
	@FXML
	private TableView<SupportRequirementType> tv_addedSuppReqT;

	@FXML
	private TableColumn<SupportRequirementType, String> tc_addedName;

	@FXML
	private TableColumn<SupportRequirementType, String> tc_addedType;

	@FXML
	private TableView<SupportRequirementType> tv_availSuppReqT;

	@FXML
	private TableColumn<SupportRequirementType, String> tc_availName;

	@FXML
	private TableColumn<SupportRequirementType, String> tc_availType;
	
	@FXML
    private ChoiceBox<String> cb_SuppReqTypeToAdd;
	
	@FXML
	private Button btn_addSuppReqToList;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		tc_addedName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tc_addedType.setCellValueFactory(new PropertyValueFactory<>("type"));

		tc_availName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tc_availType.setCellValueFactory(new PropertyValueFactory<>("type"));
		
		btn_addSuppReqToList.setTooltip(TooltipFactory.createTooltip("Add available support requirement to added supp. req. list", true));
	}
	
    public void fillAddedTable() {

		if (decPremT.getSrt().size() > 0) {

			tv_addedSuppReqT.getItems().clear();
			tv_addedSuppReqT.getItems().addAll(decPremT.getSrt());
		}
	}

	public void fillAvailTable() {
		
		if (tv_availSuppReqT.getItems().size() > 0) {
			
			tv_availSuppReqT.getItems().clear();
		}
		
		if (tv_addedSuppReqT.getItems().size() > 0) {
			
			List<SupportRequirementType> tmpList = getSuppReqList();
			if (tmpList != null) {

				for (SupportRequirementType srt : tmpList) {

					int i = 0;
					for (SupportRequirementType srt2 : tv_addedSuppReqT.getItems()) {

						if (srt2.getName().equals(srt.getName())) {

							break;
						} else {

							i++;
						}
					}
					if (i == tv_addedSuppReqT.getItems().size()) {

						tv_availSuppReqT.getItems().add(srt);
					}
				}
			}
		} else {

			List<SupportRequirementType> tmpList = getSuppReqList();
			if (tmpList != null) {

				tv_availSuppReqT.getItems().clear();
				tv_availSuppReqT.getItems().addAll(tmpList);
			}
		}
	}

	private List<SupportRequirementType> getSuppReqList() {
		
		TypedQuery<SupportRequirementType> q1 = em.createQuery("SELECT srt FROM SupportRequirementType srt",
				SupportRequirementType.class);
		if (!q1.getResultList().isEmpty()) {

			return q1.getResultList();
		} else {

			return null;
		}
	}
    
    @FXML
    void btn_addToAddedSuppReq(ActionEvent event) {

    	if (tv_availSuppReqT.getSelectionModel().getSelectedItem() != null) {
    		
    		SupportRequirementType selected = tv_availSuppReqT.getSelectionModel().getSelectedItem();
    		tv_addedSuppReqT.getItems().add(selected);
    		decPremT.getSrt().add(selected);
    		selected.getRaisedFromDecPremTypes().add(decPremT);
    		fillAvailTable();
    	}
    }

    @FXML
    void btn_delAvailSuppReq(ActionEvent event) {
    	
    	//Später
    }

    @FXML
    void btn_editAvailSuppReq(ActionEvent event) {
    	if (tv_availSuppReqT.getSelectionModel().getSelectedItem() != null) {
    		
    		SupportRequirementType selected = tv_availSuppReqT.getSelectionModel().getSelectedItem();
        	try {
    			
    			IControllerTypeLevel con = ControllerFactory.getSuppReqTypeController(selected, decPremT, em);
    			StageFactory.createAndShowStage("Add Support requirement type", false, Views.ANALYSISNEEDTYPE, con);
    			em.refresh(selected);
    		} catch (Exception e) {
    			
    			e.printStackTrace();
    		}
    	}
    }

    @FXML
    void btn_addAvailSuppReq(ActionEvent event) {
    	
    	try {

			if (cb_SuppReqTypeToAdd.getSelectionModel().getSelectedItem() != null) {
				
				IControllerTypeLevel con = ControllerFactory.getSuppReqTypeController(getTypeToAdd(), decPremT, em);
				StageFactory.createAndShowStage("Add Support requirement type", false, Views.ANALYSISNEEDTYPE, con);
				fillAddedTable();
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
    }

    @FXML
    void btn_editAddedSuppReq(ActionEvent event) {
    	
    	if (tv_addedSuppReqT.getSelectionModel().getSelectedItem() != null) {
    		
    		SupportRequirementType selected = tv_addedSuppReqT.getSelectionModel().getSelectedItem();
    		try {
				
				IControllerTypeLevel con = ControllerFactory.getSuppReqTypeController(selected, decPremT, em);
				StageFactory.createAndShowStage("Edit Support requirement type", false, Views.ANALYSISNEEDTYPE, con);
				em.refresh(selected);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
    	}
    }

    @FXML
    void btn_delAddedSuppReq(ActionEvent event) {
    	
    	System.out.println("DELETE");
    	if (tv_addedSuppReqT.getSelectionModel().getSelectedItem() != null) {
    		
    		SupportRequirementType selected = tv_addedSuppReqT.getSelectionModel().getSelectedItem();
    		tv_addedSuppReqT.getItems().remove(selected);
    		selected.getRaisedFromDecPremTypes().remove(decPremT);
    		decPremT.getSrt().remove(selected);
    		DatabaseManager.getInstance().merge(selected);
    		fillAvailTable();
    	}
    }

	private SupportRequirementType getTypeToAdd() {

		if (cb_SuppReqTypeToAdd.getSelectionModel().getSelectedItem().equals("Analysis need")) {

			return new AnalysisNeedType();
		} else {

			return null;
		}
	}
	
	public DecisionPremiseType getDecPremT() {

		return decPremT;
	}

	public void setDecPremT(DecisionPremiseType decPremT) {

		this.decPremT = decPremT;
	}

	public void setTv_availSuppReqT(TableView<SupportRequirementType> tv_availSuppReqT) {

		this.tv_availSuppReqT = tv_availSuppReqT;
	}

	public EntityManager getEm() {

		return em;
	}

	public void setEm(EntityManager em) {

		this.em = em;
	}

}
