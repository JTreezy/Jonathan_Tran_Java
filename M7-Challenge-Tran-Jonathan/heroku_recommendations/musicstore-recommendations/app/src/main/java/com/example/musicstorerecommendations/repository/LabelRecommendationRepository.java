package com.example.musicstorerecommendations.repository;

import com.example.musicstorerecommendations.model.LabelRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LabelRecommendationRepository extends JpaRepository<LabelRecommendation, Integer> {
    void deleteByLabelRecommendationId(Integer labelRecommendationId);
}
