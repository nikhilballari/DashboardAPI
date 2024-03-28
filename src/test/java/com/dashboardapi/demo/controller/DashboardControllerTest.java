package com.dashboardapi.demo.controller;

import com.dashboardapi.demo.entity.Dashboard;
import com.dashboardapi.demo.service.DashboardService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.json.JSONArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DashboardController.class)
public class DashboardControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    DashboardService dashboardService;

    @Test
    @DisplayName("Get all Dashboard information")
    public void whenRequestedForAllInfo_thenReturnAllDashboardData() throws Exception {

        Dashboard dashboard1 = Dashboard.builder()
                .id(5L)
                .title("Test Title 5")
                .createdAt(LocalDate.of(2024,01,10))
                .updatedAt(LocalDate.of(2024,01,15))
                .build();

        Dashboard dashboard2 = Dashboard.builder()
                .id(6L)
                .title("Test Title 6")
                .createdAt(LocalDate.of(2024,02,20))
                .updatedAt(LocalDate.of(2024,03,23))
                .build();

        Mockito.when(dashboardService.getAllDashboards()).thenReturn(List.of(dashboard1,dashboard2));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/dashboards"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("[0].title").value(dashboard1.getTitle()))
                        .andExpect(jsonPath("[1].title").value(dashboard2.getTitle()))
                        .andExpect(jsonPath("[0].createdAt")
                                .value(dashboard1.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))))
                        .andExpect(jsonPath("[1].updatedAt")
                                .value(dashboard2.getUpdatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))))
                        .andReturn();
        String content = result.getResponse().getContentAsString();
        ObjectMapper responseMapper = new ObjectMapper();
        responseMapper.registerModule(new JavaTimeModule());
        List<Dashboard> responseList = responseMapper.readValue(content, new TypeReference<List<Dashboard>>() {});

        Assertions.assertEquals(dashboard1.getTitle(), responseList.get(0).getTitle());
        Assertions.assertEquals(dashboard2.getTitle(), responseList.get(1).getTitle());
        Assertions.assertEquals(dashboard1.getCreatedAt(), responseList.get(0).getCreatedAt());
        Assertions.assertEquals(dashboard2.getUpdatedAt(), responseList.get(1).getUpdatedAt());
    }

    @Test
    @DisplayName("Return empty response when no data in the backend DB")
    public void whenNoDataInDB_ThenReturnEmptyResponse() throws Exception {
        Mockito.when(dashboardService.getAllDashboards()).thenReturn(new ArrayList<>());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/dashboards"))
                                    .andExpect(status().isOk())
                                    .andReturn();
        String content = result.getResponse().getContentAsString();
        ObjectMapper responseMapper = new ObjectMapper();
        List<Dashboard> responseList = responseMapper.readValue(content, new TypeReference<List<Dashboard>>() {});
        Assertions.assertEquals(Collections.emptyList(), responseList);
    }

    @Test
    @DisplayName("Return successful response when new Dashboard is added")
    public void whenNewDashboardAdded_ThenReturnSuccessfulResponse() throws Exception {

        Dashboard dashboard1 = Dashboard.builder()
                .id(5L)
                .title("Test Title 5")
                .createdAt(LocalDate.of(2024,01,10))
                .updatedAt(LocalDate.of(2024,01,15))
                .build();

        Mockito.when(dashboardService.saveDashboard(any())).thenReturn(dashboard1);

        ObjectMapper requestMapper = new ObjectMapper();
        requestMapper.registerModule(new JavaTimeModule());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/dashboards")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(requestMapper.writeValueAsString(dashboard1)))
                            .andExpect(status().isOk())
                            .andExpect(jsonPath("$.title").value(dashboard1.getTitle()))
                            .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        ObjectMapper responseMapper = new ObjectMapper();
        responseMapper.registerModule(new JavaTimeModule());
        Dashboard responseDashboard = responseMapper.readValue(responseContent, new TypeReference<Dashboard>() {});

        Assertions.assertEquals(dashboard1.getTitle(), responseDashboard.getTitle());
        Assertions.assertEquals(dashboard1.getCreatedAt(), responseDashboard.getCreatedAt());
        Assertions.assertEquals(dashboard1.getUpdatedAt(), responseDashboard.getUpdatedAt());

    }
}
