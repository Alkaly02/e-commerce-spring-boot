package com.e_com.e_com_spring.repository.seeding;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class RolesSeeder {
    private final AdminRoleSeeder adminRoleSeeder;
    private final CustomerRoleSeeder customerRoleSeeder;

    @Transactional
    public void seed() {
        log.info("RoleSeeder Started");
        adminRoleSeeder.seed();
        customerRoleSeeder.seed();
        log.info("RoleSeeder Ended");
    }
}
