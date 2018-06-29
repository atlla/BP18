package organizationalunits;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import decisionprocess.DecisionProcessType;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Entity
@Access(AccessType.PROPERTY)
public class ParticipationRelationType {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private BooleanProperty entitledToAuthorizeFinalDecision;
	private StringProperty role;
	private StringProperty participationRequired;
	private StringProperty suggestedAttendanceLevel;
	private StringProperty participationDetails;

	@Access(AccessType.FIELD)
	@ManyToOne
	private DecisionProcessType dpt;
	@Access(AccessType.FIELD)
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
	private OrganizationalUnitType out;

	@Override
	public String toString() {

		return out.getName();
	}

	/////////////// Getter/Setter ////////////////
	public final BooleanProperty entitledToAuthorizeFinalDecisionProperty() {

		if (entitledToAuthorizeFinalDecision == null) {

			entitledToAuthorizeFinalDecision = new SimpleBooleanProperty();
		}
		return this.entitledToAuthorizeFinalDecision;
	}

	public final boolean isEntitledToAuthorizeFinalDecision() {

		return this.entitledToAuthorizeFinalDecisionProperty().get();
	}

	public final void setEntitledToAuthorizeFinalDecision(final boolean entitledToAuthorizeFinalDecision) {

		this.entitledToAuthorizeFinalDecisionProperty().set(entitledToAuthorizeFinalDecision);
	}

	public final StringProperty roleProperty() {

		if (role == null) {

			role = new SimpleStringProperty();
		}
		return this.role;
	}

	public final String getRole() {

		return this.roleProperty().get();
	}

	public final void setRole(final String role) {

		this.roleProperty().set(role);
	}

	public final StringProperty participationRequiredProperty() {

		if (participationRequired == null) {

			participationRequired = new SimpleStringProperty();
		}
		return this.participationRequired;
	}

	public final String getParticipationRequired() {

		return this.participationRequiredProperty().get();
	}

	public final void setParticipationRequired(final String participationRequired) {

		this.participationRequiredProperty().set(participationRequired);
	}

	public final StringProperty suggestedAttendanceLevelProperty() {

		if (suggestedAttendanceLevel == null) {

			suggestedAttendanceLevel = new SimpleStringProperty();
		}
		return this.suggestedAttendanceLevel;
	}

	public final String getSuggestedAttendanceLevel() {

		return this.suggestedAttendanceLevelProperty().get();
	}

	public final void setSuggestedAttendanceLevel(final String suggestedAttendanceLevel) {

		this.suggestedAttendanceLevelProperty().set(suggestedAttendanceLevel);
	}

	public final StringProperty participationDetailsProperty() {

		if (participationDetails == null) {

			participationDetails = new SimpleStringProperty();
		}
		return this.participationDetails;
	}

	public final String getParticipationDetails() {

		return this.participationDetailsProperty().get();
	}

	public final void setParticipationDetails(final String participationDetails) {

		this.participationDetailsProperty().set(participationDetails);
	}

	public DecisionProcessType getDpt() {

		return dpt;
	}

	public void setDpt(DecisionProcessType dpt) {

		this.dpt = dpt;
	}

	public OrganizationalUnitType getOut() {

		return out;
	}

	public void setOut(OrganizationalUnitType out) {

		this.out = out;
	}
}
