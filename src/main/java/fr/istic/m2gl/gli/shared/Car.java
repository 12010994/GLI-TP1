package fr.istic.m2gl.gli.shared;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * The Class Car.
 * @author Victor PETIT - Amandine MANCEAU
 * 
 */
@Entity
public class Car implements CarItf {
	
	/** The id. */
	private int id;
	
	/** The remaining seat. */
	private int seat;
	
	/** the participated event. */
	private Event event;
	
	/** the list of passengers */
	private List<Participant> passengers =  new ArrayList<Participant>();
	
	
	/* (non-Javadoc)
	 * @see fr.istic.m2gl.gli.shared.CarItf#getId()
	 */
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	
	/* (non-Javadoc)
	 * @see fr.istic.m2gl.gli.shared.CarItf#setId(int)
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	@ManyToOne
	@JsonIgnore
	private Event getEvent1() {
		return event;
	}

	private void setEvent1(Event event) {
		this.event = event;
	}
	
	/* (non-Javadoc)
	 * @see fr.istic.m2gl.gli.shared.CarItf#getEvent()
	 */
	@Transient
	@JsonIgnore
	public Event getEvent() {
		return event;
	}

	/* (non-Javadoc)
	 * @see fr.istic.m2gl.gli.shared.CarItf#setEvent(fr.istic.m2gl.gli.shared.Event)
	 */
	public void setEvent(Event event) {
		this.setEvent1(event);
		event.getCars().add(this);
	}

	/* (non-Javadoc)
	 * @see fr.istic.m2gl.gli.shared.CarItf#getPassengers()
	 */
	@OneToMany(mappedBy="car")
	public List<Participant> getPassengers() {
		return passengers;
	}

	/* (non-Javadoc)
	 * @see fr.istic.m2gl.gli.shared.CarItf#setPassengers(java.util.List)
	 */
	public void setPassengers(List<Participant> passengers) {
		this.passengers = passengers;
	}

	/* (non-Javadoc)
	 * @see fr.istic.m2gl.gli.shared.CarItf#getSeat()
	 */
	public int getSeat() {
		return seat;
	}

	/* (non-Javadoc)
	 * @see fr.istic.m2gl.gli.shared.CarItf#setSeat(int)
	 */
	public void setSeat(int seat) {
		this.seat = seat;
	}
	
	/* (non-Javadoc)
	 * @see fr.istic.m2gl.gli.shared.CarItf#takeSeat()
	 */
	public void takeSeat() {
		seat--;
	}
	
}
