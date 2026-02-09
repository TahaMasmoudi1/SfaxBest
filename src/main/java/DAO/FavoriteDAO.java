package DAO;

import entities.Favorite;
import entities.Multimedia;
import entities.User;
import jakarta.persistence.EntityManager;

import java.util.List;

public class FavoriteDAO {
    private final EntityManager em;

    public FavoriteDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Favorite favorite) {
        em.persist(favorite);
    }

    public void delete(Favorite favorite) {
        em.remove(favorite);
    }

    public void update(Favorite favorite) {
        em.merge(favorite);
    }
    public Favorite findByIdAndMultimedia(Long idUser,Long idMultimedia) {
        return em.createQuery("select f from Favorite f where f.user.id=:idUser and f.multimedia.id=:idMultimedia", Favorite.class).setParameter("idUser",idUser).setParameter("idMultimedia",idMultimedia).getSingleResult();
    }
    public List<Multimedia> getFavorites(Long idUser) {
        return em.createQuery("select f.multimedia from Favorite f where f.user.id=:id order by f.addedAt", Multimedia.class).setParameter("id",idUser).getResultList();
    }
}
