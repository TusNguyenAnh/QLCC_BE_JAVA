package com.mbs.qlcc.usecases.output.Resident;

import com.mbs.qlcc.adapters.response.IResUser;
import com.mbs.qlcc.entities.Resident.AptResident;

import java.util.List;
import java.util.Optional;

public interface IAptResidentDsGateway {
    List<IResUser> findResidentsInBuildingNotInOrg(List<String> buildingId, String orgId);
    AptResident save(AptResident aptResident);
    List<AptResident> saveAll(List<AptResident> aptResidents);
}
