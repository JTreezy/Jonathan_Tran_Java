package com.example.musicstorerecommendations.repository;

import com.example.musicstorerecommendations.model.LabelRecommendation;
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
public class LabelRecommendationRepositoryTest {
    @Autowired
    private LabelRecommendationRepository repo;
    @Before
    public void setUp(){
        //clear out database
        repo.deleteAll();
    }

    @Test
    public void shouldCreateGetAndDeleteLabelRecommendation(){
        LabelRecommendation labelRecommendation = new LabelRecommendation();
        labelRecommendation.setLabelId(5);
        labelRecommendation.setUserId(3);
        labelRecommendation.setLiked(true);

        labelRecommendation = repo.save(labelRecommendation);

        LabelRecommendation labelRecommendationFromDatabase = repo.findById(labelRecommendation.getLabelRecommendationId()).get();
        assertEquals(labelRecommendation, labelRecommendationFromDatabase);

        repo.deleteById(labelRecommendation.getLabelRecommendationId());

        Optional<LabelRecommendation> shouldBeEmptyOptionalNotLabelRecommendation = repo.findById(labelRecommendation.getLabelRecommendationId());
        assertEquals(false, shouldBeEmptyOptionalNotLabelRecommendation.isPresent());

    }

}