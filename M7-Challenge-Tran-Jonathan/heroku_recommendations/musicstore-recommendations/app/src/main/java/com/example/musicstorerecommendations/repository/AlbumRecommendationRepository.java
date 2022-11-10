package com.example.musicstorerecommendations.repository;

import com.example.musicstorerecommendations.model.AlbumRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AlbumRecommendationRepository extends JpaRepository<AlbumRecommendation, Integer> {
    void deleteByAlbumRecommendationId(Integer albumRecommendationId);
}
