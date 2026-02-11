package Services;

import DAO.FavoriteDAO;
import DAO.MultimediaDAO;
import DAO.UserDAO;
import entities.Favorite;
import entities.Multimedia;
import entities.User;
import utils.TraHelper;

import java.util.List;

public class FavoriteService {

    public void add(Long idUser, Long idMultimedia) {
        TraHelper.write(em -> {
            UserDAO userDAO=new UserDAO(em);
            FavoriteDAO favoriteDAO=new FavoriteDAO(em);
            MultimediaDAO multimediaDAO=new MultimediaDAO(em);
            User user=userDAO.findById(idUser);
            Multimedia multimedia=multimediaDAO.findById(idMultimedia);
            Favorite favorite = new Favorite(user,multimedia);
            favoriteDAO.save(favorite);
        });

    }
    public void delete(Long idUser, Long idMultimedia) {
        TraHelper.write(em -> {
            FavoriteDAO favoriteDAO=new FavoriteDAO(em);
            Favorite favorite=favoriteDAO.findByIdAndMultimedia(idUser,idMultimedia);
            favoriteDAO.delete(favorite);
        });
    }

    public List<Multimedia> getFavorites(Long idUser) {
        return TraHelper.read(em ->  {
            FavoriteDAO favoriteDAO=new FavoriteDAO(em);
            return favoriteDAO.getFavorites(idUser);

        });
    }


}
