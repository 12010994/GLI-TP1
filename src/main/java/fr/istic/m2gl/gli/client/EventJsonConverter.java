package fr.istic.m2gl.gli.client;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;

import fr.istic.m2gl.gli.shared.EventItf;
import fr.istic.m2gl.gli.shared.MyFactory;

public class EventJsonConverter {

	private EventJsonConverter() {
	}
	
	private static EventJsonConverter instance = new EventJsonConverter();
	
	
	  // Instantiate the factory
	 MyFactory factory = GWT.create(MyFactory.class);
	  // In non-GWT code, use AutoBeanFactorySource.create(MyFactory.class);

	  public EventItf makeEvent() {
	    // Construct the AutoBean
	    AutoBean<EventItf> car = factory.event();

	    // Return the Book interface shim
	    return car.as();
	  }

	  String serializeToJson(EventItf car) {
	    // Retrieve the AutoBean controller
	    AutoBean<EventItf> bean = AutoBeanUtils.getAutoBean(car);

	    return AutoBeanCodex.encode(bean).getPayload();
	  }

	  EventItf deserializeFromJson(String json) {
	    AutoBean<EventItf> bean = AutoBeanCodex.decode(factory, EventItf.class, json);
	    return bean.as();
	  }

	public static EventJsonConverter getInstance() {
		return instance;
	}
}