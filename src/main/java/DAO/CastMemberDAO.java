package DAO;

import entities.CastMember;
import entities.Season;
import entities.User;
import jakarta.persistence.EntityManager;

import java.util.List;

public class CastMemberDAO {
    private final EntityManager em;

    public CastMemberDAO(EntityManager em) {
        this.em = em;
    }

    public void save(CastMember castMember) {
        em.persist(castMember);
    }

    public void delete(CastMember castMember) {
        em.remove(castMember);
    }

    public void update(CastMember castMember) {
        em.merge(castMember);
    }

    public CastMember findById(long id) {
        return em.find(CastMember.class, id);
    }

    public List<CastMember> findCastsByMultimedia(String idMultimedia) {
        return em.createQuery("select distinct vc.castMember from VideoCast vc " +
                        "where vc.multimedia.id=:idMultimedia", CastMember.class)
                .setParameter("idMultimedia", idMultimedia).getResultList();
    }
}
