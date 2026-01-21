package DAO;

import entities.Documentary;
import entities.Film;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.List;

public class DocumentaryDAO {
    private final EntityManager em;

    public DocumentaryDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Documentary documentary) {
        em.persist(documentary);
    }

    public void delete(Documentary documentary) {
        em.remove(documentary);
    }

    public void update(Documentary documentary) {
        em.merge(documentary);
    }
    public Documentary findById(Long id) {
        return em.find(Documentary.class, id);
    }
    public List<Documentary> findAll(int offset, int limit) {
            return em.createQuery("select distinct d from Documentary" +
                            " d order by d.releaseYear desc ", Documentary.class)
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .getResultList();
    }

    public List<Documentary> findbyManyCategory(List<Long> ids,int offset,int limit) {
        return em.createQuery("select distinct d from Documentary" +
                        " d join d.categories c where c.id in :ids" +
                        " group by d.id having count(distinct c.id)=:size  " +
                        "order by d.releaseYear desc ", Documentary.class)
                .setParameter("ids", ids).
                setParameter("size", (long) ids.size()).
                setFirstResult(offset).setMaxResults(limit).
                getResultList();
    }
}
