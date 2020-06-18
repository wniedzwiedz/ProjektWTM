package controllers;

import models.Group;
import models.Payment;
import models.User;
import services.PaymentService;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/payments")
public class PaymentController {

    @Inject
    private PaymentService paymentService;

    // pobiera wszystkie platnosci, platnosci w grupie - wszystkie i niezaplacone,
    // platnosci uzytkownika wszystkie i niezaplacone
    @Path("/")
    @GET
    @Produces("application/json")
    public Response getAll(@QueryParam("user") int userId, @QueryParam("group") int groupId, @QueryParam("notPaid") boolean notPaid) {
        List<Payment> payments;
        if (userId != 0) {
            if (notPaid) {
                payments = paymentService.getByUserIdNotPaid(userId);
            } else {
                payments = paymentService.getByUserId(userId);
            }
        } else if (groupId != 0) {
            if (notPaid) {
                payments = paymentService.getByGroupIdNotPaid(groupId);
            } else {
                payments = paymentService.getByGroupId(groupId);
            }
        } else {
            payments = paymentService.getAll();
        }
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (Payment p : payments) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("id", p.getId());
            if (p.getDeadline() != null) {
                objectBuilder.add("deadline", p.getDeadline().getTime());
            }
            objectBuilder.add("price", p.getPrice());
            objectBuilder.add("isPaid", p.isPaid());
            objectBuilder.add("groupId", p.getGroup().getId());
            objectBuilder.add("userId", p.getUser().getId());
            builder.add(objectBuilder);
        }
        return Response.status(200).entity(builder.build()).build();
    }

    // pobiera platnosc po id
    @Path("/{id}")
    @GET
    @Produces("application/json")
    public Response getOneById(@PathParam("id") String id) {
        try {
            int idInt = Integer.parseInt(id);
            Payment p = paymentService.getById(idInt);
            if (p != null) {
                JsonArrayBuilder builder = Json.createArrayBuilder();
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("id", p.getId());
                if (p.getDeadline() != null) {
                    objectBuilder.add("deadline", p.getDeadline().getTime());
                }
                objectBuilder.add("price", p.getPrice());
                objectBuilder.add("isPaid", p.isPaid());
                objectBuilder.add("groupId", p.getGroup().getId());
                objectBuilder.add("userId", p.getUser().getId());
                builder.add(objectBuilder);
                return Response.status(200).entity(builder.build()).build();
            } else {
                return Response.status(404).build();
            }
        } catch (NumberFormatException e) {
            return Response.status(400).build();
        } catch (Exception e) {
            return Response.status(500).build();
        }
    }

    // tworzy platnosc na podstawie jsona
    @Path("/")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response add(Payment payment) {
        try {
            Payment p = paymentService.insert(payment);
            JsonArrayBuilder builder = Json.createArrayBuilder();
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("id", p.getId());
            if (p.getDeadline() != null) {
                objectBuilder.add("deadline", p.getDeadline().getTime());
            }
            objectBuilder.add("price", p.getPrice());
            objectBuilder.add("isPaid", p.isPaid());
            objectBuilder.add("groupId", p.getGroup().getId());
            objectBuilder.add("userId", p.getUser().getId());
            builder.add(objectBuilder);
            return Response.status(201).entity(builder.build()).build();
        } catch (Exception e) {
            return Response.status(500).build();
        }
    }

    // aktualizuje platnosc na podstawie jsona i id,
    // parametr isNotPaid pozwala na cofniecie platnosci (stwierdzenie ze platnosc jednak nie jest zrealizowana)
    @Path("/{id}")
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Response update(@PathParam("id") String id, Payment payment, @QueryParam("isNotPaid") boolean isNotPaid) {
        try {
            int idInt = Integer.parseInt(id);
            if (payment.getId() == 0 || payment.getId() == idInt) {
                payment.setId(idInt);
                Payment p = paymentService.update(payment, isNotPaid);
                if (p == null) {
                    return Response.status(204).build();
                }
                JsonArrayBuilder builder = Json.createArrayBuilder();
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("id", p.getId());
                if (p.getDeadline() != null) {
                    objectBuilder.add("deadline", p.getDeadline().getTime());
                }
                objectBuilder.add("price", p.getPrice());
                objectBuilder.add("isPaid", p.isPaid());
                objectBuilder.add("groupId", p.getGroup().getId());
                objectBuilder.add("userId", p.getUser().getId());
                builder.add(objectBuilder);
                return Response.status(200).entity(builder.build()).build();
            } else {
                throw new WebApplicationException(400);
            }
        } catch (NumberFormatException e) {
            return Response.status(400).build();
        } catch (Exception e) {
            return Response.status(500).build();
        }
    }

    // usuwa platnosc na podstawie id
    @Path("/{id}")
    @DELETE
    @Produces("application/json")
    public Response delete(@PathParam("id") String id) {
        try {
            int idInt = Integer.parseInt(id);
            Payment p = paymentService.deleteById(idInt);
            if (p == null) {
                return Response.status(204).build();
            }
            JsonArrayBuilder builder = Json.createArrayBuilder();
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("id", p.getId());
            if (p.getDeadline() != null) {
                objectBuilder.add("deadline", p.getDeadline().getTime());
            }
            objectBuilder.add("price", p.getPrice());
            objectBuilder.add("isPaid", p.isPaid());
            objectBuilder.add("groupId", p.getGroup().getId());
            objectBuilder.add("userId", p.getUser().getId());
            builder.add(objectBuilder);
            return Response.status(200).entity(builder.build()).build();
        } catch (NumberFormatException e) {
            return Response.status(400).build();
        } catch (Exception e) {
            return Response.status(500).build();
        }
    }

}
