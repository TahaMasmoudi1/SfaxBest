package Services;

import DAO.CategoryDAO;
import entities.Category;
import utils.TraHelper;

public class CategoryService {
    public void add(String type) {
        TraHelper.write(em -> {
            Category category = new Category(type);
            CategoryDAO categoryDAO = new CategoryDAO(em);
            categoryDAO.save(category);
        });
    }
}
