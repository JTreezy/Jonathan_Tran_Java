package com.example.musicstorerecommendations.controller;

import com.example.musicstorerecommendations.exception.NotFoundException;
import com.example.musicstorerecommendations.model.TrackRecommendation;
import com.example.musicstorerecommendations.repository.TrackRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/track-recommendation")
public class TrackRecommendationController {
    @Autowired
    TrackRecommendationRepository trackRecommendationRepository;
    //CRUD
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public TrackRecommendation createNewTrackRecommendation(@RequestBody @Valid TrackRecommendation trackRecommendation){
        return trackRecommendationRepository.save(trackRecommendation);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<TrackRecommendation> findAllTrackRecommendations(){
        List<TrackRecommendation> trackRecommendationList = trackRecommendationRepository.findAll();
        return trackRecommendationList;
    }

    @GetMapping("/{trackRecommendationId}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<TrackRecommendation> getTrackRecommendationByTrackRecommendationId(@PathVariable Integer trackRecommendationId){
        if (trackRecommendationRepository.findById(trackRecommendationId).isPresent()){
            return trackRecommendationRepository.findById(trackRecommendationId);
        } else{
            throw new NotFoundException("unable to find this track recommendation id");
        }
    }

    @PutMapping("/{trackRecommendationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTrackRecommendationByTrackRecommendationId(@RequestBody TrackRecommendation trackRecommendation, @PathVariable Integer trackRecommendationId){
        if (trackRecommendation.getTrackRecommendationId().equals(trackRecommendationId) == false) {
            throw new NotFoundException("Request body and path variable indicate different ids. Path variable: " + trackRecommendationId +"; Request body: " + trackRecommendation.getTrackRecommendationId());
        }
        trackRecommendation.setTrackRecommendationId(trackRecommendationId);
        trackRecommendationRepository.save(trackRecommendation);
    }

    @DeleteMapping("/{trackRecommendationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrackRecommendationByTrackRecommendationId(@PathVariable Integer trackRecommendationId){
        trackRecommendationRepository.deleteByTrackRecommendationId(trackRecommendationId);
    }
}
