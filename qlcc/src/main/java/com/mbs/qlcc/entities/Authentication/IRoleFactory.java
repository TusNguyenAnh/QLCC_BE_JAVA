package com.mbs.qlcc.entities.Authentication;

public interface IRoleFactory {
    Role createRole(String roleName, String complexId, String description);
}
