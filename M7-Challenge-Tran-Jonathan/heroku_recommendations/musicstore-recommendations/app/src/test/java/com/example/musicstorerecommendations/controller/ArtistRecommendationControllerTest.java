package com.example.musicstorerecommendations.controller;

import com.example.musicstorerecommendations.model.ArtistRecommendation;
import com.example.musicstorerecommendations.repository.ArtistRecommendationRepository;
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
import java.time.format.DateTimeFormatter;
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
@WebMvcTest(ArtistRecommendationController.class)
public class ArtistRecommendationControllerTest {
    @MockBean
    private ArtistRecommendationRepository repo;

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void shouldReturn201AndArtistRecommendationOnPost() throws Exception {
        ArtistRecommendation inputAlbum = new ArtistRecommendation(1,1,true);
        String inputJson = mapper.writeValueAsString(inputAlbum);
        ArtistRecommendation outputArtistRecommendation = new ArtistRecommendation(1,1,1,true);
        String outputJson = mapper.writeValueAsString(outputArtistRecommendation);

        doReturn(outputArtistRecommendation).when(repo).save(inputAlbum);
        mockMvc.perform(post("/artist-recommendation")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldReturn200AndAlbumOnGetById() throws Exception {
        Integer id = 123;
        ArtistRecommendation outputArtistRecommendation = new ArtistRecommendation(id,1,1,true);
        String outputJson = mapper.writeValueAsString(outputArtistRecommendation);

        doReturn(Optional.of(outputArtistRecommendation)).when(repo).findById(id);
        mockMvc.perform(get("/artist-recommendation/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldRespondWith422WhenGetByNotFoundId() throws Exception {
        Integer id = 123;

        doReturn(Optional.empty()).when(repo).findById(id);
        mockMvc.perform(get("/artist-recommendation/{id}", id))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldReturn200AndArtistRecommendationListOnGetAll() throws Exception {
        ArtistRecommendation outputArtistRecommendation = new ArtistRecommendation(123,1,1,true);
        List<ArtistRecommendation> outputList = Arrays.asList(outputArtistRecommendation);
        String outputJson = mapper.writeValueAsString(outputList);

        doReturn(outputList).when(repo).findAll();

        mockMvc.perform(get("/artist-recommendation"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldReturn204OnDelete() throws Exception {
        mockMvc.perform(delete("/artist-recommendation/{id}", 123))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


    @Test
    public void shouldReturn204OnUpdate() throws Exception {
        Integer id = 123;
        ArtistRecommendation outputArtistRecommendation = new ArtistRecommendation(id,1,1,true);
        repo.save(outputArtistRecommendation);
        String inputJson = mapper.writeValueAsString(outputArtistRecommendation);

        mockMvc.perform(put("/artist-recommendation/{id}", id)
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


    @Test
    public void shouldRespondWith422WhenIdPathVariableDoesNotMatchIdInBodyOfPutRequest() throws Exception {
        Integer artistRecommendationId = 123;
        Integer pathVariableId = 124;
        ArtistRecommendation inputArtistRecommendation = new ArtistRecommendation(artistRecommendationId,1,1,true);
        String inputJson = mapper.writeValueAsString(inputArtistRecommendation);

        mockMvc.perform(put("/artist-recommendation/{id}", pathVariableId)
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

}