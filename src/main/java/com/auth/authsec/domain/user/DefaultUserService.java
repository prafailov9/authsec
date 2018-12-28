package com.auth.authsec.domain.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth.authsec.domain.config.Errors;
import com.auth.authsec.domain.converters.Converter;
import com.auth.authsec.domain.exceptions.NoSuchRoleException;
import com.auth.authsec.domain.exceptions.NoSuchUserException;
import com.auth.authsec.domain.exceptions.NullResultListException;
import com.auth.authsec.domain.exceptions.InvalidEntityStateException;
import com.auth.authsec.domain.exceptions.UserAlreadyExistsException;
import com.auth.authsec.domain.role.Role;
import com.auth.authsec.domain.role.RoleRepository;
import com.auth.authsec.ui.user.UserForm;

/**
 * Concrete implementation of the UserDetailsService interface.
 *
 * @author Plamen
 */
@Service
public class DefaultUserService implements UserService {

    /**
     * The repository for managing User entities.
     */
    private final UserRepository userRepository;

    /**
     * The repository for managing Role entities.
     */
    private final RoleRepository roleRepository;

    /**
     * The Converter class for mapping UI User Forms to domain-level User
     * entities.
     */
    private final Converter<UserForm, User> userConverter;

    /**
     * The Class for encoding user passwords in the database.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * @Autowired constructor, injecting dependencies at runtime.
     * @param userRepository - repository for managing user entities.
     * @param passwordEncoder - object for encoding passwords in the database.
     * @param roleRepository - repository for managing role entities.
     * @param userConverter - object for mapping user entities to UI forms.
     */
    @Autowired
    public DefaultUserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
            RoleRepository roleRepository, Converter<UserForm, User> userConverter) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userConverter = userConverter;
        this.passwordEncoder = passwordEncoder;

    }

    // -------------------------------------------- Public Methods --------------------------------------------
    /**
     * {@inheritDoc}
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findOneByUsername(username);

        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException(Errors.INVALID_CREDENTIALS);
        }

        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerAccount(UserForm userForm) throws UserAlreadyExistsException {
        try {

            User user = new User();
            user = setAccountDetails(user, userForm, true);
            userRepository.save(user);

        } catch (DataIntegrityViolationException exception) {
            throw new UserAlreadyExistsException();
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserForm> getAllAccounts() {
        List<UserForm> accounts = new ArrayList<>();

        try {

            accounts = userRepository.findAll().stream().map(userConverter::convertToForm).collect(Collectors.toList());

        } catch (NullPointerException e) {
            throw new NullResultListException();
        }

        return accounts;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserForm> getAllWithoutCurrent() {
        List<UserForm> allWithoutCurrent = new ArrayList<>();

        try {

            allWithoutCurrent = userRepository.findAll().stream().filter(currentUser -> withoutCurrent(currentUser))
                    .map(userConverter::convertToForm).collect(Collectors.toList());

        } catch (NullPointerException e) {
            throw new NullResultListException();
        }

        return allWithoutCurrent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserForm> getAllAdminAccounts() {
        List<UserForm> admins = new ArrayList<>();

        try {

            admins = userRepository.findAll().stream().filter(currentUser -> isAdmin(currentUser))
                    .map(userConverter::convertToForm).collect(Collectors.toList());

        } catch (NullPointerException e) {
            throw new NullResultListException();
        }

        return admins;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserForm> getAllUserAccounts() {
        List<UserForm> users = new ArrayList<>();

        try {

            users = userRepository.findAll().stream().filter(currentUser -> !isAdmin(currentUser))
                    .map(userConverter::convertToForm).collect(Collectors.toList());

        } catch (NullPointerException e) {
            throw new NullResultListException();
        }

        return users;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isUserAuthenticated() {
        return (!(getLoggedInUser() instanceof AnonymousAuthenticationToken));
    }

    /**
     * {@inheritDoc}
     */
    public Authentication getLoggedInUser() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateUserAccountToAdminAccount(final String username) {
        User user = userRepository.findOneByUsername(username);

        if (isInBadState(user)) {
            throw new InvalidEntityStateException();
        }

        try {

            List<Role> newRoles = roleRepository.findAll().stream().filter(x -> x.getAuthority().equals("ROLE_ADMIN"))
                    .collect(Collectors.toList());

            user.setRoles(newRoles);
            userRepository.save(user);

        } catch (NullPointerException e) {
            throw new NullResultListException();
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAccount(final String username) {
        try {

            User user = userRepository.findOneByUsername(username);
            userRepository.delete(user);

        } catch (EntityNotFoundException | IllegalArgumentException | NullPointerException e) {

            throw new NoSuchUserException();
        }
    }

    // -------------------------------------------- Private Methods --------------------------------------------
    /**
     * (this::WithoutCurrent) <=> (currentUser -> withoutCurrent(currentUser))
     *
     * Method to check if the processed user is the current logged in user.
     *
     * @param user
     * @return
     */
    private boolean withoutCurrent(final User user) {
        return !user.getUsername().equals(getLoggedInUser().getName());
    }

    /**
     * Helper method to check if a user model has a valid state for updating
     * their account.
     *
     * @param user
     * @return "true" if the account doesnt have a valid state, "false" - if the
     * account can be used for updating its roles.
     */
    private final boolean isInBadState(final User user) {
        return (Objects.isNull(user) || Objects.isNull(user.getAuthorities()) || isAdmin(user));
    }

    /**
     * Method to check if a user has a admin role.
     *
     * @param user
     * @return "true" if the user has a ADMIN role, false - the user has a USER
     * role.
     */
    private final boolean isAdmin(final User user) {
        return (user.getAuthorities().contains(new Role("ROLE_ADMIN")));
    }

    /**
     * Private method for fetching a role type. If the role does not exist, it
     * creates and saves the given role by the roleName parameter.
     *
     * @param roleName - name to search for.
     * @return fetched Role entity.
     */
    private final Role getUserRole(final String roleName) {
        Role role = roleRepository.findOneByRoleName(roleName);

        if (Objects.isNull(role)) {
            role = new Role(roleName);
            roleRepository.save(role);
        }
        return role;
    }

    /**
     * Private method for setting the account properties from the given user
     * form. On creating a new User account all details are set to "true" so the
     * user has access to the account. Access to the application resources is
     * determined by the role, that the user has.
     *
     * @param user
     * @param roles
     * @param setter
     */
    private final User setAccountDetails(User user, final UserForm userForm, final boolean setter) {
        List<Role> roles = new ArrayList<>();

        try {
            final String roleToFind = userForm.getRoles().get(0);
            final String encodedPassword = passwordEncoder.encode(userForm.getPassword());

            Role role = getUserRole(roleToFind);
            roles.add(role);

            userForm.setPassword(encodedPassword);
            user = userConverter.convertToModel(userForm);
            user.setRoles(roles);

        } catch (RuntimeException e) {
            throw new NoSuchRoleException();
        }

        return user;
    }

}
