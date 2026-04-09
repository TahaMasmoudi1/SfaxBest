package DAO;

import entities.Rating;
import entities.User;
import jakarta.persistence.EntityManager;

import java.util.List;

public class RatingDAO {
    private final EntityManager em;

    public RatingDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Rating rating) {
        em.merge(rating);
    }

    public void delete(Rating rating) {
        em.remove(rating);
    }

    public void update(Rating rating) {
        em.merge(rating);
    }
    public Rating find(Long id) {
        return em.find(Rating.class, id);
    }
    public List<Rating> findAll(Long idMultimedia) {
        return em.createQuery("select r from Rating r where r.multimedia.id=:id", Rating.class).setParameter("id", idMultimedia).getResultList();
    }
}
