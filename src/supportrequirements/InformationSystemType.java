package supportrequirements;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
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
public class InformationSystemType {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Access(AccessType.FIELD)
	private int id;
	private StringProperty name;
	private StringProperty description;

	@Access(AccessType.FIELD)
	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private List<InformationType> providesInfos;
	@Access(AccessType.FIELD)
	@OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private List<AnalysisNeedSatisfactionRelationType> anaNeedSatisfRel;

	@Override
	public String toString() {

		return getName();
	}

	/////////////// Getter/Setter ////////////////
	public List<InformationType> getProvidesInfos() {

		if (providesInfos == null) {

			providesInfos = new ArrayList<InformationType>();
		}
		return providesInfos;
	}

	public List<AnalysisNeedSatisfactionRelationType> getAnaNeedSatisfRel() {

		if (anaNeedSatisfRel == null) {

			anaNeedSatisfRel = new ArrayList<AnalysisNeedSatisfactionRelationType>();
		}
		return anaNeedSatisfRel;
	}

	public void setProvidesInfos(List<InformationType> providesInfos) {

		this.providesInfos = providesInfos;
	}

	public void setAnaNeedSatisfRel(List<AnalysisNeedSatisfactionRelationType> anaNeedSatisfRel) {

		this.anaNeedSatisfRel = anaNeedSatisfRel;
	}

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
}
