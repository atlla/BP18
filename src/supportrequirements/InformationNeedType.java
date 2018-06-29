package supportrequirements;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Entity
@Access(AccessType.PROPERTY)
public class InformationNeedType extends SupportRequirementType {
	
	private StringProperty informationSpecification;
	private StringProperty necessaryDataQuality;
	private final String type = "Information need";

	@Access(AccessType.FIELD)
	private List<InformationNeedSatisfactionRelation> infoNeedSatisfRel;
	

	@Override
	public void unbindProperties() {

		nameProperty().unbind();
		descriptionProperty().unbind();
		informationSpecification.unbind();
		necessaryDataQuality.unbind();
	}

	/////////////// Getter/Setter ////////////////
	public final StringProperty informationSpecificationProperty() {

		if (informationSpecification == null) {

			informationSpecification = new SimpleStringProperty();
		}
		return this.informationSpecification;
	}

	public final String getInformationSpecification() {

		return this.informationSpecificationProperty().get();
	}

	public final void setInformationSpecification(final String informationSpecification) {

		this.informationSpecificationProperty().set(informationSpecification);
	}

	public final StringProperty necessaryDataQualityProperty() {

		if (necessaryDataQuality == null) {

			necessaryDataQuality = new SimpleStringProperty();
		}
		return this.necessaryDataQuality;
	}

	public final String getNecessaryDataQuality() {

		return this.necessaryDataQualityProperty().get();
	}

	public final void setNecessaryDataQuality(final String necessaryDataQuality) {

		this.necessaryDataQualityProperty().set(necessaryDataQuality);
	}

	public List<InformationNeedSatisfactionRelation> getInfoNeedSatisfRel() {

		if (infoNeedSatisfRel == null) {

			infoNeedSatisfRel = new ArrayList<InformationNeedSatisfactionRelation>();
		}
		return infoNeedSatisfRel;
	}

	public void setInfoNeedSatisfRel(List<InformationNeedSatisfactionRelation> infoNeedSatisfRel) {

		this.infoNeedSatisfRel = infoNeedSatisfRel;
	}

	public String getType() {

		return type;
	}

}
