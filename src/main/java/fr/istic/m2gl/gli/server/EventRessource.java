package fr.istic.m2gl.gli.server;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import fr.istic.m2gl.gli.shared.Event;
import fr.istic.m2gl.gli.shared.EventItf;
import fr.istic.m2gl.gli.shared.ParticipantItf;

@Path("/events")
public class EventRessource {

	@GET
	@Produces({MediaType.APPLICATION_JSON })
	public List<Event> getEvents(){
		System.out.println("Returns the events list");
		return JpaTest.eventList.getEvents();
	}
	
	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.TEXT_HTML })
	public EventItf getEvent(@PathParam("id")int idEvent){
		return JpaTest.eventList.getEvent(idEvent);
	}
	
	@POST
	public void addEvent(@FormParam("date")String date, @FormParam("place")String place){
		JpaTest.eventList.addEvent(date, place);
	}
	
	@POST
	@Path("/toto")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public void participateWithCar(int idEvent, ParticipantItf participant){
		System.out.println("Adds a participant with a car");
	}
	
	@POST
	@Path("/titi")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public void participate(int idEvent, ParticipantItf participant){
		System.out.println("Adds a participant with no car");
	}

}
