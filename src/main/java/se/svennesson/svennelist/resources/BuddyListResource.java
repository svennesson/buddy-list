package se.svennesson.svennelist.resources;

import io.dropwizard.hibernate.UnitOfWork;
import se.svennesson.svennelist.model.BuddyList;
import se.svennesson.svennelist.model.Item;
import se.svennesson.svennelist.service.BuddyListService;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("lists")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BuddyListResource {

    private final BuddyListService listService;

    public BuddyListResource(BuddyListService listService) {
        this.listService = listService;
    }

    @GET
    @UnitOfWork
    public Response getAllLists() {
        return Response.ok(listService.getAllLists()).build();
    }

    @POST
    @UnitOfWork
    public Response createList(@Valid final BuddyList buddyList) {
        return Response.ok(listService.createList(buddyList)).build();
    }

    @GET
    @UnitOfWork
    @Path("{listId}")
    public Response getList(@PathParam("listId") final Long listId) {
        return Response.ok(listService.getListById(listId)).build();
    }

    @GET
    @UnitOfWork
    @Path("{listId}/items")
    public Response getListWithItems(@PathParam("listId") final Long listId) {
        return Response.ok(listService.getListWithItemsById(listId)).build();
    }

    @DELETE
    @UnitOfWork
    @Path("{listId}")
    public Response deleteList(@PathParam("listId") final Long listId) {
        listService.deleteListById(listId);
        return Response.ok().build();
    }

    @POST
    @UnitOfWork
    @Path("{listId}/items")
    public Response addItemToList(@PathParam("listId") final Long listId, @Valid Item item) {
        return Response.ok(listService.addItemToList(listId, item)).build();
    }

    @PUT
    @UnitOfWork
    @Path("{listId}/items/clear")
    public Response clearItems(@PathParam("listId") final Long listId) {
        listService.clearItemsFromList(listId);
        return Response.ok().build();
    }

    @PUT
    @UnitOfWork
    @Path("{listId}/items/{itemId}")
    public Response updateItem(@PathParam("listId") final Long listId, @PathParam("itemId") final Long itemId, @Valid final Item item) {
        return Response.ok(listService.updateItem(listId, itemId, item)).build();
    }
}
