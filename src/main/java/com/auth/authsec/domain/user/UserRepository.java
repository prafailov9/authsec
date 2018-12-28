package com.auth.authsec.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auth.authsec.domain.exceptions.NoSuchUserException;

/**
 * The repository for persisting and managing User entities in the database.
 * Extends a Spring Data JPA interface, which comes with built-in behavior for
 * managing entities. Annotated with @Repository so Spring can know that its a
 * component. The query language used in Spring Data JPA is JPQL(Java
 * Persistence Query Language).
 *
 * @Query annotation allows the creation of native SQL and JPQL queries.
 *
 * @author Plamen
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Custom-made query method for fetching an entity with a given name.
     *
     * @param username - name to search for
     * @return user entity with given username
     */
    @Query("SELECT u FROM User u WHERE LOWER(u.username)=LOWER(:username)")
    User findOneByUsername(@Param(value = "username") String username) throws NoSuchUserException;

}
