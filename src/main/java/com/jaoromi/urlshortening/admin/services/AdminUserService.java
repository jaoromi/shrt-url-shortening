package com.jaoromi.urlshortening.admin.services;

import com.jaoromi.urlshortening.admin.dto.AdminUserDTO;
import com.jaoromi.urlshortening.admin.entities.AdminUser;
import com.jaoromi.urlshortening.admin.mappers.AdminUserMapper;
import com.jaoromi.urlshortening.admin.repositories.AdminUserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;

@Service
public class AdminUserService {

    @Inject
    private AdminUserRepository repository;

    @Inject
    private AdminUserMapper mapper;

    @Inject
    private PasswordEncoder passwordEncoder;

    @Transactional
    public AdminUserDTO register(AdminUserDTO dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));

        return mapper.entityToDTO(
                repository.save(mapper.dtoToEntity(dto))
        );
    }

    public AdminUserDTO getAdminUser(String id) {
        return mapper.entityToDTO(repository.findOne(id));
    }

    @Transactional
    public AdminUserDTO update(AdminUserDTO dto) {
        AdminUser original = repository.findOne(dto.getId());

        BeanUtils.copyProperties(mapper.dtoToEntity(dto), original, "password");

        return mapper.entityToDTO(original);
    }

    @Transactional
    public void delete(String id) {
        repository.delete(id);
    }
}
