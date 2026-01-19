package DAO;

public class Season extends Generic {
    public void save(entities.Season season){
        try{
            begin();
            em.persist(season);
            commit();}
        catch(Exception ex){
            rollback();
        }
    }
    public void delete(entities.Season season){
        try{
            begin();
            em.remove(season);
            commit();
        }
        catch(Exception ex){
            rollback();
        }
    }
    public void update(entities.Season season){
        try{
            begin();
            em.merge(season);
            commit();
        }
        catch(Exception ex){
            rollback();
        }
    }
}
