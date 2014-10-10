package fr.istic.m2gl.gli.client;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;

import fr.istic.m2gl.gli.shared.CarItf;
import fr.istic.m2gl.gli.shared.MyFactory;

public class CarJsonConverter {

	private CarJsonConverter() {
	}
	
	private static CarJsonConverter instance = new CarJsonConverter();
	
	
	  // Instantiate the factory
	 MyFactory factory = GWT.create(MyFactory.class);
	  // In non-GWT code, use AutoBeanFactorySource.create(MyFactory.class);

	  public CarItf makeCar() {
	    // Construct the AutoBean
	    AutoBean<CarItf> car = factory.car1	();

	    // Return the Book interface shim
	    return car.as();
	  }

	  String serializeToJson(CarItf car) {
	    // Retrieve the AutoBean controller
	    AutoBean<CarItf> bean = AutoBeanUtils.getAutoBean(car);

	    return AutoBeanCodex.encode(bean).getPayload();
	  }

	  CarItf deserializeFromJson(String json) {
	    AutoBean<CarItf> bean = AutoBeanCodex.decode(factory, CarItf.class, json);
	    return bean.as();
	  }

	public static CarJsonConverter getInstance() {
		return instance;
	}
}