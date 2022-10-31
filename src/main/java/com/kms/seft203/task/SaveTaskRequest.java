package com.kms.seft203.task;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper=false)
public class SaveTaskRequest extends Task {

    @NotNull
    private String task;
    @NotNull
    private Boolean isCompleted;
    @NotNull
    private String userId;
}
