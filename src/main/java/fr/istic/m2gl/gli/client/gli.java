package fr.istic.m2gl.gli.client;

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
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

import fr.istic.m2gl.gli.shared.EventItf;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class gli implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

	private final Messages messages = GWT.create(Messages.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		// Create a text
		final TextBox area = new TextBox();
		area.setValue("");
		RootPanel.get().add(area);
		// Create a button
		com.google.gwt.user.client.ui.Button b = new Button();
		b.setText("getEvents()");
		RootPanel.get().add(b);
		
		b.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				
				RequestBuilder rb = new RequestBuilder(RequestBuilder.GET,
														"http://localhost:8888/rest/events");
				rb.setCallback(new RequestCallback() {
					
					public void onError(Request request, Throwable exception) {
						Window.alert(exception.getMessage());
					}
					
					public void onResponseReceived(Request request, Response response) {
						Window.alert("toto" + response.getStatusCode() + " tutu " + response.getText().length());
						
						/*	EventItf b = EventJsonConverter.getInstance()
									.deserializeFromJson(response.getText());
							Window.alert("get the event from :" + b.getId()
									+ " isbn : " + b.getPlace().toString());*/
						
					}
				});
				try {
					rb.send();
				} catch (RequestException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
