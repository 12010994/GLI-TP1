package fr.istic.m2gl.gli.shared;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;

public interface CarItf {

	public abstract int getId();

	public abstract void setId(int id);

	//public abstract Event getEvent();

	//public abstract void setEvent(Event event);

	public abstract List<Participant> getPassengers();

	public abstract void setPassengers(List<Participant> passengers);

	public abstract int getSeat();

	public abstract void setSeat(int seat);

}