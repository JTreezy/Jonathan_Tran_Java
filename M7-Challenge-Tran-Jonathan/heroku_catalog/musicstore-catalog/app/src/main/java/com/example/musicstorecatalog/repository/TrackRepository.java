package com.example.musicstorecatalog.repository;


import com.example.musicstorecatalog.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrackRepository extends JpaRepository<Track, Integer> {
    void deleteByTrackId(Integer trackId);
}
