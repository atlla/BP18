package decisionprocess;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Entity 
@Access(AccessType.PROPERTY)
public class StimulusType {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Access(AccessType.FIELD)
	private int id;
	
	private StringProperty name;
	private StringProperty description;
	private StringProperty valency;
	private StringProperty commonFrequency;
	private StringProperty commonUrgency;
	
	@Access(AccessType.FIELD)
	private DecisionProcessType dpt;
	
	public StimulusType() {

	}
	
	//Copy Konstruktor
	public StimulusType(StimulusType st) {

		this.name = new SimpleStringProperty(st.getName());
		this.description = new SimpleStringProperty(st.getDescription());
		this.valency = new SimpleStringProperty(st.getValency());
		this.commonFrequency = new SimpleStringProperty(st.getCommonFrequency());
		this.commonUrgency = new SimpleStringProperty(st.getCommonUrgency());
		this.dpt = st.getDpt();
	}
	
	@Override
	public String toString() {
		
		return getName();
	}

	/////////////// Getter/Setter ////////////////
	public final StringProperty nameProperty() {
	
		if(name == null){
			
			name = new SimpleStringProperty();
		}
		return this.name;
	}
	
	public final String getName() {
		
		return this.nameProperty().get();
	}
	
	public final void setName(final String name) {
		
		this.nameProperty().set(name);
	}
	
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

	public final StringProperty valencyProperty() {
		
		if(valency == null){
			
			valency = new SimpleStringProperty();
		}
		return this.valency;
	}
	

	public final String getValency() {
		
		return this.valencyProperty().get();
	}
	

	public final void setValency(final String valency) {
		
		this.valencyProperty().set(valency);
	}
	

	public final StringProperty commonFrequencyProperty() {
		
		if(commonFrequency == null){
			
			commonFrequency = new SimpleStringProperty();
		}
		return this.commonFrequency;
	}
	

	public final String getCommonFrequency() {
		
		return this.commonFrequencyProperty().get();
	}
	

	public final void setCommonFrequency(final String commonFrequency) {
		
		this.commonFrequencyProperty().set(commonFrequency);
	}
	

	public final StringProperty commonUrgencyProperty() {
		
		if(commonUrgency == null){
			
			commonUrgency = new SimpleStringProperty();
		}
		return this.commonUrgency;
	}
	

	public final String getCommonUrgency() {
		
		return this.commonUrgencyProperty().get();
	}
	

	public final void setCommonUrgency(final String commonUrgency) {
		
		this.commonUrgencyProperty().set(commonUrgency);
	}
	
	public DecisionProcessType getDpt() {
		
		return dpt;
	}

	public void setDpt(DecisionProcessType dpt) {
		
		this.dpt = dpt;
	}
	
	
	
	/////////////// Getter/Setter Ende////////////////
}
