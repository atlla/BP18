package controllertypelevel;

import java.net.URL;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import decisionpremise.DecisionPremiseType;
import decisionpremise.FactualDecisionPremiseType;
import decisionpremise.PresumedInfluenceRelationType;
import decisionpremise.ValueDecisionPremiseType;
import helpercomponents.AlertDialogFactory;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import main.MainScreenController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

public class PresumedInfluenceRelController implements IControllerTypeLevel, Initializable {

	private EntityManager em;
	private Stage stage;
	private PresumedInfluenceRelationType pirt;
	private FactualDecisionPremiseType facDecPremT;
	private ValueDecisionPremiseType selectedItem;

	@FXML
	private ChoiceBox<String> cb_interDep;

	@FXML
	private TextArea ta_justification;

	@FXML
	private TableView<ValueDecisionPremiseType> tv_influence;

	@FXML
	private TableColumn<ValueDecisionPremiseType, String> tc_type;

	@FXML
	private TableColumn<ValueDecisionPremiseType, String> tc_vdpName;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		tv_influence.getSelectionModel().selectedItemProperty().addListener((observ, oldVal, newVal) -> {
			
			if (newVal != null) {
				
				selectedItem = newVal;
			}
		});
		
		tc_vdpName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tc_type.setCellValueFactory(new PropertyValueFactory<>("type"));
		
		bindProperties();
	}

	private void bindProperties() {

		checkFields();
		fillTable();
		pirt.interdependenceProperty().bind(cb_interDep.getSelectionModel().selectedItemProperty());
		pirt.justificationProperty().bind(ta_justification.textProperty());
	}
	
	private void checkFields() {

		if (pirt.getInterdependence() != null && pirt.getJustification() != null) {

			ta_justification.setText(pirt.getJustification());
			cb_interDep.getSelectionModel().select(pirt.getInterdependence());
		}
	}

	private void fillTable() {

		try {

			TypedQuery<ValueDecisionPremiseType> q1 = em.createQuery("SELECT vdpt FROM ValueDecisionPremiseType vdpt",
					ValueDecisionPremiseType.class);
			if (q1.getResultList().size() > 0) {

				tv_influence.getItems().addAll(q1.getResultList());
				if (pirt.getInfluenceOnVdp() != null) {

					if (tv_influence.getItems().contains(pirt.getInfluenceOnVdp())) {

						tv_influence.getSelectionModel().select(pirt.getInfluenceOnVdp());
						selectedItem = tv_influence.getSelectionModel().getSelectedItem();
					}
				}
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	@FXML
	void btn_saveAction(ActionEvent event) {
		
		if (selectedItem != null) {
			
			if (typeCheck()) {
				
				facDecPremT.getPresInfluRel().add(pirt);
				pirt.setFdpt(facDecPremT);
				pirt.setInfluenceOnVdp(selectedItem);
				stage.close();
			}
		} else {
			
			AlertDialogFactory.createStandardInformationAlert("Information", "Save error", "No value decision premise selected");
		}
	}
	
	private boolean typeCheck() {
		
		for (PresumedInfluenceRelationType pirt : facDecPremT.getPresInfluRel()) {
			
			if (pirt.getInfluenceOnVdp().getName().equals(selectedItem.getName())) {
				
				AlertDialogFactory.createStandardInformationAlert("Information"
						, "Save error"
						, "Type already added");
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public void setEntityManager(EntityManager em) {

		this.em = em;
	}

	@Override
	public void setStage(Stage stage) {

		this.stage = stage;
	}

	public void setPirt(PresumedInfluenceRelationType pirt) {

		this.pirt = pirt;
	}

	public void setFacDecPremT(FactualDecisionPremiseType facDecPremT) {

		this.facDecPremT = facDecPremT;
	}

	@Override
	public void setMsc(MainScreenController msc) {
		// TODO Auto-generated method stub
		
	}

}
