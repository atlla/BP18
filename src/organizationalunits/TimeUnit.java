package organizationalunits;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Embeddable
@Access(AccessType.PROPERTY)
public class TimeUnit {

	private StringProperty level;

	/////////////// Getter/Setter ////////////////
	public final StringProperty levelProperty() {

		if (level == null) {

			level = new SimpleStringProperty();
		}
		return this.level;
	}

	public final String getLevel() {

		return this.levelProperty().get();
	}

	public final void setLevel(final String level) {

		this.levelProperty().set(level);
	}
}
