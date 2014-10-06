package fr.istic.m2gl.gli.client;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import fr.istic.m2gl.gli.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

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
      AbsolutePanel p = new AbsolutePanel();
      p.setHeight("500px");
      p.setHeight("500px");
      final TextBox tb = new TextBox();
      p.add(tb,20,20);

      tb.addChangeHandler(new ChangeHandler() {
          public void onChange(ChangeEvent changeEvent) {
              Window.alert(tb.getText());
          }
      });
      com.google.gwt.user.client.ui.Button b = new Button();
      b.setText("getEvents()");
      p.add(b,20, 50);
      RootPanel.get().add(p);
  }
}
