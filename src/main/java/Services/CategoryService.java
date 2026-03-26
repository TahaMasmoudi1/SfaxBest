package Services;

import DAO.CategoryDAO;
import entities.Category;
import utils.TraHelper;

import java.util.List;

public class CategoryService {
    public void add(String type) {
        TraHelper.write(em -> {
            Category category = new Category(type);
            CategoryDAO categoryDAO = new CategoryDAO(em);
            categoryDAO.save(category);
        });
    }
    public List<Category> listALL(List<Long> ids) {
       return TraHelper.read(em ->  {
            CategoryDAO categoryDAO = new CategoryDAO(em);
            return categoryDAO.listALL();
       });
    }
    public List<Category> listByIds(List<Long> ids) {
        return TraHelper.read(em ->  {
            CategoryDAO categoryDAO = new CategoryDAO(em);
            return categoryDAO.listByIds(ids);
        });
    }
}
