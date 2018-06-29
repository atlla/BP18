package organizationalunits;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Entity
@Access(AccessType.PROPERTY)
public abstract class UnitOfWorkType extends OrganizationalUnitType{

//	private StringProperty name;
//	private StringProperty description;
	private StringProperty mission;

	/////////////// Getter/Setter ////////////////
//	public final StringProperty nameProperty() {
//
//		if (name == null) {
//
//			name = new SimpleStringProperty();
//		}
//		return this.name;
//	}
//
//	public final String getName() {
//
//		return this.nameProperty().get();
//	}
//
//	public final void setName(final String name) {
//
//		this.nameProperty().set(name);
//	}
//
//	public final StringProperty descriptionProperty() {
//
//		if (description == null) {
//
//			description = new SimpleStringProperty();
//		}
//		return this.description;
//	}

//	public final String getDescription() {
//
//		return this.descriptionProperty().get();
//	}
//
//	public final void setDescription(final String description) {
//
//		this.descriptionProperty().set(description);
//	}

	public final StringProperty missionProperty() {

		if (mission == null) {

			mission = new SimpleStringProperty();
		}
		return this.mission;
	}

	public final String getMission() {

		return this.missionProperty().get();
	}

	public final void setMission(final String mission) {

		this.missionProperty().set(mission);
	}
}
