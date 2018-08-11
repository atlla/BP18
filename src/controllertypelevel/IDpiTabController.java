package controllertypelevel;

import javax.persistence.EntityManager;

import decisionprocess.DecisionProcessInstance;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public interface IDpiTabController extends IControllerTypeLevel{

	public void setTabPane(TabPane tPane);
	
	public void setDpi(DecisionProcessInstance dpi);
	
	public void setDpiTab(Tab tab);
	
	public EntityManager getEm();
	
	public TabPane getTpane();
}

