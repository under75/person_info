package ru.sartfoms.personinfo;

import javax.sql.DataSource;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
	private DataSource dataSource;

	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder authBuilder) throws Exception {
		authBuilder.jdbcAuthentication().dataSource(dataSource).passwordEncoder(new PasswordEncoder() {

			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				return DigestUtils.sha256Hex(rawPassword.toString()).equals(encodedPassword);
			}

			@Override
			public String encode(CharSequence rawPassword) {
				return DigestUtils.sha256Hex(rawPassword.toString());
			}
		}).usersByUsernameQuery("select u_name, u_hash, 1 from BIGADMIN.jpol_users where u_name=?")
				.authoritiesByUsernameQuery("select u_name, u_type from BIGADMIN.jpol_users where u_name=?");
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.formLogin().loginPage("/login").usernameParameter("name").passwordParameter("passwd");
		http.authorizeRequests().antMatchers("/resources/**", "/static/**", "/webjars/**").permitAll().anyRequest()
				.hasAnyAuthority("tfoms").and().formLogin().permitAll().and().logout().permitAll().and()
				.exceptionHandling().accessDeniedPage("/403");
		return http.build();
	}
}
