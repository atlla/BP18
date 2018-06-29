package controllertypelevel;

import decisionpremise.DecisionPremiseType;
import javafx.stage.Stage;
import supportrequirements.SupportRequirementType;

public interface ISuppReqTypeController extends IControllerTypeLevel {
	
	public void setDecPremT(DecisionPremiseType decPremT);
	
	public void setSuppReqType(SupportRequirementType suppReq);
}
