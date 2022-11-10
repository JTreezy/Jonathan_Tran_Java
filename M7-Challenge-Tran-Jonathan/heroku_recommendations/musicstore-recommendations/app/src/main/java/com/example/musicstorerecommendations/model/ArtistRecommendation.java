package com.example.musicstorerecommendations.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
@Table(name="artist_recommendation")
public class ArtistRecommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="artist_recommendation_id")
    private Integer artistRecommendationId;
    @Column(name="artist_id")
    private int artistId;
    @Column(name="user_id")
    private int userId;
    private boolean liked;

    public ArtistRecommendation() {
    }

    public ArtistRecommendation(Integer artistRecommendationId, int artistId, int userId, boolean liked) {
        this.artistRecommendationId = artistRecommendationId;
        this.artistId = artistId;
        this.userId = userId;
        this.liked = liked;
    }

    public ArtistRecommendation(int artistId, int userId, boolean liked) {
        this.artistId = artistId;
        this.userId = userId;
        this.liked = liked;
    }

    public Integer getArtistRecommendationId() {
        return artistRecommendationId;
    }

    public void setArtistRecommendationId(Integer artistRecommendationId) {
        this.artistRecommendationId = artistRecommendationId;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtistRecommendation that = (ArtistRecommendation) o;
        return artistRecommendationId == that.artistRecommendationId && artistId == that.artistId && userId == that.userId && liked == that.liked;
    }

    @Override
    public int hashCode() {
        return Objects.hash(artistRecommendationId, artistId, userId, liked);
    }

    @Override
    public String toString() {
        return "ArtistRecommendation{" +
                "artistRecommendationId=" + artistRecommendationId +
                ", artistId=" + artistId +
                ", userId=" + userId +
                ", liked=" + liked +
                '}';
    }
}
