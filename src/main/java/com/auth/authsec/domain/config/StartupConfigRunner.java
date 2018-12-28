package com.auth.authsec.domain.config;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.auth.authsec.domain.role.Role;
import com.auth.authsec.domain.role.RoleRepository;
import com.auth.authsec.domain.user.User;
import com.auth.authsec.domain.user.User.UserBuilder;
import com.auth.authsec.domain.user.UserRepository;

/**
 * Configuration class for creating a administrator level user on app start-up.
 *
 * @see @Component
 *
 * @author Plamen
 */
@Component
public class StartupConfigRunner {

    private static final String ROLE_USER = "ROLE_USER";

    private static final String ROLE_ADMIN = "ROLE_ADMIN";

    private static final String USERNAME = "admin";

    private static final String PASSWORD = "admin";

    private static final boolean SETTINGS = true;

    /**
     * Constructor for this class.
     */
    public StartupConfigRunner() {
        super();
    }

    /**
     * Creates a administrator level user on start-up. Checks if such user
     * exists.
     *
     * @param userRepository - repository for managing user entities.
     * @param roleRepository - repository for managing role entities.
     * @param passwordEncoder - object for encoding user passwords.
     * @return CommandLineRunner object, which is going to be loaded in the
     * ApplicationContext on start-up.
     */
    @Bean
    private CommandLineRunner runner(final UserRepository userRepository, final RoleRepository roleRepository,
            final BCryptPasswordEncoder passwordEncoder) {
        return (args) -> {
            Role adminRole = roleRepository.findOneByRoleName(ROLE_ADMIN);
            Role userRole = roleRepository.findOneByRoleName(ROLE_USER);

            if (Objects.isNull(adminRole) || Objects.isNull(userRole)) {
                adminRole = new Role(ROLE_ADMIN);
                userRole = new Role(ROLE_USER);

                roleRepository.save(adminRole);
                roleRepository.save(userRole);
            }

            User rootAdmin = userRepository.findOneByUsername(USERNAME);

            if (Objects.isNull(rootAdmin)) {
                List<Role> roles = new ArrayList<>();
                roles.add(adminRole);

                rootAdmin = new UserBuilder().username(USERNAME).password(passwordEncoder.encode(PASSWORD)).roles(roles)
                        .creationDate(LocalDateTime.now()).isAccountNonExpired(SETTINGS)
                        .isAccountNonLocked(SETTINGS).isCredentialsNonExpired(SETTINGS).isEnabled(SETTINGS).build();
            }
            userRepository.save(rootAdmin);
        };
    }

}
