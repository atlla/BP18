package controllertypelevel;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.objectdb.o.CBD;

import controllerandstagefactory.ControllerFactory;
import controllerandstagefactory.StageFactory;
import database.DatabaseManager;
import database.QueriesNameCheck;
import helpercomponents.AlertDialogFactory;
import helpercomponents.Views;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.MainScreenController;
import supportrequirements.InformationSystemType;
import supportrequirements.InformationType;
import supportrequirements.SupportRequirementType;

public class InformationSystemTypeController implements Initializable, IControllerTypeLevel {

	private InformationSystemType ist;
	private boolean onEdit;
	private EntityManager em;
	private Stage stage;

	@FXML
	private TextField tf_name;

	@FXML
	private TextArea ta_description;

	@FXML
	private ListView<InformationType> lv_provInfo;

	@FXML
	private ListView<InformationType> lv_existingInfoT;

	@FXML
	private ChoiceBox<String> cb_AddInfo;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		checkFields();
	}

	private void checkFields() {

		if (ist.getName() != null && ist.getDescription() != null) {
			
			fillProvidedInfoTable();
			fillExistingInfoList();
			onEdit = true;
			tf_name.setText(ist.getName());
			ta_description.setText(ist.getDescription());
			lv_provInfo.getItems().addAll(ist.getProvidesInfos());

		} else {
			
			fillExistingInfoList();
			bindProperties();
		}
	}

	private void fillProvidedInfoTable() {

		if (ist.getProvidesInfos().size() > 0) {

			lv_provInfo.getItems().addAll(ist.getProvidesInfos());
		}
	}

	private void bindProperties() {

		ist.nameProperty().bind(tf_name.textProperty());
		ist.descriptionProperty().bind(ta_description.textProperty());
	}

//	private void fillExistingInfoTable() {
//
//		TypedQuery<InformationType> q1 = em.createQuery("SELECT it FROM InformationType it", InformationType.class);
//		if (q1.getResultList().size() > 0) {
//
//			lv_existingInfoT.getItems().clear();
//			lv_existingInfoT.getItems().addAll(q1.getResultList());
//		}
//	}

	private void fillExistingInfoList() {

		if (lv_existingInfoT.getItems().size() > 0) {

			lv_existingInfoT.getItems().clear();
		}

		if (lv_provInfo.getItems().size() > 0) {

			List<InformationType> tmpList = getInfoTypeList();
			if (tmpList != null) {

				for (InformationType srt : tmpList) {

					int i = 0;
					for (InformationType srt2 : lv_provInfo.getItems()) {

						if (srt2.getName().equals(srt.getName())) {

							break;
						} else {

							i++;
						}
					}
					if (i == lv_provInfo.getItems().size()) {

						lv_existingInfoT.getItems().add(srt);
					}
				}
			}
		} else {

			List<InformationType> tmpList = getInfoTypeList();
			if (tmpList != null) {

				lv_existingInfoT.getItems().clear();
				lv_existingInfoT.getItems().addAll(tmpList);
			}
		}
	}

	private List<InformationType> getInfoTypeList() {
		
		TypedQuery<InformationType> q1 = em.createQuery("SELECT it FROM InformationType it", InformationType.class);
		if (q1.getResultList().size() > 0) {

			return q1.getResultList();
		} else {
			
			return null;
		}
	}

	private void unbindProperties() {

		ist.nameProperty().unbind();
		ist.descriptionProperty().unbind();
	}

	@FXML
	void btn_existingInfoAdd(ActionEvent event) {

		try {

			EntityManager em = DatabaseManager.getInstance().getEmf().createEntityManager();
			IControllerTypeLevel con = ControllerFactory.getNewInformationTypeController(new InformationType(), em);
			StageFactory.createAndShowStage("Add information type", false, Views.NEWINFORMATIONTYPE, con);
			fillExistingInfoList();
			
			if (em.isOpen()) {

				em.close();
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@FXML
	void btn_existingInfoEdit(ActionEvent event) {

		if (lv_existingInfoT.getSelectionModel().getSelectedItem() != null) {

			try {

				EntityManager em = DatabaseManager.getInstance().getEmf().createEntityManager();
				IControllerTypeLevel con = ControllerFactory
						.getNewInformationTypeController(lv_existingInfoT.getSelectionModel().getSelectedItem(), em);
				StageFactory.createAndShowStage("Edit information type", false, Views.NEWINFORMATIONTYPE, con);

				if (em.isOpen()) {

					em.close();
				}
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
	}

	@FXML
	void btn_prvonfoEdit(ActionEvent event) {

		if (lv_provInfo.getSelectionModel().getSelectedItem() != null) {

			try {

				EntityManager em = DatabaseManager.getInstance().getEmf().createEntityManager();
				IControllerTypeLevel con = ControllerFactory
						.getNewInformationTypeController(lv_provInfo.getSelectionModel().getSelectedItem(), em);
				StageFactory.createAndShowStage("Edit information type", false, Views.NEWINFORMATIONTYPE, con);

				if (em.isOpen()) {

					em.close();
				}
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
	}

	@FXML
	void btn_addInfoTypeToProvList(ActionEvent event) {
		
		if (lv_existingInfoT.getSelectionModel().getSelectedItem() != null) {
			
			InformationType tmp = lv_existingInfoT.getSelectionModel().getSelectedItem();
			lv_provInfo.getItems().add(tmp);
			tmp.getInfoSys().add(ist);
			ist.getProvidesInfos().add(tmp);
			fillExistingInfoList();
		}
	}

	@FXML
	void btn_provInfoDel(ActionEvent event) {

		InformationType tmp = lv_provInfo.getSelectionModel().getSelectedItem();
		if (tmp != null) {

			lv_provInfo.getItems().remove(tmp);
			tmp.getInfoSys().remove(ist);
			ist.getProvidesInfos().remove(tmp);
			DatabaseManager.getInstance().merge(tmp);
			fillExistingInfoList();
		}
	}

	@FXML
	void btn_saveAction(ActionEvent event) {

		if (!tf_name.getText().equals("")) {

			if (!onEdit) {
				
				if (QueriesNameCheck.InformationSystemTypeNameCheck(tf_name.getText())) {
					
					DatabaseManager.getInstance().persistType(ist);
					stage.close();
				}
			} else {
				
				ist.setName(tf_name.getText());
				ist.setDescription(ta_description.getText());
			}
		}
	}

	private void saveNonEdit() {

		if (!ist.getName().equals("")) {

			if (checkName(ist.getName())) {

				persistAndClose();
			}
		} else {

			AlertDialogFactory.createStandardInformationAlert("Information", "Persist error", "Name field is empty");
		}
	}

	private void saveEdit() {

		if (!tf_name.getText().equals("")) {

			if (QueriesNameCheck.InformationSystemTypeNameCheck(tf_name.getText())) {

				ist.setName(tf_name.getText());
				ist.setDescription(ta_description.getText());
				persistAndClose();
			}
		} else {

			AlertDialogFactory.createStandardInformationAlert("Information", "Persist error", "Name field is empty");
		}
	}

	private void persistAndClose() {

		addReferences();
		em.persist(ist);
		for (InformationType it : ist.getProvidesInfos()) {

			em.persist(it);
		}
		em.getTransaction().commit();
		em.close();
		stage.close();
	}

	private void addReferences() {

		ist.getProvidesInfos().addAll(lv_provInfo.getItems());
		for (InformationType it : lv_provInfo.getItems()) {

			it.getInfoSys().add(ist);
		}
	}

	private boolean checkName(String name) {

		if (QueriesNameCheck.InformationSystemTypeNameCheck(name)) {

			return true;
		}

		AlertDialogFactory.createStandardInformationAlert("Information", "Persist error",
				"Informationsystem with same name already exists in database!");
		return false;
	}

	public void setIst(InformationSystemType ist) {

		this.ist = ist;
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
