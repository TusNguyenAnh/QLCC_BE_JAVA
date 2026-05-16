package com.mbs.qlcc.adapters.db.Complex;

import com.mbs.qlcc.adapters.db.Specification.ComplexSpecification;
import com.mbs.qlcc.entities.Complex.Complex;
import com.mbs.qlcc.usecases.output.Complex.IComplexDsGateway;
import com.mbs.qlcc.usecases.request.Complex.FilterComplexInpRequest;
import com.mbs.qlcc.usecases.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * JPA Implementation of Complex Data Store Gateway
 */
@Component
@RequiredArgsConstructor
public class JpaComplex implements IComplexDsGateway {

    private final JpaComplexRepository repository;

    @Override
    public boolean existsByComplexName(String complexName) {
        return repository.existsByComplexName(complexName);
    }

    @Override
    public boolean existsByAddress(String address) {
        return repository.existsByAddress(address);
    }

    @Override
    public boolean existsByPhoneContact(String phoneContact) {
        return repository.existsByPhoneContact(phoneContact);
    }

    @Override
    public Complex save(Complex complex) {
        ComplexDataMapper mapper = ComplexDataMapper.builder()
                .id(complex.getId())
                .complexName(complex.getComplexName())
                .address(complex.getAddress())
                .totalBuilding(complex.getTotalBuilding())
                .totalApartment(complex.getTotalApartment())
                .nameContact(complex.getNameContact())
                .phoneContact(complex.getPhoneContact())
                .emailContact(complex.getEmailContact())
                .description(complex.getDescription())
                .createdAt(complex.getCreatedAt())
                .updatedAt(complex.getUpdatedAt())
                .deletedAt(complex.getDeletedAt())
                .build();

        ComplexDataMapper saved = repository.save(mapper);
        return mapToEntity(saved);
    }

    @Override
    public Optional<Complex> findById(String id) {
        return repository.findById(id).map(this::mapToEntity);
    }

    @Override
    public Optional<Complex> findByComplexName(String complexName) {
        return repository.findByComplexName(complexName).map(this::mapToEntity);
    }

    @Override
    public Optional<Complex> findByAddress(String address) {
        return repository.findByAddress(address).map(this::mapToEntity);
    }

    @Override
    public Optional<Complex> findByPhoneContact(String phoneContact) {
        return repository.findByPhoneContact(phoneContact).map(this::mapToEntity);
    }

    @Override
    public PageResponse<Complex> findByStatus(int status, FilterComplexInpRequest filterComplexInpRequest) {
        Sort.Direction direction = filterComplexInpRequest.getOrder() != null && filterComplexInpRequest.getOrder().equalsIgnoreCase("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(
                filterComplexInpRequest.getPageNumber() - 1,
                filterComplexInpRequest.getPageSize(),
                direction,
                "createdAt"
        );

        Specification<ComplexDataMapper> spec =
                ComplexSpecification.filter(
                        status,
                        filterComplexInpRequest.getKeyword(),
                        filterComplexInpRequest.getTimeRequestStart(),
                        filterComplexInpRequest.getTimeRequestEnd()
                );

        Page<ComplexDataMapper> page = repository.findAll(spec, pageable);


        return new PageResponse<Complex>(
                page.getContent().stream().map(this::mapToEntity).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    @Override
    public Complex updateStatusToApproved(String id) {
        Optional<ComplexDataMapper> complexOpt = repository.findById(id);
        if (complexOpt.isPresent()) {
            ComplexDataMapper complex = complexOpt.get();
            complex.setStatus(1);  // Approved
            complex.setUpdatedAt(LocalDateTime.now());
            ComplexDataMapper updated = repository.save(complex);
            return mapToEntity(updated);
        }
        return null;
    }

    @Override
    public void updateStatusToRejected(String id) {
        Optional<ComplexDataMapper> complexOpt = repository.findById(id);
        if (complexOpt.isPresent()) {
            ComplexDataMapper complex = complexOpt.get();
            complex.setStatus(2);  // Rejected
            complex.setUpdatedAt(LocalDateTime.now());
            repository.save(complex);
        }
    }

    @Override
    public List<Complex> updateMultipleStatusToApproved(List<String> ids) {
        List<ComplexDataMapper> complexes = repository.findAllByStatusAndIdIn(0, ids);
        complexes.forEach(c -> {
            c.setStatus(1);
            c.setUpdatedAt(LocalDateTime.now());
        });
        List<ComplexDataMapper> updated = repository.saveAll(complexes);
        return updated.stream().map(this::mapToEntity).toList();
    }

    @Override
    public void updateMultipleStatusToRejected(List<String> ids) {
        List<ComplexDataMapper> complexes = repository.findAllByStatusAndIdIn(0, ids);
        complexes.forEach(c -> {
            c.setStatus(2);
            c.setUpdatedAt(LocalDateTime.now());
            c.setDeletedAt(LocalDateTime.now());
        });
        repository.saveAll(complexes);
    }

    /**
     * Map ComplexDataMapper to Complex entity
     */
    private Complex mapToEntity(ComplexDataMapper mapper) {
        return new Complex(
                mapper.getId(),
                mapper.getComplexName(),
                mapper.getAddress(),
                mapper.getTotalBuilding(),
                mapper.getTotalApartment(),
                mapper.getNameContact(),
                mapper.getPhoneContact(),
                mapper.getEmailContact(),
                mapper.getDescription(),
                mapper.getFinancialModel(),
                mapper.getStatus(),
                mapper.getCreatedAt(),
                mapper.getUpdatedAt(),
                mapper.getDeletedAt()
        );
    }
}

