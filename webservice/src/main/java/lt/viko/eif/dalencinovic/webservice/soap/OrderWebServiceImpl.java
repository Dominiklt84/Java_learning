package lt.viko.eif.dalencinovic.webservice.soap;

import jakarta.jws.WebService;
import lt.viko.order_service.schema.GetAllOrdersRequest;
import lt.viko.order_service.schema.GetAllOrdersResponse;
import lt.viko.order_service.schema.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@WebService(
        endpointInterface = "lt.viko.eif.dalencinovic.webservice.soap.OrderWebService",
        targetNamespace = OrderWebService.NAMESPACE,
        serviceName = "OrderService",
        portName = "OrdersPortSoap11"
)
public class OrderWebServiceImpl implements OrderWebService {
    @Override
    public GetAllOrdersResponse getAllOrders(GetAllOrdersRequest request) {
        GetAllOrdersResponse response = new GetAllOrdersResponse();
        Order order = new Order();
        order.setCustomerName("New Customer");
        order.setOrderNumber(1);
        order.setPaid(true);
        order.setPriorityCode("123P");
        response.getOrders().addAll(List.of(order));
        System.out.println("Fetched all orders: " + response.getOrders().size());
        return response;
    }
}
