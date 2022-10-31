//package com.kms.seft203.dashboard.widget;
//
//import com.kms.seft203.dashboard.Dashboard;
//import com.kms.seft203.dashboard.DashboardService;
//import com.kms.seft203.exception.APImessages;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/widget")
//public class WidgetController {
//    @Autowired
//    WidgetService widgetService;
//    @Autowired
//    DashboardService dashboardService;
//
//    @GetMapping({"/", "/all"})
//    public List<Widget> getAll() {
//        return widgetService.getWidgets();
//    }
////    @PostMapping("/add")
////    public ResponseEntity<?> addNewWidget(@RequestBody Widget widget) {
////        if (widget != null && widget.getTitle()!= null){
////            widgetService.insertWidget(widget);
////            return new ResponseEntity<Widget>(widget, HttpStatus.FOUND);
////        } else {
////            return new ResponseEntity<String>("Cannot insert new widget", HttpStatus.BAD_REQUEST);
////        }
////    }
////    @GetMapping("/insert/{idWidget}/{idDashboard}")
////    public ResponseEntity<?> insertIntoDashboard(@PathVariable("idWidget") String idWidget, @PathVariable("idDashboard") String idDashboard) {
////        if (widgetService.findWidgetById(idWidget) == null) {
////            return new ResponseEntity<String>("Not found widget id " + idWidget, HttpStatus.NOT_FOUND);
////        } else {
////            if (dashboardService.getDashboardById(idDashboard) == null) {
////                return new ResponseEntity<String>("Not found dashboard id " + idDashboard, HttpStatus.NOT_FOUND);
////            } else
////                widgetService.findWidgetById(idWidget).setDashboard(dashboardService.getDashboardById(idDashboard));
////                return new ResponseEntity<Dashboard>(dashboardService.getDashboardById(idDashboard), HttpStatus.OK);
////        }
////    }
//    @PostMapping({"/"})
//    public ResponseEntity<?> insertNewWidget(@RequestBody Widget widget) {
//        // should be valid here
//        if (widget.getTitle()!= null){
//            widgetService.insertWidget(widget);
//            return new ResponseEntity<Widget>(widget, HttpStatus.FOUND);
//        } else {
//            return new ResponseEntity<String>("Cannot insert new widget", HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @GetMapping("/{widgetId}")
//    public ResponseEntity<?> deleteWidget(@PathVariable("widgetId") String id) {
//        if (widgetService.findWidgetById(id) == null) {
//            APImessages imessages = new APImessages("Not found widget id: " + id);
//            return new ResponseEntity<>(imessages, HttpStatus.NOT_FOUND);
//        } else {
//            widgetService.deleteWidgetById(id);
//            return new ResponseEntity<String>("Đã xóa!", HttpStatus.ACCEPTED);
//        }
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateWidget(@PathVariable(name = "id") String id, @RequestBody Widget newWidget) {
//        if (widgetService.findWidgetById(id) == null) {
//            APImessages imessages = new APImessages("Not found widget id: " + id);
//            return new ResponseEntity<>(imessages, HttpStatus.NOT_FOUND);
//        } else {
//            widgetService.updateWidget(id, newWidget);
//            return new ResponseEntity<Widget>(widgetService.findWidgetById(id), HttpStatus.ACCEPTED);
//        }
//    }
//
//
//}
