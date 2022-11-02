package com.kms.seft203.dashboard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kms.seft203.auth.user.UserService;
import com.kms.seft203.dashboard.widget.Widget;
import com.kms.seft203.dashboard.widget.WidgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dashboards")
public class DashboardApi {

    //private static final Map<String, Dashboard> DATA = new HashMap<>();
    @Autowired
    DashboardService dashboardService;
    @Autowired
    UserService userService;

    @Autowired
    WidgetRepository widgetRepository;



    @GetMapping
    public List<Dashboard> getAll() {
        // TODO: get all dashboard of a logged in user
        //return new ArrayList<>(DATA.values());
        return dashboardService.getDashboard();
    }
    @PutMapping("/{id}")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody SaveDashboardRequest request) {
        //DATA.put(id, request);
        Dashboard newDash = new Dashboard(request);
        dashboardService.updateDashboard(id, newDash);
        return new ResponseEntity<Dashboard>(dashboardService.getDashboardById(id), HttpStatus.OK);
    }

//    @GetMapping("/{id}")
//    public Dashboard findDasId(@PathVariable("id") String id) {
//        Dashboard dashboard = dashboardService.getDashboardById(id);
//        if (dashboard == null) {
//            return null;
//        } else  return dashboard;
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteById(@PathVariable("id") String id) {
//        if (dashboardService.getDashboardById(id) == null) {
//            return new ResponseEntity<String>("Not found dashboard id: " + id, HttpStatus.NOT_FOUND);
//        } else {
//            dashboardService.deleteDashboard(id);
//            return new ResponseEntity<String>("Deleted dashboard id: " + id, HttpStatus.ACCEPTED);
//        }
//    }
//    @PostMapping("/")
//    public ResponseEntity<?> insertNewDashboard(@RequestBody SaveDashboardRequest request) {
//        if (request.getUserId()== null || request.getLayoutType() == null) {
//            return new ResponseEntity<String>("Not enough information", HttpStatus.BAD_REQUEST);
//        } else {
//            //check if userId and widget id exist
//            if (userService.isUserIdExist(request.getUserId()) ) {
//                Dashboard newDash = new Dashboard(request);
//                dashboardService.insertDashboard(newDash);
//                return new ResponseEntity<Dashboard>(dashboardService.getDashboardById(newDash.getId()), HttpStatus.ACCEPTED);
//            } else return new ResponseEntity<String>("Not found user id or widget id", HttpStatus.NOT_FOUND);
//        }
//    }

//    @DeleteMapping("/delete/{idDas}/{id}")
//    public ResponseEntity<?> deleteWidget(@PathVariable("id") String widgetId, @PathVariable("idDas") String idDas){
////        if (widgetService.existWidgetId(widgetId)) {
////            dashboardService.deleteWidgetId(idDas, widgetId);
////            return new ResponseEntity<Dashboard>(dashboardService.getDashboardById(idDas), HttpStatus.OK);
////        } else return new ResponseEntity<>("false", HttpStatus.NOT_FOUND);
//        return new ResponseEntity<>("Temp", HttpStatus.BAD_REQUEST);
//    }
//    @GetMapping("/deleteAll")
//    public void deleteAll() {
//        dashboardService.deleteAll();
//    }
//@GetMapping("/createDB")
//public List<Dashboard> createDB() {
//    List<Widget> widgetList = new ArrayList<>();
//    Map<String, Object> temporary = null;
//    Widget widget1 = new Widget("Widget 1", "type A", 1000, 2000, temporary );
//    Widget widget2 = new Widget("Widget 2", "type B", 1000, 2000, temporary );
//    Widget widget3 = new Widget("Widget 3", "type C", 1000, 2000, temporary );
//    Widget widget4 = new Widget("Widget 4", "type C", 1000, 2000, temporary );
//    widgetList.add(widget1);
//    widgetList.add(widget2);
//    widgetList.add(widget3);
//    widgetRepository.save(widget1);
//    widgetRepository.save(widget2);
//    widgetRepository.save(widget3);
//    widgetRepository.save(widget4);
//
//    //  Dashboard dashboard = new Dashboard("4028ec36841e18e601841e191a300000", "title 1", "type 1", widgetList);
//    Dashboard dashboard2 = new Dashboard("4028ec36841e18e601841e191a300000", "title 2", "type 2", (List<Widget>) widget4);
//    dashboardService.insertDashboard(dashboard2);
//    return dashboardService.getDashboard();
//}
}
