package supportrequirements;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import decisionpremise.DecisionPremiseType;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Entity
@Access(AccessType.PROPERTY)
public abstract class SupportRequirementType {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Access(AccessType.FIELD)
	private int id;
	private StringProperty name;
	private StringProperty description;
	@Access(AccessType.FIELD)
	@ManyToMany
	private List<DecisionPremiseType> raisedFromDecPremTypes;
	
	public abstract void unbindProperties();
	
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

	public List<DecisionPremiseType> getRaisedFromDecPremTypes() {

		if (raisedFromDecPremTypes == null) {

			raisedFromDecPremTypes = new ArrayList<DecisionPremiseType>();
		}
		return raisedFromDecPremTypes;
	}

	public void setRaisedFromDecPremTypes(List<DecisionPremiseType> raisedFromDecPremTypes) {

		this.raisedFromDecPremTypes = raisedFromDecPremTypes;
	}

}
