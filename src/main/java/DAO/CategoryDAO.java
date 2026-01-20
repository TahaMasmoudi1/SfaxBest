package DAO;

import entities.Category;
import entities.Season;

public class CategoryDAO extends GenericDAO {
    public void save(Category category){
        try{
            begin();
            em.persist(category);
            commit();}
        catch(Exception ex){
            rollback();
        }
    }
    public void delete(Category category){
        try{
            begin();
            em.remove(category);
            commit();
        }
        catch(Exception ex){
            rollback();
        }
    }
    public void update(Category category){
        try{
            begin();
            em.merge(category);
            commit();
        }
        catch(Exception ex){
            rollback();
        }
    }
    public Category findById(Integer id){
        try{
            begin();
            return em.find(Category.class, id);
        }catch(Exception ex){
            rollback();
        }
        return null;
    }
}
