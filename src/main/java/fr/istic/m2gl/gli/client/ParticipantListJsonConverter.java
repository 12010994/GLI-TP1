package fr.istic.m2gl.gli.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;

import fr.istic.m2gl.gli.shared.EventListItf;
import fr.istic.m2gl.gli.shared.MyFactory;
import fr.istic.m2gl.gli.shared.Participant;
import fr.istic.m2gl.gli.shared.ParticipantListItf;

public class ParticipantListJsonConverter {

	private ParticipantListJsonConverter() {
	}

	MyFactory factory = GWT.create(MyFactory.class);
	
	private static ParticipantListJsonConverter instance = new ParticipantListJsonConverter();

	ParticipantListItf deserializeFromJson(String json) {
		AutoBean<ParticipantListItf> bean = AutoBeanCodex.decode(factory, ParticipantListItf.class, "{\"participants\": " + json+ "}");
		return bean.as();
	}
	
	public static ParticipantListJsonConverter getInstance() {
		return instance;
	}
}