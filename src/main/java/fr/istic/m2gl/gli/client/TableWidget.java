package fr.istic.m2gl.gli.client;

import java.util.List;

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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import fr.istic.m2gl.gli.shared.CarItf;
import fr.istic.m2gl.gli.shared.CarListItf;
import fr.istic.m2gl.gli.shared.EventItf;
import fr.istic.m2gl.gli.shared.ParticipantItf;
import fr.istic.m2gl.gli.shared.ParticipantListItf;

public class TableWidget extends Widget {

	public FlexTable tableEvents;
	private FlowPanel fp = new FlowPanel();

	public Widget addTableWidget() {
		tableEvents = new FlexTable();
		tableEvents.setTitle("Events");	
		tableEvents.setBorderWidth(1);
		tableEvents.setText(0, 0,"Id");
		tableEvents.setText(0, 1,"Evenement");
		tableEvents.setText(0, 2,"Date/Heure");
		tableEvents.setText(0, 3,"Nb participants");
		tableEvents.setText(0, 4,"Liste des participants");
		tableEvents.setText(0, 5,"Nb voitures");
		tableEvents.setText(0, 6,"Liste des voitures");
		tableEvents.addStyleName("tableEvents");  
		fp.add(tableEvents);
		return fp;
	}	
	
	public void showEvent(final EventItf event, int i){
		final VerticalPanel wrapperParticipants = new VerticalPanel();
		final VerticalPanel wrapperCars = new VerticalPanel();
		tableEvents.setText(i, 0, Integer.toString(event.getId()));
		tableEvents.setText(i, 1, event.getPlace());
		tableEvents.setText(i, 2, event.getDate());
		tableEvents.setText(i, 3, Integer.toString(event.getParticipants().size()));
		RequestBuilder requestParticipants = new RequestBuilder(RequestBuilder.GET, GWT
				.getHostPageBaseURL() + "rest/events/"+event.getId()+"/participants");
		requestParticipants.setCallback(new RequestCallback() {
			
			public void onResponseReceived(Request request, Response response) {
				ParticipantListItf participantsObject = ParticipantListJsonConverter.getInstance().deserializeFromJson(response.getText());
				List<ParticipantItf> participants = participantsObject.getParticipants();
				for(ParticipantItf p : participants){
					wrapperParticipants.add(new Label(p.getName()));
				}
			}
			
			public void onError(Request request, Throwable exception) {
				Window.alert(exception.getMessage());
			}
		});
		
		try {
			requestParticipants.send();
		} catch (RequestException e) {
			e.printStackTrace();
		}
		wrapperParticipants.add(addParticipantWidget(event.getId()));
		tableEvents.setWidget(i, 4, wrapperParticipants);
		tableEvents.setText(i, 5, Integer.toString(event.getCars().size()));
		RequestBuilder requestCars = new RequestBuilder(RequestBuilder.GET, GWT
				.getHostPageBaseURL() + "rest/events/"+event.getId()+"/cars");
		requestCars.setCallback(new RequestCallback() {
			
			public void onResponseReceived(Request request, Response response) {
				CarListItf carsObject = CarListJsonConverter.getInstance().deserializeFromJson(response.getText());
				List<CarItf> cars = carsObject.getCars();
				for(CarItf c : cars){
					HorizontalPanel hp = new HorizontalPanel();
					hp.add(new Label("Car id:"+Integer.toString(c.getId())+" - Places:"+c.getSeat()));
					hp.add(addPassengerWidget(event.getId(), c.getId()));
					wrapperCars.add(hp);
				}
			}
			
			public void onError(Request request, Throwable exception) {
				Window.alert(exception.getMessage());
			}
		});
		
		try {
			requestCars.send();
		} catch (RequestException e) {
			e.printStackTrace();
		}
		wrapperCars.add(addCarWidget(event.getId()));
	
		tableEvents.setWidget(i, 6, wrapperCars);
	}

	public void clearTable(){
		int count = tableEvents.getRowCount()-1;
		while (count > 0) {
			tableEvents.removeRow(count);
			count--;
		}
	}

	public Widget addPassengerWidget(final int idEvent, final int idCar){
		final Panel root = new HorizontalPanel();
		final Button addPassengerButton = new Button("Monter");
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

	public Widget addParticipantWidget(final int idEvent){
		final Panel root = new HorizontalPanel();
		final Button addParticipantButton = new Button("Ajouter");
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

}
