package DAO;

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

    public List<Serie> listAllWithCategories() {
        return em.createQuery(
                "select distinct s from Serie s left join fetch s.categories " +
                        " order by s.releaseYear desc",
                Serie.class
        ).getResultList();
    }

    public List<Serie> listbyManyCategory(List<Long> ids, int offset, int limit) {
        if (ids.isEmpty()) {
            return em.createQuery(
                    "select s from Serie s order by  releaseYear desc"
            ).getResultList();
        }
        return em.createQuery(
                        "select  s from Serie" +
                                " s join s.categories c where c.id in :ids" +
                                " group by s.id having count(distinct c.id)=:size  " +
                                "order by s.releaseYear desc ",
                        Serie.class
                )
                .setParameter("ids", ids)
                .setParameter("size", (long) ids.size())
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<Serie> listByReleaseYear(int releaseYear, int offset, int limit) {
        return em.createQuery(
                        "select distinct s from Serie s where s.releaseYear=:releaseYear" +
                                " order by s.createdAt desc",
                        Serie.class
                )
                .setParameter("releaseYear", releaseYear)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public Serie listFilmDetails(long id) {
        return em.createQuery(
                        "select distinct s from Serie s " +
                                "left join fetch  s.videoCasts vc left join fetch  vc.castMember cm" +
                                "left join fetch  s.categories c where s.id=:id ",
                        Serie.class
                )
                .setParameter("id", id)
                .getSingleResult();
    }

    public List<Serie> listFilmsSearch(String search, int offset, int limit) {
        String text = (search == null) ? "" : search.trim().toLowerCase();
        return em.createQuery(
                        "select s from Serie s where (:text=''" +
                                " or lower(s.title) like:text or lower(s.description)like:text)" +
                                " order by s.releaseYear desc ",
                        Serie.class
                )
                .setParameter("text", text)
                .setParameter("text", "%" + text + "%")
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<Serie> listFilmsSearchWithCategory(
            String search,
            List<Long> categoryIds,
            int offset,
            int limit
    ) {
        String text = (search == null) ? "" : search.trim().toLowerCase();
        return em.createQuery(
                        "select distinct s from Serie s " +
                                "left join s.categories c " +
                                "where (:text = '' or lower(s.title) like :text or lower(s.description) like :text) " +
                                "and (:idsEmpty = true or c.id in :ids) " +
                                "order by s.releaseYear desc",
                        Serie.class
                )
                .setParameter("text", text)
                .setParameter("text", "%" + text + "%")
                .setParameter("ids", categoryIds)
                .setParameter("idsEmpty", categoryIds == null || categoryIds.isEmpty())
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
}
