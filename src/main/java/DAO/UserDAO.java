package DAO;

import entities.User;

public class UserDAO extends GenericDAO{
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


}
