package com.e_com.e_com_spring.repository.seeding;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;

@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {
    private final RolesSeeder rolesSeeder;

    @Override
    public void run(String... args) throws Exception {
        rolesSeeder.seed();
    }
}
