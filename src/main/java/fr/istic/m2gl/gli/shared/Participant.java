package fr.istic.m2gl.gli.shared;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * The Class Participant.
 * @author Victor PETIT - Amandine MANCEAU
 * 
 */
@Entity
public class Participant implements ParticipantItf {
	
	
	/** The id. */
	private int id;
	
	/** The car owner name. */
	private String name;
	
	/** the reference to the owner car. */
	private Car car;
	
	/** the participated event. */
	private Event event;

	/* (non-Javadoc)
	 * @see fr.istic.m2gl.gli.shared.Participant#getId()
	 */
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	
	/* (non-Javadoc)
	 * @see fr.istic.m2gl.gli.shared.Participant#setId(int)
	 */
	public void setId(int id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see fr.istic.m2gl.gli.shared.Participant#getName()
	 */
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see fr.istic.m2gl.gli.shared.Participant#setName(java.lang.String)
	 */
	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see fr.istic.m2gl.gli.shared.Participant#getCar()
	 */
	@ManyToOne
	@JsonIgnore
	public Car getCar() {
		return car;
	}

	
	/* (non-Javadoc)
	 * @see fr.istic.m2gl.gli.shared.Participant#setCar(fr.istic.m2gl.gli.shared.Car)
	 */
	public void setCar(Car car) {
		this.car = car;
	}

	/* (non-Javadoc)
	 * @see fr.istic.m2gl.gli.shared.Participant#getEvent()
	 */
	@ManyToOne
	@JsonIgnore
	public Event getEvent() {
		return event;
	}

	/* (non-Javadoc)
	 * @see fr.istic.m2gl.gli.shared.Participant#setEvent(fr.istic.m2gl.gli.shared.Event)
	 */
	public void setEvent(Event event) {
		this.event = event;
		event.getParticipants().add(this);
	}
	
	
}
