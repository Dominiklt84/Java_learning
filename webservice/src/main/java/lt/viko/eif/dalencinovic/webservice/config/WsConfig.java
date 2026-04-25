package lt.viko.eif.dalencinovic.webservice.config;

import jakarta.annotation.PostConstruct;
import jakarta.xml.ws.Endpoint;
import lt.viko.eif.dalencinovic.webservice.soap.OrderWebServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "soap.publish.enabled", havingValue = "true", matchIfMissing = true)
public class WsConfig {
    private final OrderWebServiceImpl orderWebService;
    private final String endpointUrl;
    private Endpoint endpoint;

    public WsConfig(OrderWebServiceImpl orderWebService,
                    @Value("${order.service.endpoint-url}") String endpointUrl) {
        this.orderWebService = orderWebService;
        this.endpointUrl = endpointUrl;
    }

    @PostConstruct
    public void publish(){
        endpoint = Endpoint.publish(endpointUrl, orderWebService);
        System.out.println();
        System.out.println("=================================");
        System.out.println("Order JAX-WS server started successfully");
        System.out.println("Soap endpoint: " + endpointUrl);
        System.out.println("WSD          :"+endpointUrl+"?wsdl");
        System.out.println("=================================");
        System.out.println();
    }
}
