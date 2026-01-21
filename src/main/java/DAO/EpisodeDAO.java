package DAO;

import entities.Episode;
import entities.Season;
import entities.User;
import jakarta.persistence.EntityManager;

public class EpisodeDAO  {
    private final EntityManager em;

    public EpisodeDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Episode episode) {
        em.persist(episode);
    }

    public void delete(Episode episode) {
        em.remove(episode);
    }

    public void update(Episode episode) {
        em.merge(episode);
    }
    public Episode findById(Integer id){
            return em.find(Episode.class, id);

    }
}
