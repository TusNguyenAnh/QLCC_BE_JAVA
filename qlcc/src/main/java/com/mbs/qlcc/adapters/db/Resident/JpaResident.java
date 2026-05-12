package com.mbs.qlcc.adapters.db.Resident;

import com.mbs.qlcc.adapters.response.IResAptBd;
import com.mbs.qlcc.adapters.response.IResUser;
import com.mbs.qlcc.entities.Resident.Resident;
import com.mbs.qlcc.usecases.output.Resident.IResidentDsGateway;
import com.mbs.qlcc.usecases.request.Resident.FilterResidentInpRequest;
import com.mbs.qlcc.usecases.response.PageResponse;
import com.mbs.qlcc.usecases.response.Resident.ResUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaResident implements IResidentDsGateway {

    private final JpaResidentRepository repository;

    //show all
    public PageResponse<IResAptBd> findAll(String complexId, FilterResidentInpRequest filterResidentInpRequest) {
        Sort.Direction direction = filterResidentInpRequest.getOrder() != null && filterResidentInpRequest.getOrder().equalsIgnoreCase("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(
                filterResidentInpRequest.getPageNumber(),
                filterResidentInpRequest.getPageSize(),
                direction,
                "created_at"
        );


        Page<IResAptBd> page = repository.filter(complexId, filterResidentInpRequest.getBuildingId(),
                filterResidentInpRequest.getFloor(), filterResidentInpRequest.getAptNumber(), filterResidentInpRequest.getRelationship(), pageable);


        return new PageResponse<IResAptBd>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    @Override
    public Resident save(Resident resident) {
        ResidentDataMapper mapper = mapToMapper(resident);
        ResidentDataMapper saved = repository.save(mapper);
        return mapToEntity(saved);
    }

    @Override
    public void saveAll(List<Resident> residents) {
        List<ResidentDataMapper> mappers = residents.stream()
                .map(this::mapToMapper)
                .toList();
        repository.saveAll(mappers);

    }

    @Override
    public List<ResUserResponse> findResUserByOrgId(String orgId) {
        List<IResUser> resUser = repository.findResUserByOrgId(orgId);

        return resUser.stream()
                .map(r -> new ResUserResponse(
                        r.getId(),
                        r.getComplexId(),
                        r.getFullname(),
                        r.getGender(),
                        r.getEmail(),
                        r.getBirthday(),
                        r.getRelationship(),
                        r.getPhoneNumber(),
                        r.getCccd(),
                        r.getUserId()
                ))
                .toList();
    }

    @Override
    public List<String> findEmailsByComplexId(String complexId, List<String> emails) {
        return repository.findIdsByComplexIdAndEmails(complexId, emails);
    }

    @Override
    public List<String> findPhoneNumbersByComplexId(String complexId, List<String> phoneNumbers) {
        return repository.findIdsByComplexIdAndPhoneNumbers(complexId, phoneNumbers);
    }

    @Override
    public List<Resident> findCccdsByComplexId(String complexId, List<String> cccds) {
        return repository.findIdsByComplexIdAndCccds(complexId, cccds).stream().map(this::mapToEntity).toList();
    }

    @Override
    public Optional<Resident> findById(String id) {
        return repository.findById(id)
                .map(this::mapToEntity);
    }

    @Override
    public boolean existsByComplexIdAndEmail(String complexId, String email) {
        return repository.existsByComplexIdAndEmail(complexId, email);
    }

    @Override
    public boolean existsByComplexIdAndPhoneNumber(String complexId, String phoneNumber) {
        return repository.existsByComplexIdAndPhoneNumber(complexId, phoneNumber);
    }

    @Override
    public boolean existsByComplexIdAndCccd(String complexId, String cccd) {
        return repository.existsByComplexIdAndCccd(complexId, cccd);
    }

    private ResidentDataMapper mapToMapper(Resident resident) {
        return ResidentDataMapper.builder()
                .id(resident.getId())
                .complexId(resident.getComplexId())
                .fullname(resident.getFullname())
                .gender(resident.getGender())
                .email(resident.getEmail())
                .birthday(resident.getBirthday())
                .relationship(resident.getRelationship())
                .phoneNumber(resident.getPhoneNumber())
                .cccd(resident.getCccd())
                .status(resident.getStatus())
                .createdAt(resident.getCreatedAt())
                .updatedAt(resident.getUpdatedAt())
                .deletedAt(resident.getDeletedAt())
                .build();
    }


    private Resident mapToEntity(ResidentDataMapper mapper) {
        return new Resident(
                mapper.getId(),
                mapper.getComplexId(),
                mapper.getFullname(),
                mapper.getGender(),
                mapper.getEmail(),
                mapper.getBirthday(),
                mapper.getRelationship(),
                mapper.getPhoneNumber(),
                mapper.getCccd(),
                mapper.getStatus(),
                mapper.getCreatedAt(),
                mapper.getUpdatedAt(),
                mapper.getDeletedAt()
        );
    }
}
