package decisionpremise;

import java.util.List;

import javax.persistence.Entity;

@Entity
public abstract class FactualDecisionPremiseType extends DecisionPremiseType{

	public abstract List<PresumedInfluenceRelationType> getPresInfluRel ();
}
