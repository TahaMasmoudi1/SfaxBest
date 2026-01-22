package entities;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "multimedia")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Multimedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false, length = 150)
    private String title;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "release_year")
    private Integer releaseYear;

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

    @OneToMany(mappedBy = "multimedia", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<VideoCast> videoCasts = new HashSet<>();

    public Multimedia() {
    }

    public Multimedia(String title, String description, Integer releaseYear, String pathTrailer, String pathBanner) {
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.pathTrailer = pathTrailer;
        this.pathBanner = pathBanner;
    }

    public Set<VideoCast> getVideoCasts() {
        return videoCasts;
    }

    public void setVideoCasts(Set<VideoCast> videoCasts) {
        this.videoCasts = videoCasts;
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
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Multimedia{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", releaseYear=" + releaseYear +
                ", pathTrailer='" + pathTrailer + '\'' +
                ", pathBanner='" + pathBanner + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}