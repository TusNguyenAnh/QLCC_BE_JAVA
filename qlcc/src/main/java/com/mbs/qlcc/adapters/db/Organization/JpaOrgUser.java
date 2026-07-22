package com.mbs.qlcc.adapters.db.Organization;

import com.mbs.qlcc.adapters.db.Token.JpaTokenRepository;
import com.mbs.qlcc.entities.Organization.OrgUser;
import com.mbs.qlcc.usecases.exception.AppException;
import com.mbs.qlcc.usecases.output.Organization.IOrgUserDsGateway;
import com.mbs.qlcc.utils.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class JpaOrgUser implements IOrgUserDsGateway {
    final JpaOrgUserRepository repository;

    @Override
    public OrgUser findUserByOrgId(String userId, String orgId) {
        OrgUserDataMapper orgUserDataMapper = repository.findByUserIdAndOrgId(userId, orgId);
        return toOrgUser(orgUserDataMapper);
    }

    @Transactional
    @Override
    public String saveOrgUser(List<OrgUser> orgUser) {
        List<OrgUserDataMapper> orgUserDataMappers = orgUser.stream()
                .map(o -> OrgUserDataMapper.builder()
                        .userId(o.getUser_id())
                        .orgId(o.getOrg_id())
                        .roleId(o.getRole_id())
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build())
                .toList();

        repository.saveAll(orgUserDataMappers);
        return "Tạo mới thành công";
    }

    @Override
    public OrgUser updateOrgUser(OrgUser orgUser) {
        OrgUserDataMapper orgUserDataMapper = repository.findByUserIdAndOrgId(orgUser.getUser_id(), orgUser.getOrg_id());
        if (orgUserDataMapper == null) {
            throw new AppException(ErrorCode.NOT_FOUND);
        }

        orgUserDataMapper.setRoleId(orgUser.getRole_id());
        return toOrgUser(repository.save(orgUserDataMapper));
    }

    @Transactional
    @Override
    public void deleteOrgUser(List<String> userId, String orgId) {
        List<String> ids = repository.findByOrgIdAndUserIdIn(orgId, userId).stream()
                .map(OrgUserDataMapper::getId)
                .toList();

        repository.deleteAllById(ids);
    }

    @Override
    public List<String> getAllUserIdsByOrgId(String orgId, List<String> roleIds) {
        return repository.findByOrgIdAndRoleIdIn(orgId, roleIds).stream()
                .map(OrgUserDataMapper::getUserId)
                .toList();
    }


    private static OrgUser toOrgUser(OrgUserDataMapper orgUserMap) {
        return new OrgUser(orgUserMap.getUserId(), orgUserMap.getOrgId(), orgUserMap.getRoleId());
    }
}
