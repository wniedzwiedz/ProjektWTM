package controllers;

import models.Group;
import services.GroupService;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/groups")
public class GroupController {

    @Inject
    private GroupService groupService;

    // pobiera wszystkie grupy, grupy po nazwie (like case-insensitive), grupy z pakietu,
    // grupy uzytkownika (wlasciciel, czlonek, oczekujacy na potwierdzenie dolaczenia)
    @Path("/")
    @GET
    @Produces("application/json")
    public Response getAll(@QueryParam("name") String name, @QueryParam("owner") int ownerId,
                           @QueryParam("package") int packageId, @QueryParam("user") int userId,
                           @QueryParam("userRequested") boolean userRequested, @QueryParam("userConfirmed") boolean userConfirmed) {
        List<Group> groups;
        if (name != null) {
            groups = groupService.getByNameLike(name);
        } else if (ownerId != 0) {
            groups = groupService.getByOwnerId(ownerId);
        } else if (packageId != 0) {
            groups = groupService.getByPackageId(packageId);
        } else if (userId != 0) {
            if (userRequested) {
                groups = groupService.getRequestedByUserId(userId);

            } else if (userConfirmed) {
                groups = groupService.getConfirmedByUserId(userId);

            } else {
                groups = groupService.getByUserId(userId);
            }
        } else {
            groups = groupService.getAll();
        }
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (Group g : groups) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("id", g.getId());
            objectBuilder.add("name", g.getName());
            objectBuilder.add("ownerId", g.getOwner().getId());
            if (g.getaPackage() != null && g.getaPackage().getId() != 0) {
                objectBuilder.add("packageId", g.getaPackage().getId());
            }
            if (g.getBankAccountNumber() != null) {
                objectBuilder.add("bankAccountNumber", g.getBankAccountNumber());
            }
            if (g.getMaxNumberOfMembers() != 0) {
                objectBuilder.add("maxNumberOfMembers", g.getMaxNumberOfMembers());
            }
            if (g.getLoginHash() != null) {
                objectBuilder.add("loginHash", g.getLoginHash());
            }
            if (g.getPasswordHash() != null) {
                objectBuilder.add("passwordHash", g.getPasswordHash());
            }
            if (g.getGroupHash() != null) {
                objectBuilder.add("groupHash", g.getGroupHash());
            }
            builder.add(objectBuilder);
        }
        return Response.status(200).entity(builder.build()).build();
    }

    // pobiera grupe po id
    @Path("/{id}")
    @GET
    @Produces("application/json")
    public Response getOneById(@PathParam("id") String id) {
        try {
            int idInt = Integer.parseInt(id);
            Group g = groupService.getById(idInt);
            if (g != null) {
                JsonArrayBuilder builder = Json.createArrayBuilder();
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("id", g.getId());
                objectBuilder.add("name", g.getName());
                objectBuilder.add("ownerId", g.getOwner().getId());
                if (g.getaPackage() != null && g.getaPackage().getId() != 0) {
                    objectBuilder.add("packageId", g.getaPackage().getId());
                }
                if (g.getBankAccountNumber() != null) {
                    objectBuilder.add("bankAccountNumber", g.getBankAccountNumber());
                }
                if (g.getMaxNumberOfMembers() != 0) {
                    objectBuilder.add("maxNumberOfMembers", g.getMaxNumberOfMembers());
                }
                if (g.getLoginHash() != null) {
                    objectBuilder.add("loginHash", g.getLoginHash());
                }
                if (g.getPasswordHash() != null) {
                    objectBuilder.add("passwordHash", g.getPasswordHash());
                }
                if (g.getGroupHash() != null) {
                    objectBuilder.add("groupHash", g.getGroupHash());
                }
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

    // tworzy grupe na podstawie jsona
    @Path("/")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response add(Group group) {
        try {
            Group g = groupService.insert(group);
            JsonArrayBuilder builder = Json.createArrayBuilder();
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("id", g.getId());
            objectBuilder.add("name", g.getName());
            objectBuilder.add("ownerId", g.getOwner().getId());
            if (g.getaPackage() != null && g.getaPackage().getId() != 0) {
                objectBuilder.add("packageId", g.getaPackage().getId());
            }
            if (g.getBankAccountNumber() != null) {
                objectBuilder.add("bankAccountNumber", g.getBankAccountNumber());
            }
            if (g.getMaxNumberOfMembers() != 0) {
                objectBuilder.add("maxNumberOfMembers", g.getMaxNumberOfMembers());
            }
            if (g.getLoginHash() != null) {
                objectBuilder.add("loginHash", g.getLoginHash());
            }
            if (g.getPasswordHash() != null) {
                objectBuilder.add("passwordHash", g.getPasswordHash());
            }
            if (g.getGroupHash() != null) {
                objectBuilder.add("groupHash", g.getGroupHash());
            }
            builder.add(objectBuilder);
            return Response.status(201).entity(builder.build()).build();
        } catch (Exception e) {
            return Response.status(500).build();
        }
    }

    // aktualizuje grupe na podstawie jsona i id
    @Path("/{id}")
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Response update(@PathParam("id") String id, Group group) {
        try {
            int idInt = Integer.parseInt(id);
            if (group.getId() == 0 || group.getId() == idInt) {
                group.setId(idInt);
                Group g = groupService.update(group);
                if (g == null) {
                    return Response.status(204).build();
                }
                JsonArrayBuilder builder = Json.createArrayBuilder();
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("id", g.getId());
                objectBuilder.add("name", g.getName());
                objectBuilder.add("ownerId", g.getOwner().getId());
                if (g.getaPackage() != null && g.getaPackage().getId() != 0) {
                    objectBuilder.add("packageId", g.getaPackage().getId());
                }
                if (g.getBankAccountNumber() != null) {
                    objectBuilder.add("bankAccountNumber", g.getBankAccountNumber());
                }
                if (g.getMaxNumberOfMembers() != 0) {
                    objectBuilder.add("maxNumberOfMembers", g.getMaxNumberOfMembers());
                }
                if (g.getLoginHash() != null) {
                    objectBuilder.add("loginHash", g.getLoginHash());
                }
                if (g.getPasswordHash() != null) {
                    objectBuilder.add("passwordHash", g.getPasswordHash());
                }
                if (g.getGroupHash() != null) {
                    objectBuilder.add("groupHash", g.getGroupHash());
                }
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

    // usuwa grupe na podstawie id
    @Path("/{id}")
    @DELETE
    @Produces("application/json")
    public Response delete(@PathParam("id") String id) {
        try {
            int idInt = Integer.parseInt(id);
            Group g = groupService.deleteById(idInt);
            if (g == null) {
                return Response.status(204).build();
            }
            JsonArrayBuilder builder = Json.createArrayBuilder();
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("id", g.getId());
            objectBuilder.add("name", g.getName());
            objectBuilder.add("ownerId", g.getOwner().getId());
            if (g.getaPackage() != null && g.getaPackage().getId() != 0) {
                objectBuilder.add("packageId", g.getaPackage().getId());
            }
            if (g.getBankAccountNumber() != null) {
                objectBuilder.add("bankAccountNumber", g.getBankAccountNumber());
            }
            if (g.getMaxNumberOfMembers() != 0) {
                objectBuilder.add("maxNumberOfMembers", g.getMaxNumberOfMembers());
            }
            if (g.getLoginHash() != null) {
                objectBuilder.add("loginHash", g.getLoginHash());
            }
            if (g.getPasswordHash() != null) {
                objectBuilder.add("passwordHash", g.getPasswordHash());
            }
            if (g.getGroupHash() != null) {
                objectBuilder.add("groupHash", g.getGroupHash());
            }
            builder.add(objectBuilder);
            return Response.status(200).entity(builder.build()).build();
        } catch (NumberFormatException e) {
            return Response.status(400).build();
        } catch (Exception e) {
            return Response.status(500).build();
        }
    }


    //USER_IN_GROUP

    // dodaje prosbe aby dodac uzytkownika do grupy
    @Path("/{idGroup}/{idUser}")
    @POST
    @Produces("application/json")
    public Response addUserToGroupRequest(@PathParam("idGroup") String idGroup, @PathParam("idUser") String idUser) {
        try {
            int idGroupInt = Integer.parseInt(idGroup);
            int idUserInt = Integer.parseInt(idUser);
            boolean res = groupService.addUser(idGroupInt, idUserInt);
            if (res) {
                return Response.status(200).build();
            } else {
                return Response.status(204).build();
            }
        } catch (NumberFormatException e) {
            return Response.status(400).build();
        } catch (Exception e) {
            return Response.status(500).build();
        }
    }

    // dodaje uzytkownika do grupy
    @Path("/{idGroup}/{idUser}")
    @PUT
    @Produces("application/json")
    public Response addUserToGroupConfirm(@PathParam("idGroup") String idGroup, @PathParam("idUser") String idUser) {
        try {
            int idGroupInt = Integer.parseInt(idGroup);
            int idUserInt = Integer.parseInt(idUser);
            boolean res = groupService.confirmUser(idGroupInt, idUserInt);
            if (res) {
                return Response.status(200).build();
            } else {
                return Response.status(204).build();
            }
        } catch (NumberFormatException e) {
            return Response.status(400).build();
        } catch (Exception e) {
            return Response.status(500).build();
        }
    }

    // usuwa uzytkownika z grupy
    @Path("/{idGroup}/{idUser}")
    @DELETE
    @Produces("application/json")
    public Response deleteUserFromGroup(@PathParam("idGroup") String idGroup, @PathParam("idUser") String idUser) {
        try {
            int idGroupInt = Integer.parseInt(idGroup);
            int idUserInt = Integer.parseInt(idUser);
            boolean res = groupService.deleteUser(idGroupInt, idUserInt);
            if (res) {
                return Response.status(200).build();
            } else {
                return Response.status(204).build();
            }
        } catch (NumberFormatException e) {
            return Response.status(400).build();
        } catch (Exception e) {
            return Response.status(500).build();
        }
    }

}
