package Services;

import DAO.CategoryDAO;
import DAO.FilmDAO;
import entities.Film;
import jakarta.persistence.NoResultException;
import utils.TraHelper;

import java.util.List;

public class FilmService {


    public void save(String title, String description, String path_trailer
            , String path_banner, int release_year, int duration_seconds, String path_video, List<Long> categorieIds,String path_poster) {
        TraHelper.write(em ->{
            FilmDAO filmDAO = new FilmDAO(em);
            CategoryDAO categoryDAO = new CategoryDAO(em);
            Film film = new Film(title,description,release_year,path_trailer,path_banner,duration_seconds,path_video,path_poster);
            film.getCategories().addAll(categoryDAO.listByIds(categorieIds));
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
            , String path_banner, int release_year, int duration_seconds, String path_video, List<Long> categorieIds,String path_Poster) throws NoResultException {
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
            film.getCategories().addAll(categoryDAO.listByIds(categorieIds));
            film.setPathPoster(path_Poster);
        });
    }
    public List<Film> listAllWithCategories() {
        return TraHelper.read(em -> {
            FilmDAO filmDAO = new FilmDAO(em);
            return filmDAO.listAllWithCategories();
        });
    }

    public Film listFilmDetails(long id) {
        return TraHelper.read(em ->  {
            FilmDAO filmDAO = new FilmDAO(em);
            return filmDAO.listFilmDetails(id);
        });
    }
    public Film findById(Long id) {
        return TraHelper.read(em -> {
            FilmDAO filmDAO = new FilmDAO(em);
            return filmDAO.findById(id);
        });
    }

    public List<Film> listbyManyCategory(List<Long> ids,int offset,int limit) {
        return TraHelper.read(em -> {
            FilmDAO filmDAO = new FilmDAO(em);
            return filmDAO.listbyManyCategory(ids, offset, limit);
        });
    }

    public List<Film> listByReleaseYear(int releaseYear, int offset,int limit) {
        return TraHelper.read(em -> {
            FilmDAO filmDAO = new FilmDAO(em);
            return filmDAO.listByReleaseYear(releaseYear, offset, limit);
        });
    }

    public List<Film> listFilmsSearch(String search, int offset, int limit) {
        return TraHelper.read(em -> {
            FilmDAO filmDAO = new FilmDAO(em);
            return filmDAO.listFilmsSearch(search, offset, limit);
        });
    }

    public List<Film> listFilmsSearchWithCategory(String search,List<Long> categoryIds,int offset,int limit){
        return TraHelper.read(em -> {
            FilmDAO filmDAO = new FilmDAO(em);
            return filmDAO.listFilmsSearchWithCategory(search, categoryIds, offset, limit);
        });
    }
    public List<Film> listFilmByCategoryName(String category, int offset,int limit) {
        return TraHelper.read(em -> {
            FilmDAO filmDAO = new FilmDAO(em);
            return filmDAO.listByCategoryName(category, offset, limit);
        });
    }
    public int countFilms(){
        return TraHelper.read(em ->  {
            FilmDAO filmDAO = new FilmDAO(em);
            return filmDAO.countFilms();
        }).intValue();
    }
    public List<Object[]> getTopByViews(int limit){
        return TraHelper.read(em -> {
            FilmDAO filmDAO = new FilmDAO(em);
            return filmDAO.getTopByViews(limit);
        });

    }
}
