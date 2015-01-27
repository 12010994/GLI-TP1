package fr.istic.m2gl.gli.client;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;

import fr.istic.m2gl.gli.shared.CarListItf;
import fr.istic.m2gl.gli.shared.MyFactory;

public class CarListJsonConverter {

	private CarListJsonConverter() {
	}

	MyFactory factory = GWT.create(MyFactory.class);
	
	private static CarListJsonConverter instance = new CarListJsonConverter();

	CarListItf deserializeFromJson(String json) {
		AutoBean<CarListItf> bean = AutoBeanCodex.decode(factory, CarListItf.class, "{\"cars\": " + json+ "}");
		return bean.as();
	}
	
	public static CarListJsonConverter getInstance() {
		return instance;
	}
}