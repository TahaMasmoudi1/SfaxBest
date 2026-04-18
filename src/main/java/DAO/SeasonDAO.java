package DAO;

import entities.Season;
import entities.Serie;
import entities.User;
import jakarta.persistence.EntityManager;

import java.util.List;

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

    public Season findById(long id) {
        return em.find(Season.class, id);
    }

    public List<Season> listAll(long idSerie, int offset, int limit) {
        return em.createQuery("select distinct s from Season s where" +
                        " s.serie.id=:idSerie order by s.nSeason desc", Season.class).setParameter("idSerie", idSerie)
                .setFirstResult(offset).setMaxResults(limit).getResultList();
    }
    public boolean existsSeason(long serieId, int nSeason) {

        Long count = em.createQuery(
                        "SELECT COUNT(s) FROM Season s " +
                                "WHERE s.serie.id = :serieId " +
                                "AND s.nSeason = :nSeason",
                        Long.class
                )
                .setParameter("serieId", serieId)
                .setParameter("nSeason", nSeason)
                .getSingleResult();

        return count > 0;
    }
    public Season findByIdWithEpisodes(long id) {
        return em.createQuery("SELECT s from Season s LEFT join FETCH s.episodes where s.id= :id", Season.class).setParameter("id", id).getSingleResult();
    }

}
