package com.kms.seft203.dashboard;

import com.kms.seft203.dashboard.widget.Widget;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;


@Entity
@Table(name = "dashboard")
public class Dashboard {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @NotNull
    @Column(name = "dashboard_id")
    private String id;
    @NotEmpty @NotNull
    private String userId;
    @NotEmpty @NotNull
    private String title;
    @NotEmpty @NotNull
    private String layoutType;

    public Dashboard(String userId, String title, String layoutType) {
        this.userId = userId;
        this.title = title;
        this.layoutType = layoutType;
    }

    public Dashboard() {

    }
    @OneToMany( mappedBy = "dashboard",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Widget> widgets;

    public Dashboard(SaveDashboardRequest request) {
        this.userId = request.getUserId();
        this.title = request.getTitle();
        this.layoutType = request.getLayoutType();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLayoutType() {
        return layoutType;
    }

    public void setLayoutType(String layoutType) {
        this.layoutType = layoutType;
    }

    public List<Widget> getWidgets() {
        return widgets;
    }

    public void setWidgets(List<Widget> widgets) {
        this.widgets = widgets;
    }
}
