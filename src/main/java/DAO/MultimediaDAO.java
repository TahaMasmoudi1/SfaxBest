package DAO;

import entities.Category;
import entities.Multimedia;
import entities.Season;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class MultimediaDAO extends GenericDAO {
    public void save(Multimedia multimedia){
        try{
            begin();
            em.persist(multimedia);
            commit();}
        catch(Exception ex){
            rollback();
        }
    }
    public void delete(Multimedia multimedia){
        try{
            begin();
            em.remove(multimedia);
            commit();
        }
        catch(Exception ex){
            rollback();
        }
    }
    public void update(Multimedia multimedia){
        try{
            begin();
            em.merge(multimedia);
            commit();
        }
        catch(Exception ex){
            rollback();
        }
    }
    public Multimedia findById(Integer id){
        try{
            begin();
            return em.find(Multimedia.class, id);
        }catch(Exception ex){
            rollback();
        }
        return null;
    }
    public List<Multimedia> findAll(){
        try{
            TypedQuery<Multimedia> query= em.createQuery("select distinct m from Multimedia m ", Multimedia.class);
            return query.getResultList();
        }catch(Exception ex){
            return List.of();
        }
    }
    public List<Multimedia> findByCategory(Long idCategory){
        try{
            return em.createQuery("select distinct m from Multimedia m join m.categories c where c.id = :idCategory order by m.createdAt desc ", Multimedia.class).setParameter("idCategory", idCategory).getResultList();
        }catch(Exception ex){
            return List.of();
        }
    }
    public List<Multimedia> findbyManyCategory(List<Long> ids){
           return em.createQuery("select distinct m from Multimedia m join m.categories c where c.id in :ids group by m.id having count(distinct c.id)=:size  order by max(m.createdAt) desc ", Multimedia.class).setParameter("ids", ids).setParameter("size",(long)ids.size()).getResultList();
    }
}
