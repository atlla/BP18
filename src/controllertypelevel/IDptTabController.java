package controllertypelevel;

import javax.persistence.EntityManager;

import decisionprocess.DecisionProcessType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public interface IDptTabController extends IControllerTypeLevel{

	public void setTabPane(TabPane tPane);
	
	public void setDpt(DecisionProcessType dpt);
	
	public void setDptTab(Tab tab);
	
	public EntityManager getEm();
	
	public TabPane getTpane();
}
