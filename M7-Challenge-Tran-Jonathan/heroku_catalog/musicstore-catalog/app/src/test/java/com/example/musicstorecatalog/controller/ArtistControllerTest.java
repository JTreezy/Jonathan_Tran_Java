package com.example.musicstorecatalog.controller;

import com.example.musicstorecatalog.model.Artist;
import com.example.musicstorecatalog.repository.ArtistRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.swing.*;
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
@WebMvcTest(ArtistController.class)
public class ArtistControllerTest {
    @MockBean
    private ArtistRepository repo;

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturn201AndArtistOnPost() throws Exception {
        Artist inputArtist = new Artist("artist name","artist instagram","artist twitter");
        String inputJson = mapper.writeValueAsString(inputArtist);
        Artist outputArtist = new Artist(1,"artist name","artist instagram","artist twitter");
        String outputJson = mapper.writeValueAsString(outputArtist);

        doReturn(outputArtist).when(repo).save(inputArtist);
        mockMvc.perform(post("/artist")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldReturn200AndArtistOnGetById() throws Exception {
        Integer id = 1;
        Artist outputArtist = new Artist(id,"artist name","artist instagram","artist twitter");
        String outputJson = mapper.writeValueAsString(outputArtist);

        doReturn(Optional.of(outputArtist)).when(repo).findById(id);
        mockMvc.perform(get("/artist/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldRespondWith422WhenGetByNotFoundId() throws Exception {
        Integer id = 123;

        doReturn(Optional.empty()).when(repo).findById(id);
        mockMvc.perform(get("/artist/{id}", id))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldReturn200AndAlbumListOnGetAll() throws Exception {
        Artist outputArtist = new Artist(123,"artist name","artist instagram","artist twitter");
        List<Artist> outputList = Arrays.asList(outputArtist);
        String outputJson = mapper.writeValueAsString(outputList);

        doReturn(outputList).when(repo).findAll();

        mockMvc.perform(get("/artist"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldReturn204OnDelete() throws Exception {
        mockMvc.perform(delete("/artist/{id}", 123))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


    @Test
    public void shouldReturn204OnUpdate() throws Exception {
        Integer id = 123;
        Artist outputArtist = new Artist(id,"artist name","artist instagram","artist twitter");
        repo.save(outputArtist);
        String inputJson = mapper.writeValueAsString(outputArtist);

        mockMvc.perform(put("/artist/{id}", id)
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


    @Test
    public void shouldRespondWith422WhenIdPathVariableDoesNotMatchIdInBodyOfPutRequest() throws Exception {
        Integer artistId = 123;
        Integer pathVariableId = 124;
        Artist inputArtist = new Artist(artistId,"artist name","artist instagram","artist twitter");
        String inputJson = mapper.writeValueAsString(inputArtist);

        mockMvc.perform(put("/artist/{id}", pathVariableId)
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}