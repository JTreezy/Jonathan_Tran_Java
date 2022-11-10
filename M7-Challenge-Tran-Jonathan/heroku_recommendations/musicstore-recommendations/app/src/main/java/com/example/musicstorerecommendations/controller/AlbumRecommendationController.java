package com.example.musicstorerecommendations.controller;

import com.example.musicstorerecommendations.exception.NotFoundException;
import com.example.musicstorerecommendations.model.AlbumRecommendation;
import com.example.musicstorerecommendations.repository.AlbumRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/album-recommendation")
public class AlbumRecommendationController {
    @Autowired
    AlbumRecommendationRepository albumRecommendationRepository;
    //CRUD
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public AlbumRecommendation createNewAlbumRecommendation(@RequestBody @Valid AlbumRecommendation albumRecommendation){
        return albumRecommendationRepository.save(albumRecommendation);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<AlbumRecommendation> findAllAlbumRecommendations(){
        List<AlbumRecommendation> albumRecommendationList = albumRecommendationRepository.findAll();
        return albumRecommendationList;
    }

    @GetMapping("/{albumRecommendationId}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<AlbumRecommendation> getAlbumRecommendationByArtistId(@PathVariable Integer albumRecommendationId){
        if (albumRecommendationRepository.findById(albumRecommendationId).isPresent()){
            return albumRecommendationRepository.findById(albumRecommendationId);
        } else{
            throw new NotFoundException("unable to find this album recommendation id");
        }
    }

    @PutMapping("/{albumRecommendationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAlbumRecommendationByAlbumRecommendationId(@RequestBody AlbumRecommendation albumRecommendation, @PathVariable Integer albumRecommendationId){
        if (albumRecommendation.getAlbumRecommendationId().equals(albumRecommendationId) == false) {
            throw new NotFoundException("Request body and path variable indicate different ids. Path variable: " + albumRecommendationId +"; Request body: " + albumRecommendation.getAlbumId());
        }
        albumRecommendation.setAlbumId(albumRecommendationId);
        albumRecommendationRepository.save(albumRecommendation);
    }

    @DeleteMapping("/{albumRecommendationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void deleteAlbumRecommendationByAlbumRecommendationId(@PathVariable Integer albumRecommendationId){
        albumRecommendationRepository.deleteByAlbumRecommendationId(albumRecommendationId);
    }
}
