package fr.istic.m2gl.gli.client;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;

import fr.istic.m2gl.gli.shared.EventListItf;
import fr.istic.m2gl.gli.shared.MyFactory;

public class EventListJsonConverter {

	private EventListJsonConverter() {
	}

	MyFactory factory = GWT.create(MyFactory.class);
	
	private static EventListJsonConverter instance = new EventListJsonConverter();

	EventListItf deserializeFromJson(String json) {
		AutoBean<EventListItf> bean = AutoBeanCodex.decode(factory, EventListItf.class, "{\"events\": " + json+ "}");
		return bean.as();
	}
	
	public static EventListJsonConverter getInstance() {
		return instance;
	}
}