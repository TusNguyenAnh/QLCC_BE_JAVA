package com.mbs.qlcc.adapters.db.User;

import com.mbs.qlcc.entities.User.User;
import com.mbs.qlcc.usecases.output.User.IUserDsGateway;
import com.mbs.qlcc.usecases.request.User.UserInpRequest;
import com.mbs.qlcc.utils.StringHelper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class JpaUser implements IUserDsGateway {
    final JpaUserRepository repository;

    @Override
    public User findById(String id) {
        return null;
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
    public void storeList(List<UserInpRequest> userInpRequests) {
        List<UserDataMapper> userDataMapper = userInpRequests.stream()
                .map(req -> UserDataMapper.builder()
                        .username(req.getPhoneNumber())
                        .passwordHash(req.getPassword())
                        .complexId(req.getComplex_id())
                        .resId(req.getResId())
                        .staffId(req.getStaffId())
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build()
                )
                .toList();

        repository.saveAll(userDataMapper);
    }

    @Override
    public void store(UserInpRequest userInpRequests) {
        UserDataMapper userDataMapper = UserDataMapper.builder()
                .username(userInpRequests.getPhoneNumber())
                .passwordHash(userInpRequests.getPassword())
                .complexId(userInpRequests.getComplex_id())
                .resId(userInpRequests.getResId())
                .staffId(userInpRequests.getStaffId())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        repository.save(userDataMapper);
    }

    @Override
    public String generatePassword() {
        return StringHelper.generate(6);
    }
}
