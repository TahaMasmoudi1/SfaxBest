package DAO;

import entities.Comment;
import entities.User;
import jakarta.persistence.EntityManager;

public class CommentDAO {
    private final EntityManager em;

    public CommentDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Comment comment) {
        em.persist(comment);
    }

    public void delete(Comment comment) {
        em.remove(comment);
    }

    public void update(Comment comment) {
        em.merge(comment);
    }
}
