package Services;

import DAO.*;
import entities.*;
import utils.TraHelper;

import java.time.Instant;
import java.util.Optional;

public class WatchHistoryEpisodeService {
    public int getResumeSecond(long userID, long episodeID) {
        return TraHelper.read(em -> {
            WatchHistoryEpisodeDAO watchHistoryEpisodeDAO = new WatchHistoryEpisodeDAO(em);
            Optional<WatchHistoryEpisode> wm = watchHistoryEpisodeDAO.findById(userID, episodeID);
            if (wm.isEmpty()) return 0;
            WatchHistoryEpisode watchHistoryEpisode = wm.get();
            if (watchHistoryEpisode.getCompleted()) return 0;
            return watchHistoryEpisode.getProgressSecond();


        });
    }

    public void saveProgress(long userID, long episodeID,int progressSecond) {
        TraHelper.write(em -> {
            EpisodeDAO episodeDAO = new EpisodeDAO(em);
            Episode episode = episodeDAO.findById(episodeID);
            UserDAO userDAO = new UserDAO(em);
            User user = userDAO.findById(userID);
            int duration =0;
            boolean completed = progressSecond>=(duration-10);
            WatchHistoryEpisodeDAO watchHistoryEpisodeDAO = new WatchHistoryEpisodeDAO(em);
            Optional<WatchHistoryEpisode> we = watchHistoryEpisodeDAO.findById(userID,episodeID);

            WatchHistoryEpisode watchHistoryEpisode =we.orElseGet(()->{
                WatchHistoryEpisode watch =new WatchHistoryEpisode();
                watch.setId(new WatchHistoryEpisodeId(userID,episodeID));
                watch.setUser(user);
                watch.setEpisode(episode);
                watch.setCompleted(false);
                watch.setProgressSecond(0);
                watch.setLastWatchedAt(Instant.now());
                return watch;
            });
            watchHistoryEpisode.setLastWatchedAt(Instant.now());
            watchHistoryEpisode.setCompleted(completed);
            watchHistoryEpisode.setProgressSecond(progressSecond);
            watchHistoryEpisodeDAO.update(watchHistoryEpisode);

        });

    }
}
