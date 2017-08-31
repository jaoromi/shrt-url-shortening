package com.jaoromi.urlshortening.admin.mappers;

import com.jaoromi.urlshortening.admin.dto.AdminUserDTO;
import com.jaoromi.urlshortening.admin.entities.AdminUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {})
public interface AdminUserMapper {

    @Mappings({
            @Mapping(target = "password", ignore = true)
    })
    AdminUserDTO entityToDTO(AdminUser entity);

    AdminUser dtoToEntity(AdminUserDTO dto);
}
