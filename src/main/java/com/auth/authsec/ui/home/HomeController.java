package com.auth.authsec.ui.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * The controller for the home and error page mappings.
 *
 * @author Plamen
 *
 */
@Controller
public class HomeController {

    /**
     * This method maps the home page html file to the "/" path.
     *
     * @return the home page html file.
     */
    @GetMapping("/")
    public ModelAndView showHomePageSlashOnly() {

        return new ModelAndView("home");
    }

    /**
     * This method maps the home page html file to the "/home" path.
     *
     * @return the home page html file.
     */
    @GetMapping("/home")
    public ModelAndView showHomePage() {

        return new ModelAndView("home");
    }

    /**
     * This method maps the error page to the "/error" path.
     *
     * @return the error page html file.
     */
    @GetMapping("/error")
    public ModelAndView showErrorPage() {
        return new ModelAndView("error");
    }

}
