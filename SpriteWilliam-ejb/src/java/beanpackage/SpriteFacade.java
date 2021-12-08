/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanpackage;

import cst8218.john1031.entity.Sprite;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Path;

/**
 * manages the JSF pages
 * @author William
 */
@Stateless
@Path("cst8218.john1031.entity.sprite")
public class SpriteFacade extends AbstractFacade<Sprite> {
    //commented out
    @PersistenceContext(unitName = "SpriteWilliam-ejbPU")//war
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SpriteFacade() {
        super(Sprite.class);
    }
    
}
