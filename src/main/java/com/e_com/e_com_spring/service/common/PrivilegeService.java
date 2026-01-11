package com.e_com.e_com_spring.service.common;

import com.e_com.e_com_spring.model.Privilege;
import com.e_com.e_com_spring.repository.PrivilegeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrivilegeService {
    private final PrivilegeRepository privilegeRepository;

    public List<Privilege> getAll(){
        return privilegeRepository.findAll();
    }
}
