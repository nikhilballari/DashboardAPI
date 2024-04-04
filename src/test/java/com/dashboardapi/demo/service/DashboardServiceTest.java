package com.dashboardapi.demo.service;

import com.dashboardapi.demo.entity.Dashboard;
import com.dashboardapi.demo.repository.DashboardRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class DashboardServiceTest {
    @Autowired
    private DashboardService dashboardService;

    @MockBean
    private DashboardRepository dashboardRepository;

    @Test
    @DisplayName("Get all Dashboards from the database")
    public void whenDashboardsExist_thenReturnAllDashboards() {
        Dashboard dashboard1 = Dashboard.builder()
                .title("Test Title 5")
                .createdAt(LocalDateTime.of(2024, 01, 10, 18, 10, 15))
                .updatedAt(LocalDateTime.of(2024, 01, 15, 18, 10, 15))
                .build();

        Dashboard dashboard2 = Dashboard.builder()
                .title("Test Title 6")
                .createdAt(LocalDateTime.of(2024, 02, 20, 18, 10, 15))
                .updatedAt(LocalDateTime.of(2024, 03, 23, 18, 10, 15))
                .build();

        Mockito.when(dashboardRepository.findAll()).thenReturn(List.of(dashboard1, dashboard2));

        List<Dashboard> dashboardList = dashboardService.getAllDashboards();
        assertEquals(dashboardList.stream().count(), 2);
        assertEquals(dashboardList.get(0).getTitle(), "Test Title 5");
        assertNotEquals(dashboardList.get(0).getCreatedAt(), dashboardList.get(1).getCreatedAt());
    }

    @Test
    @DisplayName("Get empty dashboard list when no data")
    public void whenNoDashboardData_thenReturnNothing() {
        List<Dashboard> emptyDashboardList = new ArrayList<>();
        Mockito.when(dashboardRepository.findAll()).thenReturn(emptyDashboardList);

        List<Dashboard> dashboardList = dashboardService.getAllDashboards();
        Assertions.assertThat(dashboardList.isEmpty());

    }

    @Test
    @DisplayName("Add new Dashboard Record")
    public void whenNewRecordAdded_thenReturnSuccess() {
        Dashboard dashboard1 = Dashboard.builder()
                .id(5L)
                .title("Test Title 5")
                .createdAt(LocalDateTime.of(2024, 01, 10, 18, 10, 15))
                .updatedAt(LocalDateTime.of(2024, 01, 15, 18, 10, 15))
                .build();

        Mockito.when(dashboardRepository.save(any())).thenReturn(dashboard1);

        Dashboard responseDashboard = dashboardService.saveDashboard(dashboard1);
        assertEquals(responseDashboard.getTitle(), dashboard1.getTitle());
        assertEquals(responseDashboard.getUpdatedAt(), dashboard1.getUpdatedAt());
    }
}
