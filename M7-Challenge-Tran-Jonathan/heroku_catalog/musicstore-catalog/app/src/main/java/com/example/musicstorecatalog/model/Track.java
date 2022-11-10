package com.example.musicstorecatalog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
@Table(name="track")
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="track_id")
    private Integer trackId;
    @Column(name="album_id")
    private int albumId;
    private String title;
    @Column(name="run_time")
    private int runTime;

    public Track() {
    }

    public Track(Integer trackId, int albumId, String title, int runTime) {
        this.trackId = trackId;
        this.albumId = albumId;
        this.title = title;
        this.runTime = runTime;
    }

    public Track(int albumId, String title, int runTime) {
        this.albumId = albumId;
        this.title = title;
        this.runTime = runTime;
    }

    public Integer getTrackId() {
        return trackId;
    }

    public void setTrackId(Integer trackId) {
        this.trackId = trackId;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRunTime() {
        return runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Track track = (Track) o;
        return trackId == track.trackId && albumId == track.albumId && runTime == track.runTime && Objects.equals(title, track.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trackId, albumId, title, runTime);
    }

    @Override
    public String toString() {
        return "Track{" +
                "trackId=" + trackId +
                ", albumId=" + albumId +
                ", title='" + title + '\'' +
                ", runTime=" + runTime +
                '}';
    }

    //    index fk_album_id (album_id),
//    foreign key (album_id)
//    references album(album_id)
}
