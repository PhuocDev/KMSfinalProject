//package com.kms.seft203.dashboard.widget;
//
//import com.kms.seft203.dashboard.Dashboard;
//import com.kms.seft203.dashboard.widget.Widget;
//import com.kms.seft203.dashboard.widget.WidgetRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class WidgetService {
//    @Autowired
//    WidgetRepository widgetRepository;
//
//    public void insertWidget(Widget widget) {
//        widgetRepository.save(widget);
//    }
//
//    public List<Widget> getWidgets() {
//        return widgetRepository.findAll();
//    }
//
//    public Widget findWidgetById(String id ) {
//        return widgetRepository.findById(id).get();
//    }
//
//    public void deleteWidgetById(String id) {
//        widgetRepository.deleteById(id);
//    }
//    public void updateWidget(String id, Widget newWidget) {
//        Widget oldWidget = widgetRepository.findById(id).get();
//        oldWidget.setTitle(newWidget.getTitle());
//        oldWidget.setWidgetType(newWidget.getWidgetType());
//        oldWidget.setMinHeight(newWidget.getMinHeight());
//        oldWidget.setMinWidth(newWidget.getMinWidth());
//
//        widgetRepository.saveAndFlush(oldWidget);
//    }
//
//    public boolean existWidgetId(String id) {
//        return widgetRepository.existsById(id);
//    }
//
//    public boolean existAllWidgetId(List<Widget> widgets) {
//        for (Widget wid : widgets) {
//            if (!widgetRepository.existsById(wid.getId())) {
//                return false;
//            }
//        }
//        return true;
//    }
//}
