package com.mbs.qlcc.adapters.response;

import java.time.LocalDateTime;

public interface IResAptBd {
    String getId();

    String getComplexId();

    String getFullname();

    int getGender();

    String getEmail();

    LocalDateTime getBirthday();

    String getRelationship();

    String getPhoneNumber();

    String getCccd();

    String getBuildingId();

    int getFloor();

    String getAptNumber();

    int getStatus();
}
