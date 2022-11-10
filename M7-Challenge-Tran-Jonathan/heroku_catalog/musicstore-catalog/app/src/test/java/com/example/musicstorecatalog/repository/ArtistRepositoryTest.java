package com.example.musicstorecatalog.repository;

import com.example.musicstorecatalog.model.Artist;
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
public class ArtistRepositoryTest {
    @Autowired
    private ArtistRepository repo;
    @Before
    public void setUp(){
        //clear out database
        repo.deleteAll();
    }

    @Test
    public void shouldCreateGetAndDeleteArtist(){
        Artist artist = new Artist();
        artist.setName("Vampire Weekend");
        artist.setArtistId(1);
        artist.setInstagram("VampireWeekendInstagram");
        artist.setTwitter("VampireWeekendTwitter");

        artist = repo.save(artist);

        Artist artistFromDatabase = repo.findById(artist.getArtistId()).get();
        assertEquals(artist, artistFromDatabase);

        repo.deleteById(artist.getArtistId());

        Optional<Artist> shouldBeEmptyOptionalNotArtist = repo.findById(artist.getArtistId());
        assertEquals(false, shouldBeEmptyOptionalNotArtist.isPresent());

    }


}