package DAO;

import entities.Category;
import jakarta.persistence.EntityManager;

import java.util.List;

public class CategoryDAO {
    private final EntityManager em;

    public CategoryDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Category category) {
        em.persist(category);
    }

    public void delete(Category category) {
        em.remove(category);
    }

    public void update(Category category) {
        em.merge(category);
    }

    public List<Category> listByIds(List<Long> ids) {
        return em.createQuery("select c from Category c where c.id in:ids"
                , Category.class).setParameter("ids", ids).getResultList();
    }
}
