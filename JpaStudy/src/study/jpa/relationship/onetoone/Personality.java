package study.jpa.relationship.onetoone;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PERSONALITY")
public class Personality {
	@Id
	@GeneratedValue
	private long id;
	private double happiness;
	private double madness;
	private double sadness;

	public Personality(double happiness, double madness, double sadness) {
		super();
		this.happiness = happiness;
		this.madness = madness;
		this.sadness = sadness;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getHappiness() {
		return happiness;
	}

	public void setHappiness(double happiness) {
		this.happiness = happiness;
	}

	public double getMadness() {
		return madness;
	}

	public void setMadness(double madness) {
		this.madness = madness;
	}

	public double getSadness() {
		return sadness;
	}

	public void setSadness(double sadness) {
		this.sadness = sadness;
	}

}
