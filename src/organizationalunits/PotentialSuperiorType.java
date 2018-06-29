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
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class PotentialSuperiorType {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private StringProperty name;
	private StringProperty description;

	/////////////// Getter/Setter ////////////////
	public final StringProperty nameProperty() {

		if (name == null) {

			name = new SimpleStringProperty();
		}
		return this.name;
	}

	public final String getName() {

		return this.nameProperty().get();
	}

	public final void setName(final String name) {

		this.nameProperty().set(name);
	}

	public final StringProperty descriptionProperty() {

		if (description == null) {

			description = new SimpleStringProperty();
		}
		return this.description;
	}

	public final String getDescription() {

		return this.descriptionProperty().get();
	}

	public final void setDescription(final String description) {

		this.descriptionProperty().set(description);
	}

}
