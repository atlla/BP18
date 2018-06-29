package supportrequirements;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Entity
@Access(AccessType.PROPERTY)
public class InformationType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Access(AccessType.FIELD)
	private int id;
	private StringProperty name;
	private StringProperty description;

	@Access(AccessType.FIELD)
	private List<InformationNeedSatisfactionRelation> infoNeedSatisfRel;
	@Access(AccessType.FIELD)
	private List<InformationSystemType> infoSys;
	
	
	@Override
	public String toString() {

		return getName();
	}

	/////////////// Getter/Setter ////////////////
	public final StringProperty nameProperty() {

		if (name == null) {

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

		if (description == null) {

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

	public List<InformationNeedSatisfactionRelation> getInfoNeedSatisfRel() {

		if (infoNeedSatisfRel == null) {

			infoNeedSatisfRel = new ArrayList<InformationNeedSatisfactionRelation>();
		}
		return infoNeedSatisfRel;
	}

	public void setInfoNeedSatisfRel(List<InformationNeedSatisfactionRelation> infoNeedSatisfRel) {

		this.infoNeedSatisfRel = infoNeedSatisfRel;
	}

	public List<InformationSystemType> getInfoSys() {

		if (infoSys == null) {

			infoSys = new ArrayList<InformationSystemType>();
		}
		return infoSys;
	}

	public void setInfoSys(List<InformationSystemType> infoSys) {

		this.infoSys = infoSys;
	}

}
