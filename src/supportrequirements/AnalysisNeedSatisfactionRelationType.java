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
public class AnalysisNeedSatisfactionRelationType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Access(AccessType.FIELD)
	private int id;
	private StringProperty useRequired;
	@Access(AccessType.FIELD)
	private AnalysisNeedType referencedAnalysisNeed;
	@Access(AccessType.FIELD)
	private InformationSystemType referencedInfoSystem;

	/////////////// Getter/Setter ////////////////
	public final StringProperty useRequiredProperty() {

		if (useRequired == null) {

			useRequired = new SimpleStringProperty();
		}
		return this.useRequired;
	}

	public final String getUseRequired() {

		return this.useRequiredProperty().get();
	}

	public final void setUseRequired(final String useRequired) {

		this.useRequiredProperty().set(useRequired);
	}

	public AnalysisNeedType getReferencedAnalysisNeed() {

		return referencedAnalysisNeed;
	}

	public void setReferencedAnalysisNeed(AnalysisNeedType referencedAnalysisNeed) {

		this.referencedAnalysisNeed = referencedAnalysisNeed;
	}

	public InformationSystemType getReferencedInfoSystem() {

		return referencedInfoSystem;
	}

	public void setReferencedInfoSystem(InformationSystemType referencedInfoSystem) {

		this.referencedInfoSystem = referencedInfoSystem;
	}

}
