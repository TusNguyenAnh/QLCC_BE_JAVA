package com.mbs.qlcc.adapters.response;

import java.time.LocalDateTime;

public interface IResUser {
    String getId();

    String getComplexId();

    String getFullname();

    int getGender();

    String getEmail();

    LocalDateTime getBirthday();

    String getRelationship();

    String getPhoneNumber();

    String getCccd();

    String getUserId();

}
