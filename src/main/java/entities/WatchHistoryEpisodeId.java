package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class WatchHistoryEpisodeId implements Serializable {
    private static final long serialVersionUID = 9102022519773157519L;
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "episode_id", nullable = false)
    private Long episodeId;

    public WatchHistoryEpisodeId(Long userId, Long episodeId) {
        this.userId = userId;
        this.episodeId = episodeId;
    }

    public WatchHistoryEpisodeId() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(Long episodeId) {
        this.episodeId = episodeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        WatchHistoryEpisodeId entity = (WatchHistoryEpisodeId) o;
        return Objects.equals(this.episodeId, entity.episodeId) &&
                Objects.equals(this.userId, entity.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(episodeId, userId);
    }

}