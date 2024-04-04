package com.dashboardapi.demo.controller;

import com.dashboardapi.demo.entity.Dashboard;
import com.dashboardapi.demo.service.DashboardService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    /**
     * Controller Endpoint to fetch dashboard information from database
     *
     * @return list of dashboards in the form of JSON response
     */
    @GetMapping("/dashboards")
    public List<Dashboard> getAllDashboards() {
        log.info("Inside DashboardController.getAllDashboards() method");
        return dashboardService.getAllDashboards();
    }

    /**
     * Controller Endpoint to add a new dashboard information into database
     *
     * @return dashboard record upon successful insertion in the database
     */
    @PostMapping("/dashboards")
    public Dashboard saveDashboard(@RequestBody @Valid Dashboard dashboard) {
        log.info("Inside DashboardController.saveDashboard() method");
        return dashboardService.saveDashboard(dashboard);
    }
}
