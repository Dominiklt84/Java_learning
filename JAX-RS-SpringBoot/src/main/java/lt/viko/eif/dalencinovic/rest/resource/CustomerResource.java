package lt.viko.eif.dalencinovic.rest.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("customers")
public class CustomerResource {

    @GET
    @Path("{id}")
    public String getCustomer(@PathParam("id") int id) {
        return "Customer: " + id;
    }
}