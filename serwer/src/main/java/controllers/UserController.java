package controllers;

import models.User;
import services.UserService;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/users")
public class UserController {

    @Inject
    private UserService userService;

    // pobiera wszystkich uzytkownikow, uzytkownikow po nazwie (like case-insensitive, szuka zarowno w imieniu jak i nazwisku),
    // uzytkownikow po mailu (like case-insensitive, taki sam mail jak podany),
    // uzytkownikow z grupy (wszystkich, chcacych dolaczyc, czlonkow)
    @Path("/")
    @GET
    @Produces("application/json")
    public Response getAll(@QueryParam("name") String name, @QueryParam("email") String email,
                           @QueryParam("group") int groupId, @QueryParam("userRequested") boolean userRequested,
                           @QueryParam("userConfirmed") boolean userConfirmed) {
        List<User> users;
        if (name != null) {
            users = userService.getByNameLike(name);
        } else if (email != null) {
            users = userService.getByEMail(email);
        } else if (groupId != 0) {
            if (userRequested) {
                users = userService.getRequestedByGroupId(groupId);
            } else if (userConfirmed) {
                users = userService.getConfirmedByGroupId(groupId);
            } else {
                users = userService.getByGroupId(groupId);
            }
        } else {
            users = userService.getAll();
        }
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (User u : users) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("id", u.getId());
            objectBuilder.add("firstName", u.getFirstName());
            objectBuilder.add("lastName", u.getLastName());
            objectBuilder.add("email", u.getEmail());
            objectBuilder.add("passwordHash", u.getPasswordHash());
            builder.add(objectBuilder);
        }
        return Response.status(200).entity(builder.build()).build();
    }

    // pobiera uzytkownika po id
    @Path("/{id}")
    @GET
    @Produces("application/json")
    public Response getOneById(@PathParam("id") String id) {
        try {
            int idInt = Integer.parseInt(id);
            User user = userService.getById(idInt);
            if (user != null) {
                JsonArrayBuilder builder = Json.createArrayBuilder();
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("id", user.getId());
                objectBuilder.add("firstName", user.getFirstName());
                objectBuilder.add("lastName", user.getLastName());
                objectBuilder.add("email", user.getEmail());
                objectBuilder.add("passwordHash", user.getPasswordHash());
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

    // tworzy uzytkownika na podstawie jsona
    @Path("/")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response add(User user) {
        try {
            User newUser = userService.insert(user);
            JsonArrayBuilder builder = Json.createArrayBuilder();
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("id", newUser.getId());
            objectBuilder.add("firstName", newUser.getFirstName());
            objectBuilder.add("lastName", newUser.getLastName());
            objectBuilder.add("email", newUser.getEmail());
            objectBuilder.add("passwordHash", newUser.getPasswordHash());
            builder.add(objectBuilder);
            return Response.status(201).entity(builder.build()).build();
        } catch (Exception e) {
            if (e.getCause().getCause().getMessage().contains("Violation"))
                return Response.status(409).build();
            return Response.status(500).build();
        }
    }

    // aktualizuje uzytkownika na podstawie jsona i id
    @Path("/{id}")
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Response update(@PathParam("id") String id, User user) {
        try {
            int idInt = Integer.parseInt(id);
            if (user.getId() == 0 || user.getId() == idInt) {
                user.setId(idInt);
                User newUser = userService.update(user);
                if (newUser == null) {
                    return Response.status(204).build();
                }
                JsonArrayBuilder builder = Json.createArrayBuilder();
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("id", newUser.getId());
                objectBuilder.add("firstName", newUser.getFirstName());
                objectBuilder.add("lastName", newUser.getLastName());
                objectBuilder.add("email", newUser.getEmail());
                objectBuilder.add("passwordHash", newUser.getPasswordHash());
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

    // usuwa uzytkownika na podstawie id
    @Path("/{id}")
    @DELETE
    @Produces("application/json")
    public Response delete(@PathParam("id") String id) {
        try {
            int idInt = Integer.parseInt(id);
            User user = userService.deleteById(idInt);
            if (user == null) {
                return Response.status(204).build();
            }
            JsonArrayBuilder builder = Json.createArrayBuilder();
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("id", user.getId());
            objectBuilder.add("firstName", user.getFirstName());
            objectBuilder.add("lastName", user.getLastName());
            objectBuilder.add("email", user.getEmail());
            objectBuilder.add("passwordHash", user.getPasswordHash());
            builder.add(objectBuilder);
            return Response.status(200).entity(builder.build()).build();
        } catch (NumberFormatException e) {
            return Response.status(400).build();
        } catch (Exception e) {
            return Response.status(500).build();
        }
    }

}
