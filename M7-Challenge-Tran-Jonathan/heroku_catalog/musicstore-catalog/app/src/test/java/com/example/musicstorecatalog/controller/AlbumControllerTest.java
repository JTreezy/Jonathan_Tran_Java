package com.example.musicstorecatalog.controller;


import com.example.musicstorecatalog.model.Album;
import com.example.musicstorecatalog.repository.AlbumRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AlbumController.class)
public class AlbumControllerTest {
    @MockBean
    private AlbumRepository repo;

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Test
    public void shouldReturn201AndAlbumOnPost() throws Exception {
        Album inputAlbum = new Album("album title", 1, LocalDate.parse("2022-11-07", formatter), 1,new BigDecimal("11.99"));
        String inputJson = mapper.writeValueAsString(inputAlbum);
        Album outputAlbum = new Album(1,"album title", 1,LocalDate.parse("2022-11-07", formatter), 1,new BigDecimal("11.99"));
        String outputJson = mapper.writeValueAsString(outputAlbum);

        doReturn(outputAlbum).when(repo).save(inputAlbum);
        mockMvc.perform(post("/album")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldReturn200AndAlbumOnGetById() throws Exception {
        Integer id = 123;
        Album outputAlbum = new Album(id,"album title", 1, LocalDate.parse("2022-11-07", formatter), 1,new BigDecimal("11.99"));
        String outputJson = mapper.writeValueAsString(outputAlbum);

        doReturn(Optional.of(outputAlbum)).when(repo).findById(id);
        mockMvc.perform(get("/album/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldRespondWith422WhenGetByNotFoundId() throws Exception {
        Integer id = 123;

        doReturn(Optional.empty()).when(repo).findById(id);
        mockMvc.perform(get("/album/{id}", id))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldReturn200AndAlbumListOnGetAll() throws Exception {
        Album outputAlbum = new Album(123,"album title", 1, LocalDate.parse("2022-11-07", formatter), 1,new BigDecimal("11.99"));
        List<Album> outputList = Arrays.asList(outputAlbum);
        String outputJson = mapper.writeValueAsString(outputList);

        doReturn(outputList).when(repo).findAll();

        mockMvc.perform(get("/album"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldReturn204OnDelete() throws Exception {
        mockMvc.perform(delete("/album/{id}", 123))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


    @Test
    public void shouldReturn204OnUpdate() throws Exception {
        Integer id = 123;
        Album outputAlbum = new Album(id,"album title", 1, LocalDate.parse("2022-11-07", formatter), 1,new BigDecimal("11.99"));
        repo.save(outputAlbum);
        String inputJson = mapper.writeValueAsString(outputAlbum);

        mockMvc.perform(put("/album/{id}", id)
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


    @Test
    public void shouldRespondWith422WhenIdPathVariableDoesNotMatchIdInBodyOfPutRequest() throws Exception {
        Integer albumId = 123;
        Integer pathVariableId = 124;
        Album inputAlbum = new Album(albumId,"album title", 1, LocalDate.parse("2022-11-07", formatter), 1,new BigDecimal("11.99"));
        String inputJson = mapper.writeValueAsString(inputAlbum);

        mockMvc.perform(put("/album/{id}", pathVariableId)
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}