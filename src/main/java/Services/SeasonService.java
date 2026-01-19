package Services;

import DAO.SeasonDAO;
import entities.Season;

public class SeasonService {
    private SeasonDAO seasonDAO;

    public SeasonService() {
        this.seasonDAO = new SeasonDAO();
    }

    public void save(Season season) {
        seasonDAO.save(season);
    }

    public void delete(Season season) {
        seasonDAO.delete(season);
    }

    public void update(Season season) {
        seasonDAO.update(season);
    }
}
