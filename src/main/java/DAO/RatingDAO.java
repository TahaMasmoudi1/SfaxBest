package DAO;

import entities.Rating;
import entities.User;
import jakarta.persistence.EntityManager;

public class RatingDAO {
    private final EntityManager em;

    public RatingDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Rating rating) {
        em.persist(rating);
    }

    public void delete(Rating rating) {
        em.remove(rating);
    }

    public void update(Rating rating) {
        em.merge(rating);
    }
}
