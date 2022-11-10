package com.example.musicstorerecommendations.repository;

import com.example.musicstorerecommendations.model.ArtistRecommendation;
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
public class ArtistRecommendationRepositoryTest {
    @Autowired
    private ArtistRecommendationRepository repo;
    @Before
    public void setUp(){
        //clear out database
        repo.deleteAll();
    }

    @Test
    public void shouldCreateGetAndDeleteArtistRecommendation(){
        ArtistRecommendation artistRecommendation = new ArtistRecommendation();
        artistRecommendation.setArtistId(3);
        artistRecommendation.setUserId(2);
        artistRecommendation.setLiked(false);

        artistRecommendation = repo.save(artistRecommendation);

        ArtistRecommendation artistRecommendationFromDatabase = repo.findById(artistRecommendation.getArtistRecommendationId()).get();
        assertEquals(artistRecommendation, artistRecommendationFromDatabase);

        repo.deleteById(artistRecommendation.getArtistRecommendationId());

        Optional<ArtistRecommendation> shouldBeEmptyOptionalNotArtistRecommendation = repo.findById(artistRecommendation.getArtistRecommendationId());
        assertEquals(false, shouldBeEmptyOptionalNotArtistRecommendation.isPresent());

    }

}