package com.e_com.e_com_spring.model.auditing;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<Long> {
    @Override
    public Optional<Long> getCurrentAuditor() {
        // TODO: get id from spring security principal
        return Optional.empty();
    }
}
