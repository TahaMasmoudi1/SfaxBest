package entities;

import jakarta.persistence.*;

@Entity
@Table(name = "documentary")
@PrimaryKeyJoinColumn(name = "id")

public class Documentary extends Multimedia {

    @Column(name = "duration_seconds")
    private Integer durationSeconds;

    @Column(name = "path_video")
    private String pathVideo;

    public Documentary() {
    }

    public Documentary(String title, String description, Integer releaseYear, String pathTrailer, String pathBanner, Integer durationSeconds, String pathVideo) {
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