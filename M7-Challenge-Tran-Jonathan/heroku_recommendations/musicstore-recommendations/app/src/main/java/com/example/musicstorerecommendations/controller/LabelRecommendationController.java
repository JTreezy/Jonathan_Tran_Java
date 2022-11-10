package com.example.musicstorerecommendations.controller;

import com.example.musicstorerecommendations.exception.NotFoundException;
import com.example.musicstorerecommendations.model.LabelRecommendation;
import com.example.musicstorerecommendations.repository.LabelRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/label-recommendation")
public class LabelRecommendationController {
    @Autowired
    LabelRecommendationRepository labelRecommendationRepository;
    //CRUD
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public LabelRecommendation createNewLabelRecommendation(@RequestBody @Valid LabelRecommendation labelRecommendation){
        return labelRecommendationRepository.save(labelRecommendation);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<LabelRecommendation> findAllLabelRecommendations(){
        List<LabelRecommendation> labelRecommendationList = labelRecommendationRepository.findAll();
        return labelRecommendationList;
    }

    @GetMapping("/{labelRecommendationId}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<LabelRecommendation> getLabelRecommendationByLabelRecommendationId(@PathVariable Integer labelRecommendationId){
        if (labelRecommendationRepository.findById(labelRecommendationId).isPresent()){
            return labelRecommendationRepository.findById(labelRecommendationId);
        } else{
            throw new NotFoundException("unable to find this label recommendation id");
        }
    }

    @PutMapping("/{labelRecommendationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLabelRecommendationByLabelRecommendationId(@RequestBody LabelRecommendation labelRecommendation, @PathVariable Integer labelRecommendationId){
        if (labelRecommendation.getLabelRecommendationId().equals(labelRecommendationId) == false) {
            throw new NotFoundException("Request body and path variable indicate different ids. Path variable: " + labelRecommendationId +"; Request body: " + labelRecommendation.getLabelRecommendationId());
        }
        labelRecommendation.setLabelRecommendationId(labelRecommendationId);
        labelRecommendationRepository.save(labelRecommendation);
    }

    @DeleteMapping("/{labelRecommendationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLabelRecommendationByLabelRecommendationId(@PathVariable Integer labelRecommendationId){
        labelRecommendationRepository.deleteByLabelRecommendationId(labelRecommendationId);
    }
}
