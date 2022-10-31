package com.kms.seft203.dashboard;

import com.kms.seft203.dashboard.Dashboard;
import com.kms.seft203.dashboard.DashboardRepository;
import com.kms.seft203.dashboard.widget.Widget;
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
        //List<Widget> widgetList = dashboard.getWidgets();
        //add list widget to the dash board
        //insertWidget(widgetList, dashboard);
    }

//    private void insertWidget(List<Widget> widgetList, Dashboard dashboard) {
//        for(Widget wid : widgetList) {
////            if (widgetService.existWidgetId(wid.getId())) {
////                wid.setDashboard(dashboard);
////                widgetService.insertWidget(wid);
////            } else System.out.println("Cannot find widget id");
//            Widget newWid = new Widget(wid);
//            newWid.setDashboard(dashboard);
//            widgetService.insertWidget(wid);
//        }
//    }

    public void deleteDashboard(String id) {
        dashboardRepository.deleteById(id);
    }

    public void updateDashboard(String id, Dashboard newDashboard) {
        Dashboard oldDash = dashboardRepository.getById(id);
        oldDash.setTitle(newDashboard.getTitle());
        oldDash.setLayoutType(newDashboard.getLayoutType());
        oldDash.setUserId(newDashboard.getUserId());
        // update
        dashboardRepository.saveAndFlush(oldDash);
    }

    public void deleteAll() {
        dashboardRepository.deleteAll();
    }

    public void deleteWidgetId(String idDas, String widgetId) {

    }
}
