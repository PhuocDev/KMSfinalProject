package com.kms.seft203.dashboard;

import com.kms.seft203.dashboard.Dashboard;
import com.kms.seft203.dashboard.DashboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {
    @Autowired
    DashboardRepository dashboardRepository;

    public List<Dashboard> getDashboard(){
        return dashboardRepository.findAll();
    }

    public Dashboard getDashboardById(String id) {
        return dashboardRepository.findById(id).get();
    }
    public void insertDashboard(Dashboard dashboard) {
        dashboardRepository.save(dashboard);
    }

    public void deleteDashboard(String id) {
        dashboardRepository.deleteById(id);
    }

    public void updateDashboard(String id, Dashboard newDashboard) {
        Dashboard oldDash = dashboardRepository.getById(id);
        oldDash.setTitle(newDashboard.getTitle());
        oldDash.setLayoutType(newDashboard.getLayoutType());
        oldDash.setUserId(newDashboard.getUserId());
        oldDash.setWidgets(newDashboard.getWidgets());

        dashboardRepository.saveAndFlush(oldDash);
    }

    public void deleteAll() {
        dashboardRepository.deleteAll();
    }
}
