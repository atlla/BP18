package controllertypelevel;

import javax.persistence.EntityManager;

import javafx.stage.Stage;
import main.MainScreenController;

public interface IControllerTypeLevel {

	public void setEntityManager(EntityManager em);

	public void setStage(Stage stage);
	
	public void setMsc(MainScreenController msc);
	
	//public void setMsc(MainScreenController msc);
	
	
}
