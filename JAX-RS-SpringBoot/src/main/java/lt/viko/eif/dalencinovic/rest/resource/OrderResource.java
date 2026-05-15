package lt.viko.eif.dalencinovic.rest.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.container.AsyncResponse;
import jakarta.ws.rs.container.Suspended;
import lt.viko.eif.dalencinovic.rest.model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/orders")
public class OrderResource {

    private static List<Order> orderList;
    static {
        orderList=new ArrayList<>();
    }

    @GET
    public void read(@Suspended final AsyncResponse asyncResponse){
        asyncResponse.resume(orderList);
    }

    @POST
    public Order addNewOrder(Order order){
        orderList.add(order);
        return order;
    }

    @GET
    @Path("{orderNumber}")
    public Order getOrderById(@PathParam("orderNumber") int orderNumber){
        return orderList.stream()
                .filter(order -> order.getOrderNumber()==orderNumber)
                .collect(Collectors.toList()).get(0);
    }

    @GET
    @Path("/paid")
    public List<Order> readPaidOrders(){
        return orderList.stream()
                .filter(Order::isPaid)
                .collect(Collectors.toList());
    }

}
