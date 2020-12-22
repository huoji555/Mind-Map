package com.hwj.serviceImpl;

import com.hwj.entity.Role;
import com.hwj.repository.RoleRepository;
import com.hwj.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public void save(Role role) { roleRepository.save(role); }

    @Override
    public Role queryRoleById(Integer id) { return roleRepository.queryRoleById(id); }

}
