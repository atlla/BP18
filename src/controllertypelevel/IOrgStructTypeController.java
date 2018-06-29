package controllertypelevel;

import javax.persistence.EntityManager;

import javafx.scene.control.ListView;
import javafx.stage.Stage;
import organizationalunits.OrganizationalUnitType;

public interface IOrgStructTypeController extends IControllerTypeLevel {

	public void setOrganizationalUnitType(OrganizationalUnitType type);
	
	
}
