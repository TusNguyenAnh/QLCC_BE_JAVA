package com.mbs.qlcc.service.impl;

import com.mbs.qlcc.domain.Priority;
import com.mbs.qlcc.dto.response.PageResponse;
import com.mbs.qlcc.dto.response.Priority.PriorityResponse;
import com.mbs.qlcc.mapper.Priority.PriorityMapper;
import com.mbs.qlcc.repository.Priority.IPriorityRepository;
import com.mbs.qlcc.service.IPriorityService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PriorityServiceImpl implements IPriorityService {
    IPriorityRepository priorityRepository;

    @Override
    public PageResponse<PriorityResponse> show(int page, int perPage) {
        Pageable pageable = PageRequest.of(page, perPage, Sort.Direction.ASC, "createdAt");
        Page<Priority> result = priorityRepository
                .findAllByDeletedAtIsNull(pageable);

        return new PageResponse<>(
                result.getContent().stream().map(PriorityMapper::toResponse).toList(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );
    }
}
