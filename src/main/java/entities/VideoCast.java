package entities;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(
        name = "video_cast",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_video_cast_multimedia_cast_role",
                columnNames = {"id_multimedia", "id_cast_member", "role"}
        ),
        indexes = {
                @Index(name = "idx_video_cast_multimedia", columnList = "id_multimedia"),
                @Index(name = "idx_video_cast_cast_member", columnList = "id_cast_member")
        }
)
public class VideoCast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_multimedia", nullable = false)
    private Multimedia multimedia;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_cast_member", nullable = false)
    private CastMember castMember;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 50)
    private VideoCastRole role;

    protected VideoCast() {}

    public VideoCast(Multimedia multimedia, CastMember castMember, VideoCastRole role) {
        this.multimedia = Objects.requireNonNull(multimedia);
        this.castMember = Objects.requireNonNull(castMember);
        this.role = Objects.requireNonNull(role);
    }

    public Long getId() { return id; }
    public Multimedia getMultimedia() { return multimedia; }
    public CastMember getCastMember() { return castMember; }
    public VideoCastRole getRole() { return role; }

    public void setRole(VideoCastRole role) { this.role = Objects.requireNonNull(role); }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VideoCast)) return false;
        VideoCast other = (VideoCast) o;
        return id != null && id.equals(other.id);
    }
    @Override public int hashCode() { return 31; }
}
