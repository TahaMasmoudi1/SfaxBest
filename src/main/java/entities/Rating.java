package entities;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "rating")
public class Rating {
    @EmbeddedId
    private RatingId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @MapsId("idMultimedia")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_multimedia", nullable = false)
    private Multimedia multimedia;

    @Column(name = "rate", nullable = false)
    private Byte rate;

    @Column(name = "rate_date", nullable = false, insertable = false, updatable = false)
    private Instant rateDate;

    public Rating() {
    }

    public Rating(User user, Multimedia multimedia, Byte rate) {
        this.user = user;
        this.multimedia = multimedia;
        this.rate = rate;
    }

    public RatingId getId() {
        return id;
    }

    public void setId(RatingId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Multimedia getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(Multimedia idMultimedia) {
        this.multimedia = idMultimedia;
    }

    public Byte getRate() {
        return rate;
    }

    public void setRate(Byte rate) {
        this.rate = rate;
    }

    public Instant getRateDate() {
        return rateDate;
    }

    public void setRateDate(Instant rateDate) {
        this.rateDate = rateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Rating rating = (Rating) o;
        return Objects.equals(id, rating.id) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Rating{" +
                "id=" + id +
                ", user=" + user +
                ", rate=" + rate +
                ", rateDate=" + rateDate +
                '}';
    }
}