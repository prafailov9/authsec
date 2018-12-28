package com.auth.authsec.domain.user;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.auth.authsec.domain.exceptions.UserAlreadyExistsException;
import com.auth.authsec.ui.user.UserForm;

/**
 * The service for managing domain-level logic with the User model. Extends the
 * UserDetailsService which is a specification in Spring Security. Its contract
 * is the loadUserByUsername(username) method, which the User service should
 * implement.
 *
 * @author Plamen
 */
public interface UserService extends UserDetailsService {

    /**
     *
     * The service for registering a new Account into the system. Throws a
     * "UserAlreadyExistsException" if the given username already exists in the
     * database.
     *
     * @param userForm
     * @throws UserAlreadyExistsException
     */
    void registerAccount(UserForm userForm) throws UserAlreadyExistsException;

    /**
     * Locates the user in the database based on the given username.
     *
     * @param username the username identifying the user whose data is required.
     *
     * @return a fully populated user record (never <code>null</code>)
     *
     * @throws UsernameNotFoundException if the user could not be found or the
     * user has no GrantedAuthority
     */
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    /**
     * Method for getting all user and admin accounts from the database.
     *
     * @return all accounts.
     */
    List<UserForm> getAllAccounts();

    /**
     * Method for getting all admin accounts.
     *
     * @return all admins.
     */
    List<UserForm> getAllAdminAccounts();

    /**
     * Method for getting all user accounts.
     *
     * @return all users.
     */
    List<UserForm> getAllUserAccounts();

    /**
     * Method for getting all accounts without the current logged in user.
     *
     * @return all without the current logged in user.
     */
    List<UserForm> getAllWithoutCurrent();

    /**
     * Method for changing the roles of a user. Can only be done by another
     * admin user.
     *
     * @param userForm to be updated.
     */
    void updateUserAccountToAdminAccount(final String username);

    /**
     * Method for checking if there is a user currently logged into the system.
     *
     * @param auth contains the current logged-in user's authentication.
     * @return {true} if user is logged in, {false} if there is no logged in
     * user.
     */
    boolean isUserAuthenticated();

    /**
     * Method for getting the authentication details for the current logged-in
     * user.
     *
     * @return current logged-in user
     */
    Authentication getLoggedInUser();

    /**
     * Method for deleting a selected account. Can only be done by an admin.
     *
     * @param userForm to be deleted.
     */
    void deleteAccount(final String username);

}
