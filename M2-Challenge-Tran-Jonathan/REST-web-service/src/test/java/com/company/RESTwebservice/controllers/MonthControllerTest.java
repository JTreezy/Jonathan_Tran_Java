package com.company.RESTwebservice.controllers;

import com.company.RESTwebservice.models.Month;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(MonthController.class)
public class MonthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void shouldReturnAMonth() throws Exception {
        //arrange
        Month month = new Month (2, "February");
        String outputMonthJson = mapper.writeValueAsString(month);
        //act
        mockMvc.perform(get("/month/{monthNumber}", 2))
                .andDo(print())
                //assert
                .andExpect(status().isOk())
                .andExpect(content().json(outputMonthJson));

    }

    @Test
    public void shouldRespondWith422WhenIntValueIsNotOneThroughTwelve() throws Exception{
        mockMvc.perform(get("/month/{monthNumber}", 0))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

    }

    @Test
    public void shouldReturnARandomMonth() throws Exception{
        mockMvc.perform(get("/randomMonth"))
                .andDo(print())
                //assert
                .andExpect(status().isOk());


    }

}