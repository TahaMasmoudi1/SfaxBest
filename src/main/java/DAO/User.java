package DAO;

public class User extends Generic {
    public void save(entities.User user){
        try{
        begin();
        em.persist(user);
        commit();}
        catch(Exception ex){
            rollback();
        }
    }
    public void delete(entities.User user){
        try{
        begin();
        em.remove(user);
        commit();
        }
        catch(Exception ex){
            rollback();
        }
    }
    public void update(entities.User user){
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
