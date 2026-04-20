package Services;

import DAO.MultimediaDAO;
import DAO.RatingDAO;
import DAO.UserDAO;
import entities.Multimedia;
import entities.Rating;
import entities.User;
import utils.TraHelper;

import java.util.List;
import java.util.OptionalDouble;

public class RatingService {
    public void add(Long idUser,Long idMultimedia,Byte rate){
        TraHelper.write(em -> {
            RatingDAO ratingDAO=new RatingDAO(em);
            MultimediaDAO multimediaDAO=new MultimediaDAO(em);
            UserDAO userDAO=new UserDAO(em);
            Multimedia multimedia=multimediaDAO.findById(idMultimedia);
            User user=userDAO.findById(idUser);
            Rating rating=new Rating(user,multimedia,rate);
            ratingDAO.save(rating);
        });
    }

    public void delete(Long idRate){
        TraHelper.write(em -> {
            RatingDAO ratingDAO=new RatingDAO(em);
            Rating rating=ratingDAO.find(idRate);
            ratingDAO.delete(rating);
        });
    }
    public void update(Long idRate,Long idUser,Long idMultimedia,Byte rate){
        TraHelper.write(em -> {
            RatingDAO ratingDAO=new RatingDAO(em);
            MultimediaDAO multimediaDAO=new MultimediaDAO(em);
            UserDAO userDAO=new UserDAO(em);
            Rating rating=ratingDAO.find(idRate);
            rating.setRate(rate);
        });
    }
    public List<Rating> findAll(Long idMultimedia){
        return TraHelper.read(em -> {
            RatingDAO ratingDAO=new RatingDAO(em);
            return ratingDAO.findAll(idMultimedia);
        });
    }
    public Double calculateRate(Long idMultimedia){
        List<Rating> ratings=this.findAll( idMultimedia);
        OptionalDouble avg =
                ratings.stream()
                        .mapToDouble(Rating::getRate)
                        .average();

        return avg.orElse(0.0);
    }
}
