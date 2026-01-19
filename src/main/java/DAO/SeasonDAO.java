package DAO;

import entities.Season;
import entities.User;

public class SeasonDAO extends GenericDAO{
    public void save(Season season){
        try{
            begin();
            em.persist(season);
            commit();}
        catch(Exception ex){
            rollback();
        }
    }
    public void delete(Season season){
        try{
            begin();
            em.remove(season);
            commit();
        }
        catch(Exception ex){
            rollback();
        }
    }
    public void update(Season season){
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
