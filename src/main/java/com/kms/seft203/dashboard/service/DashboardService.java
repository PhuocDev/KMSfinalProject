//package com.kms.seft203.dashboard.service;
//
//import com.kms.seft203.dashboard.Dashboard;
//import com.kms.seft203.dashboard.DashboardRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class DashboardService {
//    @Autowired
//    DashboardRepository dashboardRepository;
//
//    List<Dashboard> getDashboard(){
//        return dashboardRepository.findAll();
//    }
//
//    Dashboard getDashboardById(String id) {
//        return dashboardRepository.findById(id).get();
//    }
//    void insertDashboard(Dashboard dashboard) {
//        dashboardRepository.save(dashboard);
//    }
//
//    void deleteDashboard(String id) {
//        dashboardRepository.deleteById(id);
//    }
//
//
//}
