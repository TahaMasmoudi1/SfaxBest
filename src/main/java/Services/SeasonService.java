package Services;

import DAO.CategoryDAO;
import DAO.SeasonDAO;
import DAO.SerieDAO;
import entities.Season;
import entities.Serie;
import jakarta.persistence.NoResultException;
import utils.TraHelper;

import java.util.List;

public class SeasonService {
    public void addSeasonToSerie(long idSerie,Integer nSeason, String pathBannerSeason, String pathTrailerSeason) throws NoResultException {
        TraHelper.write(em -> {
            SeasonDAO seasonDAO = new SeasonDAO(em);
            SerieDAO serieDAO = new SerieDAO(em);
            Serie serie = serieDAO.findById(idSerie);
            if(serie==null){
                throw new NoResultException("Serie not found");
            }
            Season season = new Season(serie,nSeason, pathBannerSeason, pathTrailerSeason);
            seasonDAO.save(season);
        });
    }

    public void delete(long idSeason) throws NoResultException {
        TraHelper.write(em -> {
            SeasonDAO seasonDAO = new SeasonDAO(em);
            Season season = seasonDAO.findById(idSeason);
            if (season == null) {
                throw new NoResultException("Season Not Found");
            }
            seasonDAO.delete(season);
        });
    }
    public void update(long id ,Integer nSeason, String pathBannerSeason, String pathTrailerSeason) throws NoResultException {
        TraHelper.write(em -> {
            SeasonDAO seasonDAO = new SeasonDAO(em);
            Season season = seasonDAO.findById(id);
            if (season == null) {
                throw new NoResultException("Serie Not Found");
            }
            season.setnSeason(nSeason);
            season.setPathBannerSeason(pathBannerSeason);
            season.setPathTrailerSeason(pathTrailerSeason);
        });
    }
    public List<Season> listAll(long idSerie,int offset,int limit)  {
        return TraHelper.read(em ->  {
            SeasonDAO seasonDAO = new SeasonDAO(em);
                return seasonDAO.listAll(idSerie, offset, limit);
        });

    }

}
