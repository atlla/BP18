package decisionpremise;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class DecisionGoalType extends AbstractGoalType {

	private final String type = "Decision goal";
	
	@Override
	public void unbindProperties() {

		nameProperty().unbind();
		descriptionProperty().unbind();
	}
	
	@Override
	public String printDetails() {

		String s = "Decision goal \n" + "Name: " + this.getName() + "\n" + "DG abs. prior.: "
				+ this.getAbsolutePriority().getValue();

		return s;
	}

	public String getType() {

		return type;
	}

}
