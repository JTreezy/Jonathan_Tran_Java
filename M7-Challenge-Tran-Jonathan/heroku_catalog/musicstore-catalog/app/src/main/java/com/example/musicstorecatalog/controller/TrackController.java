package com.example.musicstorecatalog.controller;

import com.example.musicstorecatalog.exception.NotFoundException;
import com.example.musicstorecatalog.model.Track;
import com.example.musicstorecatalog.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/track")
public class TrackController {
    @Autowired
    TrackRepository trackRepository;
    //CRUD
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Track createNewTrack(@RequestBody @Valid Track track){
        return trackRepository.save(track);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Track> findAllTracks(){
        List<Track> trackList = trackRepository.findAll();
        return trackList;
    }

    @GetMapping("/{trackId}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Track> getTrackByTrackId(@PathVariable Integer trackId){
        if (trackRepository.findById(trackId).isPresent()){
            return trackRepository.findById(trackId);
        } else{
            throw new NotFoundException("unable to find this track id");
        }
    }

    @PutMapping("/{trackId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTrackByTrackId(@RequestBody Track track, @PathVariable Integer trackId){
        if (track.getTrackId().equals(trackId) == false) {
            throw new NotFoundException("Request body and path variable indicate different ids. Path variable: " + trackId +"; Request body: " + track.getTrackId());
        }
        track.setTrackId(trackId);
        trackRepository.save(track);
    }

    @DeleteMapping("/{trackId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrackByTrackId(@PathVariable Integer trackId){
        trackRepository.deleteByTrackId(trackId);
    }
}
