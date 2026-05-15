package lt.viko.eif.dalencinovic.rest.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.MatrixParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("cars")
public class CarsResource {

    @GET
    @Path("{brand}/{model}/{year}")
    public String getCar(
            @PathParam("brand") String brand,
            @PathParam("model") String model,
            @PathParam("year") int year,
            @MatrixParam("color") String color){

        return "brand=" + brand +
                ", model=" + model +
                ", year=" + year +
                ", color=" + color;
    }
}
