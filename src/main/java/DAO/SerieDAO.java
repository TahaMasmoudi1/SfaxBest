package DAO;

import entities.Film;
import entities.Serie;
import jakarta.persistence.EntityManager;

import java.util.List;

public class SerieDAO {
    private final EntityManager em;

    public SerieDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Serie serie) {
        em.persist(serie);
    }

    public void delete(Serie serie) {
        em.remove(serie);
    }

    public void update(Serie serie) {
        em.merge(serie);
    }
    public Serie findById(Long id) {
        return em.find(Serie.class, id);
    }
    public List<Serie> listAll(int offset, int limit) {
        return em.createQuery("select distinct s from Serie s" +
                        " order by s.releaseYear desc", Serie.class)
                .setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    public List<Serie> listbyManyCategory(List<Long> ids,int offset,int limit) {
        return em.createQuery("select distinct s from Serie" +
                        " s join s.categories c where c.id in :ids" +
                        " group by s.id having count(distinct c.id)=:size  " +
                        "order by s.releaseYear desc ", Serie.class)
                .setParameter("ids", ids).
                setParameter("size", (long) ids.size()).
                setFirstResult(offset).setMaxResults(limit).
                getResultList();
    }
}
