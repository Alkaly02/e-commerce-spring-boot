package com.e_com.e_com_spring.repository.seeding;

import com.e_com.e_com_spring.model.Role;
import com.e_com.e_com_spring.model.RoleType;
import com.e_com.e_com_spring.repository.PrivilegeRepository;
import com.e_com.e_com_spring.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerRoleSeeder {
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;

    public void seed(){
        Optional<Role> optionalRole = roleRepository.findByRoleType(RoleType.ROLE_CUSTOMER);
        if (optionalRole.isEmpty()){
            Role customerRole = new Role();
            customerRole.setName("Customer");
            customerRole.setRoleType(RoleType.ROLE_CUSTOMER);
            customerRole.setPrivileges(Collections.singleton(privilegeRepository.findById(10L).orElse(null)));
            roleRepository.save(customerRole);
            roleRepository.flush();
            log.info("ROLE_CUSTOMER created");
        }
    }
}
