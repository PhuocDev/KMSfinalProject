package com.kms.seft203.dashboard;

import com.kms.seft203.dashboard.widget.Widget;
import com.kms.seft203.dashboard.widget.WidgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dashboards")
public class DashboardApi {

    //private static final Map<String, Dashboard> DATA = new HashMap<>();
    @Autowired
    DashboardService dashboardService;
    @Autowired
    WidgetService widgetService;


    @GetMapping("/createDashboard")
    public List<Dashboard> createDashboard() {
        Dashboard newDashboard = new Dashboard("12345","title","Type 1");
        dashboardService.insertDashboard(newDashboard);
        return dashboardService.getDashboard();
    }
    @GetMapping("/insertWidget/{id}")
    public List<Dashboard> createDB(@PathVariable(name = "id") String id) {
        Dashboard newDashboard = dashboardService.getDashboardById(id);
        Widget newWidget = new Widget( "id3423","Title", "Type 1", 1000,1000, newDashboard);
        Widget newWidget2 = new Widget(  "534543","Title 2", "Type 2", 500,500, newDashboard);

        widgetService.insertWidget(newWidget);
        widgetService.insertWidget(newWidget2);

        return dashboardService.getDashboard();
    }
    @GetMapping("/deleteAll")
    public void deleteAll() {
        dashboardService.deleteAll();
    }
    @GetMapping
    public List<Dashboard> getAll() {
        // TODO: get all dashboard of a logged in user
        //return new ArrayList<>(DATA.values());
        return dashboardService.getDashboard();
    }
    @PutMapping("/{id}")
    public Dashboard save(@PathVariable String id, @RequestBody SaveDashboardRequest request) {
        //DATA.put(id, request);
        Dashboard newDash = new Dashboard(request);
        dashboardService.updateDashboard(id, newDash);
        return newDash;
    }

    //Addition
    @GetMapping("/{id}")
    public Dashboard findDasId(@PathVariable("id") String id) {
        Dashboard dashboard = dashboardService.getDashboardById(id);
        if (dashboard == null) {
            return null;
        } else  return dashboard;
    }

    @PostMapping("/add")
    public ResponseEntity<?> insertNewDashboard(@RequestBody SaveDashboardRequest request) {
        if (request == null || request.getUserId()== null || request.getLayoutType() == null) {
            return new ResponseEntity<String>("Not enough information", HttpStatus.BAD_REQUEST);
        } else {
            Dashboard newDash = new Dashboard(request);
            dashboardService.insertDashboard(newDash);
            return new ResponseEntity<Dashboard>(newDash, HttpStatus.ACCEPTED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id) {
        if (dashboardService.getDashboardById(id) == null) {
            return new ResponseEntity<String>("Not found dashboard id: " + id, HttpStatus.NOT_FOUND);
        } else {
            dashboardService.deleteDashboard(id);
            return new ResponseEntity<String>("Deleted dashboard id: " + id, HttpStatus.ACCEPTED);
        }
    }
}
