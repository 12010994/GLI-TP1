package fr.istic.m2gl.gli.shared;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;


public interface MyFactory extends AutoBeanFactory {
	AutoBean<EventItf> event();

	AutoBean<ParticipantItf> part();

	AutoBean<CarItf> car1();
	
	AutoBean<EventListItf>  eventListItf();
	
	AutoBean<ParticipantListItf> participantListItf();
	
	AutoBean<CarListItf> carListItf();
}