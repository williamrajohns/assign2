/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import cst8218.john1031.entity.Sprite;
import java.util.List;
import java.util.Objects;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * SpriteFacadeREST manages the postman inputs each calls the appropriate method
 * @author William
 */

@Stateless
@DeclareRoles ({"Admin","RestGroup"}) //added ({"Admin","Regular"})
@RolesAllowed ({"Admin","RestGroup"}) //added
@Path("cst8218.john1031.entity.sprite")
public class SpriteFacadeREST extends AbstractFacade<Sprite> {

    @PersistenceContext(unitName = "SpriteWilliam-ejbPU") //was -warPU
    private EntityManager em;

    public SpriteFacadeREST() {
        super(Sprite.class);
    }

    /**
     * Creates a new sprite when POST is called on root source will only do so
     * if the id field is null if there is an ID in the post, it will check to
     * see if that ID exists if it does it updates the values in that Sprite to
     * match the given new ones otherwise it is invalid
     *
     * @param entity sprite passed by postman
     */
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    //@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Sprite entity) {
        if (entity.getId() == null) {
            super.create(entity); //null values are given default values
            Response.status(Response.Status.CREATED).entity(entity).build();
        } else { //id is not null

            if (super.find(entity.getId()) != null) {//if id corresponds
                //update that sprite with new non null values

                //create a new entity
                //set the values to the old entity
                //set the new values onto it
                //merge the new entity
                Sprite newSprite = find(entity.getId());//
                newSprite.updates(entity); //

                super.edit(newSprite);
                Response.status(Response.Status.ACCEPTED).entity(entity).build();
            } else {//else id doesn't correspond    
                //invalid
                Response.status(Response.Status.NOT_FOUND).entity(entity).build();// invalid
            }
        }
        //Response.status(Response.Status.NOT_ACCEPTABLE).entity(entity).build(); //won't ever get here
    }

    /**
     * for a post with a specific ID in the 'header' checks if there is a sprite
     * with that ID if there is a match checks if the id in the header matches
     * the body and updates the values of the Sprite with the new ones
     *
     * @param id specified by the path
     * @param entity new sprite specified by the 'body'
     * @return response 
     */
    @POST
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response editPost(@PathParam("id") Long id, Sprite entity) {
        if (Objects.equals(id, entity.getId()) && find(id) != null) { //id matches body and can find that id
            //create a new entity
            //set the values to the old entity
            //set the new values onto it
            //merge the new entity

            Sprite newSprite = find(id);//
            newSprite.updates(entity); //

            super.edit(newSprite);
            return Response.status(Response.Status.ACCEPTED).entity(newSprite).build();
            //return valid
        } //else not Error
        //return invalid
        return Response.status(Response.Status.NOT_MODIFIED).entity(entity).build();
    }

    //put without id isn't supported
    /**
     * when put is called, updates that specified sprite with new values if
     * there is a sprite with a matching ID and the body's id matches the id
     *
     * @param id specified by the path
     * @param entity new sprite specified by the 'body'
     * @return returns a response
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("id") Long id, Sprite entity) {
        if (Objects.equals(id, entity.getId()) && find(id) != null) {

            //replace the sprite with the one sent in, only new values (don't preserve the old values
            //create a new entity
            //set the values to the old entity
            //set the new values onto it
            //merge the new entity
            //unsure if the next two lines should be in the put section or not, the document is unclear
            Sprite newSprite = find(id);//
            newSprite.updates(entity); //

            super.edit(entity);
            return Response.status(Response.Status.ACCEPTED).entity(entity).build();
            //return valid
        } //else not Error
        //return invalid
        return Response.status(Response.Status.NOT_MODIFIED).entity(entity).build();
    }

    /**
     * removes the Sprite matching the id
     *
     * @param id specified by the path
     * @return response if it worked or not
     */
    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") Long id) {
        if (super.find(id) != null) {
            super.remove(super.find(id));
            return Response.status(Response.Status.ACCEPTED).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * returns the matching Sprite for that id if it exists
     *
     * @param id specified by the path
     * @return matching Sprite
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Sprite find(@PathParam("id") Long id) {

        Response.status(Response.Status.ACCEPTED).build();
        return super.find(id);
    }

    /**
     * returns a list of all of the Sprites
     * @return list of all of the Sprites
     */
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Sprite> findAll() {
        return super.findAll();
    }

    /**
     * unused
     * should return all of the Sprites with ids between to and from
     * @param from first id
     * @param to last id 
     * @return all Sprites with ids between the two values
     */
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Sprite> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    /**
     * returns the amount of sprites in the database
     * @return the amount of sprites in the database
     */
    @GET
    @Path("count")
    //@Produces(MediaType.TEXT_PLAIN) //try commenting this out and replacing it with
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON}) //this line
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
