package com.mbs.qlcc.usecases.interactor;

import com.mbs.qlcc.entities.Apartment.Apartment;
import com.mbs.qlcc.entities.Organization.OrgUser;
import com.mbs.qlcc.entities.Resident.AptResident;
import com.mbs.qlcc.entities.Resident.IAptResidentFactory;
import com.mbs.qlcc.entities.Resident.IResidentFactory;
import com.mbs.qlcc.entities.Resident.Resident;
import com.mbs.qlcc.usecases.exception.AppException;
import com.mbs.qlcc.usecases.input.IResidentInputBoundary;
import com.mbs.qlcc.usecases.output.Apartment.IApartmentDsGateway;
import com.mbs.qlcc.usecases.output.Building.IBuildingDsGateway;
import com.mbs.qlcc.usecases.output.Organization.IOrgUserDsGateway;
import com.mbs.qlcc.usecases.output.Resident.IAptResidentDsGateway;
import com.mbs.qlcc.usecases.output.Resident.IResidentDsGateway;
import com.mbs.qlcc.usecases.request.Resident.*;
import com.mbs.qlcc.usecases.response.Organization.OrgUserResponse;
import com.mbs.qlcc.usecases.response.PageResponse;
import com.mbs.qlcc.usecases.response.Resident.ResAptBdResponse;
import com.mbs.qlcc.usecases.response.Resident.ResUserResponse;
import com.mbs.qlcc.usecases.response.Resident.ResidentResponse;
import com.mbs.qlcc.utils.ErrorCode;

import java.util.*;
import java.util.stream.Collectors;


public class ResidentInteractor implements IResidentInputBoundary {

    private final IResidentDsGateway residentGateway;
    private final IAptResidentDsGateway aptResidentGateway;
    private final IBuildingDsGateway buildingGateway;
    private final IApartmentDsGateway apartmentDsGateway;
    private final IOrgUserDsGateway orgUserDsGateway;
    private final IResidentFactory residentFactory;
    private final IAptResidentFactory aptResidentFactory;

    public ResidentInteractor(IResidentDsGateway residentGateway, IAptResidentDsGateway aptResidentGateway,
                              IBuildingDsGateway buildingGateway, IApartmentDsGateway apartmentDsGateway,
                              IOrgUserDsGateway orgUserDsGateway, IResidentFactory residentFactory,
                              IAptResidentFactory aptResidentFactory) {
        this.residentGateway = residentGateway;
        this.aptResidentGateway = aptResidentGateway;
        this.buildingGateway = buildingGateway;
        this.apartmentDsGateway = apartmentDsGateway;
        this.orgUserDsGateway = orgUserDsGateway;
        this.residentFactory = residentFactory;
        this.aptResidentFactory = aptResidentFactory;
    }

    @Override
    public ResidentResponse createResident(CreateResidentInpRequest request) {
        boolean emailExists = residentGateway.existsByComplexIdAndEmail(request.getComplexId(), request.getEmail());
        boolean phoneExists = residentGateway.existsByComplexIdAndPhoneNumber(request.getComplexId(), request.getPhoneNumber());
        boolean cccdExists = residentGateway.existsByComplexIdAndCccd(request.getComplexId(), request.getCccd());

        if (emailExists) {
            throw new AppException(ErrorCode.RESIDENT_EMAIL_EXISTED);
        }

        if (phoneExists) {
            throw new AppException(ErrorCode.RESIDENT_PHONE_EXISTED);
        }

        if (cccdExists) {
            throw new AppException(ErrorCode.RESIDENT_CCCD_EXISTED);
        }

        Resident resident = residentFactory.create(
                request.getComplexId(),
                request.getFullname(),
                request.getGender(),
                request.getEmail(),
                request.getBirthday(),
                request.getRelationship(),
                request.getPhoneNumber(),
                request.getCccd()
        );

        Resident saved = residentGateway.save(resident);

        return mapToResponse(saved);
    }

    @Override
    public ResidentResponse findById(String id) {
        Optional<Resident> resident = residentGateway.findById(id);
        if (resident.isEmpty()) {
            throw new AppException(ErrorCode.RESIDENT_NOT_FOUND);
        }

        return mapToResponse(resident.get());
    }

    @Override
    public PageResponse<ResAptBdResponse> filterResident(String complexId, FilterResidentInpRequest request) {
        var result = residentGateway.findAll(complexId, request);

        // Map residents to responses
        List<ResAptBdResponse> responses = result.getData().stream()
                .map(p -> new ResAptBdResponse(
                        p.getId(),
                        p.getComplexId(),
                        p.getFullname(),
                        p.getGender(),
                        p.getEmail(),
                        p.getBirthday(),
                        p.getRelationship(),
                        p.getPhoneNumber(),
                        p.getCccd(),
                        p.getBuildingId(),
                        p.getFloor(),
                        p.getAptNumber(),
                        p.getStatus()
                )).toList();

        return new PageResponse<>(
                responses,
                result.getPage(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );
    }

    @Override
    public List<ResUserResponse> findByOrgId(String orgId) {
        return residentGateway.findResUserByOrgId(orgId);
    }

    @Override
    public List<ResUserResponse> findByBuildingId(List<String> buildingId, String complexId) {
        var residents = aptResidentGateway.findResidentsInBuildingNotInOrg(buildingId, complexId);
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


    // tiep tuc
    @Override
    public List<String> importResidents(List<ImportResidentInpRequest> residents, String complexId) {
        // Collect all emails, phones, cccds for batch duplicate check
        Set<String> emails = new HashSet<>();
        Set<String> phones = new HashSet<>();
        Set<String> cccds = new HashSet<>();

        for (ImportResidentInpRequest resident : residents) {
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

        // Check for duplicates in database
        List<String> existingEmails = residentGateway.findEmailsByComplexId(complexId, emails.stream().toList());
        List<String> existingPhones = residentGateway.findPhoneNumbersByComplexId(complexId, phones.stream().toList());
        List<String> existingCccds = residentGateway.findCccdsByComplexId(complexId, cccds.stream().toList()).stream().map(Resident::getCccd).toList();

        List<String> errorsList = new ArrayList<>();
        if (!existingEmails.isEmpty() || !existingPhones.isEmpty() || !existingCccds.isEmpty()) {
            int rowNum = 4;
            StringBuilder errorBuilder = new StringBuilder();
            for (ImportResidentInpRequest resident : residents) {
                boolean hasError = false;
                rowNum++;
                errorBuilder.append("Dòng ").append(rowNum).append(": ");
                // Check duplicate email
                if (existingEmails.contains(resident.getEmail())) {
                    errorBuilder.append("Email đã tồn tại; ");
                    hasError = true;
                }

                // Check duplicate phone
                if (existingPhones.contains(resident.getPhoneNumber())) {
                    errorBuilder.append("Số điện thoại đã tồn tại; ");
                    hasError = true;
                }

                // Check duplicate cccd
                if (existingCccds.contains(resident.getCccd())) {
                    errorBuilder.append("CCCD đã tồn tại; ");
                    hasError = true;
                }

                if (hasError) {
                    errorsList.add(errorBuilder.toString());
                }

            }
            return errorsList;
        }

        List<Resident> residentSave = residents.stream()
                .map(r -> residentFactory.create(
                        complexId,
                        r.getFullname(),
                        r.getGender(),
                        r.getEmail(),
                        r.getBirthday(),
                        r.getRelationship(),
                        r.getPhoneNumber(),
                        r.getCccd()
                )).toList();

        residentGateway.saveAll(residentSave);

        return errorsList;
    }


    @Override
    public List<String> importAptResidents(List<ImportAptResidentInpRequest> request, String complexId) {
        Set<String> cccds = new HashSet<>();
        Set<String> buildingName = new HashSet<>();
        for (ImportAptResidentInpRequest data : request) {
            if (data.getCccd() != null && !data.getCccd().isEmpty()) {
                cccds.add(data.getCccd());
            }
            if (data.getBuildingName() != null && !data.getBuildingName().isEmpty()) {
                buildingName.add(data.getBuildingName());
            }
        }

        // kiem tra building va cccd co ton tai hay khong
        Map<String, String> existingBuilding = buildingGateway.findByBuildingName(buildingName, complexId);
        Map<String, String> existingCccds = residentGateway.findCccdsByComplexId(complexId, cccds.stream().toList())
                .stream()
                .collect(Collectors.toMap(
                        Resident::getCccd,
                        Resident::getId
                ));

        boolean isMissingBuilding = buildingName.size() != existingBuilding.size();
        boolean isMissingCccd = cccds.size() != existingCccds.size();

        List<String> errorsList = new ArrayList<>();

        if (isMissingBuilding || isMissingCccd) {
            int rowNum = 4;
            StringBuilder errorBuilder = new StringBuilder();
            for (ImportAptResidentInpRequest aptres : request) {
                boolean hasError = false;
                rowNum++;
                errorBuilder.append("Dòng ").append(rowNum).append(": ");
                // Check duplicate email
                if (!existingBuilding.containsKey(aptres.getBuildingName())) {
                    errorBuilder.append("Tòa nhà không tồn tại; ");
                    hasError = true;
                }

                // Check duplicate cccd
                if (!existingCccds.containsKey(aptres.getCccd())) {
                    errorBuilder.append("CCCD không tồn tại; ");
                    hasError = true;
                }

                if (hasError) {
                    errorsList.add(errorBuilder.toString());
                }

            }
            return errorsList;
        }

        //check can ho co thuoc toa nha k
        List<AptResident> aptResidentsSave = new ArrayList<>();
        List<String> buildingId = existingBuilding.values().stream().toList();
        List<Apartment> apartments = apartmentDsGateway.findByBuildingIdIn(buildingId);
        Map<String, List<String>> aptMapBuilding = apartments.stream()
                .collect(Collectors.groupingBy(
                        Apartment::getBuildingId,
                        Collectors.mapping(Apartment::getAptNumber, Collectors.toList())
                ));

        Map<String, String> aptMapId = apartments.stream()
                .collect(Collectors.toMap(
                        Apartment::getAptNumber,
                        Apartment::getId
                ));

        if (!aptMapBuilding.isEmpty()) {
            int rowNum = 4;
            StringBuilder errorBuilder = new StringBuilder();
            for (ImportAptResidentInpRequest aptres : request) {
                rowNum++;
                errorBuilder.append("Dòng ").append(rowNum).append(": ");
                String buildingIdForRow = existingBuilding.get(aptres.getBuildingName());
                if (!aptMapBuilding.get(buildingIdForRow).contains(aptres.getApartmentNumber())) {
                    errorBuilder.append("Căn hộ ");
                    errorBuilder.append(aptres.getApartmentNumber());
                    errorBuilder.append(" không tồn tại trong tòa nhà ");
                    errorBuilder.append(aptres.getBuildingName());
                    errorsList.add(errorBuilder.toString());
                    continue;
                }
                aptResidentsSave.add(aptResidentFactory.create(
                        aptMapId.get(aptres.getApartmentNumber()),
                        existingCccds.get(aptres.getCccd())
                ));
            }

            if (!errorsList.isEmpty())
                return errorsList;
            aptResidentGateway.saveAll(aptResidentsSave);
        }

        return errorsList;
    }

    @Override
    public String addResidentsToOrg(UpdateResidentInOrgInpRequest request) {
        if (request.getUserIds() == null || request.getUserIds().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }

        List<OrgUser> orgUsers = request.getUserIds().stream()
                .map(userId -> new OrgUser(userId, request.getOrgId(), ""))
                .toList();

        return orgUserDsGateway.saveOrgUser(orgUsers);
    }

    @Override
    public void removeResidentsFromOrg(UpdateResidentInOrgInpRequest request) {
        if (request.getUserIds() == null || request.getUserIds().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }

        orgUserDsGateway.deleteOrgUser(request.getUserIds(), request.getOrgId());
    }

    @Override
    public OrgUserResponse updatePositionInOrg(String userId, String orgId, String roleId) {
        OrgUser orgUser = new OrgUser(userId, orgId, roleId);
        return mapToOrgUserResponse(orgUserDsGateway.updateOrgUser(orgUser));
    }

    private ResidentResponse mapToResponse(Resident resident) {
        return new ResidentResponse(
                resident.getId(),
                resident.getComplexId(),
                resident.getFullname(),
                resident.getGender(),
                resident.getEmail(),
                resident.getBirthday(),
                resident.getRelationship(),
                resident.getPhoneNumber(),
                resident.getCccd(),
                resident.getStatus(),
                resident.getCreatedAt(),
                resident.getUpdatedAt()
        );
    }

    private OrgUserResponse mapToOrgUserResponse(OrgUser orgUser) {
        return new OrgUserResponse(
                orgUser.getId(),
                orgUser.getUser_id(),
                orgUser.getOrg_id(),
                orgUser.getRole_id()
        );
    }
}
