package entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "series")
@PrimaryKeyJoinColumn(name = "id")
public class Serie extends Multimedia {
    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Season> seasons = new ArrayList<>();

    public Serie() {
    }

    public Serie(String title, String description, Integer releaseYear, String pathTrailer, String pathBanner) {
        super(title, description, releaseYear, pathTrailer, pathBanner);
    }


    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }


}