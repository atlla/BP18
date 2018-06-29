package decisionpremise;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import decisionprocess.DecisionProcessType;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Entity
@Access(AccessType.PROPERTY)
public class RelevanceRelationType {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Access(AccessType.FIELD)
	private int id;

	private StringProperty considerationRequired;

	@Access(AccessType.FIELD)
	private DecisionProcessType decProcType;
	@Access(AccessType.FIELD)
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE})
	private DecisionPremiseType decPremType;

	@Override
	public String toString() {

		return decPremType.getName();
	}

	/////////////// Getter/Setter ////////////////
	public final StringProperty considerationRequiredProperty() {

		if (considerationRequired == null) {

			considerationRequired = new SimpleStringProperty();
		}
		return this.considerationRequired;
	}

	public final String getConsiderationRequired() {

		return this.considerationRequiredProperty().get();
	}

	public final void setConsiderationRequired(final String considerationRequired) {

		this.considerationRequiredProperty().set(considerationRequired);
	}

	public void setDecProcType(DecisionProcessType decProcType) {

		this.decProcType = decProcType;
	}

	public DecisionProcessType getDecProcType() {

		return decProcType;
	}

	public DecisionPremiseType getDecPremType() {

		return decPremType;
	}

	public void setDecPremType(DecisionPremiseType decPremType) {

		this.decPremType = decPremType;
	}
}
