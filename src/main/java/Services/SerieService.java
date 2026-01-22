package Services;

import DAO.CategoryDAO;
import DAO.SerieDAO;
import entities.Serie;
import jakarta.persistence.NoResultException;
import utils.TraHelper;

import java.util.List;

public class SerieService {


    public void save(String title, String description, Integer releaseYear, String pathTrailer, String pathBanner, List<Long> categorieIds) {
        TraHelper.write(em -> {
            SerieDAO serieDAO = new SerieDAO(em);
            CategoryDAO categoryDAO = new CategoryDAO(em);
            Serie serie = new Serie(title, description, releaseYear, pathTrailer, pathBanner);
            serie.getCategories().addAll(categoryDAO.listByIds(categorieIds));
            serieDAO.save(serie);
        });

    }

    public void delete(long id) throws NoResultException {
        TraHelper.write(em -> {
            SerieDAO serieDAO = new SerieDAO(em);
            Serie serie = serieDAO.findById(id);
            if (serie == null) {
                throw new NoResultException("Serie Not Found");
            }
            serieDAO.delete(serie);
        });
    }

    public void update(Long serieId, String title, String description, String path_trailer, String path_banner, int release_year, List<Long> categorieIds) throws NoResultException {
        TraHelper.write(em -> {
            SerieDAO serieDAO = new SerieDAO(em);
            CategoryDAO categoryDAO = new CategoryDAO(em);
            Serie serie = serieDAO.findById(serieId);
            if (serie == null) {
                throw new NoResultException("Serie Not Found");
            }
            serie.setTitle(title);
            serie.setDescription(description);
            serie.setPathTrailer(path_trailer);
            serie.setPathBanner(path_banner);
            serie.setReleaseYear(release_year);
            serie.getCategories().clear();
            serie.getCategories().addAll(categoryDAO.listByIds(categorieIds));
        });
    }
}