package com.mbs.qlcc.adapters.db.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<UserDataMapper, String> {
    UserDataMapper findByUsernameAndComplexId(String username, String complexId);
}
