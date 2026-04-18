package Services;

import DAO.CategoryDAO;
import DAO.SerieDAO;
import entities.Serie;
import jakarta.persistence.NoResultException;
import org.hibernate.Hibernate;
import utils.TraHelper;

import java.util.List;

public class SerieService {


    public void save(String title, String description, Integer releaseYear,
                     String pathTrailer, String pathBanner,
                     List<Long> categorieIds, String pathPoster) {

        TraHelper.write(em -> {

            SerieDAO serieDAO = new SerieDAO(em);
            CategoryDAO categoryDAO = new CategoryDAO(em);

            Serie serie = new Serie(
                    title,
                    description,
                    releaseYear,
                    pathTrailer,
                    pathBanner,
                    pathPoster
            );

            serie.getCategories()
                    .addAll(categoryDAO.listByIds(categorieIds));

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


    public void update(Long serieId,
                       String title,
                       String description,
                       String path_trailer,
                       String path_banner,
                       int releaseYear,
                       List<Long> categorieIds,
                       String pathPoster) throws NoResultException {

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
            serie.setReleaseYear(releaseYear);

            serie.getCategories().clear();

            serie.getCategories()
                    .addAll(categoryDAO.listByIds(categorieIds));

            serie.setPathPoster(pathPoster);

        });

    }


    public List<Serie> listAllWithCategories() {

        return TraHelper.read(em -> {

            SerieDAO serieDAO = new SerieDAO(em);

            return serieDAO.listAllWithCategories();

        });

    }


    public Serie listSerieDetails(long id) {

        return TraHelper.read(em -> {

            SerieDAO serieDAO = new SerieDAO(em);
            Serie serie =serieDAO.listSerieDetails(id);
            Hibernate.initialize(serie.getSeasons());
            Hibernate.initialize(serie.getCategories());
            Hibernate.initialize(serie.getVideoCasts());
            return  serie;

        });

    }


    public List<Serie> listbyManyCategory(List<Long> ids,
                                          int offset,
                                          int limit) {

        return TraHelper.read(em -> {

            SerieDAO serieDAO = new SerieDAO(em);

            return serieDAO.listbyManyCategory(
                    ids,
                    offset,
                    limit
            );

        });

    }


    public List<Serie> listByReleaseYear(int releaseYear,
                                         int offset,
                                         int limit) {

        return TraHelper.read(em -> {

            SerieDAO serieDAO = new SerieDAO(em);

            return serieDAO.listByReleaseYear(
                    releaseYear,
                    offset,
                    limit
            );

        });

    }


    public List<Serie> listSeriesSearch(String search,
                                        int offset,
                                        int limit) {

        return TraHelper.read(em -> {

            SerieDAO serieDAO = new SerieDAO(em);

            return serieDAO.listSeriesSearch(
                    search,
                    offset,
                    limit
            );

        });

    }


    public List<Serie> listSeriesSearchWithCategory(
            String search,
            List<Long> categoryIds,
            int offset,
            int limit) {

        return TraHelper.read(em -> {

            SerieDAO serieDAO = new SerieDAO(em);

            return serieDAO.listSeriesSearchWithCategory(
                    search,
                    categoryIds,
                    offset,
                    limit
            );

        });

    }


    public List<Serie> listByCategoryName(String categoryName,
                                          int offset,
                                          int limit) {

        return TraHelper.read(em -> {

            SerieDAO serieDAO = new SerieDAO(em);

            return serieDAO.listByCategoryName(
                    categoryName,
                    offset,
                    limit
            );

        });

    }


    public int countSerie(){

        return TraHelper.read(em ->  {

            SerieDAO serieDAO = new SerieDAO(em);

            return serieDAO.countSeries();

        }).intValue();

    }

}