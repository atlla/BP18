package decisionprocess;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DecisionProcessInstance {

	private StringProperty instanceName;
	private StringProperty instanceGeneralAim;
	
	@ManyToOne
	private DecisionProcessType dptReference;
	@OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
	private StimulusInstance stimInstReference;

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

	public final StringProperty instanceGeneralAimProperty() {

		if (instanceGeneralAim == null) {

			instanceGeneralAim = new SimpleStringProperty();
		}
		return this.instanceGeneralAim;
	}

	public final String getInstanceGeneralAim() {

		return this.instanceGeneralAimProperty().get();
	}

	public final void setInstanceGeneralAim(final String instanceGeneralAim) {

		this.instanceGeneralAimProperty().set(instanceGeneralAim);
	}

	public DecisionProcessType getDptReference() {

		return dptReference;
	}

	public void setDptReference(DecisionProcessType dptReference) {
		
		this.dptReference = dptReference;
	}

}