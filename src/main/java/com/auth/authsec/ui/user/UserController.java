package com.auth.authsec.ui.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.auth.authsec.domain.config.Errors;
import com.auth.authsec.domain.user.UserService;

/**
 * The Controller for the User model. Its responsibility is sending user
 * information to the domain level from the browser and backwards.
 *
 * @author Plamen
 */
@Controller
public class UserController {

    /**
     * The service for managing user entities.
     */
    private final UserService userService;

    /**
     * Constructor dependency injection.
     *
     * @param userService
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * GetMapping for the register page. Adds a UserForm object to the web
     * model.
     *
     * @param model - the object, containing the HTML page.
     * @return HTML page with a loaded UserForm object.
     */
    @PreAuthorize("isAnonymous()")
    @GetMapping("/register")
    public ModelAndView getRegisterPage(@RequestParam(required = false) String error) {

        final ModelAndView mav = new ModelAndView("/register");

        if (!Objects.isNull(error)) {
            mav.addObject("error", Errors.USERNAME_ALREADY_EXISTS);
            return mav;
        }
        mav.addObject("userForm", new UserForm());
        return mav;
    }

    /**
     * PostMapping for processing the user registration.
     *
     * @param userForm - Object which was sent to the HTML page.
     * @param bindingResult - checks the page for errors.
     * @return HTML page.
     */
    @PreAuthorize("isAnonymous()")
    @PostMapping("/register")
    public ModelAndView processUserRegistration(@Valid @ModelAttribute UserForm userForm, BindingResult bindingResult,
            String error, Model model) {

        ModelAndView mav = new ModelAndView("/register");

        if (bindingResult.hasErrors()) {
            mav.addObject("error");
            return mav;
        }

        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");
        userForm.setRoles(roles);

        userService.registerAccount(userForm);
        mav = new ModelAndView("redirect:/");
        return mav;
    }

    /**
     * Get method for showing the admin registration page.
     *
     * @param error
     * @param model
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/add-admin")
    public ModelAndView showAddAdminPage(@RequestParam(required = false) String error, Model model) {

        final ModelAndView mav = new ModelAndView("/add-admin");

        mav.addObject("userForm", new UserForm());

        if (!Objects.isNull(error)) {
            mav.addObject("error", Errors.USERNAME_ALREADY_EXISTS);
            return mav;
        }
        return mav;
    }

    /**
     * Post method for procesing the admin registration. This method adds a
     * admin role before the user is created.
     *
     * @param userForm
     * @param bindingResult
     * @param error
     * @param model
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add-admin")
    public String processAdminRegistration(@Valid @ModelAttribute UserForm userForm, BindingResult bindingResult,
            String error, Model model) {

        if (bindingResult.hasErrors()) {
            return "error";
        }

        List<String> roles = new ArrayList<>();
        roles.add("ROLE_ADMIN");
        userForm.setRoles(roles);

        userService.registerAccount(userForm);

        return "redirect:/";
    }

    /**
     * Get method for the dashboard page.
     *
     * @return the dashboard page.
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/dashboard")
    public ModelAndView showDashboardPage() {
        return new ModelAndView("dashboard");
    }

    /**
     * Get method for the ADMIN dashboard page.
     *
     * @return the admin dashboard page.
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin-dashboard")
    public ModelAndView showAdminDashboardPage() {
        return new ModelAndView("admin-dashboard");
    }

    /**
     * GetMapping for the native login with link to login with third-party
     * providers. Checks if a user is already logged into the application. If
     * so, then redirects to the home page.
     *
     * @param error - error message
     * @param model - model for the native-login page.
     * @return /native-login page
     */
    @PreAuthorize("isAnonymous()")
    @GetMapping("/native-login")
    public ModelAndView showLoginPage(@RequestParam(required = false) String error) {

        final ModelAndView mav = new ModelAndView();

        if (userService.isUserAuthenticated()) {
            mav.addObject("redirect:/");
            return mav;
        }

        if (!Objects.isNull(error)) {
            mav.addObject("error", Errors.INVALID_CREDENTIALS);
        }

        mav.addObject("native-login");

        return mav;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/make-admin")
    public ModelAndView showMakeAdminPage() {

        final ModelAndView mav = new ModelAndView("/make-admin");

        final List<UserForm> userAccountForms = userService.getAllUserAccounts();
        mav.addObject("allUsers", userAccountForms);
        return mav;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/make-admin-account")
    public ModelAndView processMakeAdminPage(@RequestParam("username") final String username) {

        userService.updateUserAccountToAdminAccount(username);
        final ModelAndView mav = new ModelAndView("redirect:/make-admin-success");
        mav.addObject("username", username);

        return mav;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/make-admin-success")
    public ModelAndView showMakeAdminSuccessPage(@RequestParam("username") final String username) {

        final ModelAndView mav = new ModelAndView("/make-admin-success");

        mav.addObject("username", username);

        return mav;

    }

    /**
     * Shows all accounts, with the exception of the current logged-in user's
     * account.
     *
     * @param error
     * @param model
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/delete-account")
    public ModelAndView showDeleteAccountPage() {

        final ModelAndView mav = new ModelAndView("/delete-account");

        final List<UserForm> accountForms = userService.getAllWithoutCurrent();

        mav.addObject("allAccounts", accountForms);
        return mav;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/delete-user-account")
    public ModelAndView processDeleteAccountPage(@RequestParam(name = "username") final String username) {

        userService.deleteAccount(username);
        final ModelAndView mav = new ModelAndView("redirect:/delete-success");
        mav.addObject("username", username);
        return mav;

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/delete-success")
    public ModelAndView showDeleteSuccessPage(@RequestParam(name = "username") final String username) {
        final ModelAndView mav = new ModelAndView("/delete-success");

        mav.addObject("username", username);

        return mav;
    }

}
