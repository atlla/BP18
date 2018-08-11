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
import decisionprocess.DecisionProcessInstance;
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

public class DpiTabController implements Initializable, IDpiTabController {
	private DecisionProcessInstance dpt;
	private Tab tab;
	private TabPane tPane;
	private EntityManager em;
	
	@Override
	public void setEntityManager(EntityManager em) {
		// TODO Auto-generated method stub
		this.em = em;
	}

	@Override
	public void setStage(Stage stage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTabPane(TabPane tPane) {
		// TODO Auto-generated method stub
		this.tPane = tPane;
	}

	@Override
	public void setDpi(DecisionProcessInstance dpi) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDpiTab(Tab tab) {
		// TODO Auto-generated method stub
		this.tab = tab;
	}

	@Override
	public EntityManager getEm() {
		// TODO Auto-generated method stub
		return em;
	}

	@Override
	public TabPane getTpane() {
		// TODO Auto-generated method stub
		return this.tPane;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	//ToDO bind Properties
}
