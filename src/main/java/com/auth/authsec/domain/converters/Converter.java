package com.auth.authsec.domain.converters;

/**
 * Contract for mapping entities to form objects and backwards.
 *
 * @author Plamen
 *
 * @param <F> form object.
 * @param <M> domain-level object.
 */
public interface Converter<F, M> {

    /**
     * Mapping a model object to a form object.
     *
     * @param model - object to convert.
     * @return converted form.
     */
    F convertToForm(final M model);

    /**
     * Mapping a form object to a model object.
     *
     * @param form - object to convert.
     * @return converted model.
     */
    M convertToModel(final F form);

}
