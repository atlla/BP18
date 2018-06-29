package controllertypelevel;

import javax.persistence.EntityManager;

import javafx.stage.Stage;

public interface IControllerTypeLevel {

	public void setEntityManager(EntityManager em);

	public void setStage(Stage stage);
	
}
