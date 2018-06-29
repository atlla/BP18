package organizationalunits;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Entity
@Access(AccessType.PROPERTY)
public class RoleType extends SingleUnitType {

	private IntegerProperty period;
	private BooleanProperty internal;
	private StringProperty procedure;
	private final String orgType = "Role";
	
	@Override
	public void unbindProperties() {
		
		nameProperty().unbind();
		descriptionProperty().unbind();
		period.unbind();
		procedure.unbind();
		internal.unbind();
	}
	
	/////////////// Getter/Setter ////////////////
	public final IntegerProperty periodProperty() {

		if (period == null) {

			period = new SimpleIntegerProperty();
		}
		return this.period;
	}

	public final int getPeriod() {

		return this.periodProperty().get();
	}

	public final void setPeriod(final int period) {

		if (period >= 0) {

			this.periodProperty().set(period);
		}
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

	public final StringProperty procedureProperty() {

		if (procedure == null) {

			procedure = new SimpleStringProperty();
		}
		return this.procedure;
	}

	public final String getProcedure() {

		return this.procedureProperty().get();
	}

	public final void setProcedure(final String procedure) {

		this.procedureProperty().set(procedure);
	}

	public String getOrgType() {

		return orgType;
	}

}
