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

    public List<Film> listAllWithCategories() {
            return em.createQuery("select distinct f from Film f left join fetch f.categories " +
                            " order by f.releaseYear desc", Film.class).getResultList();
    }

    public List<Film> listbyManyCategory(List<Long> ids,int offset,int limit) {
        if(ids.isEmpty()){
            return em.createQuery("select f from Film f order by  releaseYear desc").getResultList();
        }
        return em.createQuery("select  f from Film" +
                        " f join f.categories c where c.id in :ids" +
                        " group by f.id having count(distinct c.id)=:size  " +
                        "order by f.releaseYear desc ", Film.class)
                .setParameter("ids", ids).
                setParameter("size", (long) ids.size()).
                setFirstResult(offset).setMaxResults(limit).
                getResultList();
    }
    public List<Film> listByReleaseYear(int releaseYear, int offset,int limit) {
        return em.createQuery("select distinct f from Film f where f.releaseYear=:releaseYear" +
                        " order by f.createdAt desc", Film.class).setParameter("releaseYear",releaseYear)
                .setFirstResult(offset).setMaxResults(limit).getResultList();
    }
    public Film listFilmDetails(long id) {
        return em.createQuery("select distinct f from Film f " +
                "left join fetch  f.videoCasts vc left join fetch  vc.castMember cm " +
                "left join fetch  f.categories c where f.id=:id ",Film.class).setParameter("id",id).getSingleResult();
    }
    public List<Film> listFilmsSearch(String search, int offset, int limit) {
        String text=(search==null)?"":search.trim().toLowerCase();
        return em.createQuery("select f from Film f where (:text=''" +
                " or lower(f.title) like:text or lower(f.description)like:text)" +
                " order by f.releaseYear desc ",Film.class).setParameter("text",text).setParameter("text","%"+text+"%").setFirstResult(offset).setMaxResults(limit).getResultList();
    }
    public List<Film> listFilmsSearchWithCategory(String search,List<Long> categoryIds,int offset,int limit) {
       String text=(search==null)?"":search.trim().toLowerCase();
        return em.createQuery(
                        "select distinct f from Film f " +
                                "left join f.categories c " +
                                "where (:text = '' or lower(f.title) like :text or lower(f.description) like :text) " +
                                "and (:idsEmpty = true or c.id in :ids) " +
                                "order by f.releaseYear desc",Film.class)
                .setParameter("text", text)
                .setParameter("text", "%" + text + "%")
                .setParameter("ids", categoryIds)
                .setParameter("idsEmpty", categoryIds == null || categoryIds.isEmpty()).setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
    public List<Film> listByCategoryName(String categoryName, int offset, int limit) {
        return em.createQuery(
                        "select distinct f from Film f " +
                                "join fetch f.categories c " +
                                "where lower(c.categorie) = lower(:categoryName) " +
                                "order by f.releaseYear desc",
                        Film.class)
                .setParameter("categoryName", categoryName).setFirstResult(offset).setMaxResults(limit).getResultList();
    }
    public Long countFilms(){
        return em.createQuery("select count (f) from Film f", Long.class).getSingleResult();
    }
    public List<Object[]> getTopByViews(int limit){

        return em.createQuery(
                        "select f.title, count(w.multimedia.id) " +
                                "from Film f " +
                                "join Multimedia m on m.id = f.id " +
                                "join WatchHistoryMultimedia w on w.multimedia.id = m.id " +
                                "group by f.title " +
                                "order by count(w.multimedia.id) desc",
                        Object[].class
                )
                .setMaxResults(limit)
                .getResultList();

    }
}

