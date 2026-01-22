package DAO;

import entities.Episode;
import entities.Season;
import entities.User;
import jakarta.persistence.EntityManager;

import java.util.List;

public class EpisodeDAO {
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

    public Episode findById(long id) {
        return em.find(Episode.class, id);
    }

    public List<Episode> listAll(long idSeason, int offset, int limit) {
        return em.createQuery("select distinct e from Episode e where e.season.id=:idSeason" +
                        " order by e.nEpisode ", Episode.class).setParameter("idSeason", idSeason)
                .setFirstResult(offset).setMaxResults(limit).getResultList();
    }
}
