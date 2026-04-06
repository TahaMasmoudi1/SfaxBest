package DAO;

import entities.Multimedia;
import jakarta.persistence.EntityManager;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MultimediaDAO {
    EntityManager em;
    public MultimediaDAO(EntityManager em) {
        this.em = em;
    }
    public Multimedia findById(long id) {
        return em.find(Multimedia.class, id);
    }
    public Map<String, Integer> countFilmsByCategory() {

        List<Object[]> results = em.createQuery(
                "select c.categorie, count(m) " +
                        "from Multimedia m join m.categories c " +
                        "group by c.categorie " +
                        "order by c.categorie",
                Object[].class
        ).getResultList();

        Map<String, Integer> map = new LinkedHashMap<>();

        for (Object[] row : results) {
            String categoryName = (String) row[0];
            Long count = (Long) row[1];

            map.put(categoryName, count.intValue());
        }

        return map;
    }
}
