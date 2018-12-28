/*
 * 
 * EuroRisk Systems (c) Ltd. All rights reserved.
 * 
 */
package com.auth.authsec.domain.user;

import org.junit.After;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Plamen
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserRepositoryTests {
    
    @Autowired
    private UserRepository userRepo;
    
    public UserRepositoryTests() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void shitTest() {
        assertTrue(true);
    }
    
}
