package com.dashboardapi.demo.repository;

import com.dashboardapi.demo.entity.Dashboard;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository class that interacts with database
 */
public interface DashboardRepository extends JpaRepository<Dashboard, Long> {

    /**
     * This method fetches the dashboard information from the backend table for the requested title
     *
     * @return dashboard record upon successful retrieval from the database
     */
    Dashboard findByTitle(String Title);
}
