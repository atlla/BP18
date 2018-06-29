package controllertypelevel;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.metamodel.EntityType;
import com.sun.javafx.sg.prism.NGExternalNode;
import controllerandstagefactory.ControllerFactory;
import controllerandstagefactory.TabFactory;
import database.DatabaseManager;
import decisionprocess.DecisionProcessType;
import helpercomponents.AlertDialogFactory;
import helpercomponents.Views;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class ChooseDptEditController implements Initializable, IControllerTypeLevel {

	private EntityManager em;
	private TabPane tPane;
	private Stage stage;
	private String selectedItem;

	@FXML
	private ListView<String> lv_types;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		lv_types.getSelectionModel().selectedItemProperty().addListener((obs, oldI, newI) -> {
			
			if (newI != null) {
				
				selectedItem = newI;
			}
		});
		fillList();
	}

	private void fillList() {

		try {
			
			EntityType<DecisionProcessType> type = em.getMetamodel().entity(DecisionProcessType.class);
			if (type != null) {
				
				TypedQuery<String> q1 = em.createQuery("SELECT dpt.name FROM DecisionProcessType dpt", String.class);
				if (q1.getResultList().size() > 0) {

					lv_types.getItems().addAll(q1.getResultList());
				}
			}
			
			em.close();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	@FXML
	void btn_okAction(ActionEvent event) throws IOException {

		if (lv_types.getSelectionModel().getSelectedItem() != null) {
			
			EntityManager em = DatabaseManager.getInstance().getEmf().createEntityManager();
			IDptTabController con = ControllerFactory.getDptTabController(getDpt(em, selectedItem)
					, tPane, em);
			TabFactory.createAndShowDptTab(Views.MSDECPROCTYPETAB, "New DPT", con);
			stage.close();
//			try {

//				FXMLLoader loader = new FXMLLoader(new File(Views.MSDECPROCTYPETAB).toURI().toURL());
//				DptTabController con = new DptTabController();
//				con.setTab(tab);
//				con.settPane(tPane);
//				EntityManager em = DecisionProcessManager.getInstance().getEmf().createEntityManager();
//				// em.getTransaction().begin();
//				con.setEm(em);
//				con.setDpt(getDpt(em, lv_types.getSelectionModel().getSelectedItem()));
//				loader.setController(con);
//				tab.setContent(loader.load());
//				tPane.getSelectionModel().select(tab);
//				stage.close();

//			} catch (MalformedURLException e) {
//
//				e.printStackTrace();
//			} catch (IOException e) {
//
//				e.printStackTrace();
//			}

//			tPane.getTabs().add(tab);
//			// tabPane.getSelectionModel().select(tab);
//			System.out.println("neues Tab?");
		}
	}

	private DecisionProcessType getDpt(EntityManager em, String dptName) {
		
		try {
			
			TypedQuery<DecisionProcessType> q1 = em.createQuery("SELECT dpt FROM DecisionProcessType dpt WHERE dpt.name = '" + dptName + "'"
					, DecisionProcessType.class);
			return q1.getResultList().get(0);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}

	public void settPane(TabPane tPane) {

		this.tPane = tPane;
	}
	
	@Override
	public void setStage(Stage stage) {

		this.stage = stage;
	}

	@Override
	public void setEntityManager(EntityManager em) {

		this.em = em;
	}

}
