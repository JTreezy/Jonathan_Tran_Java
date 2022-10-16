package com.company.RESTwebservice.controllers;

import com.company.RESTwebservice.exceptions.ArgumentIsNotANumberException;
import com.company.RESTwebservice.models.CustomErrorResponse;
import com.company.RESTwebservice.models.MathSolution;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.awt.*;
import java.time.LocalDateTime;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MathSolutionController.class)
public class MathSolutionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void shouldReturnAdditionSuccessfulResponse() throws Exception{
        MathSolution testInput = new MathSolution();
        testInput.setOperand1(60);
        testInput.setOperand2(30);

        String testInputJson = mapper.writeValueAsString(testInput);

        MathSolution testOutput = new MathSolution();
        testOutput.setOperand1(60);
        testOutput.setOperand2(30);
        testOutput.setOperation("add");
        testOutput.setAnswer(testOutput.getOperand1() + testOutput.getOperand2());

        String testOutputJson = mapper.writeValueAsString(testOutput);

        mockMvc.perform(post("/add")
                        .content(testInputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(testOutputJson));
    }

    @Test
    public void shouldReturnAdditionInvalidRequest() throws Exception{
        MathSolution testInput = new MathSolution();
        testInput.setOperand2(30);

        String testInputJson = mapper.writeValueAsString(testInput);

        mockMvc.perform(post("/add")
                .content(testInputJson)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

    }

    @Test
    public void shouldReturnSubtractionSuccessfulResponse() throws Exception{
        MathSolution testInput = new MathSolution();
        testInput.setOperand1(60);
        testInput.setOperand2(30);

        String testInputJson = mapper.writeValueAsString(testInput);

        MathSolution testOutput = new MathSolution();
        testOutput.setOperand1(60);
        testOutput.setOperand2(30);
        testOutput.setOperation("subtract");
        testOutput.setAnswer(testOutput.getOperand1() - testOutput.getOperand2());

        String testOutputJson = mapper.writeValueAsString(testOutput);

        mockMvc.perform(post("/subtract")
                        .content(testInputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(testOutputJson));
    }

    @Test
    public void shouldReturnSubtractionInvalidRequest() throws Exception{
        MathSolution testInput = new MathSolution();
        testInput.setOperand2(30);

        String testInputJson = mapper.writeValueAsString(testInput);

        mockMvc.perform(post("/subtract")
                        .content(testInputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldReturnMultiplicationSuccessfulResponse() throws Exception{
        MathSolution testInput = new MathSolution();
        testInput.setOperand1(60);
        testInput.setOperand2(30);

        String testInputJson = mapper.writeValueAsString(testInput);

        MathSolution testOutput = new MathSolution();
        testOutput.setOperand1(60);
        testOutput.setOperand2(30);
        testOutput.setOperation("multiply");
        testOutput.setAnswer(testOutput.getOperand1() * testOutput.getOperand2());

        String testOutputJson = mapper.writeValueAsString(testOutput);

        mockMvc.perform(post("/multiply")
                        .content(testInputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(testOutputJson));
    }

    @Test
    public void shouldReturnMultiplicationInvalidRequest() throws Exception{
        MathSolution testOutput = new MathSolution();
        testOutput.setOperand1(20);

        String testJson = mapper.writeValueAsString(testOutput);

        mockMvc.perform(post("/multiply")
                        .content(testJson)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldReturnDivisionSuccessfulResponse() throws Exception{
        MathSolution testInput = new MathSolution();
        testInput.setOperand1(60);
        testInput.setOperand2(30);

        String testInputJson = mapper.writeValueAsString(testInput);

        MathSolution testOutput = new MathSolution();
        testOutput.setOperand1(60);
        testOutput.setOperand2(30);
        testOutput.setOperation("divide");
        testOutput.setAnswer(testOutput.getOperand1() / testOutput.getOperand2());

        String testOutputJson = mapper.writeValueAsString(testOutput);

        mockMvc.perform(post("/divide")
                        .content(testInputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(testOutputJson));
    }

    @Test
    public void shouldReturnDivisionInvalidRequest() throws Exception{
        MathSolution testOutput = new MathSolution();
        testOutput.setOperand1(20);
        testOutput.setOperation("divide");

        String testJson = mapper.writeValueAsString(testOutput);

        mockMvc.perform(post("/divide")
                        .content(testJson)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldReturnDivideByZeroRequest() throws Exception{
        MathSolution testOutput = new MathSolution();
        testOutput.setOperand1(20);
        testOutput.setOperand2(0);
        testOutput.setOperation("divide");

        String testJson = mapper.writeValueAsString(testOutput);

        mockMvc.perform(post("/divide")
                .content(testJson)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}