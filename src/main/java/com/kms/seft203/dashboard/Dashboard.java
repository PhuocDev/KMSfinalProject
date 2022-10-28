package com.kms.seft203.dashboard;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Table(name = "dashboard")
public class Dashboard {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @NotNull
    private String id;
    @NotEmpty @NotNull
    private String userId;
    @NotEmpty @NotNull
    private String title;
    @NotEmpty @NotNull
    private String layoutType;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "widget_id")
    private List<Widget> widgets;
}
