package com.kms.seft203.dashboard;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
@Table(name = "widget")
public class Widget {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @NotNull
    private String title;
    @NotEmpty
    private String widgetType;
    @NotEmpty
    private Integer minWidth;
    @NotEmpty
    private Integer minHeight;

    @NotNull
    private Map<String, Object> configs;
}
