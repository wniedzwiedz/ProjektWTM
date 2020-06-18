package controllers;

import models.Package;
import models.Package;
import services.PackageService;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/packages")
public class PackageController {

    @Inject
    private PackageService packageService;

    // pobiera wszystkie pakiety, pakiety po nazwie (like case-insensitive), pakiety z aplikacji
    @Path("/")
    @GET
    @Produces("application/json")
    public Response getAll(@QueryParam("name") String name, @QueryParam("application") int applicationId) {
        List<Package> packages;
        if (name != null) {
            packages = packageService.getByNameLike(name);
        } else if (applicationId != 0) {
            packages = packageService.getByApplicationId(applicationId);
        } else {
            packages = packageService.getAll();
        }
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (Package p : packages) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("id", p.getId());
            objectBuilder.add("name", p.getName());
            objectBuilder.add("applicationId", p.getApplication().getId());
            builder.add(objectBuilder);
        }
        return Response.status(200).entity(builder.build()).build();
    }

    // pobiera pakiet po id
    @Path("/{id}")
    @GET
    @Produces("application/json")
    public Response getOneById(@PathParam("id") String id) {
        try {
            int idInt = Integer.parseInt(id);
            Package p = packageService.getById(idInt);
            if (p != null) {
                JsonArrayBuilder builder = Json.createArrayBuilder();
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("id", p.getId());
                objectBuilder.add("name", p.getName());
                objectBuilder.add("applicationId", p.getApplication().getId());
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

    // tworzy pakiet na podstawie jsona
    @Path("/")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response add(Package aPackage) {
        try {
            Package p = packageService.insert(aPackage);
            JsonArrayBuilder builder = Json.createArrayBuilder();
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("id", p.getId());
            objectBuilder.add("name", p.getName());
            objectBuilder.add("applicationId", p.getApplication().getId());
            builder.add(objectBuilder);
            return Response.status(201).entity(builder.build()).build();
        } catch (Exception e) {
            return Response.status(500).build();
        }
    }

    // aktualizuje pakiet na podstawie jsona i id
    @Path("/{id}")
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Response update(@PathParam("id") String id, Package aPackage) {
        try {
            int idInt = Integer.parseInt(id);
            if (aPackage.getId() == 0 || aPackage.getId() == idInt) {
                aPackage.setId(idInt);
                Package p = packageService.update(aPackage);
                if (p == null) {
                    return Response.status(204).build();
                }
                JsonArrayBuilder builder = Json.createArrayBuilder();
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("id", p.getId());
                objectBuilder.add("name", p.getName());
                objectBuilder.add("applicationId", p.getApplication().getId());
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

    // usuwa pakiet na podstawie id
    @Path("/{id}")
    @DELETE
    @Produces("application/json")
    public Response delete(@PathParam("id") String id) {
        try {
            int idInt = Integer.parseInt(id);
            Package p = packageService.deleteById(idInt);
            if (p == null) {
                return Response.status(204).build();
            }
            JsonArrayBuilder builder = Json.createArrayBuilder();
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("id", p.getId());
            objectBuilder.add("name", p.getName());
            objectBuilder.add("applicationId", p.getApplication().getId());
            builder.add(objectBuilder);
            builder.add(objectBuilder);
            return Response.status(200).entity(builder.build()).build();
        } catch (NumberFormatException e) {
            return Response.status(400).build();
        } catch (Exception e) {
            return Response.status(500).build();
        }
    }

}
