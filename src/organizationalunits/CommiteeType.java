package organizationalunits;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Entity
@Access(AccessType.PROPERTY)
public class CommiteeType extends UnitOfWorkType {

	private BooleanProperty internal;
	private StringProperty corporateRelevance;
	private IntegerProperty meetingsPerYear;
//	private StringProperty mission;
	private final String orgType = "Committee";
	
	@Override
	public void unbindProperties() {
		
		nameProperty().unbind();
		descriptionProperty().unbind();
		missionProperty().unbind();
		internal.unbind();
		corporateRelevance.unbind();
		meetingsPerYear.unbind();
	}
	
	/////////////// Getter/Setter ////////////////
	public final BooleanProperty internalProperty() {

		if (internal == null) {

			internal = new SimpleBooleanProperty();
		}
		return this.internal;
	}

	public final StringProperty corporateRelevanceProperty() {

		if (corporateRelevance == null) {

			corporateRelevance = new SimpleStringProperty();
		}
		return this.corporateRelevance;
	}

	public final String getCorporateRelevance() {

		return this.corporateRelevanceProperty().get();
	}

	public final void setCorporateRelevance(final String corporateRelevance) {

		this.corporateRelevanceProperty().set(corporateRelevance);
	}

	public final boolean isInternal() {

		return this.internalProperty().get();
	}

	public final void setInternal(final boolean internal) {

		this.internalProperty().set(internal);
	}

	public final IntegerProperty meetingsPerYearProperty() {
		
		if(meetingsPerYear == null){
			
			meetingsPerYear = new SimpleIntegerProperty();
		}
		return this.meetingsPerYear;
	}

	public final int getMeetingsPerYear() {
		
		return this.meetingsPerYearProperty().get();
	}

	public final void setMeetingsPerYear(final int meetingsPerYear) {
		
		this.meetingsPerYearProperty().set(meetingsPerYear);
	}

//	public final StringProperty missionProperty() {
//
//		if (mission == null) {
//
//			mission = new SimpleStringProperty();
//		}
//		return this.mission;
//	}
//
//	public final String getMission() {
//
//		return this.missionProperty().get();
//	}
//
//	public final void setMission(final String mission) {
//
//		this.missionProperty().set(mission);
//	}

	public String getOrgType() {
		
		return orgType;
	}

}
