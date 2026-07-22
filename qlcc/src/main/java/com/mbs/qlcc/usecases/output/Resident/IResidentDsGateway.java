package com.mbs.qlcc.usecases.output.Resident;

import com.mbs.qlcc.adapters.response.IResAptBd;
import com.mbs.qlcc.entities.Resident.Resident;
import com.mbs.qlcc.usecases.request.Resident.FilterResidentInpRequest;
import com.mbs.qlcc.usecases.response.PageResponse;
import com.mbs.qlcc.usecases.response.Resident.ResUserResponse;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IResidentDsGateway {
    List<IResAptBd> findAll(String complexId, FilterResidentInpRequest filterResidentInpRequest);

    Resident save(Resident resident);

    void saveAll(List<Resident> residents);

    List<ResUserResponse> findResUserByOrgId(String orgId);

    Optional<Resident> findById(String id);

    boolean existsByComplexIdAndEmail(String complexId, String email);

    boolean existsByComplexIdAndPhoneNumber(String complexId, String phoneNumber);

    boolean existsByComplexIdAndCccd(String complexId, String cccd);

    List<String> findEmailsByComplexId(String complexId, List<String> emails);

    List<String> findPhoneNumbersByComplexId(String complexId, List<String> phoneNumbers);

    List<Resident> findCccdsByComplexId(String complexId, Set<String> cccds);

}
