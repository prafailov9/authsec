package com.auth.authsec.domain.role;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;

/**
 * The Role entity for this application. Implements Spring Security's
 * GrantedAuthority interface. The Framework uses this class for role
 * management, given the implemented methods from the interface.
 *
 * @author Plamen
 *
 */
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    /**
     *
     */
    private static final long serialVersionUID = 6883167511493768156L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Column(name = "role_name")
    @NotNull
    private String roleName;

    public Role() {
        super();
    }

    public Role(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String getAuthority() {
        return roleName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((roleName == null) ? 0 : roleName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Role other = (Role) obj;
        if (this.roleName.equals(other.roleName)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {

        return "Role [id=" + id + ", roleName=" + roleName + "]";
    }

}
