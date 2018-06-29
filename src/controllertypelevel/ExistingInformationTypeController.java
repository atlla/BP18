package controllertypelevel;

import java.net.URL;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import helpercomponents.AlertDialogFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import supportrequirements.InformationType;

public class ExistingInformationTypeController implements Initializable, IControllerTypeLevel {

	private EntityManager em;
	private Stage stage;
	private ListView<InformationType> tmpLv;
	private boolean editRequest;

	@FXML
	private ListView<InformationType> lv_infoType;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		try {

			TypedQuery<InformationType> q1 = em.createQuery("SELECT it FROM InformationType it", InformationType.class);
			if (q1.getResultList().size() > 0) {

				lv_infoType.getItems().addAll(q1.getResultList());
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	@FXML
	void btn_saveAction(ActionEvent event) {
		
		InformationType itTmp = lv_infoType.getSelectionModel().getSelectedItem();
		
		if (itTmp != null) {
			
			if (!tmpLv.getItems().contains(itTmp)) {
				
				tmpLv.getItems().add(itTmp);
				stage.close();
			} else {
				
				AlertDialogFactory.createStandardInformationAlert("Information"
						, "Save error"
						, "Information type already added!");
			}
		}
	}

	public void setEm(EntityManager em) {

		this.em = em;
	}

	public void setStage(Stage stage) {

		this.stage = stage;
	}

	public void setTmpLv(ListView<InformationType> tmpLv) {
		this.tmpLv = tmpLv;
	}

	@Override
	public void setEntityManager(EntityManager em) {

		this.em = em;
	}

}
