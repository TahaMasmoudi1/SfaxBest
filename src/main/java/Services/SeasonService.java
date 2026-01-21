package Services;

import DAO.EpisodeDAO;
import DAO.SeasonDAO;
import entities.Episode;
import entities.Season;
import utils.TraHelper;

public class SeasonService {
    public void save(Season season) {
        TraHelper.write(em -> {
            SeasonDAO seasonDAO = new SeasonDAO(em);
            seasonDAO.save(season);
        });
    }

    public void delete(Season season) {
        TraHelper.write(em -> {
            SeasonDAO seasonDAO = new SeasonDAO(em);
            seasonDAO.delete(season);
        });
    }
    public void edit(Season season) {
        TraHelper.write(em -> {
            SeasonDAO seasonDAO = new SeasonDAO(em);
            seasonDAO.update(season);
        });
    }

}
