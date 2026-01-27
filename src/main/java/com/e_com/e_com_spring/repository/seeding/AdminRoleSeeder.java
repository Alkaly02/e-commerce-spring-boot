package com.e_com.e_com_spring.repository.seeding;

import com.e_com.e_com_spring.model.Role;
import com.e_com.e_com_spring.model.RoleType;
import com.e_com.e_com_spring.repository.PrivilegeRepository;
import com.e_com.e_com_spring.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminRoleSeeder {
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;

    public void seed(){
        Optional<Role> optionalRole = roleRepository.findByRoleType(RoleType.ROLE_ADMIN);
        if (optionalRole.isEmpty()){
            Role adminRole = new Role();
            adminRole.setName("Admin");
            adminRole.setRoleType(RoleType.ROLE_ADMIN);
            adminRole.setPrivileges(privilegeRepository.findAll());
            roleRepository.save(adminRole);
            roleRepository.flush();
            log.info("ROLE_ADMIN created");
        }
    }
}
