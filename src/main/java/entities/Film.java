package entities;

import jakarta.persistence.*;


@Entity
@Table(name = "film")
@PrimaryKeyJoinColumn(name = "id")
public class Film extends Multimedia {
    @Column(name = "duration_seconds", nullable = false)
    private Integer durationSeconds;

    @Column(name = "path_video", nullable = false)
    private String pathVideo;

    public Film() {
    }

    public Film(String title, String description, Integer releaseYear, String pathTrailer, String pathBanner, Integer durationSeconds, String pathVideo) {
        super(title, description, releaseYear, pathTrailer, pathBanner);
        this.durationSeconds = durationSeconds;
        this.pathVideo = pathVideo;
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

}