package decisionpremise;

import javax.persistence.Embeddable;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Embeddable
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Value {

}
