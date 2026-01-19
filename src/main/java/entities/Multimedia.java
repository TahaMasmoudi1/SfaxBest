package entities;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "multimedia")
public class Multimedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private MultimediaType type;

    @Column(name = "title", nullable = false, length = 150)
    private String title;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "release_year")
    private Integer releaseYear;

    @Column(name = "duration_seconds")
    private Integer durationSeconds;

    @Column(name = "path_video")
    private String pathVideo;

    @Column(name = "path_trailer")
    private String pathTrailer;

    @Column(name = "path_banner")
    private String pathBanner;

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private Instant createdAt;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "multimedia_category",
            joinColumns = @JoinColumn(name = "id_multimedia"),
            inverseJoinColumns = @JoinColumn(name = "id_category")
    )
    private Set<Category> categories = new HashSet<>();

    public Multimedia() {
    }

    public Multimedia(MultimediaType type, String title, String description, Integer releaseYear, Integer durationSeconds, String pathVideo, String pathTrailer, String pathBanner) {
        this.type = type;
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.durationSeconds = durationSeconds;
        this.pathVideo = pathVideo;
        this.pathTrailer = pathTrailer;
        this.pathBanner = pathBanner;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MultimediaType getType() {
        return type;
    }

    public void setType(MultimediaType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Integer getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(Integer durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public String getPathVideo() {
        return pathVideo;
    }

    public void setPathVideo(String pathVideo) {
        this.pathVideo = pathVideo;
    }

    public String getPathTrailer() {
        return pathTrailer;
    }

    public void setPathTrailer(String pathTrailer) {
        this.pathTrailer = pathTrailer;
    }

    public String getPathBanner() {
        return pathBanner;
    }

    public void setPathBanner(String pathBanner) {
        this.pathBanner = pathBanner;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Multimedia that = (Multimedia) o;
        return Objects.equals(id, that.id) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Multimedia{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", releaseYear=" + releaseYear +
                ", durationSeconds=" + durationSeconds +
                ", pathVideo='" + pathVideo + '\'' +
                ", pathTrailer='" + pathTrailer + '\'' +
                ", pathBanner='" + pathBanner + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}