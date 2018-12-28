package com.auth.authsec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * BSD 3-Clause License
 * 
 * Copyright (c) 2018, Plamen Rumyanov Rafailov, All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 
 * Neither the name of the copyright holder nor the names of its contributors
 * may be used to endorse or promote products derived from this software without
 * specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 * 
 * 
 * @see @SpringBootApplication
 * 
 * @author Plamen Rumyanov Rafailov
 * 
 *         This class is the starting point of the application. Extends The
 *         SpringBootServletInitializer class, which Binds "Servlet", "Filter"
 *         and "ServletContextInitializer" beans from the application context to
 *         the server.
 */
@SpringBootApplication
public class AuthsecApplication extends SpringBootServletInitializer {
	
	/**
	 * Good practice, I guess.
	 */
	public AuthsecApplication() {
		super();
	}

	/**
	 * Main method of the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(AuthsecApplication.class, args);
	}

	/**
	 * @param application
	 * @return
	 */
	protected final SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
		return application.sources(AuthsecApplication.class);
	}

	/**
	 * A Bean, which is loaded in the ApplicationContext on start-up. Used for
	 * encoding user passwords.
	 * 
	 * @see @Bean
	 * @return BCryptPasswordEncoder
	 */
	@Bean
	public PasswordEncoder getBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
