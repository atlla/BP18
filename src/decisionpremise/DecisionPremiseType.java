package decisionpremise;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import supportrequirements.SupportRequirementType;

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class DecisionPremiseType {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Access(AccessType.FIELD)
	private int id;

	private StringProperty name;
	private StringProperty description;

	@Access(AccessType.FIELD)
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<RelevanceRelationType> relRelation;
	@Access(AccessType.FIELD)
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<SupportRequirementType> srt;

	@Override
	public String toString() {

		return this.getName();
	}

	public abstract String printDetails();

	public abstract void unbindProperties();
	
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

	public List<RelevanceRelationType> getRelRelation() {

		if (relRelation == null) {

			relRelation = new ArrayList<RelevanceRelationType>();
		}
		return relRelation;
	}

	public void setRelRelation(List<RelevanceRelationType> relRelation) {

		this.relRelation = relRelation;
	}

	public List<SupportRequirementType> getSrt() {
		
		if(srt == null){
			
			srt = new ArrayList<SupportRequirementType>();
		}
		return srt;
	}

	public void setSrt(List<SupportRequirementType> srt) {
		
		this.srt = srt;
	}
	
}
