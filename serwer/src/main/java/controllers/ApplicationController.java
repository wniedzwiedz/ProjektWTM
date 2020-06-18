package controllers;

import models.Application;
import services.ApplicationService;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/applications")
public class ApplicationController {

    @Inject
    private ApplicationService applicationService;

    // pobiera wszystkie aplikacje, aplikacje po nazwie (like case-insensitive), aplikacje uzytkownika
    @Path("/")
    @GET
    @Produces("application/json")
    public Response getAll(@QueryParam("name") String name, @QueryParam("user") int userId) {
        List<Application> applications;
        if (name != null) {
            applications = applicationService.getByNameLike(name);
        } else if (userId != 0) {
            applications = applicationService.getByUserId(userId);
        } else {
            applications = applicationService.getAll();
        }
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (Application u : applications) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("id", u.getId());
            objectBuilder.add("name", u.getName());
            builder.add(objectBuilder);
        }
        return Response.status(200).entity(builder.build()).build();
    }

    // pobiera (jedna) aplikacje po id
    @Path("/{id}")
    @GET
    @Produces("application/json")
    public Response getOneById(@PathParam("id") String id) {
        try {
            int idInt = Integer.parseInt(id);
            Application application = applicationService.getById(idInt);
            if (application != null) {
                JsonArrayBuilder builder = Json.createArrayBuilder();
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("id", application.getId());
                objectBuilder.add("name", application.getName());
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

    // tworzy aplikacje na podstawie jsona
    @Path("/")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response add(Application application) {
        try {
            Application newApplication = applicationService.insert(application);
            JsonArrayBuilder builder = Json.createArrayBuilder();
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("id", newApplication.getId());
            objectBuilder.add("name", newApplication.getName());
            builder.add(objectBuilder);
            return Response.status(201).entity(builder.build()).build();
        } catch (Exception e) {
            return Response.status(500).build();
        }
    }

    // aktualizuje aplikacje na podstawie jsona i id
    @Path("/{id}")
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Response update(@PathParam("id") String id, Application application) {
        try {
            int idInt = Integer.parseInt(id);
            if (application.getId() == 0 || application.getId() == idInt) {
                application.setId(idInt);
                Application a = applicationService.update(application);
                if (a == null) {
                    return Response.status(204).build();
                }
                JsonArrayBuilder builder = Json.createArrayBuilder();
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("id", a.getId());
                objectBuilder.add("name", a.getName());
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

    // usuwa aplikacje na podstawie id
    @Path("/{id}")
    @DELETE
    @Produces("application/json")
    public Response delete(@PathParam("id") String id) {
        try {
            int idInt = Integer.parseInt(id);
            Application application = applicationService.deleteById(idInt);
            if (application == null) {
                return Response.status(204).build();
            }
            JsonArrayBuilder builder = Json.createArrayBuilder();
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("id", application.getId());
            objectBuilder.add("name", application.getName());
            builder.add(objectBuilder);
            return Response.status(200).entity(builder.build()).build();
        } catch (NumberFormatException e) {
            return Response.status(400).build();
        } catch (Exception e) {
            return Response.status(500).build();
        }
    }

}
