package com.jaoromi.urlshortening.admin.repositories;

import com.jaoromi.urlshortening.admin.entities.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, String> {

}
