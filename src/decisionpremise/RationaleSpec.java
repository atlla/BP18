package decisionpremise;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Embeddable
@Inheritance(strategy=InheritanceType.JOINED)
@Access(AccessType.PROPERTY)
public abstract class RationaleSpec {

	private StringProperty description;

	/////////////// Getter/Setter ////////////////
	public final StringProperty descriptionProperty() {
		
		if(description == null){
			
			description = new SimpleStringProperty();
		}
		return this.description;
	}

	public final String getDescription() {

		return this.descriptionProperty().get();
	}

	public final void setDescription(final String description) {

		this.descriptionProperty().set(description);
	}
}
