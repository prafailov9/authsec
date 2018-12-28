package com.auth.authsec.domain.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * The Repository for the Role entities. Extends the JpaRepository interface,
 * which is built-in in Spring Data.
 *
 * @author Plamen
 * @see @Repository
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Custom-made query method for finding a role entity by role name.
     *
     * @param roleName - desired role entity's name.
     * @return Role object.
     */
    @Query("SELECT r FROM Role r WHERE LOWER(r.roleName)=LOWER(:roleName)")
    Role findOneByRoleName(@Param(value = "roleName") String roleName);

}
