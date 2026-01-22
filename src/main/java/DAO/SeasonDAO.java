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

}
