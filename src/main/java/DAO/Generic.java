package DAO;

import jakarta.persistence.EntityManager;
import utils.JPAUtil;

public  class Generic {
    protected EntityManager em;
    protected Generic(){
        this.em= JPAUtil.emf().createEntityManager();
    }
    protected void begin(){
        em.getTransaction().begin();
    }
    protected void commit(){
        em.getTransaction().commit();
    }
    protected void rollback(){
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }
    protected void close(){
        em.close();
    }
}
