package com.jaoromi.urlshortening.admin.entities;

import com.jaoromi.urlshortening.admin.entities.converters.GrantedAuthoritiesConverter;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Tolerate;
import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "admin_user", indexes = {
        @Index(name = "user_name_idx", columnList = "name"),
        @Index(name = "user_phone_idx", columnList = "phone", unique = true),
        @Index(name = "user_email_idx", columnList = "email", unique = true)
})
@Data
@Builder
@EqualsAndHashCode(callSuper = false, of = "id")
public class AdminUser implements UserDetails {

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

    @Column
    @Convert(converter = GrantedAuthoritiesConverter.class)
    private List<GrantedAuthority> authorities;

    @Column
    @Builder.Default
    private boolean enabled = true;

    @Tolerate
    public AdminUser() {
        this.enabled = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getUsername() {
        return this.id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
