package organizationalunits;

import javax.persistence.Embeddable;

@Embeddable
public class Duration {

	private float dur;
	private TimeUnit unit;

	/////////////// Getter/Setter ////////////////
	public float getDur() {

		return dur;
	}

	public void setDur(float dur) {

		this.dur = dur;
	}

	public TimeUnit getUnit() {

		return unit;
	}

	public void setUnit(TimeUnit unit) {

		this.unit = unit;
	}

}
