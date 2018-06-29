package organizationalunits;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Entity
@Access(AccessType.PROPERTY)
public abstract class OrganizationalUnitType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Access(AccessType.FIELD)
	private int id;
	private StringProperty name;
	private StringProperty description;
	
	//Authorizes relation noch hinzufügen
	@Access(AccessType.FIELD)
	@OneToMany(cascade = {CascadeType.PERSIST})
	private List<ParticipationRelationType> partRelations;
	
	@Override
	public String toString() {

		return getName();
	}

	public abstract void unbindProperties();
	
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

	public List<ParticipationRelationType> getPartRelations() {

		if (partRelations == null) {

			partRelations = new ArrayList<ParticipationRelationType>();
		}
		return partRelations;
	}

	public void setPartRelations(List<ParticipationRelationType> partRelations) {

		this.partRelations = partRelations;
	}

}
