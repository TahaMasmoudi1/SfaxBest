package DAO;

import entities.Season;
import entities.User;
import jakarta.persistence.EntityManager;

public class SeasonDAO {
    private final EntityManager em;

    public SeasonDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Season season) {
        em.persist(season);
    }

    public void delete(Season season) {
        em.remove(season);
    }

    public void update(Season season) {
        em.merge(season);
    }
    public Season findById(Integer id) {
        return em.find(Season.class, id);
    }

}
