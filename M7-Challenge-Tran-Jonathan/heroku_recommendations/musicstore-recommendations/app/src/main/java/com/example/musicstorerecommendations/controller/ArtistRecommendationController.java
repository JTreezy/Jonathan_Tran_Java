package com.example.musicstorerecommendations.controller;

import com.example.musicstorerecommendations.exception.NotFoundException;
import com.example.musicstorerecommendations.model.ArtistRecommendation;
import com.example.musicstorerecommendations.repository.ArtistRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/artist-recommendation")
public class ArtistRecommendationController {
    @Autowired
    ArtistRecommendationRepository artistRecommendationRepository;
    //CRUD
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ArtistRecommendation createNewArtistRecommendation(@RequestBody @Valid ArtistRecommendation artistRecommendation){
        return artistRecommendationRepository.save(artistRecommendation);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ArtistRecommendation> findAllArtistRecommendations(){
        List<ArtistRecommendation> artistRecommendationList = artistRecommendationRepository.findAll();
        return artistRecommendationList;
    }

    @GetMapping("/{artistRecommendationId}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<ArtistRecommendation> getArtistRecommendationByArtistRecommendationId(@PathVariable Integer artistRecommendationId){
        if (artistRecommendationRepository.findById(artistRecommendationId).isPresent()){
            return artistRecommendationRepository.findById(artistRecommendationId);
        } else{
            throw new NotFoundException("unable to find this artist recommendation id");
        }
    }

    @PutMapping("/{artistRecommendationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateArtistRecommendationByArtistRecommendationId(@RequestBody ArtistRecommendation artistRecommendation, @PathVariable Integer artistRecommendationId){
        if (artistRecommendation.getArtistRecommendationId().equals(artistRecommendationId) == false) {
            throw new NotFoundException("Request body and path variable indicate different ids. Path variable: " + artistRecommendationId +"; Request body: " + artistRecommendation.getArtistRecommendationId());
        }
        artistRecommendation.setArtistRecommendationId(artistRecommendationId);
        artistRecommendationRepository.save(artistRecommendation);
    }

    @DeleteMapping("/{artistRecommendationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtistRecommendationByArtistRecommendationId(@PathVariable Integer artistRecommendationId){
        artistRecommendationRepository.deleteByArtistRecommendationId(artistRecommendationId);
    }
}
