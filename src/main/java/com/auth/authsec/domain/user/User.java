package com.auth.authsec.domain.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.security.core.userdetails.UserDetails;

import com.auth.authsec.domain.converters.LocalDateTimePersistenceConverter;
import com.auth.authsec.domain.role.Role;

/**
 * @author Plamen The User entity for this application. Users can register
 * locally in the app's database. Implements the UserDetails interface so Spring
 * Security can manage authorities and account details for the user in the
 * background.
 */
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class User implements UserDetails {

    private static final long serialVersionUID = -1306352279165749420L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Many-to-Many relation between the User and Role entities. Spring Security
     * uses this type of relation internally.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = {
        @JoinColumn(name = "user_id")}, inverseJoinColumns = {
        @JoinColumn(name = "role_id")})
    private List<Role> roles;

    @Column(name = "is_account_non_expired", nullable = false)
    private boolean isAccountNonExpired;

    @Column(name = "is_account_non_locked", nullable = false)
    private boolean isAccountNonLocked;

    @Column(name = "is_credentials_non_expired", nullable = false)
    private boolean isCredentialsNonExpired;

    @Column(name = "is_enabled", nullable = false)
    private boolean isEnabled;

    /**
     * User's account creation date.
     */
    @Column(name = "creation_date")
    @Convert(converter = LocalDateTimePersistenceConverter.class)
    private LocalDateTime creationDate;

    /**
     * Default constructor;
     */
    public User() {
        super();
    }

    /**
     * Constructor for initializing the username and pasword.
     *
     * @param username
     * @param password
     */
    public User(String username, String password) {
        super();
        this.username = username;
        this.password = password;

    }

    /**
     * Constructor for setting the roles.
     *
     * @param username
     * @param password
     * @param roles
     */
    public User(String username, String password, List<Role> roles) {
        this(username, password);
        this.roles = roles;
    }

    // ----------------------------------------- Getter Methods -----------------------------------------
    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public List<Role> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", password=" + password + ", isAccountNonExpired="
                + isAccountNonExpired + ", isAccountNonLocked=" + isAccountNonLocked + ", isCredentialsNonExpired="
                + isCredentialsNonExpired + ", isEnabled=" + isEnabled + ", roles=" + roles + ", creationDate="
                + creationDate + "]";
    }

    public static class UserBuilder {

        private String username;
        private String password;
        private List<Role> roles;
        private LocalDateTime creationDate;
        private boolean isAccountNonExpired;
        private boolean isAccountNonLocked;
        private boolean isCredentialsNonExpired;
        private boolean isEnabled;

        public UserBuilder() {
            super();
        }

        // ------------------------------- builder methods -------------------------------
        public UserBuilder roles(final List<Role> roles) {
            this.roles = roles;
            return this;
        }

        public UserBuilder isEnabled(boolean isEnabled) {
            this.isEnabled = isEnabled;
            return this;
        }

        public UserBuilder username(final String username) {
            this.username = username;
            return this;
        }

        public UserBuilder password(final String password) {
            this.password = password;
            return this;
        }

        public UserBuilder creationDate(final LocalDateTime creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public UserBuilder isAccountNonLocked(boolean accountNonLocked) {
            this.isAccountNonLocked = accountNonLocked;
            return this;
        }

        public UserBuilder isAccountNonExpired(boolean isAccountNonExpired) {
            this.isAccountNonExpired = isAccountNonExpired;
            return this;
        }

        public UserBuilder isCredentialsNonExpired(boolean isCredentialsNonExpired) {
            this.isCredentialsNonExpired = isCredentialsNonExpired;
            return this;
        }

        public User build() {
            User user = new User();

            user.username = this.username;
            user.password = this.password;
            user.roles = this.roles;
            user.creationDate = this.creationDate;
            user.isAccountNonExpired = this.isAccountNonExpired;
            user.isAccountNonLocked = this.isAccountNonLocked;
            user.isCredentialsNonExpired = this.isCredentialsNonExpired;
            user.isEnabled = this.isEnabled;

            return user;
        }
    }

}
