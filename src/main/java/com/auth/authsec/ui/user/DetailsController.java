package com.auth.authsec.ui.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.authsec.domain.user.UserService;

/**
 * Rest Controller, returns the current logged-in user's information.
 *
 * @author Plamen
 */
@RestController
public class DetailsController {

    private UserService userService;

    @Autowired
    public DetailsController(UserService userService) {
        super();
        this.userService = userService;
    }

    /**
     * Contains the current logged user's information. If the name returns a
     * string of numbers and letters the user is authenticated through an
     * outside provider.
     *
     * @return the user's authentication details.
     */
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/user")
    public Authentication getUserDetails() {
        return userService.getLoggedInUser();
    }

    /**
     * Shows all accounts in the database.
     *
     * @return all accounts in the database.
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all-accounts")
    public List<UserForm> showAllAccounts() {
        return userService.getAllAccounts();
    }

    /**
     * Shows all accounts with the "ADMIN" role in the database.
     *
     * @return all "ADMIN" accounts.
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admins")
    public List<UserForm> showAllAdmins() {
        return userService.getAllAdminAccounts();
    }

    /**
     * Shows all accounts with the "USER" role in the database.
     *
     * @return all "USER" accounts.
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    public List<UserForm> showAllUsers() {
        return userService.getAllUserAccounts();
    }

}
