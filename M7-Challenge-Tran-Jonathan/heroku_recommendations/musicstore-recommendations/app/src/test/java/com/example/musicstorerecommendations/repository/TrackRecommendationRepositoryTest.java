package com.example.musicstorerecommendations.repository;

import com.example.musicstorerecommendations.model.TrackRecommendation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrackRecommendationRepositoryTest {
    @Autowired
    private TrackRecommendationRepository repo;
    @Before
    public void setUp(){
        //clear out database
        repo.deleteAll();
    }

    @Test
    public void shouldCreateGetAndDeleteTrackRecommendation(){
        TrackRecommendation trackRecommendation = new TrackRecommendation();
        trackRecommendation.setTrackId(9);
        trackRecommendation.setUserId(4);
        trackRecommendation.setLiked(false);

        trackRecommendation = repo.save(trackRecommendation);

        TrackRecommendation trackRecommendationFromDatabase = repo.findById(trackRecommendation.getTrackRecommendationId()).get();
        assertEquals(trackRecommendation, trackRecommendationFromDatabase);

        repo.deleteById(trackRecommendation.getTrackRecommendationId());

        Optional<TrackRecommendation> shouldBeEmptyOptionalNotTrackRecommendation = repo.findById(trackRecommendation.getTrackRecommendationId());
        assertEquals(false, shouldBeEmptyOptionalNotTrackRecommendation.isPresent());

    }

}