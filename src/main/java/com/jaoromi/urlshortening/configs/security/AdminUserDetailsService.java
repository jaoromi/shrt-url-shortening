package com.jaoromi.urlshortening.configs.security;

import com.jaoromi.urlshortening.admin.repositories.AdminUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class AdminUserDetailsService implements UserDetailsService {

    @Inject
    private AdminUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findOne(username);
    }
}
