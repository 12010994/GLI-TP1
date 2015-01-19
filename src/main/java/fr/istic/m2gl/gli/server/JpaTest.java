package fr.istic.m2gl.gli.server;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import fr.istic.m2gl.gli.shared.CarItf;
import fr.istic.m2gl.gli.shared.Event;
import fr.istic.m2gl.gli.shared.EventItf;
import fr.istic.m2gl.gli.shared.Participant;
import fr.istic.m2gl.gli.shared.ParticipantItf;

public class JpaTest {

	public static EntityManager manager;
	public static EventService eventService;
	public static EntityTransaction tx;

	static{
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dev");
		manager = factory.createEntityManager();
		tx = manager.getTransaction(); 
		eventService = new EventService(manager, tx);
		run();
	}
	
	public static void run() {
		
		Event event1 = new Event();
		Event event2 = new Event();
		
		event1.setPlace("Evénements sur Redon");
		event1.setDate("Jeudi 16/10/14 10h");
		
		event2.setPlace("Evénement sur Rennes");
		event2.setDate("Mardi 21/10/12 16h30");
		
		eventService.addEvent(event1);
		eventService.addEvent(event2);
		
		Participant coco = eventService.addParticipant(1, "Coco");
		Participant bob = eventService.addParticipant(1, "Bob");
		Participant tom = eventService.addParticipant(2, "Tom");
		
		eventService.addCar(1, 4);
		eventService.addCar(2, 4);
		eventService.addCar(2, 4);
//		tx.begin();
//		List<Participant> participants =  manager.createQuery(
//				"SELECT p FROM Participant p").getResultList();
//		System.out.println("/////"+participants.get(0).getName());
//		Participant participant = (Participant) manager.createQuery(
//				"SELECT p FROM Participant p WHERE p.name=Bob").getSingleResult();
//		System.out.println("/////"+participant.getName());
//		tx.commit();
		eventService.addToCar(1, coco.getName());
		eventService.addToCar(1, bob.getName());
		eventService.addToCar(2, tom.getName());
		
		System.out.println("--------------------------------------");
		for(EventItf e : eventService.getEvents() ){
			System.out.println(e.getDate()+" "+e.getPlace());
			System.out.print("Participants: ");
			for(ParticipantItf p : e.getParticipants() ){
				System.out.print(p.getName()+" ");
			}
			System.out.println("\nNb cars: "+e.getCars().size());
			for(CarItf c : e.getCars() ){
				System.out.print("Car id: "+c.getId()+" - nb seats: "+c.getSeat()+" -  Passengers: ");
				for(ParticipantItf p : c.getPassengers() ){
					System.out.print(p.getName()+" ");
				}
				System.out.println();
			}
			System.out.println("\n--------------------------------------");
		}

		
		/*
		Enseignant es = (Enseignant) manager.createQuery(
				"select e1 from Enseignant as e1 where e1.nom='barais'")
				.getSingleResult();
		*/
		
	}

}
