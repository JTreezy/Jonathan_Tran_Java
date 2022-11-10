package com.example.musicstorecatalog.controller;

import com.example.musicstorecatalog.exception.NotFoundException;
import com.example.musicstorecatalog.model.Label;
import com.example.musicstorecatalog.repository.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/label")
public class LabelController {
    @Autowired
    LabelRepository labelRepository;
    //CRUD
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Label createNewLabel(@RequestBody @Valid Label label){

        return labelRepository.save(label);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Label> findAllLabels(){
        List<Label> labelList = labelRepository.findAll();
        return labelList;
    }

    @GetMapping("/{labelId}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Label> getLabelByLabelId(@PathVariable Integer labelId){
        if (labelRepository.findById(labelId).isPresent()){
            return labelRepository.findById(labelId);
        } else{
            throw new NotFoundException("unable to find this label id");
        }
    }

    @PutMapping("/{labelId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLabelByLabelId(@RequestBody Label label, @PathVariable Integer labelId){
        if (label.getLabelId().equals(labelId) == false) {
            throw new NotFoundException("Request body and path variable indicate different ids. Path variable: " + labelId +"; Request body: " + label.getLabelId());
        }
        label.setLabelId(labelId);
        labelRepository.save(label);
    }

    @DeleteMapping("/{labelId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLabelByLabelId(@PathVariable Integer labelId){
        labelRepository.deleteByLabelId(labelId);
    }
}
