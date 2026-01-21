package DAO;

import entities.User;
import entities.VideoCast;
import jakarta.persistence.EntityManager;

public class VideoCastDAO {
    private final EntityManager em;

    public VideoCastDAO(EntityManager em) {
        this.em = em;
    }

    public void save(VideoCast videoCast) {
        em.persist(videoCast);
    }

    public void delete(VideoCast videoCast) {
        em.remove(videoCast);
    }

    public void update(VideoCast videoCast) {
        em.merge(videoCast);
    }
}
