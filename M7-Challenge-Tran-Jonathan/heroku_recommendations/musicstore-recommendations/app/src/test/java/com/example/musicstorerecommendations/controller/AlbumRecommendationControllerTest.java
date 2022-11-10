package com.example.musicstorerecommendations.controller;

import com.example.musicstorerecommendations.model.AlbumRecommendation;
import com.example.musicstorerecommendations.repository.AlbumRecommendationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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
@WebMvcTest(AlbumRecommendationController.class)
public class AlbumRecommendationControllerTest {
    @MockBean
    private AlbumRecommendationRepository repo;

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturn201AndAlbumRecommendationOnPost() throws Exception {
        AlbumRecommendation inputAlbumRecommendation = new AlbumRecommendation(1,1,true);
        String inputJson = mapper.writeValueAsString(inputAlbumRecommendation);
        AlbumRecommendation outputAlbumRecommendation = new AlbumRecommendation(1,1,1,true);
        String outputJson = mapper.writeValueAsString(outputAlbumRecommendation);

        doReturn(outputAlbumRecommendation).when(repo).save(inputAlbumRecommendation);
        mockMvc.perform(post("/album-recommendation")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldReturn200AndAlbumRecommendationOnGetById() throws Exception {
        Integer id = 123;
        AlbumRecommendation outputAlbum = new AlbumRecommendation(id,1,1,true);
        String outputJson = mapper.writeValueAsString(outputAlbum);

        doReturn(Optional.of(outputAlbum)).when(repo).findById(id);
        mockMvc.perform(get("/album-recommendation/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldRespondWith422WhenGetByNotFoundId() throws Exception {
        doReturn(Optional.empty()).when(repo).findById(32);
        mockMvc.perform(get("/album-recommendation/{id}", 32))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldReturn200AndAlbumListOnGetAll() throws Exception {
        AlbumRecommendation outputAlbumRecommendation = new AlbumRecommendation(123,1,1,true);
        List<AlbumRecommendation> outputList = Arrays.asList(outputAlbumRecommendation);
        String outputJson = mapper.writeValueAsString(outputList);

        doReturn(outputList).when(repo).findAll();

        mockMvc.perform(get("/album-recommendation"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldReturn204OnDelete() throws Exception {
        mockMvc.perform(delete("/album-recommendation/{id}", 123))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


    @Test
    public void shouldReturn204OnUpdate() throws Exception {
        Integer id = 123;
        AlbumRecommendation outputAlbumRecommendation = new AlbumRecommendation(id,1,1,true);
        repo.save(outputAlbumRecommendation);
        String inputJson = mapper.writeValueAsString(outputAlbumRecommendation);

        mockMvc.perform(put("/album-recommendation/{id}", id)
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


    @Test
    public void shouldRespondWith422WhenIdPathVariableDoesNotMatchIdInBodyOfPutRequest() throws Exception {
        Integer albumRecommendationId = 123;
        Integer pathVariableId = 124;
        AlbumRecommendation inputAlbumRecommendation = new AlbumRecommendation(albumRecommendationId,1,1,true);
        String inputJson = mapper.writeValueAsString(inputAlbumRecommendation);

        mockMvc.perform(put("/album-recommendation/{id}", pathVariableId)
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

}