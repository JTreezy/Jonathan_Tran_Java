package com.example.musicstorerecommendations.controller;

import com.example.musicstorerecommendations.model.LabelRecommendation;
import com.example.musicstorerecommendations.repository.AlbumRecommendationRepository;
import com.example.musicstorerecommendations.repository.LabelRecommendationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LabelRecommendationController.class)
public class LabelRecommendationControllerTest {
    @MockBean
    private LabelRecommendationRepository repo;

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturn201AndLabelRecommendationOnPost() throws Exception {
        LabelRecommendation inputLabelRecommendation = new LabelRecommendation(1,1,true);
        String inputJson = mapper.writeValueAsString(inputLabelRecommendation);
        LabelRecommendation outputLabelRecommendation = new LabelRecommendation(1,1,1,true);
        String outputJson = mapper.writeValueAsString(outputLabelRecommendation);

        doReturn(outputLabelRecommendation).when(repo).save(inputLabelRecommendation);
        mockMvc.perform(post("/label-recommendation")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldReturn200AndLabelRecommendationOnGetById() throws Exception {
        Integer id = 123;
        LabelRecommendation outputLabelRecommendation = new LabelRecommendation(id,1,1,true);
        String outputJson = mapper.writeValueAsString(outputLabelRecommendation);

        doReturn(Optional.of(outputLabelRecommendation)).when(repo).findById(id);
        mockMvc.perform(get("/label-recommendation/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldRespondWith422WhenGetByNotFoundId() throws Exception {
        Integer id = 123;

        doReturn(Optional.empty()).when(repo).findById(id);
        mockMvc.perform(get("/label-recommendation/{id}", id))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldReturn200AndLabelRecommendationListOnGetAll() throws Exception {
        LabelRecommendation outputLabelRecommendation = new LabelRecommendation(123,1,1,true);
        List<LabelRecommendation> outputList = Arrays.asList(outputLabelRecommendation);
        String outputJson = mapper.writeValueAsString(outputList);

        doReturn(outputList).when(repo).findAll();

        mockMvc.perform(get("/label-recommendation"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldReturn204OnDelete() throws Exception {
        mockMvc.perform(delete("/label-recommendation/{id}", 123))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


    @Test
    public void shouldReturn204OnUpdate() throws Exception {
        Integer id = 123;
        LabelRecommendation outputLabelRecommendation = new LabelRecommendation(id,1,1,true);
        repo.save(outputLabelRecommendation);
        String inputJson = mapper.writeValueAsString(outputLabelRecommendation);

        mockMvc.perform(put("/label-recommendation/{id}", id)
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


    @Test
    public void shouldRespondWith422WhenIdPathVariableDoesNotMatchIdInBodyOfPutRequest() throws Exception {
        Integer labelRecommendationId = 123;
        Integer pathVariableId = 124;
        LabelRecommendation inputAlbum = new LabelRecommendation(labelRecommendationId,1,1,true);
        String inputJson = mapper.writeValueAsString(inputAlbum);

        mockMvc.perform(put("/label-recommendation/{id}", pathVariableId)
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

}