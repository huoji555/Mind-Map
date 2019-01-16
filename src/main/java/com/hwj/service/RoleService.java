package com.hwj.service;

import com.hwj.entity.Role;

public interface RoleService {

    void save(Role role);

    Role queryRoleById(Integer id);

}
