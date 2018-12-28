package com.auth.authsec.domain.role;

import org.springframework.stereotype.Component;

import com.auth.authsec.domain.converters.Converter;
import com.auth.authsec.ui.role.RoleForm;

/**
 * A Mapper Class for mapping domain-level Role entities to UI Form objects for
 * the browser. Implements the ModelMapper Interface.
 *
 * @see Converter
 * @author Plamen
 *
 */
@Component
public class RoleConverter implements Converter<RoleForm, Role> {

    /**
     * Default Constructor
     */
    public RoleConverter() {
        super();
    }

    /**
     * Mapping an entity to a form object.
     *
     * @param entity - object to convert.
     * @return converted form.
     */
    @Override
    public RoleForm convertToForm(final Role model) {
        return new RoleForm(model.getAuthority());
    }

    /**
     * Mapping a form object to an entity.
     *
     * @param form - object to convert.
     * @return converted entity.
     */
    @Override
    public Role convertToModel(final RoleForm form) {
        return new Role(form.getRoleName());
    }

}
