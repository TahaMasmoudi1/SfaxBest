package entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "episode")
public class Episode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_season", nullable = false)
    private Season season;

    @Column(name = "n_episode", nullable = false)
    private Integer nEpisode;

    @Column(name = "titre", nullable = false, length = 150)
    private String titre;

    @Column(name = "duree_seconds")
    private Integer dureeSeconds;

    @Lob
    @Column(name = "resume")
    private String resume;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "video_path", nullable = false)
    private String videoPath;

    public Episode() {
    }

    public Episode(Season season, Integer nEpisode, String titre, Integer dureeSeconds, String resume, String thumbnailUrl, String videoPath) {
        this.season = season;
        this.nEpisode = nEpisode;
        this.titre = titre;
        this.dureeSeconds = dureeSeconds;
        this.resume = resume;
        this.thumbnailUrl = thumbnailUrl;
        this.videoPath = videoPath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season idSaison) {
        this.season = idSaison;
    }

    public Integer getNEpisode() {
        return nEpisode;
    }

    public void setNEpisode(Integer nEpisode) {
        this.nEpisode = nEpisode;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Integer getDureeSeconds() {
        return dureeSeconds;
    }

    public void setDureeSeconds(Integer dureeSeconds) {
        this.dureeSeconds = dureeSeconds;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Episode episode = (Episode) o;
        return Objects.equals(id, episode.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Episode{" +
                "id=" + id +
                ", nEpisode=" + nEpisode +
                ", titre='" + titre + '\'' +
                ", dureeSeconds=" + dureeSeconds +
                ", resume='" + resume + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                ", videoPath='" + videoPath + '\'' +
                '}';
    }
}