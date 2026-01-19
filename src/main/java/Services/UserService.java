package Services;

import DAO.UserDAO;
import entities.User;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public void save(User user) {
        userDAO.save(user);
    }

    public void delete(User user) {
        userDAO.delete(user);
    }

    public void update(User user) {
        userDAO.update(user);
    }

}
