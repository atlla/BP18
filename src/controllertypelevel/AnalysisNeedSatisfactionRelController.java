package controllertypelevel;

import java.net.URL;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import main.MainScreenController;
import supportrequirements.AnalysisNeedSatisfactionRelationType;
import supportrequirements.AnalysisNeedType;
import supportrequirements.InformationSystemType;
import supportrequirements.InformationType;

public class AnalysisNeedSatisfactionRelController implements Initializable, IControllerTypeLevel {

	private AnalysisNeedSatisfactionRelationType ansrt;
	private AnalysisNeedType ant;
	private EntityManager em;
	private Stage stage;

	@FXML
	private ChoiceBox<String> cb_useReq;

	@FXML
	private ListView<InformationSystemType> lv_chooseIs;

	@FXML
	private ListView<InformationType> lv_provInfo;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		ansrt.useRequiredProperty().bind(cb_useReq.getSelectionModel().selectedItemProperty());
		try {

			TypedQuery<InformationSystemType> q1 = em.createQuery("SELECT ist FROM InformationSystemType ist",
					InformationSystemType.class);
			if (q1.getResultList().size() > 0) {

				lv_chooseIs.getItems().addAll(q1.getResultList());
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		lv_chooseIs.getSelectionModel().selectedItemProperty().addListener((observ, oldIst, newIst) -> {

			if (newIst != null) {

				lv_provInfo.getItems().clear();
				if (lv_chooseIs.getSelectionModel().getSelectedItem().getProvidesInfos().size() > 0) {

					lv_provInfo.getItems().addAll(lv_chooseIs.getSelectionModel().getSelectedItem().getProvidesInfos());
				}
			}
		});
	}

	@FXML
	void btn_saveAction(ActionEvent event) {

		InformationSystemType tmp = lv_chooseIs.getSelectionModel().getSelectedItem();

		if (tmp != null) {

			if (checkAdded()) {
				
				ansrt.setReferencedInfoSystem(tmp);
				ansrt.setReferencedAnalysisNeed(ant);
				tmp.getAnaNeedSatisfRel().add(ansrt);
				ant.getAnalSatRel().add(ansrt);
				stage.close();
			}
		}
	}
	
	private boolean checkAdded() {
		
		if (lv_chooseIs.getSelectionModel().getSelectedItem() != null) {
			
			InformationSystemType tmp = lv_chooseIs.getSelectionModel().getSelectedItem();
			
			for (AnalysisNeedSatisfactionRelationType ansrt : ant.getAnalSatRel()) {
				
				if (ansrt.getReferencedInfoSystem().getName().equals(tmp.getName())) {
					
					return false;
				}
			}
		}
		return true;
	}
	
	public void setAnsrt(AnalysisNeedSatisfactionRelationType ansrt) {

		this.ansrt = ansrt;
	}

	public void setAnt(AnalysisNeedType ant) {

		this.ant = ant;
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

	@Override
	public void setMsc(MainScreenController msc) {
		// TODO Auto-generated method stub
		
	}

}