package DAO;

import entities.WatchHistoryMultimedia;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class WatchHistoryMultimediaDAO {
    private final EntityManager em;

    public WatchHistoryMultimediaDAO(EntityManager em) {
        this.em = em;
    }

    public void save(WatchHistoryMultimedia watchHistoryMultimedia) {
        em.persist(watchHistoryMultimedia);
    }

    public void delete(WatchHistoryMultimedia watchHistoryMultimedia) {
        em.remove(watchHistoryMultimedia);
    }

    public void update(WatchHistoryMultimedia watchHistoryMultimedia) {
        em.merge(watchHistoryMultimedia);
    }
    public Optional<WatchHistoryMultimedia> findById(long idUser, long multimediaID) {
        return em.createQuery("select w from WatchHistoryMultimedia" +
                " w where w.user.id=:idUser and w.multimedia.id=:idMultimedia", WatchHistoryMultimedia.class)
                .setParameter("idUser",idUser).setParameter("idMultimedia",multimediaID).getResultStream().findFirst();

    }
    public WatchHistoryMultimedia find(WatchHistoryMultimedia watchHistoryMultimedia) {
        return em.find(WatchHistoryMultimedia.class, watchHistoryMultimedia.getId());
    }
    public List<WatchHistoryMultimedia> findByUserId(long idUser) {
        return em.createQuery("select w from WatchHistoryMultimedia" +
                        " w JOIN FETCH w.multimedia m  where w.user.id=:idUser order by w.lastWatchedAt desc", WatchHistoryMultimedia.class)
                .setParameter("idUser",idUser).getResultList();
    }


}
