package fr.istic.m2gl.gli.server;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;

import fr.istic.m2gl.gli.shared.Car;
import fr.istic.m2gl.gli.shared.CarItf;
import fr.istic.m2gl.gli.shared.Event;
import fr.istic.m2gl.gli.shared.EventItf;
import fr.istic.m2gl.gli.shared.Participant;

/**
 * TAA - TP 1 - JPA test : Event and car pooling manager.
 * @author Victor PETIT - Amandine MANCEAU 
 */
public class EventList {
	
	public EntityManager manager;
	EntityTransaction tx;
	
	public EventList(EntityManager manager, EntityTransaction tx){
		this.manager = manager;
		this.tx = tx;
	}
	
	public void addEvent(String date, String place){
		EventItf event;
		try {
			event = new Event();
			event.setDate(date);
			event.setPlace(place);
			tx.begin();
			manager.persist(event);
		} catch (NoResultException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			tx.commit();
		}
	}
	
	public List<Event> getEvents(){
		//return the list of all events
		return manager.createQuery("FROM Event").getResultList();
	}
	
	public Event getEvent(int id){
		//return the id selected event 
		Event event = (Event) manager.createQuery(
				"FROM Event WHERE id="+id).getSingleResult();
		return(event);
	}
	
	public Participant addParticipant(int idEvent, String name){
		tx.begin();
		Event event = getEvent(idEvent);
		Participant participant = new Participant();
		participant.setName(name);
		participant.setEvent(event);
		manager.persist(participant);
		tx.commit();
		return participant;
	}
	

	
	public void addCar(int idEvent, int nbSeat){
		tx.begin();
		Event event = getEvent(idEvent);
		Car car = new Car();
		car.setSeat(nbSeat);
		car.setEvent(event);
		manager.persist(car);
		tx.commit();
	}
	
	public void addToCar(int idCar, Participant participant){
		tx.begin();
		Car car = (Car) manager.createQuery(
				"FROM Car WHERE id="+idCar).getSingleResult();
		//List<Participant> participants = (List<Participant>) manager.createQuery("FROM Participant").getResultList();
		//Participant participant = participants.get(0);
		participant.setCar(car);
		car.getPassengers().add(participant);
		car.takeSeat();
		tx.commit();
	}
	
}
