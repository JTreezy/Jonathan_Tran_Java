package com.example.musicstorecatalog.controller;


import com.example.musicstorecatalog.model.Label;
import com.example.musicstorecatalog.repository.LabelRepository;
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
@WebMvcTest(LabelController.class)
public class LabelControllerTest {
    @MockBean
    private LabelRepository repo;

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturn201AndAlbumOnPost() throws Exception {
        Label inputLabel = new Label("label name", "label website");
        String inputJson = mapper.writeValueAsString(inputLabel);
        Label outputLabel = new Label(1,"label name", "label website");
        String outputJson = mapper.writeValueAsString(outputLabel);

        doReturn(outputLabel).when(repo).save(inputLabel);
        mockMvc.perform(post("/label")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldReturn200AndAlbumOnGetById() throws Exception {
        Integer id = 123;
        Label outputLabel = new Label(id,"label name", "label website");
        String outputJson = mapper.writeValueAsString(outputLabel);

        doReturn(Optional.of(outputLabel)).when(repo).findById(id);
        mockMvc.perform(get("/label/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldRespondWith422WhenGetByNotFoundId() throws Exception {
        Integer id = 123;

        doReturn(Optional.empty()).when(repo).findById(id);
        mockMvc.perform(get("/label/{id}", id))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldReturn200AndAlbumListOnGetAll() throws Exception {
        Label outputLabel = new Label(123,"label name", "label website");
        List<Label> outputList = Arrays.asList(outputLabel);
        String outputJson = mapper.writeValueAsString(outputList);

        doReturn(outputList).when(repo).findAll();

        mockMvc.perform(get("/label"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldReturn204OnDelete() throws Exception {
        mockMvc.perform(delete("/label/{id}", 123))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


    @Test
    public void shouldReturn204OnUpdate() throws Exception {
        Integer id = 123;
        Label outputLabel = new Label(id,"label name", "label website");
        repo.save(outputLabel);
        String inputJson = mapper.writeValueAsString(outputLabel);

        mockMvc.perform(put("/label/{id}", id)
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


    @Test
    public void shouldRespondWith422WhenIdPathVariableDoesNotMatchIdInBodyOfPutRequest() throws Exception {
        Integer labelId = 123;
        Integer pathVariableId = 124;
        Label inputLabel = new Label(labelId,"label name", "label website");
        String inputJson = mapper.writeValueAsString(inputLabel);

        mockMvc.perform(put("/label/{id}", pathVariableId)
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

}