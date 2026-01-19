package entities;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "watch_history")
public class WatchHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_multimedia", nullable = false)
    private Multimedia multimedia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_episode")
    private Episode episode;

    @Column(name = "progress_second", nullable = false)
    private Integer progressSecond = 0;

    @Column(name = "completed", nullable = false)
    private Boolean completed = false;

    @Column(name = "last_watched_at", nullable = false, insertable = false, updatable = false)
    private Instant lastWatchedAt;

    public WatchHistory() {
    }

    public WatchHistory(User user, Multimedia multimedia) {
        this.user = user;
        this.multimedia = multimedia;
    }

    public WatchHistory(User user, Multimedia multimedia, Episode episode) {
        this.user = user;
        this.multimedia = multimedia;
        this.episode = episode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Multimedia getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(Multimedia idMultimedia) {
        this.multimedia = idMultimedia;
    }

    public Episode getEpisode() {
        return episode;
    }

    public void setEpisode(Episode idEpisode) {
        this.episode = idEpisode;
    }

    public Integer getProgressSecond() {
        return progressSecond;
    }

    public void setProgressSecond(Integer progressSecond) {
        this.progressSecond = progressSecond;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Instant getLastWatchedAt() {
        return lastWatchedAt;
    }

    public void setLastWatchedAt(Instant lastWatchedAt) {
        this.lastWatchedAt = lastWatchedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        WatchHistory that = (WatchHistory) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "WatchHistory{" +
                "id=" + id +
                ", episode=" + episode +
                ", progressSecond=" + progressSecond +
                ", completed=" + completed +
                ", lastWatchedAt=" + lastWatchedAt +
                '}';
    }
}