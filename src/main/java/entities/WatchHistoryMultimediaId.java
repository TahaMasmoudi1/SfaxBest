package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class WatchHistoryMultimediaId implements Serializable {
    private static final long serialVersionUID = 5329646729383039876L;
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "multimedia_id", nullable = false)
    private Long multimediaId;

    public WatchHistoryMultimediaId() {
    }

    public WatchHistoryMultimediaId(Long userId, Long multimediaId) {
        this.userId = userId;
        this.multimediaId = multimediaId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMultimediaId() {
        return multimediaId;
    }

    public void setMultimediaId(Long multimediaId) {
        this.multimediaId = multimediaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        WatchHistoryMultimediaId entity = (WatchHistoryMultimediaId) o;
        return Objects.equals(this.multimediaId, entity.multimediaId) &&
                Objects.equals(this.userId, entity.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(multimediaId, userId);
    }

}