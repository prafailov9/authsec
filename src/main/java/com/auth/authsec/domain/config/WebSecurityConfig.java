package com.auth.authsec.domain.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.auth.authsec.domain.user.UserService;

/**
 * Web security configuration for this application. Deals with different login
 * strategies, logout handling, customized user management, CSRF protection,
 * etc.
 *
 * @see @Configuration
 * @see @EnableWebSecurity
 * @author Plamen
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Custom-built user service for creating, storing and managing user
     * entities in the application.
     */
    private final UserService userService;

    /**
     * Encoder for user passwords.
     */
    private final PasswordEncoder bCryptPasswordEncoder;

    /**
     * Constructor for this class
     *
     * @param userService
     * @param bCryptPasswordEncoder
     */
    @Autowired
    public WebSecurityConfig(UserService userService, PasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * Adds authentication based upon the custom UserDetailsService that is
     * passed in. It then returns a DaoAuthenticationConfigurer to allow
     * customization of the authentication.
     *
     *
     * @param authenticationManagerBuilder
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(this.userService)
                .passwordEncoder(this.bCryptPasswordEncoder);
    }

    /**
     * The custom configuration of how Spring Security behaves with this
     * application. Specifies which paths are and are not permitted, different
     * ways to log in(native or with an outside provider), logging out of the
     * application and CSRF protection.
     *
     *
     * @param httpSecurity
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                .antMatchers("/", "/home", "/register",
                        "/logout", "/bootstrap/**", "/jquery/**",
                        "/fragments/**", "/styles.css", "/webjars/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2Login()
                .defaultSuccessUrl("/home")
                .and()
                .formLogin()
                .loginPage("/native-login")
                .defaultSuccessUrl("/home")
                .permitAll()
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
                .and()
                .rememberMe()
                .key("ezclap")
                .and()
                .csrf()
                .disable();

        // httpSecurity.csrf().csrfTokenRepository(getCookieCsrfTokenRepository());
        // TODO: Configure CSRF!
    }

    @Bean
    public CookieCsrfTokenRepository getCookieCsrfTokenRepository() {
        return CookieCsrfTokenRepository.withHttpOnlyFalse();
    }

}
