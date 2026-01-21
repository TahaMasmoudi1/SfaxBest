package entities;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Entity
@Table(name = "watch_history_episode")
public class WatchHistoryEpisode {
    @EmbeddedId
    private WatchHistoryEpisodeId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @MapsId("episodeId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "episode_id", nullable = false)
    private Episode episode;

    @ColumnDefault("b'0'")
    @Column(name = "completed", nullable = false)
    private Boolean completed = false;

    @ColumnDefault("0")
    @Column(name = "progress_second", nullable = false)
    private Integer progressSecond;

    @Column(name = "last_watched_at", nullable = false)
    private Instant lastWatchedAt;

    public WatchHistoryEpisode(WatchHistoryEpisodeId id, User user, Episode episode, Boolean completed, Integer progressSecond, Instant lastWatchedAt) {
        this.id = id;
        this.user = user;
        this.episode = episode;
        this.completed = completed;
        this.progressSecond = progressSecond;
        this.lastWatchedAt = lastWatchedAt;
    }

    public WatchHistoryEpisode() {
    }

    public WatchHistoryEpisodeId getId() {
        return id;
    }

    public void setId(WatchHistoryEpisodeId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Episode getEpisode() {
        return episode;
    }

    public void setEpisode(Episode episode) {
        this.episode = episode;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Integer getProgressSecond() {
        return progressSecond;
    }

    public void setProgressSecond(Integer progressSecond) {
        this.progressSecond = progressSecond;
    }

    public Instant getLastWatchedAt() {
        return lastWatchedAt;
    }

    public void setLastWatchedAt(Instant lastWatchedAt) {
        this.lastWatchedAt = lastWatchedAt;
    }

}