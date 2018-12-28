/*
 * 
 * EuroRisk Systems (c) Ltd. All rights reserved.
 * 
 */
package com.auth.authsec.domain.exceptions;

/**
 *
 * @author Plamen
 */
public class UnregisteredClientException extends RuntimeException {

    private final static String MESSAGE = "Unregistered client! Check clinet configuration properties.";

    public UnregisteredClientException() {

        super();

    }

    @Override
    public String getMessage() {
        return MESSAGE;
    }

}
