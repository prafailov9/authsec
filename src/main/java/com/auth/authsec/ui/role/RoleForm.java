package com.auth.authsec.ui.role;

/**
 * Class for representing the Role model into a browser.
 *
 * @author Plamen
 *
 */
public class RoleForm {

    /**
     * Name of the role.
     */
    private String roleName;

    /**
     * Default Constructor, Good practice O.o
     */
    public RoleForm() {
        super();
    }

    /**
     * C'mon
     *
     * @param roleName
     */
    public RoleForm(String roleName) {
        this.roleName = roleName;
    }

    /**
     * Getter for the role name
     *
     * @return the role name
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * Setter for the role name
     *
     * @param roleName the role name
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}
