package com.auth.authsec.domain.user;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.auth.authsec.domain.converters.Converter;
import com.auth.authsec.domain.user.User.UserBuilder;
import com.auth.authsec.ui.user.UserForm;

/**
 * Mapper Class for mapping User entities to UI forms and backwards.
 *
 * @author Plamen
 *
 */
@Component
public class UserConverter implements Converter<UserForm, User> {

    public UserConverter() {
        super();
    }

    /**
     * Mapping an entity to a form object.
     *
     * @param entity - object to convert.
     * @return converted form.
     */
    @Override
    public UserForm convertToForm(final User model) {
        List<String> roles = model.getAuthorities().stream().map(x -> x.getAuthority())
                .collect(Collectors.toList());
        return new UserForm(model.getUsername(), model.getPassword(), roles);
    }

    /**
     * Mapping a form object to an entity. Roles are assigned after the user
     * entity is created.
     *
     * @param form - object to convert.
     * @return converted entity.
     */
    @Override
    public User convertToModel(final UserForm form) {

        return new UserBuilder()
                .username(form.getUsername())
                .password(form.getPassword())
                .creationDate(LocalDateTime.now())
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isEnabled(true)
                .build();
    }

}
