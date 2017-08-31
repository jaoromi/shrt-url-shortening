package com.jaoromi.urlshortening.admin.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "admin_user", indexes = {
        @Index(name = "user_name_idx", columnList = "name"),
        @Index(name = "user_phone_idx", columnList = "phone", unique = true),
        @Index(name = "user_email_idx", columnList = "email", unique = true)
})
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
public class AdminUser {

    public static final String LOGIN_REGEX = "^[_'.@A-Za-z0-9-]*$";

    @Id
    @Pattern(regexp = LOGIN_REGEX)
    @Size(min = 1, max = 20)
    @Column(length = 20)
    private String id;

    @NotNull
    @Size(min = 60, max = 60)
    @Column(name = "password", length = 60)
    private String password;

    @Size(max = 50)
    @Column(name = "name", length = 10)
    private String name;

    @Column(name = "phone", length = 50)
    private String phone;

    @Email
    @Size(max = 100)
    @Column(length = 100)
    private String email;

}
