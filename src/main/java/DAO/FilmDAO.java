package DAO;

import entities.Film;
import jakarta.persistence.EntityManager;

import java.util.List;

public class FilmDAO {
    private final EntityManager em;

    public FilmDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Film film) {
        em.persist(film);
    }

    public void delete(Film film) {
        em.remove(film);
    }

    public void update(Film film) {
        em.merge(film);
    }
    public Film findById(Long id) {
        return em.find(Film.class, id);
    }
    public List<Film> findAll(int offset, int limit) {
            return em.createQuery("select distinct f from Film f" +
                            " order by f.releaseYear desc", Film.class)
                    .setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    public List<Film> findbyManyCategory(List<Long> ids,int offset,int limit) {
        return em.createQuery("select distinct f from Film" +
                        " f join f.categories c where c.id in :ids" +
                        " group by f.id having count(distinct c.id)=:size  " +
                        "order by f.releaseYear desc ", Film.class)
                .setParameter("ids", ids).
                setParameter("size", (long) ids.size()).
                setFirstResult(offset).setMaxResults(limit).
                getResultList();
    }
}
