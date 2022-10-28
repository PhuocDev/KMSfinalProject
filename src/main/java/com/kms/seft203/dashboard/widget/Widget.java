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
@Table(name = "widget")
public class Widget {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @NotNull
    private String id;

    @Column(name = "title")
    private String title;
    @NotEmpty
    private String widgetType;
    @NotEmpty
    private Integer minWidth;
    @NotEmpty
    private Integer minHeight;

//    @NotNull
//    private Map<String, Object> configs;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "dashboard_id", nullable = false)
    @JsonIgnore
    private Dashboard dashboard;

    public Widget() {

    }
    public Widget(String id, String title, String widgetType, Integer minWidth, Integer minHeight, Dashboard dashboard) {
        this.id = id;
        this.title = title;
        this.widgetType = widgetType;
        this.minWidth = minWidth;
        this.minHeight = minHeight;
        this.dashboard = dashboard;
    }

    public Widget(String title, String widgetType, Integer minWidth, Integer minHeight, Dashboard dashboard) {
        this.title = title;
        this.widgetType = widgetType;
        this.minWidth = minWidth;
        this.minHeight = minHeight;
        this.dashboard = dashboard;
    }

    public Widget(String title, String widgetType, Integer minWidth, Integer minHeight) {
        this.title = title;
        this.widgetType = widgetType;
        this.minWidth = minWidth;
        this.minHeight = minHeight;
    }
}
