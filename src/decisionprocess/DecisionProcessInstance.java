package decisionprocess;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Entity
@Access(AccessType.PROPERTY)
public class DecisionProcessInstance {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Access(AccessType.FIELD)
	private int id;
	
	//private String test;
	private StringProperty instanceName;
	private StringProperty instanceGeneralAim;
	
//	@Access(AccessType.FIELD)
	@ManyToOne
	private DecisionProcessType dptReference;
//	@Access(AccessType.FIELD)
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
