package supportrequirements;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class AnalysisNeedType extends SupportRequirementType {

	@Access(AccessType.FIELD)
	@OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	private List<AnalysisNeedSatisfactionRelationType> analSatRel;
	private final String type = "Analysis need";


	@Override
	public void unbindProperties() {
	
		nameProperty().unbind();
		descriptionProperty().unbind();
	}
	
	public List<AnalysisNeedSatisfactionRelationType> getAnalSatRel() {

		if(analSatRel == null){
			
			analSatRel =  new ArrayList<AnalysisNeedSatisfactionRelationType>();
		}
		return analSatRel;
	}

	public void setAnalSatRel(List<AnalysisNeedSatisfactionRelationType> analSatRel) {
		
		this.analSatRel = analSatRel;
	}

	public String getType() {
	
		return type;
	}

}
