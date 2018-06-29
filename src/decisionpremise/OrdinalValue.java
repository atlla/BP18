package decisionpremise;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Access(AccessType.PROPERTY)
@Embeddable
public class OrdinalValue extends Value {

	private StringProperty value;

	public final StringProperty valueProperty() {

		if (value == null) {

			value = new SimpleStringProperty();
		}
		return this.value;
	}

	public final String getValue() {
	
		return this.valueProperty().get();
	}

	public final void setValue(final String value) {

		this.valueProperty().set(value);
	}
}
