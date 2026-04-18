package com.mbs.qlcc.adapters.request.Organization;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * Create Organization Request DTO (Adapter Level)
 * Received from HTTP client
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateOrganizationRequest {
    String orgCode;
    String orgName;
    String parentOrgId;      // Optional
    String description;
    List<String> buildingIds;  // Optional
}
