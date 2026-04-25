package lt.viko.eif.dalencinovic.webservice.soap;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import lt.viko.order_service.schema.GetAllOrdersRequest;
import lt.viko.order_service.schema.GetAllOrdersResponse;

@WebService(
        name = "OrdersPort",
        targetNamespace = OrderWebService.NAMESPACE,
        serviceName = "OrderService"
)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL,
parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface OrderWebService {

    String NAMESPACE = "http://www.viko.lt/order-service/schema";

    @WebMethod(operationName = "getAllOrders")
    @WebResult(name = "getAllOrdersResponse", targetNamespace = NAMESPACE)
    GetAllOrdersResponse getAllOrders(
            @WebParam(name = "getAllOrdersRequest", targetNamespace = NAMESPACE)
            GetAllOrdersRequest request
    );
}
