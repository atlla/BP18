package controllertypelevel;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;

import controllerandstagefactory.ControllerFactory;
import controllerandstagefactory.StageFactory;
import database.DatabaseManager;
import database.QueriesNameCheck;
import decisionpremise.DecisionPremiseType;
import helpercomponents.Views;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.MainScreenController;
import supportrequirements.AnalysisNeedSatisfactionRelationType;
import supportrequirements.AnalysisNeedType;
import supportrequirements.InformationSystemType;
import supportrequirements.SupportRequirementType;

public class AnalysisNeedTypeController implements Initializable, ISuppReqTypeController {

	private AnalysisNeedType ant;
	private DecisionPremiseType decPremT;
	private EntityManager em;
	private Stage stage;
	private boolean onEdit;
	private String nameBeforeEdit;
	private AnalysisNeedSatisfactionRelationType selectedItem;

	@FXML
	private TextField tf_name;

	@FXML
	private TableView<AnalysisNeedSatisfactionRelationType> tv_addIsRel;

	@FXML
	private TableColumn<AnalysisNeedSatisfactionRelationType, String> tc_useReq;

	@FXML
	private TableColumn<AnalysisNeedSatisfactionRelationType, InformationSystemType> tc_is;

	@FXML
	private TextArea ta_description;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		tv_addIsRel.getSelectionModel().selectedItemProperty().addListener((observ, oldItem, newItem) -> {

			if (newItem != null) {

				selectedItem = newItem;
			}
		});

		tc_is.setCellValueFactory(new PropertyValueFactory<>("referencedInfoSystem"));
		tc_useReq.setCellValueFactory(new PropertyValueFactory<>("useRequired"));

		checkFields();
	}

	private void checkFields() {

		if (ant.getName() != null && ant.getDescription() != null) {

			fillTable();
			onEdit = true;
			nameBeforeEdit = ant.getName();
			tf_name.setText(ant.getName());
			ta_description.setText(ant.getDescription());

		} else {

			bindProperties();
		}
	}

	private void fillTable() {

		if (ant.getAnalSatRel().size() > 0) {

			tv_addIsRel.getItems().clear();
			tv_addIsRel.getItems().addAll(ant.getAnalSatRel());
		}
	}

	private void bindProperties() {

		ant.nameProperty().bind(tf_name.textProperty());
		ant.descriptionProperty().bind(ta_description.textProperty());
	}

	@FXML
	void btn_addIsRelAction(ActionEvent event) {

		try {
			IControllerTypeLevel con = ControllerFactory
					.getAnalysisNeedSatisfRelController(new AnalysisNeedSatisfactionRelationType(), ant, em);
			StageFactory.createAndShowStage("Add analysis need satisfaction relation"
					, false, Views.ANALYSISNEEDSATISFACTIONRELATIONTYPE, con);
			fillTable();
			
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@FXML
	void btn_delRel(ActionEvent event) {

		// if (selectedItem != null) {
		//
		// AnalysisNeedSatisfactionRelationType toRemove = null;
		// for (AnalysisNeedSatisfactionRelationType ansrt :
		// ant.getAnalSatRel()) {
		//
		// if
		// (ansrt.getReferencedInfoSystem().getName().equals(selectedItem.get))
		// {
		//
		// toRemove = ansrt;
		// break;
		// }
		// }
		// if (toRemove != null) {
		//
		// ant.getAnalSatRel().remove(toRemove);
		// toRemove.getReferencedInfoSystem().getAnaNeedSatisfRel().remove(toRemove);
		// DatabaseManager.getInstance().remove(toRemove);
		// em.refresh(ant);
		// }
		// }
	}

	@FXML
	void btn_saveAction(ActionEvent event) {

		if (!onEdit) {

			if (!ant.getName().equals("")) {

				if (QueriesNameCheck.SuppReqTypeNameCheck(ant.getName())) {

					decPremT.getSrt().add(ant);
					ant.getRaisedFromDecPremTypes().add(decPremT);
					stage.close();
				}
			}

		} else {

			if (!tf_name.getText().equals("")) {

				if (QueriesNameCheck.SuppReqTypeEditNameCheck(nameBeforeEdit, tf_name.getText())) {

					ant.setName(tf_name.getText());
					ant.setDescription(ta_description.getText());
					DatabaseManager.getInstance().merge(ant);
					em.refresh(ant);
					stage.close();
				}
			}
		}
	}

	public void setAnt(AnalysisNeedType ant) {

		this.ant = ant;
	}

	@Override
	public void setStage(Stage stage) {

		this.stage = stage;
	}

	public void setEm(EntityManager em) {

		this.em = em;
	}

	@Override
	public void setDecPremT(DecisionPremiseType decPremT) {

		this.decPremT = decPremT;
	}

	@Override
	public void setEntityManager(EntityManager em) {

		this.em = em;
	}

	@Override
	public void setSuppReqType(SupportRequirementType suppReq) {

		this.ant = (AnalysisNeedType) suppReq;
	}

	@Override
	public void setMsc(MainScreenController msc) {
		// TODO Auto-generated method stub
		
	}

}
