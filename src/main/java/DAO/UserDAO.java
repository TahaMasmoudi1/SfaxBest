package DAO;

import entities.User;
import jakarta.persistence.NoResultException;

public class UserDAO extends GenericDAO {
    public void save(User user) {
        try {
            begin();
            em.persist(user);
            commit();
        } catch (Exception ex) {
            rollback();
        }
    }

    public void delete(User user) {
        try {
            begin();
            em.remove(user);
            commit();
        } catch (Exception ex) {
            rollback();
        }
    }

    public void update(User user) {
        try {
            begin();
            em.merge(user);
            commit();
        } catch (Exception ex) {
            rollback();
        }
    }

    public User findByUsername(String username) {
        try {
            return em.createQuery(
                            "SELECT u FROM User u WHERE lower(u.username) = :username", User.class)
                    .setParameter("username", username.trim().toLowerCase())
                    .getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }

    public User findByEmail(String email) {
        try {
            return em.createQuery("SELECT u from User u where lower(u.email)=:email", User.class)
                    .setParameter("email", email.trim().toLowerCase())
                    .getSingleResult();
        } catch (NoResultException exception) {
            return null;
        }
    }

    public boolean checkEmail(String email) {
        try {
            Long count = em.createQuery(
                            "SELECT COUNT(u.id) FROM User u WHERE lower(u.email) = :email",
                            Long.class
                    ).setParameter("email", email.trim().toLowerCase())
                    .getSingleResult();
            return count > 0;
        } catch (NoResultException exception) {
            return false;
        }

        }
        public boolean checkUsername(String username) {
        try {
            long count =em.createQuery("select count(u.id) from User u where u.username=:username ",Long.class).setParameter("username", username).getSingleResult();
            return count > 0;
        }catch (NoResultException exception){
            return false;
        }
    }


}
