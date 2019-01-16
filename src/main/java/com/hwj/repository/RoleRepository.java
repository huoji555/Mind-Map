package com.hwj.repository;

import com.hwj.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,String> {

    Role queryRoleById(Integer id);

}
