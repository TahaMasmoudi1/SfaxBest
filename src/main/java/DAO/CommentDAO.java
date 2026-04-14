package DAO;

import entities.Comment;
import entities.User;
import jakarta.persistence.EntityManager;

import java.util.List;

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
    public Comment findById(Long id) {
        return em.find(Comment.class, id);
    }
    public List<Comment> findAllById(Long idMultimedia) {
        return  em.createQuery("select c from Comment c join fetch c.user where c.multimedia.id=:id " +
                "order by c.commentDate", Comment.class)
                .setParameter("id", idMultimedia).getResultList();
    }
    public List<Comment> findAll() {
        return  em.createQuery("select c from Comment c " +
                        "order by c.commentDate", Comment.class).getResultList();
    }

}
