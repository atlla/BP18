package decisionpremise;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Entity
@Access(AccessType.PROPERTY)
public class SymbolicGoalType extends AbstractGoalType {

	private StringProperty targetGroup;
	private StringProperty targetGroupDescription;
	private StringProperty typeOfAnnouncement;
	private final String type = "Symbolic goal";
	
	@Override
	public void unbindProperties() {

		nameProperty().unbind();
		descriptionProperty().unbind();
		targetGroup.unbind();
		targetGroupDescription.unbind();
		typeOfAnnouncement.unbind();
	}
	
	@Override
	public String printDetails() {

		String s = "Symbolic goal \n" + "Name: " + this.getName() + "\n" + "SG abs. prior.: "
				+ this.getAbsolutePriority().getValue() + "\nSG targ. gr.: " + this.getTargetGroup()
				+ "\nSG targ. gr. descr.:" + this.getTargetGroupDescription() + "\nSG type of ann.: "
				+ this.getTypeOfAnnouncement();

		return s;
	}

	/////////////// Getter/Setter ////////////////
	public final StringProperty targetGroupProperty() {

		if (targetGroup == null) {

			targetGroup = new SimpleStringProperty();
		}
		return this.targetGroup;
	}

	public final String getTargetGroup() {

		return this.targetGroupProperty().get();
	}

	public final void setTargetGroup(final String targetGroup) {

		this.targetGroupProperty().set(targetGroup);
	}

	public final StringProperty targetGroupDescriptionProperty() {

		if (targetGroupDescription == null) {

			targetGroupDescription = new SimpleStringProperty();
		}
		return this.targetGroupDescription;
	}

	public final String getTargetGroupDescription() {

		return this.targetGroupDescriptionProperty().get();
	}

	public final void setTargetGroupDescription(final String targetGroupDescription) {

		this.targetGroupDescriptionProperty().set(targetGroupDescription);
	}

	public final StringProperty typeOfAnnouncementProperty() {

		if (typeOfAnnouncement == null) {

			typeOfAnnouncement = new SimpleStringProperty();
		}
		return this.typeOfAnnouncement;
	}

	public final String getTypeOfAnnouncement() {

		return this.typeOfAnnouncementProperty().get();
	}

	public final void setTypeOfAnnouncement(final String typeOfAnnouncement) {

		this.typeOfAnnouncementProperty().set(typeOfAnnouncement);
	}

	public String getType() {

		return type;
	}

}
