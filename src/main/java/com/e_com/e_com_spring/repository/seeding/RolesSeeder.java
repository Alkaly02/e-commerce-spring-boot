package com.e_com.e_com_spring.repository.seeding;

import com.e_com.e_com_spring.model.Role;
import com.e_com.e_com_spring.model.RoleType;
import com.e_com.e_com_spring.repository.RoleRepository;
import com.e_com.e_com_spring.service.common.PrivilegeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RolesSeeder {
    private final RoleRepository roleRepository;
    private final PrivilegeService privilegeService;

    public void seed() {
        Optional<Role> optionalRole = roleRepository.findByRoleType(RoleType.ADMIN.name());
        if (optionalRole.isEmpty()){
            Role adminRole = new Role();
            adminRole.setName("Admin");
            adminRole.setRoleType(RoleType.ADMIN);
            adminRole.setPrivileges(privilegeService.getAll());
            roleRepository.save(adminRole);
        }
    }
}
