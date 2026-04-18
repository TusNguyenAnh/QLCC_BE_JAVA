package com.mbs.qlcc.adapters.request.Organization;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * Update Organization Request DTO (Adapter Level)
 * Received from HTTP client
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateOrganizationRequest {
    String complexId;
    String orgCode;
    String orgName;
    String description;
    String parentOrgId;
    List<String> buildingIds;  // Optional
}
