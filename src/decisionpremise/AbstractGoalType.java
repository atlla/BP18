package decisionpremise;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public abstract class AbstractGoalType extends ValueDecisionPremiseType {

	@Access(AccessType.FIELD)
	@Embedded
	private List<RationaleSpec> justification;
	@Embedded
	private OrdinalValue absolutePriority;

	public List<RationaleSpec> getJustification() {

		if (justification == null) {

			justification = new ArrayList<RationaleSpec>();
		}
		return justification;
	}

	public void setJustification(List<RationaleSpec> justification) {

		this.justification = justification;
	}

	 public OrdinalValue getAbsolutePriority() {
	
	 if(absolutePriority == null){
	
	 absolutePriority = new OrdinalValue();
	 }
	 return absolutePriority;
	 }
	
	 public void setAbsolutePriority(OrdinalValue absolutePriority) {
	
	 this.absolutePriority = absolutePriority;
	 }

}
