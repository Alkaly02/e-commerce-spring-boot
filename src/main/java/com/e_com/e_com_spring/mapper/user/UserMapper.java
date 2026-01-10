package com.e_com.e_com_spring.mapper.user;

import com.e_com.e_com_spring.dto.auth.RegisterPostDto;
import com.e_com.e_com_spring.model.Role;
import com.e_com.e_com_spring.model.RoleType;
import com.e_com.e_com_spring.model.User;
import jdk.jfr.Name;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Mappings(
            @Mapping(source = "roleType", target = "role", qualifiedByName = "mapRole")
    )
    public abstract User registerDtoToUser(RegisterPostDto postDto);

    @Named("mapRole")
    protected Role mapRole(String role){
        if (role == null){
            return null;
        }
        Role newRole = new Role();
        newRole.setName(role);
        newRole.setRoleType(RoleType.valueOf(role));
        return newRole;
    }
}
