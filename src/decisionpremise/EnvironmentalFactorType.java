package decisionpremise;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Entity
@Access(AccessType.PROPERTY)
public class EnvironmentalFactorType extends FactualDecisionPremiseType {

	private StringProperty presumedPredictability;
	private final String type = "Environmental factor";

	@Access(AccessType.FIELD)
	@OneToMany(cascade = CascadeType.ALL)
	private List<PresumedInfluenceRelationType> presInfluRelations;
	
	@Override
	public void unbindProperties() {

		nameProperty().unbind();
		descriptionProperty().unbind();
		presumedPredictability.unbind();
	}
	
	@Override
	public String printDetails() {

		return getName();
	}
	
	@Override
	public List<PresumedInfluenceRelationType> getPresInfluRel() {
		
		if (presInfluRelations == null) {

			presInfluRelations = new ArrayList<PresumedInfluenceRelationType>();
		}
		return this.presInfluRelations;
	}
	
	public final StringProperty presumedPredictabilityProperty() {

		if (presumedPredictability == null) {

			presumedPredictability = new SimpleStringProperty();
		}
		return this.presumedPredictability;
	}

	public final String getPresumedPredictability() {

		return this.presumedPredictabilityProperty().get();
	}

	public final void setPresumedPredictability(final String presumedPredictability) {

		this.presumedPredictabilityProperty().set(presumedPredictability);
	}

	public List<PresumedInfluenceRelationType> getPresInfluRelations() {

		if (presInfluRelations == null) {

			presInfluRelations = new ArrayList<PresumedInfluenceRelationType>();
		}
		return presInfluRelations;
	}

	public void setPresInfluRelations(List<PresumedInfluenceRelationType> presInfluRelations) {

		this.presInfluRelations = presInfluRelations;
	}

	public String getType() {

		return type;
	}

}
