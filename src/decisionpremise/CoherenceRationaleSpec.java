package decisionpremise;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.Entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Embeddable
@Access(AccessType.PROPERTY)
public class CoherenceRationaleSpec extends RationaleSpec {

	private StringProperty discussion;
	private StringProperty source;
	
	@Override
	public String toString() {

		return "CoherenceRationaleSpec";
	}

	/////////////// Getter/Setter ////////////////
	public final StringProperty discussionProperty() {
		
		if(discussion == null){
			
			discussion = new SimpleStringProperty();
		}
		return this.discussion;
	}
	
	public final String getDiscussion() {
		
		return this.discussionProperty().get();
	}
	
	public final void setDiscussion(final String discussion) {
		
		this.discussionProperty().set(discussion);
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
