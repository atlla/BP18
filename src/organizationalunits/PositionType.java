package organizationalunits;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;

@Entity
@Access(AccessType.PROPERTY)
public class PositionType extends SingleUnitType {

	private FloatProperty averageSpan;
	private BooleanProperty staff;
	private final String orgType = "Position";

	@Override
	public void unbindProperties() {
		
		nameProperty().unbind();
		descriptionProperty().unbind();
		staff.unbind();
	}
	
	public final FloatProperty averageSpanProperty() {

		if (averageSpan == null) {

			averageSpan = new SimpleFloatProperty();
		}
		return this.averageSpan;
	}

	public final float getAverageSpan() {

		return this.averageSpanProperty().get();
	}

	public final void setAverageSpan(final float averageSpan) {

		this.averageSpanProperty().set(averageSpan);
	}

	public final BooleanProperty staffProperty() {

		if (staff == null) {

			staff = new SimpleBooleanProperty();
		}
		return this.staff;
	}

	public final boolean isStaff() {

		return this.staffProperty().get();
	}

	public final void setStaff(final boolean staff) {

		this.staffProperty().set(staff);
	}

	public String getOrgType() {
		
		return orgType;
	}

}
