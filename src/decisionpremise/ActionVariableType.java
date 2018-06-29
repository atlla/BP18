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
public class ActionVariableType extends FactualDecisionPremiseType {

	private StringProperty quality;
	private final String type = "Action variable";

	@Access(AccessType.FIELD)
	@OneToMany(cascade = CascadeType.ALL)
	private List<PresumedInfluenceRelationType> presInfluRelations;

	@Override
	public String printDetails() {

		return getName();
	}
	
	@Override
	public void unbindProperties() {

		nameProperty().unbind();
		descriptionProperty().unbind();
		quality.unbind();
	}
	
	@Override
	public List<PresumedInfluenceRelationType> getPresInfluRel() {
		
		if (presInfluRelations == null) {

			presInfluRelations = new ArrayList<PresumedInfluenceRelationType>();
		}
		return this.presInfluRelations;
	}
	
	public final StringProperty qualityProperty() {

		if (quality == null) {

			quality = new SimpleStringProperty();
		}
		return this.quality;
	}

	public final String getQuality() {

		return this.qualityProperty().get();
	}

	public final void setQuality(final String quality) {

		this.qualityProperty().set(quality);
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
