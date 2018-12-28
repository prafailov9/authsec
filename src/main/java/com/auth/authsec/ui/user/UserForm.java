package com.auth.authsec.ui.user;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

/**
 * Class for representing the User model in a browser.
 *
 * @author Plamen
 *
 */
public class UserForm {

    /**
     * The username. Should not be less than 5 characters.
     */
    @Size(min = 5, message = "Username is too short!")
    private String username;

    /**
     * The password. Should not be less than 5 characters.
     */
    @Size(min = 5, message = "Password is too short!")
    private String password;

    private List<String> roles;

    /**
     * Default Constructor
     */
    public UserForm() {
        super();
        this.roles = new ArrayList<>();
    }

    public UserForm(List<String> roles) {
        super();
        this.roles = roles;
    }

    public UserForm(String username, String password) {
        super();
        this.username = username;
        this.password = password;
        this.roles = new ArrayList<>();
    }

    public UserForm(String username, String password, List<String> roles) {
        this(username, password);
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

}
