package fr.istic.m2gl.gli.shared;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * The Class Event.
 * @author Victor PETIT - Amandine MANCEAU
 * 
 */
@Entity
public class Event implements EventItf {

	/** The unique id of the event. */
	private int id;
	
	/** The date of the event. */
	private String date;
	
	/** The place of the event. */
	private String place;
	
	/** The participants list. */
	private List <Participant> participants = new ArrayList<Participant>();
	
	/** The cars list. */
	private List<Car> cars = new ArrayList<Car>();
	
	/* (non-Javadoc)
	 * @see fr.istic.m2gl.gli.shared.EventItf#getId()
	 */
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}


	/* (non-Javadoc)
	 * @see fr.istic.m2gl.gli.shared.EventItf#setId(int)
	 */
	public void setId(int id) {
		this.id = id;
	}

	
	/* (non-Javadoc)
	 * @see fr.istic.m2gl.gli.shared.EventItf#getDate()
	 */
	public String getDate() {
		return date;
	}

	
	/* (non-Javadoc)
	 * @see fr.istic.m2gl.gli.shared.EventItf#setDate(java.lang.String)
	 */
	public void setDate(String date) {
		this.date = date;
	}

	
	/* (non-Javadoc)
	 * @see fr.istic.m2gl.gli.shared.EventItf#getPlace()
	 */
	public String getPlace() {
		return place;
	}

	
	/* (non-Javadoc)
	 * @see fr.istic.m2gl.gli.shared.EventItf#setPlace(java.lang.String)
	 */
	public void setPlace(String place) {
		this.place = place;
	}

	
	/* (non-Javadoc)
	 * @see fr.istic.m2gl.gli.shared.EventItf#getParticipants()
	 */
	@OneToMany(cascade=CascadeType.REMOVE)
	@JoinColumn(name="eventId")
	public List<Participant> getParticipants() {
		return participants;
	}

	
	/* (non-Javadoc)
	 * @see fr.istic.m2gl.gli.shared.EventItf#setParticipants(java.util.List)
	 */
	public void setParticipants(List<Participant> participants) {
		this.participants = participants;
	}

	
	/* (non-Javadoc)
	 * @see fr.istic.m2gl.gli.shared.EventItf#getCars()
	 */
	@OneToMany(mappedBy="event1")
	public List<Car> getCars() {
		return cars;
	}

	
	/* (non-Javadoc)
	 * @see fr.istic.m2gl.gli.shared.EventItf#setCars(java.util.List)
	 */
	public void setCars(List<Car> cars) {
		this.cars = cars;
	}
	
}
