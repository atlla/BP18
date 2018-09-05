package decisionprocess;


import java.time.LocalDate;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

import java.sql.Date;

@Entity
@Access(AccessType.PROPERTY)
public class StimulusInstance {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Access(AccessType.FIELD)
	private int id;
	
	private StringProperty instanceName;
	private StringProperty perceivedUrgency;
	private Date recordedDate;
	
	@Transient
	private ObjectProperty<LocalDate> recorded;
	
	@ManyToOne
	private StimulusType initiatedStimulusType;
	@OneToOne //(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
	private DecisionProcessInstance initiatedDpi;

	/////////////// Getter/Setter ////////////////
	public final StringProperty instanceNameProperty() {

		if (instanceName == null) {

			instanceName = new SimpleStringProperty();
		}
		return this.instanceName;
	}

	public final String getInstanceName() {

		return this.instanceNameProperty().get();
	}

	public final void setInstanceName(final String instanceName) {

		this.instanceNameProperty().set(instanceName);
	}

	public final StringProperty perceivedUrgencyProperty() {

		if (perceivedUrgency == null) {

			perceivedUrgency = new SimpleStringProperty();
		}
		return this.perceivedUrgency;
	}

	public final String getPerceivedUrgency() {

		return this.perceivedUrgencyProperty().get();
	}

	public final void setPerceivedUrgency(final String perceivedUrgency) {

		this.perceivedUrgencyProperty().set(perceivedUrgency);
	}
	
	public ObjectProperty<LocalDate> recordedProperty() {
		if (recorded == null) {
			
			recorded = new SimpleObjectProperty<>();
		}
		return recorded;
	}
	public LocalDate getRecorded() {

		return this.recordedProperty().get();
	}

	public void setRecorded(LocalDate recorded) {

		this.recordedProperty().set(recorded);
	}

	public StimulusType getInitatedStimulusType() {
		return initiatedStimulusType;
	}
	
	public void setInitiatedStimulusType(StimulusType initiatedStimulusType) {
		this.initiatedStimulusType = initiatedStimulusType;
	}
	
	public DecisionProcessInstance getInitiatedDpi() {

		return initiatedDpi;
	}

	public void setInitiatedDpi(DecisionProcessInstance initiatedDpi) {

		this.initiatedDpi = initiatedDpi;
	}
	
	public void setRecordedDateinLocalDate(Date sqlDate) {
		//recorded.set(sqlDate.toLocalDate());
		setRecorded(sqlDate.toLocalDate());
	}
	
	public void setRecordedInDate(LocalDate recorded) {
		recordedDate = Date.valueOf(recorded);
	}
	public void setRecordedDate(Date recordedDate) {
		this.recordedDate = recordedDate;
	}
	public Date getRecordedDate() {
		return recordedDate;
	}
	public int getID() {
		return id;
	}
}
