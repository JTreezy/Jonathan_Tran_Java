package com.example.musicstorerecommendations.repository;

import com.example.musicstorerecommendations.model.TrackRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface TrackRecommendationRepository extends JpaRepository<TrackRecommendation, Integer> {
    void deleteByTrackRecommendationId(Integer trackRecommendationId);
}
