package Services;

import DAO.CategoryDAO;
import DAO.FilmDAO;
import entities.Film;
import jakarta.persistence.NoResultException;
import utils.TraHelper;

import java.util.List;

public class FilmService {


    public void save(String title, String description, String path_trailer
            , String path_banner, int release_year, int duration_seconds, String path_video, List<Long> categorieIds) {
        TraHelper.write(em ->{
            FilmDAO filmDAO = new FilmDAO(em);
            CategoryDAO categoryDAO = new CategoryDAO(em);
            Film film = new Film(title,description,release_year,path_trailer,path_banner,duration_seconds,path_video);
            film.getCategories().addAll(categoryDAO.findById(categorieIds));
            filmDAO.save(film);
        });

    }

    public void delete(long id) throws NoResultException {
        TraHelper.write(em ->{
            FilmDAO filmDAO = new FilmDAO(em);
            Film film=filmDAO.findById(id);
            if(film==null){
                throw new NoResultException("Film Not Found");
            }
            filmDAO.delete(film);
        });
    }
    public void update(Long filmId,String title, String description, String path_trailer
            , String path_banner, int release_year, int duration_seconds, String path_video, List<Long> categorieIds) throws NoResultException {
        TraHelper.write(em ->{
            FilmDAO filmDAO = new FilmDAO(em);
            CategoryDAO categoryDAO = new CategoryDAO(em);
            Film film=filmDAO.findById(filmId);
            if(film==null){
                throw new NoResultException("Film Not Found");
            }
            film.setTitle(title);
            film.setDescription(description);
            film.setPathTrailer(path_trailer);
            film.setPathBanner(path_banner);
            film.setReleaseYear(release_year);
            film.setDurationSeconds(duration_seconds);
            film.setPathVideo(path_video);
            film.getCategories().clear();
            film.getCategories().addAll(categoryDAO.findById(categorieIds));
        });
    }
}
