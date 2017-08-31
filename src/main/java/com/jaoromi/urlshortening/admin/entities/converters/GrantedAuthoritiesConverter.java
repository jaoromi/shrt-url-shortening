package com.jaoromi.urlshortening.admin.entities.converters;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class GrantedAuthoritiesConverter implements AttributeConverter<List<GrantedAuthority>, String> {
    @Override
    public String convertToDatabaseColumn(List<GrantedAuthority> authorities) {
        if(authorities == null)
            return null;

        return authorities.stream()
                .map(a -> a.getAuthority())
                .collect(Collectors.joining(","));
    }

    @Override
    public List<GrantedAuthority> convertToEntityAttribute(String dbData) {
        if(dbData == null)
            return null;

        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (String authority : dbData.split(",")) {
            authorities.add(new SimpleGrantedAuthority(authority));
        }
        return authorities;
    }
}
