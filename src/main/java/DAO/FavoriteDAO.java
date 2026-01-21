package DAO;

import entities.Favorite;
import entities.User;
import jakarta.persistence.EntityManager;

public class FavoriteDAO {
    private final EntityManager em;

    public FavoriteDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Favorite favorite) {
        em.persist(favorite);
    }

    public void delete(Favorite favorite) {
        em.remove(favorite);
    }

    public void update(Favorite favorite) {
        em.merge(favorite);
    }
}
