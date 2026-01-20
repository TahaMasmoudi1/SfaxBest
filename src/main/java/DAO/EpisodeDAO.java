package DAO;

import entities.Episode;
import entities.Season;

public class EpisodeDAO extends GenericDAO {
    public void save(Episode episode){
        try{
            begin();
            em.persist(episode);
            commit();}
        catch(Exception ex){
            rollback();
        }
    }
    public void delete(Episode episode){
        try{
            begin();
            em.remove(episode);
            commit();
        }
        catch(Exception ex){
            rollback();
        }
    }
    public void update(Episode episode){
        try{
            begin();
            em.merge(episode);
            commit();
        }
        catch(Exception ex){
            rollback();
        }
    }
    public Episode findById(Integer id){
        try{
            begin();
            return em.find(Episode.class, id);
        }catch(Exception ex){
            rollback();
        }
        return null;
    }
}
