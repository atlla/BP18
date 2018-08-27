package decisionprocess;


import java.time.LocalDate;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StimulusInstance {

	private StringProperty instanceName;
	private StringProperty perceivedUrgency;
	private LocalDate recorded;
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

	public LocalDate getRecorded() {

		return recorded;
	}

	public void setRecorded(LocalDate recorded) {

		this.recorded = recorded;
	}

	public DecisionProcessInstance getInitiatedDpi() {

		return initiatedDpi;
	}

	public void setInitiatedDpi(DecisionProcessInstance initiatedDpi) {

		this.initiatedDpi = initiatedDpi;
	}

}
