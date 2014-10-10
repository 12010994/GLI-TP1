package fr.istic.m2gl.gli.client;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;

import fr.istic.m2gl.gli.shared.ParticipantItf;
import fr.istic.m2gl.gli.shared.MyFactory;

public class ParticipantJsonConverter {

	private ParticipantJsonConverter() {
	}
	
	private static ParticipantJsonConverter instance = new ParticipantJsonConverter();
	
	
	  // Instantiate the factory
	 MyFactory factory = GWT.create(MyFactory.class);
	  // In non-GWT code, use AutoBeanFactorySource.create(MyFactory.class);

	  public ParticipantItf makeParticipant() {
	    // Construct the AutoBean
	    AutoBean<ParticipantItf> car = factory.part();

	    // Return the Book interface shim
	    return car.as();
	  }

	  String serializeToJson(ParticipantItf car) {
	    // Retrieve the AutoBean controller
	    AutoBean<ParticipantItf> bean = AutoBeanUtils.getAutoBean(car);

	    return AutoBeanCodex.encode(bean).getPayload();
	  }

	  ParticipantItf deserializeFromJson(String json) {
	    AutoBean<ParticipantItf> bean = AutoBeanCodex.decode(factory, ParticipantItf.class, json);
	    return bean.as();
	  }

	public static ParticipantJsonConverter getInstance() {
		return instance;
	}
}