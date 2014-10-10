package fr.istic.m2gl.gli.shared;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

public interface EventItf {

	public abstract int getId();

	public abstract void setId(int id);

	public abstract String getDate();

	public abstract void setDate(String date);

	public abstract String getPlace();

	public abstract void setPlace(String place);

	public abstract List<Participant> getParticipants();

	public abstract void setParticipants(List<Participant> participants);

	public abstract List<Car> getCars();

	public abstract void setCars(List<Car> cars);

}