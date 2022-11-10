package com.example.musicstorecatalog.controller;

import com.example.musicstorecatalog.model.Track;
import com.example.musicstorecatalog.repository.TrackRepository;
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
@WebMvcTest(TrackController.class)
public class TrackControllerTest {
    @MockBean
    private TrackRepository repo;

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturn201AndAlbumOnPost() throws Exception {
        Track inputTrack = new Track(1,"track title", 300);
        String inputJson = mapper.writeValueAsString(inputTrack);
        Track outputTrack = new Track(1,1,"track title", 300);
        String outputJson = mapper.writeValueAsString(outputTrack);

        doReturn(outputTrack).when(repo).save(inputTrack);
        mockMvc.perform(post("/track")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldReturn200AndAlbumOnGetById() throws Exception {
        Integer id = 123;
        Track outputTrack = new Track(id,1,"track title", 300);
        String outputJson = mapper.writeValueAsString(outputTrack);

        doReturn(Optional.of(outputTrack)).when(repo).findById(id);
        mockMvc.perform(get("/track/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldRespondWith422WhenGetByNotFoundId() throws Exception {
        Integer id = 123;

        doReturn(Optional.empty()).when(repo).findById(id);
        mockMvc.perform(get("/track/{id}", id))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldReturn200AndAlbumListOnGetAll() throws Exception {
        Track outputTrack = new Track(123,1,"track title", 300);
        List<Track> outputList = Arrays.asList(outputTrack);
        String outputJson = mapper.writeValueAsString(outputList);

        doReturn(outputList).when(repo).findAll();

        mockMvc.perform(get("/track"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldReturn204OnDelete() throws Exception {
        mockMvc.perform(delete("/track/{id}", 123))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


    @Test
    public void shouldReturn204OnUpdate() throws Exception {
        Integer id = 123;
        Track outputTrack = new Track(id,1,"track title", 300);
        repo.save(outputTrack);
        String inputJson = mapper.writeValueAsString(outputTrack);

        mockMvc.perform(put("/track/{id}", id)
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


    @Test
    public void shouldRespondWith422WhenIdPathVariableDoesNotMatchIdInBodyOfPutRequest() throws Exception {
        Integer trackId = 123;
        Integer pathVariableId = 124;
        Track inputTrack = new Track(trackId,1,"track title", 300);
        String inputJson = mapper.writeValueAsString(inputTrack);

        mockMvc.perform(put("/track/{id}", pathVariableId)
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

}