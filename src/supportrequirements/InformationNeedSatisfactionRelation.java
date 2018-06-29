package supportrequirements;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Entity
@Access(AccessType.PROPERTY)
public class InformationNeedSatisfactionRelation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private StringProperty perceivedDegreeOfSatisfaction;
	@Access(AccessType.FIELD)
	private InformationNeedType referencedInfoNeed;
	@Access(AccessType.FIELD)
	private InformationType referencedinformation;

	/////////////// Getter/Setter ////////////////
	public final StringProperty perceivedDegreeOfSatisfactionProperty() {

		if (perceivedDegreeOfSatisfaction == null) {

			perceivedDegreeOfSatisfaction = new SimpleStringProperty();
		}
		return this.perceivedDegreeOfSatisfaction;
	}

	public final String getPerceivedDegreeOfSatisfaction() {

		return this.perceivedDegreeOfSatisfactionProperty().get();
	}

	public final void setPerceivedDegreeOfSatisfaction(final String perceivedDegreeOfSatisfaction) {

		this.perceivedDegreeOfSatisfactionProperty().set(perceivedDegreeOfSatisfaction);
	}

	public InformationNeedType getReferencedInfoNeed() {

		return referencedInfoNeed;
	}

	public void setReferencedInfoNeed(InformationNeedType referencedInfoNeed) {

		this.referencedInfoNeed = referencedInfoNeed;
	}

	public InformationType getReferencedinformation() {

		return referencedinformation;
	}

	public void setReferencedinformation(InformationType referencedinformation) {

		this.referencedinformation = referencedinformation;
	}

}
