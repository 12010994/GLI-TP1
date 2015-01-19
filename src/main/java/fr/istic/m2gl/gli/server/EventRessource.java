package fr.istic.m2gl.gli.server;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import fr.istic.m2gl.gli.shared.CarItf;
import fr.istic.m2gl.gli.shared.Event;
import fr.istic.m2gl.gli.shared.EventItf;
import fr.istic.m2gl.gli.shared.Participant;
import fr.istic.m2gl.gli.shared.ParticipantItf;

@Path("/")
public class EventRessource {

	@GET
	@Path("events")
	@Produces({MediaType.APPLICATION_JSON })
	public List<Event> getEvents(){
		System.out.println("Return the events list");
		return JpaTest.eventService.getEvents();
	}
	
	@GET
	@Path("events/{id}")
	@Produces({MediaType.APPLICATION_JSON })
	public EventItf getEvent(@PathParam("id")int idEvent){
		System.out.println("Return the event selected by the id");
		return JpaTest.eventService.getEvent(idEvent);
	}
	
	@POST
	@Path("addEvent")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addEvent(Event e){
		System.out.println("Add an event to DB");
		JpaTest.eventService.addEvent(e);
	}
	
	@POST
	@Path("participate/{participant}/{idEvent}")
	@Consumes(MediaType.TEXT_PLAIN)
	public void participate(@PathParam("participant") String participant,@PathParam("idEvent") int idEvent){
		System.out.println("Add a participant to an event");
		JpaTest.eventService.addParticipant(idEvent, participant);
	}
	
	@POST
	@Path("addCar/{nbSeat}/{idEvent}")
	@Consumes(MediaType.TEXT_PLAIN)
	public void addACar(@PathParam("nbSeat") int nbSeat, @PathParam("idEvent") int idEvent){ 
		System.out.println("Add a car to an event");
		JpaTest.eventService.addCar(idEvent, nbSeat); 
	}
	
	@POST
	@Path("addPassenger/{participant}/{idCar}")
	@Consumes(MediaType.TEXT_PLAIN)
	public void addPassenger(@PathParam("participant") String participant, @PathParam("idCar") int idCar){ 
		System.out.println("Add a participant to a car");
		JpaTest.eventService.addToCar(idCar, participant);
	}
	
	
}
