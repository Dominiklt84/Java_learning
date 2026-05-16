package lt.viko.eif.dalencinovic.rest;

import lt.viko.eif.dalencinovic.rest.resource.CarsResource;
import lt.viko.eif.dalencinovic.rest.resource.OrderResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JaxRsSpringBootApplication extends ResourceConfig {

	public JaxRsSpringBootApplication(){
		register(OrderResource.class);
		register(CarsResource.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(JaxRsSpringBootApplication.class, args);
	}

}
