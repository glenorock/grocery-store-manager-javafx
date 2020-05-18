/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alimentation;
import javax.persistence.*;
/**
 *
 * @author jhy
 */
public interface EntityClasses {
    final static EntityManagerFactory emf = Persistence.createEntityManagerFactory("projetBDPU");
    
    public static void init(){
        EntityManager em = emf.createEntityManager();
        em.clear();
    }
    
    public static void add(EntityClasses m){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(m);
        em.getTransaction().commit();
        em.close();
    }
    
    public static EntityClasses get(Class ec,Integer id){
        EntityManager em = emf.createEntityManager();
        EntityClasses e = (EntityClasses) em.find(ec, id);
        em.close();
        return e;
    }
    public Integer getId();
    public static void remove(EntityClasses m){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.find(m.getClass(),m.getId()));
        em.getTransaction().commit();
    }
    
    
    
}
