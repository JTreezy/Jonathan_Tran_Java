package com.example.musicstorerecommendations.controller;

import com.example.musicstorerecommendations.model.TrackRecommendation;
import com.example.musicstorerecommendations.repository.TrackRecommendationRepository;
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
@WebMvcTest(TrackRecommendationController.class)
public class TrackRecommendationControllerTest {
    @MockBean
    private TrackRecommendationRepository repo;

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturn201AndTrackRecommendationOnPost() throws Exception {
        TrackRecommendation inputTrackRecommendation = new TrackRecommendation(1,1,true);
        String inputJson = mapper.writeValueAsString(inputTrackRecommendation);
        TrackRecommendation outputTrackRecommendation = new TrackRecommendation(1,1,1,true);
        String outputJson = mapper.writeValueAsString(outputTrackRecommendation);

        doReturn(outputTrackRecommendation).when(repo).save(inputTrackRecommendation);
        mockMvc.perform(post("/track-recommendation")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldReturn200AndTrackRecommendationOnGetById() throws Exception {
        Integer id = 123;
        TrackRecommendation outputTrackRecommendation = new TrackRecommendation(id,1,1,true);
        String outputJson = mapper.writeValueAsString(outputTrackRecommendation);

        doReturn(Optional.of(outputTrackRecommendation)).when(repo).findById(id);
        mockMvc.perform(get("/track-recommendation/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldRespondWith422WhenGetByNotFoundId() throws Exception {
        Integer id = 123;

        doReturn(Optional.empty()).when(repo).findById(id);
        mockMvc.perform(get("/track-recommendation/{id}", id))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldReturn200AndTrackRecommendationListOnGetAll() throws Exception {
        TrackRecommendation outputTrackRecommendation = new TrackRecommendation(123,1,1,true);
        List<TrackRecommendation> outputList = Arrays.asList(outputTrackRecommendation);
        String outputJson = mapper.writeValueAsString(outputList);

        doReturn(outputList).when(repo).findAll();

        mockMvc.perform(get("/track-recommendation"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldReturn204OnDelete() throws Exception {
        mockMvc.perform(delete("/track-recommendation/{id}", 123))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


    @Test
    public void shouldReturn204OnUpdate() throws Exception {
        Integer id = 123;
        TrackRecommendation outputTrackRecommendation = new TrackRecommendation(id,1,1,true);
        repo.save(outputTrackRecommendation);
        String inputJson = mapper.writeValueAsString(outputTrackRecommendation);

        mockMvc.perform(put("/track-recommendation/{id}", id)
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


    @Test
    public void shouldRespondWith422WhenIdPathVariableDoesNotMatchIdInBodyOfPutRequest() throws Exception {
        Integer trackRecommendationId = 123;
        Integer pathVariableId = 124;
        TrackRecommendation inputTrackRecommendation = new TrackRecommendation(trackRecommendationId,1,1,true);
        String inputJson = mapper.writeValueAsString(inputTrackRecommendation);

        mockMvc.perform(put("/track-recommendation/{id}", pathVariableId)
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

}
