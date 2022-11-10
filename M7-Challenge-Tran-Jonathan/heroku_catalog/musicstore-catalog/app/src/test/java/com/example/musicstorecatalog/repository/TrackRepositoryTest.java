package com.example.musicstorecatalog.repository;

import com.example.musicstorecatalog.model.Track;
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
public class TrackRepositoryTest {
    @Autowired
    private TrackRepository repo;
    @Before
    public void setUp(){
        //clear out database
        repo.deleteAll();
    }

    @Test
    public void shouldCreateGetAndDeleteAlbum(){
        Track track = new Track();
        track.setAlbumId(3);
        track.setTitle("Hybrid Theory");
        track.setRunTime(300);

        track = repo.save(track);

        Track trackFromDatabase = repo.findById(track.getTrackId()).get();
        assertEquals(track, trackFromDatabase);

        repo.deleteById(track.getTrackId());

        Optional<Track> shouldBeEmptyOptionalNotTrack = repo.findById(track.getTrackId());
        assertEquals(false, shouldBeEmptyOptionalNotTrack.isPresent());

    }


}