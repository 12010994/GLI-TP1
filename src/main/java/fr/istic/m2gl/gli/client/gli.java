package fr.istic.m2gl.gli.client;

import java.util.List;

import org.hibernate.ejb.event.ListenerCallback;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import fr.istic.m2gl.gli.shared.CarItf;
import fr.istic.m2gl.gli.shared.Event;
import fr.istic.m2gl.gli.shared.EventItf;
import fr.istic.m2gl.gli.shared.EventListItf;
import fr.istic.m2gl.gli.shared.ParticipantItf;

/**
 * This class contains the Entry point of the GWT application.
 */
public class gli implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	private RootPanel root = RootPanel.get();

	private FlexTable tableEvents;
	
	public Widget searchWidget(){
		HorizontalPanel hp = new HorizontalPanel();
		final Button sendButton = new Button("get event");
		final Button allButton = new Button("all events");
		final TextBox idEventArea = new TextBox();
		final Label label = new Label("id de l'event");
		idEventArea.setValue("1");
		sendButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent clickEvent) {
				RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, GWT
						.getHostPageBaseURL() + "rest/events/"+idEventArea.getText());
				rb.setCallback(new RequestCallback() {

					public void onResponseReceived(Request request, Response response) {

						EventItf event = EventJsonConverter.getInstance()
								.deserializeFromJson(response.getText());
						showEvent(event, 1);
					}

					public void showEvent(EventItf event, int i){
						VerticalPanel wrapper = new VerticalPanel();
						clearTable();
						tableEvents.setText(i, 0, Integer.toString(event.getId()));
						tableEvents.setText(i, 1, event.getPlace());
						tableEvents.setText(i, 2, event.getDate());
						tableEvents.setText(i, 3, Integer.toString(event.getParticipants().size()));
						tableEvents.setWidget(i, 4, addParticipantWidget(event.getId()));
						tableEvents.setText(i, 5, Integer.toString(event.getCars().size()));
						wrapper.add(addCarWidget(event.getId()));
						wrapper.add(addPassengerWidget(event.getId(),1));
						tableEvents.setWidget(i, 6, wrapper);
						
					}
					
					public void clearTable(){
						int count = tableEvents.getRowCount()-1;
						while (count > 0) {
						   tableEvents.removeRow(count);
						   count--;
						}
					}

					public void onError(Request request, Throwable exception) {
						Window.alert(exception.getMessage());
					}
				});

				try {
					rb.send();
				} catch (RequestException e) {
					e.printStackTrace();
				}
			}
		});
		
		allButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent clickEvent) {
				RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, GWT
						.getHostPageBaseURL() + "rest/events");
				rb.setCallback(new RequestCallback() {

					public void onResponseReceived(Request request, Response response) {

						EventListItf eventsObject = EventListJsonConverter.getInstance()
								.deserializeFromJson(response.getText());
						List<EventItf> events = eventsObject.getevents();
						int i = 1;
						for (EventItf event : events ){
							showEvent(event, i);
							i++;
						}
					}

					public void showEvent(EventItf event, int i){
						VerticalPanel wrapper = new VerticalPanel();
						tableEvents.setText(i, 0, Integer.toString(event.getId()));
						tableEvents.setText(i, 1, event.getPlace());
						tableEvents.setText(i, 2, event.getDate());
						tableEvents.setText(i, 3, Integer.toString(event.getParticipants().size()));
						//CarItf cars = CarJsonConverter.getInstance().deserializeFromJson(event.getCars());
						//cars = event.getCars().get(1);
						//Window.alert(Integer.toString(cars.getId()));
						//Window.alert(Integer.toString(event.getCars().get(1).getId()));
						//tableEvents.setText(i, 4, event.getParticipants().get(0).getName());
						tableEvents.setWidget(i, 4, addParticipantWidget(event.getId()));
						tableEvents.setText(i, 5, Integer.toString(event.getCars().size()));
						wrapper.add(addCarWidget(event.getId()));
						wrapper.add(addPassengerWidget(event.getId(),1));
						tableEvents.setWidget(i, 6, wrapper);
					}

					public void onError(Request request, Throwable exception) {
						Window.alert(exception.getMessage());
					}
				});

				try {
					rb.send();
				} catch (RequestException e) {
					e.printStackTrace();
				}
			}
		});
		hp.add(label);
		hp.add(idEventArea);
		hp.add(sendButton);
		hp.add(allButton);
		return hp;
	}

	public Widget displayWidget(){
		FlowPanel fp = new FlowPanel();

		tableEvents = new FlexTable();
		tableEvents.setTitle("Events");	
		tableEvents.setBorderWidth(1);
		tableEvents.setText(0, 0,"Id");
		tableEvents.setText(0, 1,"Evenement");
		tableEvents.setText(0, 2,"Date/Heure");
		tableEvents.setText(0, 3,"Nombre de participants");
		tableEvents.setText(0, 4,"Liste des participants");
		tableEvents.setText(0, 5,"Nombre de voitures");
		tableEvents.setText(0, 6,"Liste des voitures");
		tableEvents.setCellSpacing(2);
		fp.add(tableEvents);
		return fp;
	}

	public Widget addEventWidget(){
		VerticalPanel vp = new VerticalPanel();
		Label title = new HTML("<h3>Ajouter un événement</h3>");

		HorizontalPanel hp1 = new HorizontalPanel();
		Label nameEventLabel = new Label("Nom et lieu :");
		final TextBox nameEventTextBox = new TextBox();
		hp1.add(nameEventLabel);
		hp1.add(nameEventTextBox);

		HorizontalPanel hp2 = new HorizontalPanel();
		Label dateEventLabel = new Label("Date et heure :");
		final TextBox dateEventTextBox = new TextBox();
		hp2.add(dateEventLabel);
		hp2.add(dateEventTextBox);

		Button sendButton = new Button("Creer");
		sendButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent clickEvent) {

				EventItf event = EventJsonConverter.getInstance().makeEvent();
				event.setDate(dateEventTextBox.getText());
				event.setPlace(nameEventTextBox.getText());

				RequestBuilder rb = new RequestBuilder(RequestBuilder.POST,
						GWT.getHostPageBaseURL()+ "rest/addEvent");
				rb.setHeader("Content-Type", "application/json");
				rb.setRequestData(EventJsonConverter.getInstance().serializeToJson(event));
				rb.setCallback(new RequestCallback() {
					public void onResponseReceived(Request request, Response response) {
					}
					public void onError(Request request, Throwable exception) {
						Window.alert(exception.getMessage());
					}
				});
				try {
					rb.send();
				} catch (RequestException e) {
					e.printStackTrace();
				}
			}
		});
		vp.add(title);
		vp.add(hp1);
		vp.add(hp2);
		vp.add(sendButton);
		return vp;
	}
	
	public Widget addParticipantWidget(final int idEvent){
		final Panel root = new HorizontalPanel();
		final Button addParticipantButton = new Button("+");
		final Button sendButton = new Button("Ajouter");
		
		final HorizontalPanel hp1 = new HorizontalPanel();
		final Label nameLabel = new Label("Nom:");
		final TextBox nameTextBox = new TextBox();
		hp1.add(nameLabel);
		hp1.add(nameTextBox);
		hp1.add(sendButton);
		
		addParticipantButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent clickEvent) {
				root.add(hp1);
				root.remove(addParticipantButton);
			}
		});
		sendButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent clickEvent) {
				
				RequestBuilder rb = new RequestBuilder(RequestBuilder.POST,
						GWT.getHostPageBaseURL()+ "rest/participate/"+nameTextBox.getText()+"/"+idEvent);
				rb.setCallback(new RequestCallback() {
					public void onResponseReceived(Request request, Response response) {
					}
					public void onError(Request request, Throwable exception) {
						Window.alert(exception.getMessage());
					}
				});
				try {
					rb.send();
					root.remove(hp1);
					root.add(addParticipantButton);
				} catch (RequestException e) {
					e.printStackTrace();
				}
			}
		});
		
		root.add(addParticipantButton);
		return root;
				
	}
	
	public Widget addPassengerWidget(final int idEvent, final int idCar){
		final Panel root = new HorizontalPanel();
		final Button addPassengerButton = new Button("+");
		final Button sendButton = new Button("Ajouter");
		
		final HorizontalPanel hp1 = new HorizontalPanel();
		final Label nameLabel = new Label("Nom:");
		final TextBox nameTextBox = new TextBox();
		hp1.add(nameLabel);
		hp1.add(nameTextBox);
		hp1.add(sendButton);
		
		addPassengerButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent clickEvent) {
				root.add(hp1);
				root.remove(addPassengerButton);
			}
		});
		
		sendButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent clickEvent) {
				String namePassenger = nameTextBox.getText();
				RequestBuilder rb = new RequestBuilder(RequestBuilder.POST,
						GWT.getHostPageBaseURL()+ "rest/addPassenger/"+nameTextBox.getText()+"/"+idCar);
				rb.setCallback(new RequestCallback() {
					public void onResponseReceived(Request request, Response response) {
					}
					public void onError(Request request, Throwable exception) {
						Window.alert(exception.getMessage());
					}
				});
				try {
					rb.send();
					root.remove(hp1);
					root.add(addPassengerButton);
				} catch (RequestException e) {
					e.printStackTrace();
				}
			}
		});
		
		root.add(addPassengerButton);
		return root;
				
	}
	
	public Widget addCarWidget(final int idEvent){
		final Panel root = new HorizontalPanel();
		final Button addPassengerButton = new Button("Ajouter voiture");
		final Button sendButton = new Button("Ajouter");
		
		final HorizontalPanel hp1 = new HorizontalPanel();
		final Label nameLabel = new Label("Nombre de places:");
		final TextBox nbPlaceTextBox = new TextBox();
		hp1.add(nameLabel);
		hp1.add(nbPlaceTextBox);
		hp1.add(sendButton);
		
		addPassengerButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent clickEvent) {
				root.add(hp1);
				root.remove(addPassengerButton);
			}
		});
		
		sendButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent clickEvent) {
				
				RequestBuilder rb = new RequestBuilder(RequestBuilder.POST,
						GWT.getHostPageBaseURL()+ "rest/addCar/"+nbPlaceTextBox.getText()+"/"+idEvent);
				rb.setCallback(new RequestCallback() {
					public void onResponseReceived(Request request, Response response) {
					}
					public void onError(Request request, Throwable exception) {
						Window.alert(exception.getMessage());
					}
				});
				try {
					rb.send();
					root.remove(hp1);
					root.add(addPassengerButton);
				} catch (RequestException e) {
					e.printStackTrace();
				}
			}
		});
		
		root.add(addPassengerButton);
		return root;
				
	}

	public void onModuleLoad() {
		Panel panel = new HorizontalPanel();
		VerticalPanel Vpanel = new VerticalPanel();
		Vpanel.add(searchWidget());
		Vpanel.add(displayWidget());
		panel.add(Vpanel);
		panel.add(addEventWidget());
		root.add(panel);
	}

}
