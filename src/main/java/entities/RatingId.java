package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RatingId implements Serializable {
    private static final long serialVersionUID = 667000008410631365L;
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "id_multimedia", nullable = false)
    private Long idMultimedia;

    public RatingId() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getIdMultimedia() {
        return idMultimedia;
    }

    public void setIdMultimedia(Long idMultimedia) {
        this.idMultimedia = idMultimedia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RatingId entity = (RatingId) o;
        return Objects.equals(this.idMultimedia, entity.idMultimedia) &&
                Objects.equals(this.userId, entity.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMultimedia, userId);
    }

}