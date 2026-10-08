package com.mbs.qlcc.dto.response.Task;

import java.time.Instant;

public interface ITaskOrgResponse {
    String getId();

    String getComplexId();

    String getTaskTypeId();

    String getCurrentOrgId();

    String getCreator();

    Integer getCurrentStep();

    String getTaskName();

    String getDescription();

    String getStatus(); // 'PENDING', 'APPROVED', 'REJECTED','UNFINISHED'

    String getCategory();
    Instant getCreatedAt();
    String getTypeName();

    String getPriorityName();

    String getPriorityId();

    String getUsername();

    String getPhoneNumber();

    String getFullName();

    String getAptNumber();

    Integer getLevel();

    String getBuildingName();

}
