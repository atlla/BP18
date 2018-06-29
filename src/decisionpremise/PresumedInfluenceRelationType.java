package decisionpremise;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Entity
@Access(AccessType.PROPERTY)
public class PresumedInfluenceRelationType {

	private StringProperty interdependence;
	private StringProperty justification;
	
	@Access(AccessType.FIELD)
	private ValueDecisionPremiseType influenceOnVdp;
	
	@ManyToOne
	@Access(AccessType.FIELD)
	private FactualDecisionPremiseType fdpt;

	public final StringProperty interdependenceProperty() {

		if (interdependence == null) {

			interdependence = new SimpleStringProperty();
		}
		return this.interdependence;
	}

	public final String getInterdependence() {

		return this.interdependenceProperty().get();
	}

	public final void setInterdependence(final String interdependence) {

		this.interdependenceProperty().set(interdependence);
	}

	public final StringProperty justificationProperty() {

		if (justification == null) {

			justification = new SimpleStringProperty();
		}
		return this.justification;
	}

	public final String getJustification() {

		return this.justificationProperty().get();
	}

	public final void setJustification(final String justification) {

		this.justificationProperty().set(justification);
	}

	public ValueDecisionPremiseType getInfluenceOnVdp() {

		return influenceOnVdp;
	}

	public void setInfluenceOnVdp(ValueDecisionPremiseType influenceOnVdp) {

		this.influenceOnVdp = influenceOnVdp;
	}

	public FactualDecisionPremiseType getFdpt() {

		return fdpt;
	}

	public void setFdpt(FactualDecisionPremiseType fdpt) {
		
		if (fdpt instanceof ActionVariableType || fdpt instanceof EnvironmentalFactorType) {
			
			this.fdpt = fdpt;
		}
	}

}
