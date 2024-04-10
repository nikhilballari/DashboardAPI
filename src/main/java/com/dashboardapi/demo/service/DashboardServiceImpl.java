package com.dashboardapi.demo.service;

import com.dashboardapi.demo.entity.Dashboard;
import com.dashboardapi.demo.repository.DashboardRepository;
import com.dashboardapi.demo.error.ExistingDashboardTitleException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Layer Class with implementation for the methods interacting with Repository layer
 */
@Service
@Slf4j
public class DashboardServiceImpl implements DashboardService {
    @Autowired
    private DashboardRepository dashboardRepository;

    /**
     * This method fetches all the dashboard records  from the backend database table
     *
     * @return List of dashboard records upon successful retrieval from the database
     */
    @Override
    public List<Dashboard> getAllDashboards() {
        log.info("Inside getAllDashboards() method");
        return dashboardRepository.findAll();
    }

    /**
     * This method inserts a new record in the backend database table
     *
     * @return dashboard record upon successful insertion in the database
     */
    @Override
    public Dashboard saveDashboard(Dashboard dashboard) throws ExistingDashboardTitleException {
        log.info("Inside saveDashboard() method");
        List<Dashboard> existingDashboard = dashboardRepository.findAll();
        List<Dashboard> dbDashboardList =  existingDashboard.stream()
                .filter(dashboard1 -> dashboard1.getTitle().equalsIgnoreCase(dashboard.getTitle()))
                .collect(Collectors.toList());
        if (!dbDashboardList.isEmpty())
        {
            throw new ExistingDashboardTitleException("Dashboard with same title already present");
        }
        return dashboardRepository.save(dashboard);
    }
}
