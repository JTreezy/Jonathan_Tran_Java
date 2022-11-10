package com.example.musicstorecatalog.controller;

import com.example.musicstorecatalog.exception.NotFoundException;
import com.example.musicstorecatalog.model.Artist;
import com.example.musicstorecatalog.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/artist")
public class ArtistController {
    @Autowired
    ArtistRepository artistRepository;
    //CRUD
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Artist createNewArtist(@RequestBody @Valid Artist artist){
        return artistRepository.save(artist);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Artist> findAllArtists(){
        List<Artist> artistList = artistRepository.findAll();
        return artistList;
    }

    @GetMapping("/{artistId}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Artist> getArtistByArtistId(@PathVariable Integer artistId){
        if (artistRepository.findById(artistId).isPresent()){
            return artistRepository.findById(artistId);
        } else{
            throw new NotFoundException("unable to find this artist id");
        }
    }

    @PutMapping("/{artistId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateArtistByArtistId(@RequestBody Artist artist, @PathVariable Integer artistId){
        if (artist.getArtistId().equals(artistId) == false) {
            throw new NotFoundException("Request body and path variable indicate different ids. Path variable: " + artistId +"; Request body: " + artist.getArtistId());
        }
        artist.setArtistId(artistId);
        artistRepository.save(artist);
    }

    @DeleteMapping("/{artistId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtistByArtistId(@PathVariable Integer artistId){
        artistRepository.deleteByArtistId(artistId);
    }
}
