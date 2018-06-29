package organizationalunits;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Entity
@Access(AccessType.PROPERTY)
public class BoardType extends OrganizationalUnitType {

	private StringProperty mission;
	private BooleanProperty internal;
	private final String orgType = "Board";
	
	@Override
	public void unbindProperties() {
		
		nameProperty().unbind();
		descriptionProperty().unbind();
		mission.unbind();
		internal.unbind();
	}
	
	/////////////// Getter/Setter ////////////////
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

	public final BooleanProperty internalProperty() {

		if (internal == null) {

			internal = new SimpleBooleanProperty();
		}
		return this.internal;
	}

	public final boolean isInternal() {

		return this.internalProperty().get();
	}

	public final void setInternal(final boolean internal) {

		this.internalProperty().set(internal);
	}

	public String getOrgType() {

		return orgType;
	}

}
