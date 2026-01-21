package Services;

import DAO.EpisodeDAO;
import entities.Episode;
import utils.TraHelper;

public class EpisodeService {
    public void save(Episode episode) {
        TraHelper.write(em->{
            EpisodeDAO episodeDAO=new EpisodeDAO(em);
            episodeDAO.save(episode);
        });
    }
    public void delete(Episode episode) {
        TraHelper.write(em->{
            EpisodeDAO episodeDAO=new EpisodeDAO(em);
            episodeDAO.delete(episode);
        });
    }
    public void edit(Episode episode) {
        TraHelper.write(em->{
            EpisodeDAO episodeDAO=new EpisodeDAO(em);
            episodeDAO.update(episode);
        });
    }
}
