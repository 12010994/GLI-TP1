package fr.istic.m2gl.gli.client;

import java.util.List;

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
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import fr.istic.m2gl.gli.shared.EventItf;
import fr.istic.m2gl.gli.shared.EventListItf;

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

	private TableWidget tableEvents;

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

						tableEvents.clearTable();
						tableEvents.showEvent(event, 1);
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
				RequestBuilder eventsRequest = new RequestBuilder(RequestBuilder.GET, GWT
						.getHostPageBaseURL() + "rest/events");
				eventsRequest.setCallback(new RequestCallback() {

					public void onResponseReceived(Request request, Response response) {

						EventListItf eventsObject = EventListJsonConverter.getInstance()
								.deserializeFromJson(response.getText());
						List<EventItf> events = eventsObject.getevents();
						int i = 1;
						for (EventItf event : events ){
							tableEvents.showEvent(event, i);
							i++;
						}
					}

					public void onError(Request request, Throwable exception) {
						Window.alert(exception.getMessage());
					}
				});

				try {
					eventsRequest.send();
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
		vp.setStyleName("addEvent");
		return vp;
	}

	public void onModuleLoad() {
		Panel panel = new HorizontalPanel();
		VerticalPanel consulterEventsPanel = new VerticalPanel();
		Label title = new HTML("<h3>Consulter les événements</h3>");
		consulterEventsPanel.add(title);
		consulterEventsPanel.add(searchWidget());
		tableEvents = new TableWidget();
		consulterEventsPanel.add(tableEvents.addTableWidget());
		consulterEventsPanel.addStyleName("tableWidget");
		panel.add(consulterEventsPanel);
		panel.add(addEventWidget());
		root.add(panel);
	}

}
