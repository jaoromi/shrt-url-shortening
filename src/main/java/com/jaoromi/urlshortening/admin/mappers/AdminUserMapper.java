package com.jaoromi.urlshortening.admin.mappers;

import com.jaoromi.urlshortening.admin.dto.AdminUserDTO;
import com.jaoromi.urlshortening.admin.entities.AdminUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AdminUserMapper {

    @Mappings({
            @Mapping(target = "password", ignore = true)
    })
    AdminUserDTO entityToDTO(AdminUser entity);

    @Mappings({
            @Mapping(target = "authorities", constant = "ROLE_ADMIN"),
            @Mapping(target = "enabled", ignore = true)
    })
    AdminUser dtoToEntity(AdminUserDTO dto);

    default List<GrantedAuthority> stringToGrantedAuthorities(String authority) {
        final List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(authority));

        return authorities;
    }
}
