package DAO;

import entities.User;

public class UserDAO extends GenericDAO {
    public void save(User user){
        try{
        begin();
        em.persist(user);
        commit();}
        catch(Exception ex){
            rollback();
        }
    }
    public void delete(User user){
        try{
        begin();
        em.remove(user);
        commit();
        }
        catch(Exception ex){
            rollback();
        }
    }
    public void update(User user){
        try{
        begin();
        em.merge(user);
        commit();
        }
        catch(Exception ex){
            rollback();
        }
    }
    public User findById(Integer id){
        try{
        begin();
        return em.find(User.class, id);
        }catch(Exception ex){
            rollback();
        }
        return null;
    }
    public User findByEmail(String email){
        try{
        begin();
        return em.find(User.class, email);
        }catch(Exception ex){
            rollback();
        }
        return null;
    }


}
