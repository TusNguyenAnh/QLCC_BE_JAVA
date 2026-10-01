package com.mbs.qlcc.service.impl;

import com.mbs.qlcc.domain.OrgUser;
import com.mbs.qlcc.domain.Resident;
import com.mbs.qlcc.dto.request.Resident.CreateResidentRequest;
import com.mbs.qlcc.dto.request.Resident.FilterResidentRequest;
import com.mbs.qlcc.dto.request.Resident.UpdatePositionRequest;
import com.mbs.qlcc.dto.request.Resident.UpdateResInOrgRequest;
import com.mbs.qlcc.dto.response.Organization.OrgUserResponse;
import com.mbs.qlcc.dto.response.Resident.ResAptBdResponse;
import com.mbs.qlcc.dto.response.Resident.ResUserResponse;
import com.mbs.qlcc.dto.response.Resident.ResidentResponse;
import com.mbs.qlcc.exception.AppException;
import com.mbs.qlcc.repository.Organization.IOrgUserRepository;
import com.mbs.qlcc.repository.Resident.IAptResidentRepository;
import com.mbs.qlcc.repository.Resident.IResidentRepository;
import com.mbs.qlcc.service.IResidentService;
import com.mbs.qlcc.utils.ErrorCode;
import com.mbs.qlcc.mapper.Resident.ResidentMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.mbs.qlcc.domain.Apartment;
import com.mbs.qlcc.domain.AptResident;
import com.mbs.qlcc.domain.Building;
import com.mbs.qlcc.dto.request.Resident.ImportAptResidentRequest;
import com.mbs.qlcc.repository.Apartment.IApartmentRepository;
import com.mbs.qlcc.repository.Building.IBuildingRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ResidentServiceImpl implements IResidentService {
    IResidentRepository residentRepository;
    IAptResidentRepository aptResidentRepository;
    IOrgUserRepository orgUserRepository;
    IBuildingRepository buildingRepository;
    IApartmentRepository apartmentRepository;

    @Override
    @Transactional
    public ResidentResponse createResident(String complexId, CreateResidentRequest request) {
        boolean emailExists = residentRepository.existsByComplexIdAndEmail(complexId, request.getEmail());
        boolean phoneExists = residentRepository.existsByComplexIdAndPhoneNumber(complexId, request.getPhoneNumber());
        boolean cccdExists = residentRepository.existsByComplexIdAndCccd(complexId, request.getCccd());

        if (emailExists) {
            throw new AppException(ErrorCode.RESIDENT_EMAIL_EXISTED);
        }

        if (phoneExists) {
            throw new AppException(ErrorCode.RESIDENT_PHONE_EXISTED);
        }

        if (cccdExists) {
            throw new AppException(ErrorCode.RESIDENT_CCCD_EXISTED);
        }

        Resident resident = ResidentMapper.toEntity(request, complexId);

        Resident saved = residentRepository.save(resident);

        return ResidentMapper.toResponse(saved);
    }

    @Override
    public ResidentResponse findById(String id) {
        Resident resident = residentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.RESIDENT_NOT_FOUND));

        return ResidentMapper.toResponse(resident);
    }

    @Override
    public List<ResAptBdResponse> filterResident(String complexId, FilterResidentRequest request) {
        String aptNumber = request.getAptNumber() != null && !request.getAptNumber().isEmpty() ? request.getAptNumber() : null;
        String buildingId = request.getBuildingId() != null && !request.getBuildingId().isEmpty() ? request.getBuildingId() : null;

        var result = residentRepository.filter(complexId, buildingId, request.getFloor(), aptNumber, request.getRelationship());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        // Map residents to responses
        return result.stream()
                .map(p -> new ResAptBdResponse(
                        p.getId(),
                        p.getComplexId(),
                        p.getFullname(),
                        p.getGender(),
                        p.getEmail(),
                        p.getBirthday().format(formatter),
                        p.getRelationship(),
                        p.getPhoneNumber(),
                        p.getCccd(),
                        p.getBuildingId(),
                        p.getFloor(),
                        p.getAptNumber(),
                        p.getStatus()
                )).toList();
    }

    @Override
    public List<ResUserResponse> findByBuildingId(List<String> buildingIds, String orgId) {
        var residents = aptResidentRepository.findResidentsInBuildingNotInOrg(buildingIds, orgId);
        return residents.stream()
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
    public List<ResUserResponse> findByOrgId(String orgId) {
        var result = residentRepository.findResUserByOrgId(orgId);
        return result.stream()
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
    @Transactional
    public String addResidentsToOrg(String orgId, UpdateResInOrgRequest request) {
        if (request.getUserIds() == null || request.getUserIds().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }

        List<OrgUser> orgUsers = request.getUserIds().stream()
                .map(userId -> OrgUser.builder()
                        .userId(userId)
                        .orgId(orgId)
                        .roleId("") // Role default or logic needs to be added
                        .build())
                .toList();

        orgUserRepository.saveAll(orgUsers);
        return "Success";
    }

    @Override
    @Transactional
    public void removeResidentsFromOrg(String orgId, UpdateResInOrgRequest request) {
        if (request.getUserIds() == null || request.getUserIds().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }

        List<String> ids = orgUserRepository.findByOrgIdAndUserIdIn(orgId, request.getUserIds()).stream()
                .map(OrgUser::getId)
                .toList();

        orgUserRepository.deleteAllById(ids);
    }

    @Override
    @Transactional
    public OrgUserResponse updatePosition(UpdatePositionRequest request) {
        OrgUser orgUser = orgUserRepository.findByUserIdAndOrgId(request.getUserId(), request.getOrgId());
        if (orgUser == null) {
            throw new AppException(ErrorCode.NOT_FOUND);
        }

        orgUser.setRoleId(request.getRoleId());
        orgUser = orgUserRepository.save(orgUser);

        return new OrgUserResponse(
                orgUser.getId(),
                orgUser.getUserId(),
                orgUser.getOrgId(),
                orgUser.getRoleId()
        );
    }

    @Override
    @Transactional
    public String importResidents(List<CreateResidentRequest> residents, String complexId) {
        Set<String> emails = new HashSet<>();
        Set<String> phones = new HashSet<>();
        Set<String> cccds = new HashSet<>();

        for (CreateResidentRequest resident : residents) {
            if (resident.getEmail() != null && !resident.getEmail().isEmpty()) {
                emails.add(resident.getEmail());
            }
            if (resident.getPhoneNumber() != null && !resident.getPhoneNumber().isEmpty()) {
                phones.add(resident.getPhoneNumber());
            }
            if (resident.getCccd() != null && !resident.getCccd().isEmpty()) {
                cccds.add(resident.getCccd());
            }
        }

        List<String> existingEmails = emails.isEmpty() ? List.of() : residentRepository.findEmailsByComplexId(complexId, emails);
        List<String> existingPhones = phones.isEmpty() ? List.of() : residentRepository.findPhoneNumbersByComplexId(complexId, phones);
        List<String> existingCccds = cccds.isEmpty() ? List.of() : residentRepository.findByComplexIdAndCccdIn(complexId, cccds).stream().map(Resident::getCccd).toList();

        StringBuilder errorBuilder = new StringBuilder();
        if (!existingEmails.isEmpty() || !existingPhones.isEmpty() || !existingCccds.isEmpty()) {
            int rowNum = 4;
            for (CreateResidentRequest resident : residents) {
                rowNum++;
                StringBuilder rowError = new StringBuilder();
                if (existingEmails.contains(resident.getEmail())) {
                    rowError.append("Email đã tồn tại; ");
                }
                if (existingPhones.contains(resident.getPhoneNumber())) {
                    rowError.append("Số điện thoại đã tồn tại; ");
                }
                if (existingCccds.contains(resident.getCccd())) {
                    rowError.append("CCCD đã tồn tại; ");
                }
                if (!rowError.isEmpty()) {
                    errorBuilder.append("Dòng ").append(rowNum).append(": ").append(rowError);
                }
            }
            return errorBuilder.toString();
        }

        List<Resident> residentSave = residents.stream()
                .map(r -> Resident.builder()
                        .complexId(complexId)
                        .fullname(r.getFullname())
                        .gender(r.getGender())
                        .email(r.getEmail())
                        .birthday(r.getBirthday())
                        .relationship(r.getRelationship())
                        .phoneNumber(r.getPhoneNumber())
                        .cccd(r.getCccd())
                        .status(0)
                        .build())
                .toList();

        residentRepository.saveAll(residentSave);
        return "";
    }

    @Override
    @Transactional
    public String importAptResidents(List<ImportAptResidentRequest> request, String complexId) {
        Set<String> cccds = new HashSet<>();
        Set<String> buildingNames = new HashSet<>();
        for (ImportAptResidentRequest data : request) {
            if (data.getCccd() != null && !data.getCccd().isEmpty()) {
                cccds.add(data.getCccd());
            }
            if (data.getBuildingName() != null && !data.getBuildingName().isEmpty()) {
                buildingNames.add(data.getBuildingName());
            }
        }

        List<Building> buildings = buildingNames.isEmpty() ? List.of() : buildingRepository.findAllByBuildingNameInAndComplexIdAndDeletedAtIsNull(buildingNames, complexId);
        Map<String, String> existingBuilding = buildings.stream()
                .collect(Collectors.toMap(Building::getBuildingName, Building::getId));

        Map<String, String> existingCccds = cccds.isEmpty() ? Map.of() : residentRepository.findByComplexIdAndCccdIn(complexId, cccds)
                .stream()
                .collect(Collectors.toMap(Resident::getCccd, Resident::getId));

        boolean isMissingBuilding = buildingNames.size() != existingBuilding.size();
        boolean isMissingCccd = cccds.size() != existingCccds.size();

        if (isMissingBuilding || isMissingCccd) {
            int rowNum = 4;
            StringBuilder errorBuilder = new StringBuilder();
            for (ImportAptResidentRequest aptres : request) {
                rowNum++;
                StringBuilder rowError = new StringBuilder();
                if (!existingBuilding.containsKey(aptres.getBuildingName())) {
                    rowError.append("Tòa nhà không tồn tại; ");
                }
                if (!existingCccds.containsKey(aptres.getCccd())) {
                    rowError.append("CCCD không tồn tại; ");
                }
                if (!rowError.isEmpty()) {
                    errorBuilder.append("Dòng ").append(rowNum).append(": ").append(rowError);
                }
            }
            return errorBuilder.toString();
        }

        //check can ho co thuoc toa nha k
        List<String> buildingIds = existingBuilding.values().stream().toList();
        List<Apartment> apartments = buildingIds.isEmpty() ? List.of() : apartmentRepository.findByBuilding_IdInAndDeletedAtIsNull(buildingIds);
        Map<String, List<String>> aptMapBuilding = apartments.stream()
                .collect(Collectors.groupingBy(
                        apt -> apt.getBuilding() != null ? apt.getBuilding().getId() : "",
                        Collectors.mapping(Apartment::getAptNumber, Collectors.toList())
                ));

        Map<String, String> aptMapId = apartments.stream()
                .collect(Collectors.toMap(
                        Apartment::getAptNumber,
                        Apartment::getId,
                        (existing, replacement) -> existing
                ));

        List<AptResident> aptResidentsSave = new ArrayList<>();
        int rowNum = 4;
        StringBuilder errorBuilder = new StringBuilder();
        for (ImportAptResidentRequest aptres : request) {
            rowNum++;
            String buildingIdForRow = existingBuilding.get(aptres.getBuildingName());
            List<String> aptsInBuilding = aptMapBuilding.get(buildingIdForRow);
            if (aptsInBuilding == null || !aptsInBuilding.contains(aptres.getApartmentNumber())) {
                errorBuilder.append("Dòng ").append(rowNum).append(": ");
                errorBuilder.append("Căn hộ ").append(aptres.getApartmentNumber());
                errorBuilder.append(" không tồn tại trong tòa nhà ").append(aptres.getBuildingName()).append("; ");
                continue;
            }
            aptResidentsSave.add(AptResident.builder()
                    .apartment(Apartment.builder().id(aptMapId.get(aptres.getApartmentNumber())).build())
                    .resident(Resident.builder().id(existingCccds.get(aptres.getCccd())).build())
                    .status(0)
                    .build());
        }

        if (!errorBuilder.isEmpty()) {
            return errorBuilder.toString();
        }

        aptResidentRepository.saveAll(aptResidentsSave);
        return "";
    }
}
