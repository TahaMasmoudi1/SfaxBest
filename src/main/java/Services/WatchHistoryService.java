package Services;

import DAO.*;
import entities.*;
import utils.TraHelper;

import java.time.Instant;
import java.util.Optional;

public class WatchHistoryService {

    public int getResumeSecond(long userID, long multimediaID) {
        return TraHelper.read(em -> {
            WatchHistoryMultimediaDAO dao = new WatchHistoryMultimediaDAO(em);
            Optional<WatchHistoryMultimedia> wm = dao.findById(userID, multimediaID);
            if (wm.isEmpty()) return 0;
            WatchHistoryMultimedia watch = wm.get();
            if (watch.getCompleted()) return 0;
            return watch.getProgressSecond();
                });
            }


    public void saveProgress(long userID, long multimediaID, int progressSecond) {
        TraHelper.write(em -> {
            MultimediaDAO multimediaDAO = new MultimediaDAO(em);
            UserDAO userDAO = new UserDAO(em);
            Multimedia multimedia = multimediaDAO.findById(multimediaID);
            User user = userDAO.findById(userID);
            int duration = multimedia.getDurationSeconds();
            boolean completed = progressSecond >= (duration - 10);
            WatchHistoryMultimediaDAO dao = new WatchHistoryMultimediaDAO(em);
            Optional<WatchHistoryMultimedia> existing = dao.findById(userID, multimediaID);
            WatchHistoryMultimedia watch = existing.orElseGet(() -> {
                WatchHistoryMultimedia w = new WatchHistoryMultimedia();
                w.setId(new WatchHistoryMultimediaId(userID, multimediaID));
                w.setUser(user);
                w.setMultimedia(multimedia);
                w.setCompleted(false);
                w.setProgressSecond(0);
                w.setLastWatchedAt(Instant.now());
                return w;
                    });

            watch.setLastWatchedAt(Instant.now());
            watch.setCompleted(completed);
            watch.setProgressSecond(progressSecond);
            dao.update(watch);

            });
        }

}