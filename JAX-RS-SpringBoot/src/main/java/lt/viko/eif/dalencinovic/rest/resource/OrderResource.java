package lt.viko.eif.dalencinovic.rest.resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.container.AsyncResponse;
import jakarta.ws.rs.container.Suspended;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import lt.viko.eif.dalencinovic.rest.model.Order;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Path("/orders")
public class OrderResource {

    private static List<Order> orderList;

    static {
        orderList = new ArrayList<>();
    }

    @GET
    public void read(@Suspended final AsyncResponse asyncResponse) {
        asyncResponse.resume(orderList);
    }

    @POST
    public Order addNewOrder(Order order) {
        orderList.add(order);
        return order;
    }

    @GET
    @Path("{orderNumber}")
    public Order getOrderById(@PathParam("orderNumber") int orderNumber) {
        return orderList.stream()
                .filter(order -> order.getOrderNumber() == orderNumber)
                .collect(Collectors.toList()).get(0);
    }

    @GET
    @Path("/paid")
    public List<Order> readPaidOrders() {
        return orderList.stream()
                .filter(Order::isPaid)
                .collect(Collectors.toList());
    }

    @GET
    @Path("customer")
    public String getCustomer(@HeaderParam("customerName") String customerName) {
        return "Customer: "+ customerName;
    }

    @GET
    public String getHeader(@Context HttpHeaders headers){
        for (String header : headers.getRequestHeaders().keySet()){
            System.out.print("This header was set: "+ header+ " ");
            System.out.println(headers.getRequestHeaders().get(header));
        }
        return "";
    }


}
