package com.example.musicstorerecommendations.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
@Table(name="album_recommendation")
public class AlbumRecommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="album_recommendation_id")
    private Integer albumRecommendationId;
    @Column(name="album_id")
    private int albumId;
    @Column(name="user_id")
    private int userId;
    private boolean liked;

    public AlbumRecommendation() {
    }

    public AlbumRecommendation(Integer albumRecommendationId, int albumId, int userId, boolean liked) {
        this.albumRecommendationId = albumRecommendationId;
        this.albumId = albumId;
        this.userId = userId;
        this.liked = liked;
    }

    public AlbumRecommendation(int albumId, int userId, boolean liked) {
        this.albumId = albumId;
        this.userId = userId;
        this.liked = liked;
    }

    public Integer getAlbumRecommendationId() {
        return albumRecommendationId;
    }

    public void setAlbumRecommendationId(Integer albumRecommendationId) {
        this.albumRecommendationId = albumRecommendationId;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
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
        AlbumRecommendation that = (AlbumRecommendation) o;
        return albumRecommendationId == that.albumRecommendationId && albumId == that.albumId && userId == that.userId && liked == that.liked;
    }

    @Override
    public int hashCode() {
        return Objects.hash(albumRecommendationId, albumId, userId, liked);
    }

    @Override
    public String toString() {
        return "AlbumRecommendation{" +
                "albumRecommendationId=" + albumRecommendationId +
                ", albumId=" + albumId +
                ", userId=" + userId +
                ", liked=" + liked +
                '}';
    }
}
