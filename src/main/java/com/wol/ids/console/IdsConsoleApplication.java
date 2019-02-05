package com.wol.ids.console;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
public class IdsConsoleApplication
{
	private static Logger logger = LoggerFactory.getLogger(IdsConsoleApplication.class);
	
	@Configuration
	@EnableWebSecurity
	@EnableGlobalMethodSecurity(prePostEnabled = true)
	public class SecurityConfiguration extends WebSecurityConfigurerAdapter
	{
		public static final String DEFAULT_ADMIN_USERNAME = "super";
		
		@Value("${SC_DEFAULT_ADMIN_PASSWD}")
		private String defaultAdminPassword = "";
		
		@Override
		protected void configure(HttpSecurity http) throws Exception
		{
			http.authorizeRequests()
			     .antMatchers("/", "/webjars/**", "/register", "/reset/password").permitAll()
			     .anyRequest().authenticated()
			     .and()
			     .formLogin().loginPage("/login").loginProcessingUrl("/doLogin").permitAll()
			     .and()
			     .httpBasic()
			     .and()
			     .logout().logoutUrl("/doLogout").permitAll();
			
			http.csrf().disable();
			http.headers().frameOptions().disable();
		}
		
		public void configure(AuthenticationManagerBuilder builder)
		{
			try
			{
				logger.info("Attempting to build in-memory authentication for default admin user: '{}', pwd: '{}'", DEFAULT_ADMIN_USERNAME, defaultAdminPassword);
				builder.inMemoryAuthentication().withUser(DEFAULT_ADMIN_USERNAME).password(defaultAdminPassword).roles("ADMIN");
//				builder.jdbcAuthentication().getUserDetailsService()
			}
			catch (Exception e)
			{
				logger.error("Failed to initialize JDBC authentification for default user: '{}'", e.getMessage(), e);
			}		
		}
		
	}

	public static void main(String[] args)
	{
		SpringApplication.run(IdsConsoleApplication.class, args);
	}
}
