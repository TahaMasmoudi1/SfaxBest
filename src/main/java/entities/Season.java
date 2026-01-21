package entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(
        name = "season",
        uniqueConstraints = @UniqueConstraint(columnNames = {"series_id", "n_season"})
)
public class Season {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "series_id", nullable = false)
    private Series series;

    @Column(name = "n_season", nullable = false)
    private Integer nSeason;

    @Column(name = "path_banner_season")
    private String pathBannerSeason;

    @Column(name = "path_trailer_season")
    private String pathTrailerSeason;
    @OneToMany(mappedBy = "season", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Episode> episodes = new ArrayList<>();

    public Season( Integer nSeason, String pathBannerSeason, String pathTrailerSeason) {
        this.nSeason = nSeason;
        this.pathBannerSeason = pathBannerSeason;
        this.pathTrailerSeason = pathTrailerSeason;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public Integer getnSeason() {
        return nSeason;
    }

    public void setnSeason(Integer nSeason) {
        this.nSeason = nSeason;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }

    public Season() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getPathBannerSeason() {
        return pathBannerSeason;
    }

    public void setPathBannerSeason(String pathBannerSaison) {
        this.pathBannerSeason = pathBannerSaison;
    }

    public String getPathTrailerSeason() {
        return pathTrailerSeason;
    }

    public void setPathTrailerSeason(String pathTrailerSaison) {
        this.pathTrailerSeason = pathTrailerSaison;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Season season = (Season) o;
        return Objects.equals(id, season.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Season{" +
                "id=" + id +
                ", nSeason=" + nSeason +
                ", pathBannerSeason='" + pathBannerSeason + '\'' +
                ", pathTrailerSeason='" + pathTrailerSeason + '\'' +
                '}';
    }
}