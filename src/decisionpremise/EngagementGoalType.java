package decisionpremise;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class EngagementGoalType extends AbstractGoalType {

	private final String type = "Engagement goal";

	@Override
	public void unbindProperties() {

		nameProperty().unbind();
		descriptionProperty().unbind();
	}
	
	@Override
	public String printDetails() {

		String s = "Engagement goal \n" + "Name: " + this.getName() + "\n" + "EG abs. prior.: "
				+ this.getAbsolutePriority().getValue();
		if (this.getJustification().size() > 0) {

			for (RationaleSpec r : this.getJustification()) {

				s += "\n" + r.getDescription();
			}
		}
		return s;
	}

	public String getType() {

		return type;
	}

}
