package DAO;

import entities.Multimedia;
import jakarta.persistence.EntityManager;

public class MultimediaDAO {
    EntityManager em;
    public MultimediaDAO(EntityManager em) {
        this.em = em;
    }
    public Multimedia findById(long id) {
        return em.find(Multimedia.class, id);
    }
}
