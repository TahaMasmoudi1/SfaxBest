package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FavoriteId implements Serializable {
    private static final long serialVersionUID = -1831836565283899259L;
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "id_multimedia", nullable = false)
    private Long idMultimedia;

    public FavoriteId() {
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
        FavoriteId entity = (FavoriteId) o;
        return Objects.equals(this.idMultimedia, entity.idMultimedia) &&
                Objects.equals(this.userId, entity.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMultimedia, userId);
    }

}