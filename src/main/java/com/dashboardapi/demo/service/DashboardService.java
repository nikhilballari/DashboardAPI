package com.dashboardapi.demo.service;

import com.dashboardapi.demo.entity.Dashboard;
import com.dashboardapi.demo.error.ExistingDashboardTitleException;

import java.util.List;

/**
 * Service Layer Interface
 */
public interface DashboardService {

    List<Dashboard> getAllDashboards();

    Dashboard saveDashboard(Dashboard dashboard) throws ExistingDashboardTitleException;

    Dashboard saveDashboardByCheckingTitle(Dashboard dashboard) throws ExistingDashboardTitleException;
}
