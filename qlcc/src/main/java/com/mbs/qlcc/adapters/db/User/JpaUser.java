package com.mbs.qlcc.adapters.db.User;

import com.mbs.qlcc.entities.User.User;
import com.mbs.qlcc.usecases.output.User.IUserDsGateway;
import com.mbs.qlcc.usecases.request.User.UserFilterInpRequest;
import com.mbs.qlcc.usecases.request.User.UserInpRequest;
import com.mbs.qlcc.usecases.response.User.IResUserResponse;
import com.mbs.qlcc.usecases.response.User.IStaffUserResponse;
import com.mbs.qlcc.utils.StringHelper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class JpaUser implements IUserDsGateway {
    final JpaUserRepository repository;
    final PasswordEncoder encoder;

    @Override
    public User findById(String id) {
        Optional<UserDataMapper> entity = repository.findById(id);
        return entity.map(userDataMapper -> new User(
                userDataMapper.getId(),
                userDataMapper.getUsername(),
                userDataMapper.getPasswordHash(),
                userDataMapper.getResId(),
                userDataMapper.getStaffId(),
                userDataMapper.isDeleted(),
                userDataMapper.getComplexId()
        )).orElse(null);
    }

    @Override
    public User findByUsernameAndComplexId(String username, String complexId) {
        var entity = repository.findByUsernameAndComplexId(username, complexId);
        if (entity == null) return null;

        return new User(
                entity.getId(),
                entity.getUsername(),
                entity.getPasswordHash(),
                entity.getResId(),
                entity.getStaffId(),
                entity.isDeleted(),
                entity.getComplexId()
        );
    }

    @Override
    public List<User> storeList(List<UserInpRequest> userInpRequests) {
        List<UserDataMapper> userDataMapper = userInpRequests.stream()
                .map(req -> UserDataMapper.builder()
                        .username(req.getPhoneNumber())
                        .passwordHash(encoder.encode(req.getPassword()))
                        .complexId(req.getComplex_id())
                        .resId(req.getResId())
                        .staffId(req.getStaffId())
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build()
                )
                .toList();

        return repository.saveAll(userDataMapper).stream().map(JpaUser::mapToEntity).toList();
    }

    @Override
    public User store(UserInpRequest userInpRequests) {
        UserDataMapper userDataMapper = UserDataMapper.builder()
                .username(userInpRequests.getPhoneNumber())
                .passwordHash(encoder.encode(userInpRequests.getPassword()))
                .complexId(userInpRequests.getComplex_id())
                .resId(userInpRequests.getResId())
                .staffId(userInpRequests.getStaffId())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return mapToEntity(repository.save(userDataMapper));
    }

    @Override
    public String generatePassword() {
        return StringHelper.generate(6);
    }

    @Override
    public List<String> getBuildingIdsManage(String userId) {
        return repository.findBuildingIdsManage(userId);
    }

    @Override
    public List<IStaffUserResponse> findStaffByOrgId(String orgId) {
        return repository.findStaffByOrgId(orgId);
    }

    @Override
    public List<IResUserResponse> findResByOrgId(String orgId) {
        return repository.findResByOrgId(orgId);
    }

    @Override
    public Map<String, String> getUserIdByUsername(Set<String> usernames, String complexId) {
        List<UserDataMapper> userDataMappers = repository.getUserIdByUsername(usernames, complexId);
        return userDataMappers.stream().collect(Collectors.toMap(UserDataMapper::getUsername, UserDataMapper::getId));
    }

    @Override
    public List<IResUserResponse> filterUser(UserFilterInpRequest request, String complexId) {
        Map<String, Object> params = new HashMap<>();
        if (request.getBuildingId() != null && !request.getBuildingId().isEmpty()) {
            params.put("buildingId", request.getBuildingId());
        }

        if (request.getFloor() > 0) {
            params.put("floor", request.getFloor());
        }

        if (request.getAptNumber() != null && !request.getAptNumber().isEmpty()) {
            params.put("aptNumber", request.getAptNumber());
        }

        if (request.getRelationship() != null) {
            params.put("relationship", request.getRelationship());
        }

        return repository.filterUser(complexId, params.get("buildingId"), params.get("floor"), params.get("aptNumber"), params.get("relationship"));
    }

    public static User mapToEntity(UserDataMapper mapper) {
        return new User(
                mapper.getId(),
                mapper.getUsername(),
                mapper.getPasswordHash(),
                mapper.getResId(),
                mapper.getStaffId(),
                mapper.isDeleted(),
                mapper.getComplexId()
        );
    }
}
