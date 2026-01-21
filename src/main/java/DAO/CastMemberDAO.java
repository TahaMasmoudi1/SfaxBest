package DAO;

import entities.CastMember;
import entities.Season;
import entities.User;
import jakarta.persistence.EntityManager;

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
}
