package Services;

import DAO.EpisodeDAO;
import DAO.SeasonDAO;
import entities.Episode;
import entities.Season;
import jakarta.persistence.NoResultException;
import utils.TraHelper;

import java.util.List;

public class EpisodeService {
    public void addEpisodeToSeason(long idSeason, Integer nEpisode, String titre, Integer dureeSeconds, String resume, String thumbnailUrl, String videoPath) throws NoResultException {
        TraHelper.write(em -> {
            SeasonDAO seasonDAO = new SeasonDAO(em);
            EpisodeDAO episodeDAO = new EpisodeDAO(em);
            Season season = seasonDAO.findById(idSeason);
            if (season == null) {
                throw new NoResultException("Season not found");
            }
            Episode episode = new Episode(season,nEpisode,titre,dureeSeconds,resume,thumbnailUrl,videoPath);
            episodeDAO.save(episode);
        });
    }

    public void delete(long idEpisode,long seasonId) throws NoResultException {
        TraHelper.write(em -> {
            EpisodeDAO episodeDAO = new EpisodeDAO(em);
            Episode episode = episodeDAO.findById(idEpisode);
            if (episode == null) {
                throw new NoResultException("Episode Not Found");
            }
            if(!episode.getSeason().getId().equals(seasonId)){
                throw new NoResultException("Season Not Found");
            }
            episodeDAO.delete(episode);
        });
    }

    public void update(long idEpisode, Integer nEpisode, String titre, Integer dureeSeconds, String resume, String thumbnailUrl, String videoPath) throws NoResultException {
        TraHelper.write(em -> {
            EpisodeDAO episodeDAO = new EpisodeDAO(em);
            Episode episode = episodeDAO.findById(idEpisode);
            if (episode == null) {
                throw new NoResultException("Episode Not Found");
            }
            episode.setTitre(titre);
            episode.setNEpisode(nEpisode);
            episode.setDureeSeconds(dureeSeconds);
            episode.setResume(resume);
            episode.setThumbnailUrl(thumbnailUrl);
            episode.setVideoPath(videoPath);
        });
    }
    public List<Episode> listAll(long idSeason, int offset, int limit) throws NoResultException {
        return TraHelper.read(em ->  {
            EpisodeDAO episodeDAO = new EpisodeDAO(em);
            try {
                return episodeDAO.listAll(idSeason, offset, limit);
            }catch (Exception e){
                throw new NoResultException("listAll Error");
            }
        });

    }
}
