package decisionpremise;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.Entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Embeddable
@Access(AccessType.PROPERTY)
public class CorrespondenceRationaleSpec extends RationaleSpec {
	
	private StringProperty source;
	@Access(AccessType.FIELD)
	private OrdinalValue validity;
	@Access(AccessType.FIELD)
	private OrdinalValue reliability;
	
	@Override
	public String toString() {

		return "CorrespondenceRationaleSpec";
	}
	
	public OrdinalValue getValidity() {
		
		if (validity == null) {
			
			validity = new OrdinalValue();
		}
		return validity;
	}
	
	public void setValidity(OrdinalValue validity) {
		
		this.validity = validity;
	}
	
	public OrdinalValue getReliability() {
		
		if (reliability == null) {
			
			reliability = new OrdinalValue();
		}
		return reliability;
	}
	
	public void setReliability(OrdinalValue reliability) {
		
		this.reliability = reliability;
	}
	
	public final StringProperty sourceProperty() {
		
		if(source == null){
			
			source = new SimpleStringProperty();
		}
		return this.source;
	}
	

	public final String getSource() {
		
		return this.sourceProperty().get();
	}
	

	public final void setSource(final String source) {
		
		this.sourceProperty().set(source);
	}
	
}
