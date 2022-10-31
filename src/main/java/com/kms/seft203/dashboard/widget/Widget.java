package com.kms.seft203.dashboard.widget;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kms.seft203.dashboard.Dashboard;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
@Entity
public class Widget {
//    @Id
//    @GeneratedValue(generator="system-uuid")
//    @GenericGenerator(name="system-uuid", strategy = "uuid")
//    private String id;

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    private String title;
    private String widgetType;
    private Integer minWidth;
    private Integer minHeight;
    @Transient
    private Map<String, Object> configs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Widget() {

    }

    public Widget(String id, String title, String widgetType, Integer minWidth, Integer minHeight, Map<String, Object> configs) {
        this.id = id;
        this.title = title;
        this.widgetType = widgetType;
        this.minWidth = minWidth;
        this.minHeight = minHeight;
        this.configs = configs;
    }

    public Widget(String title, String widgetType, Integer minWidth, Integer minHeight, Map<String, Object> configs) {
        this.title = title;
        this.widgetType = widgetType;
        this.minWidth = minWidth;
        this.minHeight = minHeight;
        this.configs = configs;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWidgetType() {
        return widgetType;
    }

    public void setWidgetType(String widgetType) {
        this.widgetType = widgetType;
    }

    public Integer getMinWidth() {
        return minWidth;
    }

    public void setMinWidth(Integer minWidth) {
        this.minWidth = minWidth;
    }

    public Integer getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(Integer minHeight) {
        this.minHeight = minHeight;
    }

    public Map<String, Object> getConfigs() {
        return configs;
    }

    public void setConfigs(Map<String, Object> configs) {
        this.configs = configs;
    }
}
