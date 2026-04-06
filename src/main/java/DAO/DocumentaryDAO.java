package DAO;

import entities.Documentary;
import entities.Film;
import jakarta.persistence.EntityManager;

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

    public List<Documentary> listAllWithCategories() {
        return em.createQuery("select distinct d from Documentary d left join fetch d.categories " +
                " order by d.releaseYear desc", Documentary.class).getResultList();
    }

    public List<Documentary> listbyManyCategory(List<Long> ids, int offset, int limit) {
        if (ids.isEmpty()) {
            return em.createQuery("select d from Documentary d order by  releaseYear desc").getResultList();
        }
        return em.createQuery("select  d from Documentary" +
                        " d join d.categories c where c.id in :ids" +
                        " group by d.id having count(distinct c.id)=:size  " +
                        "order by d.releaseYear desc ", Documentary.class)
                .setParameter("ids", ids).
                setParameter("size", (long) ids.size()).
                setFirstResult(offset).setMaxResults(limit).
                getResultList();
    }

    public List<Documentary> listByReleaseYear(int releaseYear, int offset, int limit) {
        return em.createQuery("select distinct d from Documentary d where d.releaseYear=:releaseYear" +
                        " order by d.createdAt desc", Documentary.class).setParameter("releaseYear", releaseYear)
                .setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    public Documentary listDocumentaryDetails(long id) {
        return em.createQuery("select distinct d from Documentary d " +
                        "left join fetch  d.videoCasts vc left join fetch  vc.castMember cm" +
                        "left join fetch  d.categories c where d.id=:id ", Documentary.class)
                .setParameter("id", id).getSingleResult();
    }

    public List<Documentary> listDocumentarySearch(String search, int offset, int limit) {
        String text = (search == null) ? "" : search.trim().toLowerCase();
        return em.createQuery("select d from Documentary d where (:text=''" +
                                " or lower(d.title) like:text or lower(d.description)like:text)" +
                                " order by d.releaseYear desc ",
                        Documentary.class)
                .setParameter("text", text).setParameter("text", "%" + text + "%")
                .setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    public List<Documentary> listDocumentarySearchWithCategory(String search, List<Long> categoryIds, int offset, int limit) {
        String text = (search == null) ? "" : search.trim().toLowerCase();
        return em.createQuery(
                        "select distinct d from Documentary d " +
                                "left join d.categories c " +
                                "where (:text = '' or lower(d.title) like :text or lower(d.description) like :text) " +
                                "and (:idsEmpty = true or c.id in :ids) " +
                                "order by d.releaseYear desc", Documentary.class)
                .setParameter("text", text)
                .setParameter("text", "%" + text + "%")
                .setParameter("ids", categoryIds)
                .setParameter("idsEmpty", categoryIds == null || categoryIds.isEmpty()).setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
    public double countDocumentary(){
        return em.createQuery("select count (d) from Documentary d", double.class).getSingleResult();
    }
    public List<Documentary> listByCategoryName(String categoryName, int offset, int limit) {
        return em.createQuery(
                        "select distinct d from Documentary d " +
                                "join d.categories c " +
                                "where lower(c.categorie) = lower(:categoryName) " +
                                "order by d.releaseYear desc",
                        Documentary.class)
                .setParameter("categoryName", categoryName).setFirstResult(offset).setMaxResults(limit).getResultList();
    }
    public double countFilms(){
        return em.createQuery("select count (f) from Film f", double.class).getSingleResult();
    }
}
