package com.mbs.qlcc.adapters.db.Resident;

import com.mbs.qlcc.adapters.db.Apartment.ApartmentDataMapper;
import com.mbs.qlcc.adapters.db.Apartment.JpaApartmentRepository;
import com.mbs.qlcc.adapters.db.Building.BuildingDataMapper;
import com.mbs.qlcc.adapters.response.IResUser;
import com.mbs.qlcc.entities.Resident.AptResident;
import com.mbs.qlcc.usecases.exception.AppException;
import com.mbs.qlcc.usecases.output.Resident.IAptResidentDsGateway;
import com.mbs.qlcc.utils.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaAptResident implements IAptResidentDsGateway {

    private final JpaAptResidentRepository repository;
    private final JpaApartmentRepository apartmentRepository;
    private final JpaResidentRepository residentRepository;


    @Override
    public AptResident save(AptResident aptResident) {
        ApartmentDataMapper apartmentDataMapper = apartmentRepository.findById(aptResident.getAptId())
                .orElseThrow(() -> new AppException(ErrorCode.APARTMENT_NOT_FOUND));

        ResidentDataMapper residentDataMapper = residentRepository.findById(aptResident.getResidentId())
                .orElseThrow(() -> new AppException(ErrorCode.RESIDENT_NOT_FOUND)
                );

        AptResidentDataMapper mapper = AptResidentDataMapper.builder()
                .apartmentDataMapper(apartmentDataMapper)
                .residentDataMapper(residentDataMapper)
                .status(aptResident.getStatus())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        AptResidentDataMapper saved = repository.save(mapper);
        return mapToEntity(saved);
    }

    @Override
    public List<AptResident> saveAll(List<AptResident> aptResidents) {
        List<AptResidentDataMapper> mappers = aptResidents.stream()
                .map(aptRes -> {
                    ApartmentDataMapper apartmentDataMapper = new ApartmentDataMapper();
                    apartmentDataMapper.setId(aptRes.getAptId());

                    ResidentDataMapper residentDataMapper = new ResidentDataMapper();
                    residentDataMapper.setId(aptRes.getResidentId());

                    return AptResidentDataMapper.builder()
                            .apartmentDataMapper(apartmentDataMapper)
                            .residentDataMapper(residentDataMapper)
                            .build();
                })
                .toList();

        List<AptResidentDataMapper> saved = repository.saveAll(mappers);
        return saved.stream()
                .map(this::mapToEntity)
                .toList();
    }

    @Override
    public List<IResUser> findResidentsInBuildingNotInOrg(List<String> buildingId, String orgId) {
        return repository.findResidentsInBuildingNotInOrg(buildingId, orgId);
    }

    private AptResident mapToEntity(AptResidentDataMapper mapper) {
        return new AptResident(
                mapper.getId(),
                mapper.getApartmentDataMapper().getId(),
                mapper.getResidentDataMapper().getId(),
                mapper.getStatus(),
                mapper.getCreatedAt(),
                mapper.getUpdatedAt(),
                mapper.getDeletedAt()
        );
    }
}
