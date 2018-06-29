package decisionpremise;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.Entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Embeddable
@Access(AccessType.PROPERTY)
public class ConsensusRationaleSpec extends RationaleSpec {

	private StringProperty proposition;
	private StringProperty preliminaryAffirmation;
	
	@Override
	public String toString() {

		return "ConsensusRationaleSpec";
	}
	
	/////////////// Getter/Setter ////////////////
	public final StringProperty propositionProperty() {
		
		if(proposition == null){
			
			proposition = new SimpleStringProperty();
		}
		return this.proposition;
	}
	
	public final String getProposition() {
		
		return this.propositionProperty().get();
	}
	
	public final void setProposition(final String proposition) {
		
		this.propositionProperty().set(proposition);
	}
	
	public final StringProperty preliminaryAffirmationProperty() {
		
		if(preliminaryAffirmation == null){
			
			preliminaryAffirmation = new SimpleStringProperty();
		}
		return this.preliminaryAffirmation;
	}
	
	public final String getPreliminaryAffirmation() {
		
		return this.preliminaryAffirmationProperty().get();
	}
	
	public final void setPreliminaryAffirmation(final String preliminaryAffirmation) {
		
		this.preliminaryAffirmationProperty().set(preliminaryAffirmation);
	}
	

	
	
}
