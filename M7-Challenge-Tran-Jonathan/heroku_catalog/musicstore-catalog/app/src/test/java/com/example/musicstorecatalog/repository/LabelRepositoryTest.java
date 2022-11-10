package com.example.musicstorecatalog.repository;

import com.example.musicstorecatalog.model.Label;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LabelRepositoryTest {
    @Autowired
    private LabelRepository repo;
    @Before
    public void setUp(){
        //clear out database
        repo.deleteAll();
    }

    @Test
    public void shouldCreateGetAndDeleteAlbum(){
        Label label = new Label();
        label.setName("Label");
        label.setWebsite("LabelWebsite");


        label = repo.save(label);

        Label labelFromDatabase = repo.findById(label.getLabelId()).get();
        assertEquals(label, labelFromDatabase);

        repo.deleteById(label.getLabelId());

        Optional<Label> shouldBeEmptyOptionalNotLabel = repo.findById(label.getLabelId());
        assertEquals(false, shouldBeEmptyOptionalNotLabel.isPresent());

    }


}