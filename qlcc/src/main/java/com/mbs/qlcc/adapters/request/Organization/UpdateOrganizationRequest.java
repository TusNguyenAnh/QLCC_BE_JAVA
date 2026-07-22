package com.mbs.qlcc.adapters.request.Organization;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateOrganizationRequest {
    String orgCode;
    String orgName;
    String description;
    String parentOrgId;
    List<String> building;  // Optional
}
