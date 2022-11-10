package com.example.musicstorerecommendations.repository;

import com.example.musicstorerecommendations.model.ArtistRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ArtistRecommendationRepository extends JpaRepository<ArtistRecommendation, Integer> {
    void deleteByArtistRecommendationId(Integer artistRecommendationId);
}
