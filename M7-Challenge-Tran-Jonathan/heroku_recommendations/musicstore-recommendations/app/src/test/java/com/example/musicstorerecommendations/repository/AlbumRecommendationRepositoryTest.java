package com.example.musicstorerecommendations.repository;

import com.example.musicstorerecommendations.model.AlbumRecommendation;
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
public class AlbumRecommendationRepositoryTest {
    @Autowired
    private AlbumRecommendationRepository repo;
    @Before
    public void setUp(){
        //clear out database
        repo.deleteAll();
    }

    @Test
    public void shouldCreateGetAndDeleteAlbumRecommendation(){
        AlbumRecommendation albumRecommendation = new AlbumRecommendation();
        albumRecommendation.setAlbumId(1);
        albumRecommendation.setUserId(5);
        albumRecommendation.setLiked(true);

        albumRecommendation = repo.save(albumRecommendation);

        AlbumRecommendation albumRecommendationFromDatabase = repo.findById(albumRecommendation.getAlbumRecommendationId()).get();
        assertEquals(albumRecommendation, albumRecommendationFromDatabase);

        repo.deleteById(albumRecommendation.getAlbumRecommendationId());

        Optional<AlbumRecommendation> shouldBeEmptyOptionalNotAlbumRecommendation = repo.findById(albumRecommendation.getAlbumRecommendationId());
        assertEquals(false, shouldBeEmptyOptionalNotAlbumRecommendation.isPresent());

    }
}