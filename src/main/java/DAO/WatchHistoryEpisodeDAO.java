package DAO;

import entities.WatchHistoryEpisode;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public class WatchHistoryEpisodeDAO {
    private final EntityManager em;

    public WatchHistoryEpisodeDAO(EntityManager em) {
        this.em = em;
    }

    public void save(WatchHistoryEpisode watchHistoryEpisode) {
        em.persist(watchHistoryEpisode);
    }

    public void delete(WatchHistoryEpisode watchHistoryEpisode) {
        em.remove(watchHistoryEpisode);
    }

    public void update(WatchHistoryEpisode watchHistoryEpisode) {
        em.merge(watchHistoryEpisode);
    }

    public Optional<WatchHistoryEpisode> findById(long idUser, long episodeID) {
        WatchHistoryEpisode result = em.createQuery(
                        "select w from WatchHistoryEpisode w " +
                                "where w.user.id = :idUser and w.episode.id = :idEpisode",
                        WatchHistoryEpisode.class)
                .setParameter("idUser", idUser)
                .setParameter("idEpisode", episodeID)
                .getSingleResult();

        return Optional.of(result);
    }

    public WatchHistoryEpisode find(WatchHistoryEpisode watchHistoryEpisode) {
        return em.find(WatchHistoryEpisode.class, watchHistoryEpisode.getId());
    }
}