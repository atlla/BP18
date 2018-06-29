package controllertypelevel;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;

import controllerandstagefactory.ControllerFactory;
import controllerandstagefactory.StageFactory;
import database.DatabaseManager;
import database.QueriesNameCheck;
import decisionpremise.RelevanceRelationType;
import decisionprocess.DecisionProcessType;
import decisionprocess.StimulusType;
import helpercomponents.AlertDialogFactory;
import helpercomponents.Views;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import organizationalunits.ParticipationRelationType;

public class DptTabController implements Initializable, IDptTabController {

	// Model
	private DecisionProcessType dpt;
	private Tab tab;
	private TabPane tPane;
	private EntityManager em;
	private boolean onEdit;
	private String nameBeforeEdit;
	private List<Object> relationsToRemove = new ArrayList<>();

	@FXML
	private TextField tf_name;

	@FXML
	private TextArea tf_generalAim;

	@FXML
	private ChoiceBox<String> cb_activityFocus;

	@FXML
	private ChoiceBox<String> cb_presumedImpact;

	@FXML
	private ListView<StimulusType> lv_stimTypes;

	@FXML
	private ListView<ParticipationRelationType> lv_partTypes;

	@FXML
	private ListView<RelevanceRelationType> lv_relDecPrem;

	// Wird beim Erstellen des Controllers aufgerufen
	// Controller wird erzeugt wenn per FXML loader eine View geladen wird aus
	// FXML Datei
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Binding der DPT Properties an die View Element Properties. So bleiben
		// Model und View in Synchronisation
		// Change in der View bedingt automatisch Change der "gebundenen"
		// Property im Model
		bindProperties();
		// lv_stimulusTypes.setItems(dpt.getInitiatingStimulusTypes());
	}

	private void bindProperties() {

		checkFields();
		dpt.nameProperty().bind(tf_name.textProperty());
		dpt.generalAimProperty().bind(tf_generalAim.textProperty());
		dpt.commonActivityFocusProperty().bind(cb_activityFocus.getSelectionModel().selectedItemProperty());
		dpt.presumedImpactProperty().bind(cb_presumedImpact.getSelectionModel().selectedItemProperty());
	}

	private void checkFields() {

		if (dpt.getName() != null && dpt.getGeneralAim() != null) {

			onEdit = true;
			nameBeforeEdit = dpt.getName();
			tf_name.setText(dpt.getName());
			tf_generalAim.setText(dpt.getGeneralAim());
			cb_activityFocus.getSelectionModel().select(dpt.getCommonActivityFocus());
			cb_presumedImpact.getSelectionModel().select(dpt.getPresumedImpact());

			fillLists();
		}
	}

	private void fillLists() {

		if (!dpt.getInitiatingStimulusType().isEmpty()) {

			lv_stimTypes.getItems().addAll(dpt.getInitiatingStimulusType());
		}

		if (!dpt.getPartRelations().isEmpty()) {

			lv_partTypes.getItems().addAll(dpt.getPartRelations());
		}

		if (!dpt.getRelRelations().isEmpty()) {

			lv_relDecPrem.getItems().addAll(dpt.getRelRelations());
		}
	}

	// DPT in Datenbank schreiben
	@FXML
	void btn_saveAction(ActionEvent event) {

		if (!(dpt.getName().equals(""))) {

			if (!(dpt.getInitiatingStimulusType().size() == 0)) {

				if (!onEdit) {

					if (QueriesNameCheck.dptNameCheck(dpt.getName())) {

						DatabaseManager.getInstance().persistDpt(dpt, em);
						tPane.getTabs().remove(tab);

					} else {

						AlertDialogFactory.createStandardInformationAlert("Information",
								"Database persist error (duplicate name)",
								"A decision process type with the same name" + ", already exists in database!");
					}

				} else {

					if (QueriesNameCheck.dptEditNameCheck(nameBeforeEdit, dpt.getName())) {

						removeRelations();
						DatabaseManager.getInstance().persistDpt(dpt, em);
						tPane.getTabs().remove(tab);

					} else {

						AlertDialogFactory.createStandardInformationAlert("Information",
								"Database persist error (duplicate name)",
								"A decision process type with the same name" + ", already exists in database!");
					}
				}
			} else {

				AlertDialogFactory.createStandardInformationAlert("Information", "Database persist error",
						"No stimulus type added! (At least one)");
			}
		} else {

			AlertDialogFactory.createStandardInformationAlert("Information", "Database persist error",
					"No name entered for decision process type!");
		}

	}

	private void removeRelations() {

		if (relationsToRemove.size() > 0) {

			for (Object o : relationsToRemove) {

				DatabaseManager.getInstance().remove(o);
			}
		}
	}

	// Button actions zum Hinzufügen/Editieren/Löschen von Stimulustypen
	@FXML
	void btn_addStimType(ActionEvent event) throws IOException {

		try {

			IControllerTypeLevel con = ControllerFactory.getStimulusTypeController(dpt, new StimulusType(), em);
			StageFactory.createAndShowStage("Add Stimulustype", false, Views.STIMULUSTYPE, con);

			if (dpt.getInitiatingStimulusType().size() > 0) {

				lv_stimTypes.getItems().clear();
				lv_stimTypes.getItems().addAll(dpt.getInitiatingStimulusType());
			}

		} catch (MalformedURLException e) {

			e.printStackTrace();
		}
	}

	@FXML
	void btn_delStimType(ActionEvent event) {

		if (lv_stimTypes.getItems().size() > 0 && !lv_stimTypes.getSelectionModel().isEmpty()) {

			dpt.getInitiatingStimulusType().remove(lv_stimTypes.getSelectionModel().getSelectedItem());
			lv_stimTypes.getItems().clear();
			if (dpt.getInitiatingStimulusType().size() > 0) {

				lv_stimTypes.getItems().addAll(dpt.getInitiatingStimulusType());
			}
		}
	}

	@FXML
	void btn_editStimType(ActionEvent event) throws IOException {

		if (lv_stimTypes.getItems().size() > 0 && !lv_stimTypes.getSelectionModel().isEmpty()) {

			try {

				IControllerTypeLevel con = ControllerFactory.getStimulusTypeController(dpt,
						lv_stimTypes.getSelectionModel().getSelectedItem(), em);
				StageFactory.createAndShowStage("Edit Stimulustype", false, Views.STIMULUSTYPE, con);

				if (dpt.getInitiatingStimulusType().size() > 0) {

					lv_stimTypes.getItems().clear();
					lv_stimTypes.getItems().addAll(dpt.getInitiatingStimulusType());
				}

			} catch (MalformedURLException e) {

				e.printStackTrace();
			}
		}
	}

	// Stimulustypen Buttons Ende

	// Participation Button actions
	@FXML
	void btn_addPartRelType(ActionEvent event) {

		try {

			IControllerTypeLevel con = ControllerFactory.getParticipationRelTypeController(new ParticipationRelationType(), dpt,
					em);
			StageFactory.createAndShowStage("Add participation relation", false, Views.PARTICIPATIONRELATIONTYPE, con);

			if (dpt.getPartRelations().size() > 0) {

				lv_partTypes.getItems().clear();
				lv_partTypes.getItems().addAll(dpt.getPartRelations());
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@FXML
	void btn_editPartType(ActionEvent event) {

		if (lv_partTypes.getSelectionModel().getSelectedItem() != null) {

			try {

				IControllerTypeLevel con = ControllerFactory
						.getParticipationRelTypeController(lv_partTypes.getSelectionModel().getSelectedItem(), dpt, em);
				StageFactory.createAndShowStage("Edit participation relation type", false,
						Views.PARTICIPATIONRELATIONTYPE, con);

				fillPartTypeList();
			} catch (Exception e) {

				e.printStackTrace();
			}
		} else {

			AlertDialogFactory.createStandardInformationAlert("Information", "Edit error",
					"No index selected for editing");
		}
	}

	private void fillPartTypeList() {

		if (dpt.getPartRelations().size() > 0) {

			lv_partTypes.getItems().clear();
			lv_partTypes.getItems().addAll(dpt.getPartRelations());
		}
	}

	@FXML
	void btn_delPartType(ActionEvent event) {

		if (lv_partTypes.getSelectionModel().getSelectedItem() != null) {

			ParticipationRelationType tmp = lv_partTypes.getSelectionModel().getSelectedItem();
			tmp.getOut().getPartRelations().remove(tmp);
			dpt.getPartRelations().remove(tmp);
			lv_partTypes.getItems().remove(tmp);
			relationsToRemove.add(tmp);
		}
	}
	// Participation Buttons Ende

	// Relevance Relation Buttons
	@FXML
	void btn_addRelevanceRelType(ActionEvent event) {

		try {

			IControllerTypeLevel con = ControllerFactory.getRelevanceRelTypeController(new RelevanceRelationType(), dpt, em);
			StageFactory.createAndShowStage("Add relevance relation type", true, Views.RELEVANCERELATIONTYPE, con);

			if (dpt.getRelRelations().size() > 0) {

				lv_relDecPrem.getItems().clear();
				lv_relDecPrem.getItems().addAll(dpt.getRelRelations());
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@FXML
	void btn_editRelevanceRel(ActionEvent event) {

		if (lv_relDecPrem.getSelectionModel().getSelectedItem() != null) {

			try {

				IControllerTypeLevel con = ControllerFactory
						.getRelevanceRelTypeController(lv_relDecPrem.getSelectionModel().getSelectedItem(), dpt, em);
				StageFactory.createAndShowStage("Add relevance relation type", true, Views.RELEVANCERELATIONTYPE, con);

				if (dpt.getRelRelations().size() > 0) {

					lv_relDecPrem.getItems().clear();
					lv_relDecPrem.getItems().addAll(dpt.getRelRelations());
				}
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
	}

	@FXML
	void btn_delRelevanceRel(ActionEvent event) {

		if (lv_relDecPrem.getSelectionModel().getSelectedItem() != null) {

			RelevanceRelationType tmp = lv_relDecPrem.getSelectionModel().getSelectedItem();
			tmp.getDecPremType().getRelRelation().remove(tmp);
			dpt.getRelRelations().remove(tmp);
			lv_relDecPrem.getItems().remove(tmp);
			relationsToRemove.add(tmp);
		}
	}
	// Relevance Relation Buttons Ende

	/////////////// Getter/Setter ////////////////
	public DecisionProcessType getDpt() {

		return dpt;
	}

	public void setDpt(DecisionProcessType dpt) {

		this.dpt = dpt;
	}

	public TextField getTf_name() {

		return tf_name;
	}

	public ChoiceBox<String> getCb_activityFocus() {

		return cb_activityFocus;
	}

	public ChoiceBox<String> getCb_presumedImpact() {

		return cb_presumedImpact;
	}

	public EntityManager getEm() {

		return em;
	}

	// public DptTabController(DecisionProcessType dpt) {
	//
	// this.dpt = dpt;
	// System.out.println("Controller gestartet?");
	// }

	@Override
	public void setEntityManager(EntityManager em) {

		this.em = em;
	}

	@Override
	public void setStage(Stage stage) {

	}

	@Override
	public void setTabPane(TabPane tPane) {

		this.tPane = tPane;
	}

	@Override
	public void setDptTab(Tab tab) {

		this.tab = tab;
	}

	@Override
	public TabPane getTpane() {

		return this.tPane;
	}
}
