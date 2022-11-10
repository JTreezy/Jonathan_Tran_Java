package com.example.musicstorecatalog.repository;

import com.example.musicstorecatalog.model.Album;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlbumRepositoryTest {
    @Autowired
    private AlbumRepository repo;
   @Before
   public void setUp(){
       //clear out database
       repo.deleteAll();
   }

    @Test
    public void shouldCreateGetAndDeleteAlbum(){
       Album album = new Album();
       album.setTitle("Hybrid Theory");
       album.setArtistId(1);
       album.setReleaseDate(LocalDate.parse("2022-11-07"));
       album.setLabelId(1);
       album.setListPrice(new BigDecimal("11.99"));

       album = repo.save(album);

       Album albumFromDatabase = repo.findById(album.getAlbumId()).get();
       assertEquals(album, albumFromDatabase);

       repo.deleteById(album.getAlbumId());

        Optional<Album> shouldBeEmptyOptionalNotAlbum = repo.findById(album.getAlbumId());
        assertEquals(false, shouldBeEmptyOptionalNotAlbum.isPresent());

    }

    @Test
    public void shouldFilterByDesiredTitle(){
       //Arrange
       Album album1 = new Album(null, "generic album name", 2, LocalDate.parse("2022-11-09"),3,new BigDecimal("21.95") );
       Album album2 = new Album(null, "desired title", 2, LocalDate.parse("2022-11-09"),3,new BigDecimal("21.95") );
       Album album3 = new Album(null, "desired title", 2, LocalDate.parse("2022-11-09"),3,new BigDecimal("21.95") );

       album1 = repo.save(album1);
       album2 = repo.save(album2);
       album3 = repo.save(album3);

       //Act
        List<Album> desiredTitleList = repo.findByTitle("desired title");

        //Assert - maybe turn these into two sets and then compare (we don't have a guaranteed order
        //on the query we wrote, but sets just confirm that every item is present in both)
        assertEquals(new HashSet<Album>(Arrays.asList(album2,album3)), new HashSet<Album>(desiredTitleList));

        List<Album> genericAlbumNameList = repo.findByTitle("generic album name");
        assertEquals(new HashSet<Album>(Arrays.asList(album1)), new HashSet<Album>(genericAlbumNameList));

        List<Album> coolestTitleList = repo.findByTitle("coolest album name ever");
        assertEquals(0, coolestTitleList.size());




    }

    //arrange
    //build the whatIExpect variable (and parameters)
    //act
    //generate the whatIGot variable (by calling a method)
    //assert
    //assertEquals(whatIExpect, whatIGot)

}