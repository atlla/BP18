package decisionprocess;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import decisionpremise.RelevanceRelationType;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import organizationalunits.ParticipationRelationType;

@Entity
@Access(AccessType.PROPERTY)
// @Table(name="decisionprocesstype")
public class DecisionProcessType {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Access(AccessType.FIELD)
	private int id;

	private StringProperty name;
	private StringProperty generalAim;
	private StringProperty commonActivityFocus;
	private StringProperty presumedImpact;

	@Access(AccessType.FIELD)
	@OneToMany(cascade = CascadeType.ALL)
	private List<StimulusType> initiatingStimulusType;
	@Access(AccessType.FIELD)
	@OneToMany(cascade = CascadeType.ALL)
	private List<ParticipationRelationType> partRelations;
	@Access(AccessType.FIELD)
	@OneToMany(cascade = CascadeType.ALL)
	private List<RelevanceRelationType> relRelations;
	@Access(AccessType.FIELD)
	private List<DecisionProcessInstance> dptInstances;
	// private ObservableList<StimulusType> initiatingStimulusType;
	// @ManyToMany
	// private ObservableList<StimulusType> triggeredStimulusTypes =
	// FXCollections.observableArrayList();

	/////////////// Getter/Setter ////////////////

	// Getter/Setter name Property
	public final StringProperty nameProperty() {

		if (name == null) {

			this.name = new SimpleStringProperty();
		}
		return this.name;
	}

	public final String getName() {

		return this.nameProperty().get();
	}

	public final void setName(final String name) {

		this.nameProperty().set(name);
	}

	// Getter/Setter generalAim Property
	public final StringProperty generalAimProperty() {

		if (generalAim == null) {

			generalAim = new SimpleStringProperty();
		}

		return this.generalAim;
	}

	public final String getGeneralAim() {

		return this.generalAimProperty().get();
	}

	public final void setGeneralAim(final String generalAim) {

		this.generalAimProperty().set(generalAim);
	}

	// Getter/Setter commonActivityFocus Property
	public final StringProperty commonActivityFocusProperty() {

		if (commonActivityFocus == null) {

			commonActivityFocus = new SimpleStringProperty();
		}

		return this.commonActivityFocus;
	}

	public final String getCommonActivityFocus() {

		return this.commonActivityFocusProperty().get();
	}

	public final void setCommonActivityFocus(final String commonActivityFocus) {

		this.commonActivityFocusProperty().set(commonActivityFocus);
	}

	// Getter/Setter name Property
	public final StringProperty presumedImpactProperty() {

		if (presumedImpact == null) {

			presumedImpact = new SimpleStringProperty();
		}

		return this.presumedImpact;
	}

	public final String getPresumedImpact() {

		return this.presumedImpactProperty().get();
	}

	public final void setPresumedImpact(final String presumedImpact) {

		this.presumedImpactProperty().set(presumedImpact);
	}

	public List<StimulusType> getInitiatingStimulusType() {

		if (initiatingStimulusType == null) {

			initiatingStimulusType = new ArrayList<StimulusType>();
		}

		return initiatingStimulusType;
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

	public List<RelevanceRelationType> getRelRelations() {

		if (relRelations == null) {

			relRelations = new ArrayList<RelevanceRelationType>();
		}
		return relRelations;
	}

	public void setRelRelations(List<RelevanceRelationType> relRelations) {

		this.relRelations = relRelations;
	}

	public List<DecisionProcessInstance> getDptInstances() {

		if (dptInstances == null) {

			dptInstances = new ArrayList<DecisionProcessInstance>();
		}
		return dptInstances;
	}

	public void setDptInstances(List<DecisionProcessInstance> dptInstances) {

		this.dptInstances = dptInstances;
	}

	/////////////// Getter/Setter Ende////////////////
}
